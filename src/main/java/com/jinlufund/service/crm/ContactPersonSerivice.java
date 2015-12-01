package com.jinlufund.service.crm;

import com.jinlufund.entity.crm.ContactPerson;
import com.jinlufund.exception.JinluException;
import com.jinlufund.repository.crm.ContactPersonRepository;
import com.jinlufund.repository.crm.CustomerRepository;
import com.jinlufund.utils.JpaSpecificationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zz on 15/11/27.
 */
@Service
public class ContactPersonSerivice {
    @Autowired
    ContactPersonRepository contactPersonRepository;
    @Autowired
    CustomerRepository customerRepository;
    //TODO
    public void updateOrSave(Long customerId,ContactPerson input){
        ContactPerson contactPerson = assertHasExistByCustomerId(customerId);
        if (contactPerson!=null){
            update(customerId,input);
        }else{
            save(input);
        }
    }
    public void save(ContactPerson contactPerson){
        contactPersonRepository.save(contactPerson);
    }

    public void update(Long customerId,ContactPerson input){
        ContactPerson contactPerson = assertHasExistByCustomerId(customerId);
        contactPerson.setName(input.getName());
        contactPerson.setZipCode(input.getZipCode());
        contactPerson.setWorkUnit(input.getWorkUnit());
        contactPerson.setPhoneNum(input.getPhoneNum());
        contactPerson.setSuppleMentaryPhone(input.getSuppleMentaryPhone());
        contactPerson.setContactAddress(input.getContactAddress());
        contactPerson.setEmail(input.getEmail());
        contactPerson.setRelationship(input.getRelationship());
        contactPersonRepository.save(contactPerson);
    }

    private ContactPerson assertHasExistByCustomerId(Long customerId){
        ContactPerson contactPerson = findByCustomerId(customerId);
        if (contactPerson == null){
            JinluException exception = new JinluException();
            exception.setErrorCode("contactPersonService.customerId.notExist");
            exception.addParameter("customerId","null");
            throw exception;
        }
        return contactPerson;
    }
    public  ContactPerson findByCustomerId(Long customerId){
        Map<String,Object> searchParas = new HashMap<String,Object>();
        searchParas.put("customerId_EQ_INTEGER",customerId);
        Specification<ContactPerson> specification = JpaSpecificationUtils.createSpecification(searchParas,ContactPerson.class);
        ContactPerson one = contactPersonRepository.findOne(specification);
        if (one==null){
            return null;
        }else if (one.isDeleteOrNot()){
            return null;
        }
        return one;
    }
}
