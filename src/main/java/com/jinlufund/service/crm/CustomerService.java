package com.jinlufund.service.crm;

import com.jinlufund.entity.crm.Customer;
import com.jinlufund.exception.JinluException;
import com.jinlufund.repository.crm.CustomerRepository;
import com.jinlufund.repository.crm.TelephoneRepository;
import com.jinlufund.utils.JpaSpecificationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CustomerService {
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	TelephoneRepository telephoneRepository;

	//通过 证件号 检测用户是否已经存在
	public Customer assertUserExistByCeNo(String certificateNo){
		Customer customer = customerRepository.findByCertificateNo(certificateNo);
		if(customer != null){
			JinluException e = new JinluException();
			e.setErrorCode("customerService.customer.hasExist");
			e.addParameter("certificateNo", certificateNo);
			throw e;
		}
		return customer;
	}
	public Customer save(Customer input){
		input.setCreateDate(new Date());
		input.setLastUpdate(new Date());
		input.setDeleteOrNot(false);
		Customer customer = customerRepository.save(input);
		return customer;
	}
	public Customer find(Long id){
		Customer customer = customerRepository.findOne(id);
		return customer;
	}

	// TODO: 15/11/28  
	//更新用户信息
	public void update(Long customerId,Customer input){
		Customer customer = assertUserExistById(customerId);
		customer.setName(input.getName());
		customer.setCertificateType(input.getCertificateType());
		customer.setCertificateNo(input.getCertificateNo());
		customer.setLastUpdate(new Date());
		customer.setSex(input.getSex());
		customer.setAddress(input.getAddress());
		customer.setValidityPeriod(input.getValidityPeriod());
		customerRepository.save(customer);
	}
	
	//通过id 检测用户是否存在 不存在 报错
	public Customer assertUserExistById(Long customerId){
		Customer customer = customerRepository.findOne(customerId);
		if(customer == null){
			JinluException e = new JinluException();
			e.setErrorCode("customerService.customer.notExist");
			e.addParameter("customerId", customerId);
			throw e;
		}
		return customer;
	}


	public Page<Customer> search(Map<String, Object> searchParams, Pageable pageable) {
		Specification<Customer> specification = JpaSpecificationUtils.createSpecification(searchParams,Customer.class);
		Page<Customer> page = customerRepository.findAll(specification,pageable);
		return page;
	}

	public void deleteByChoosen(List<Long> ids){
		for (Long id :
				ids) {
			Customer customer = assertUserExistById(id);
			customer.setDeleteOrNot(true);
		}
	}
}
