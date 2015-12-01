package com.jinlufund.service.crm;

import com.jinlufund.entity.crm.Customer;
import com.jinlufund.entity.crm.Telephone;
import com.jinlufund.exception.JinluException;
import com.jinlufund.repository.crm.TelephoneRepository;
import com.jinlufund.utils.JpaSpecificationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zz on 15/11/29.
 */
@Service
public class TelephoneService {
    @Autowired
    TelephoneRepository telephoneRepository;
    @Autowired
    CustomerService customerService;

    //添加客户电话
    public void addTelephone(Long customerId,Telephone telephone){
        Customer customer = customerService.assertUserExistById(customerId);
        telephone.setCustomerId(customerId);
        telephone.setName(customer.getName());
        telephone.setStatus(Telephone.StatusType.ENABLED);
        telephone.setDeleteOrNot(false);
        telephoneRepository.save(telephone);
    }
    //
    public void deleteById(Long id){
        Telephone telephone = assertTelephoneExist(id);
        if (telephone!=null){
            telephone.setDeleteOrNot(true);
            telephoneRepository.save(telephone);
        }else {
            JinluException exception = new JinluException();
            exception.setErrorCode("telephoneService.telephone.notExist");
            exception.addParameter("id","null");
        }
    }

    public void deleteByCustomerId(Long customerId){
        List<Telephone> telephones = findByCustomerId(customerId);
        for (Telephone telephone :
                telephones) {
            deleteById(telephone.getId());
        }
    }
    private Telephone assertTelephoneExist(Long id){
        Telephone telephone = telephoneRepository.findOne(id);
        return telephone;
    }
    public Telephone update(Long id,Telephone input){
        Telephone telephone = assertTelephoneExist(id);
        telephone.setStatus(input.getStatus());
        telephone.setName(input.getName());
        telephone.setAreaCode(input.getAreaCode());
        telephone.setExtensionNo(input.getExtensionNo());
        telephone.setType(input.getType());
        telephone.setTelephoneNo(input.getTelephoneNo());
        return telephoneRepository.save(telephone);
    }
    public List<Telephone> findByCustomerId(Long customerId){
        Map<String,Object> searchParams = new HashMap<String,Object>();
        searchParams.put("customerId_EQ_INTEGER",customerId);
        Specification<Telephone> specification = new JpaSpecificationUtils().createSpecification(searchParams,Telephone.class);
        List<Telephone> all = telephoneRepository.findAll(specification);
        List<Telephone> telephones = new ArrayList<Telephone>();
        for (Telephone telephone :all) {
            if (!telephone.isDeleteOrNot()){
                telephones.add(telephone);
            }
        }
        return telephones;
    }
}
