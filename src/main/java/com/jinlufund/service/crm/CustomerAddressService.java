package com.jinlufund.service.crm;

import com.jinlufund.entity.crm.Address;
import com.jinlufund.exception.JinluException;
import com.jinlufund.repository.crm.CustomerAddressRepository;
import com.jinlufund.utils.JpaSpecificationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zz on 15/12/01.
 */
@Service
public class CustomerAddressService {
    @Autowired
    CustomerAddressRepository addressRepository;
    public void save(Long id,Address address){
        address.setCustomerId(id);
        address.setDeleteOrNot(false);
        addressRepository.save(address);
    }
    public void update(Long customerId, Address input){
        Address address = assertAddressExistById(input.getId());
        address.setCityId(input.getCityId());
        address.setUsage(input.getUsage());
        address.setType(input.getType());
        address.setProvinceId(input.getProvinceId());
        address.setDistrictId(input.getDistrictId());
        address.setStreet(input.getStreet());
        address.setDetail(input.getDetail());
        address.setPostcode(input.getPostcode());
        address.setStatus(input.getStatus());
        addressRepository.save(address);
    }
    public void delete(Address input){
        assertAddressExistById(input.getId());
        Address address = assertAddressExistById(input.getId());
        address.setDeleteOrNot(true);
        addressRepository.save(address);
    }
    public void deleteByCustomerId(Long customerId){
        List<Address> all = findByCustmerId(customerId);
        for (Address a :
                all) {
            delete(a);
        }
    }
    public Address assertAddressExistById(Long id){
        Address one = addressRepository.findOne(id);
        if (one == null){
            JinluException exception = new JinluException();
            exception.setErrorCode("customerAddressServce.customerid.notExist");
            exception.addParameter("id",null);
            throw exception;
        }
        return one;
    }
    public List<Address> findByCustmerId(Long customerId){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("customerId_EQ_INTEGER",customerId);
        Specification<Address> specification = JpaSpecificationUtils.createSpecification(map, Address.class);
        List<Address> all = addressRepository.findAll(specification);
        List<Address> addresses = new ArrayList<Address>();
        for (Address a :
                all) {
            if (a.isDeleteOrNot()){
                addresses.add(a);
            }
        }
        return addresses;
    }

}
