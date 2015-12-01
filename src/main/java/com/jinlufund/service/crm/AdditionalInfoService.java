package com.jinlufund.service.crm;

import com.jinlufund.entity.crm.AdditionalInfo;
import com.jinlufund.entity.uc.User;
import com.jinlufund.exception.JinluException;
import com.jinlufund.repository.crm.AdditionalInfoRepository;
import com.jinlufund.service.uc.UserService;
import com.jinlufund.utils.JpaSpecificationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zz on 15/11/28.
 */

// TODO: 15/12/01
@Service
public class AdditionalInfoService {
    @Autowired
    AdditionalInfoRepository additionalInfoRepository;
    @Autowired
    UserService userService;

    public AdditionalInfo update(Long customerId, AdditionalInfo input) {
        AdditionalInfo additionalInfo = assertInfoExitByCustomerId(customerId);
        additionalInfo.setEmail(input.getEmail());
        additionalInfo.setIsMarry(input.getIsMarry());
        additionalInfo.setWorkUnit(input.getWorkUnit());
        additionalInfo.setJob(input.getJob());
        additionalInfo.setTheSupplementaryPhone(input.getTheSupplementaryPhone());
        additionalInfo.setCustomerFrom(input.getCustomerFrom());
        additionalInfo.setSupervisor(input.getSupervisor());
        additionalInfoRepository.save(additionalInfo);
        return additionalInfo;
    }
    public AdditionalInfo save(AdditionalInfo additionalInfo,Long managerId){
        User user = userService.find(managerId);
        additionalInfo.setAgentName(user.getName());
        return additionalInfoRepository.save(additionalInfo);
    }
    public void updateOrSave(Long customerId, AdditionalInfo input,Long managerId) {
        AdditionalInfo additionalInfo = assertInfoExitByCustomerId(customerId);
        if (additionalInfo!=null){
            update(customerId,input);
        }else {
            save(input,managerId);
        }
    }

    public AdditionalInfo assertInfoExitByCustomerId(Long customerId){
        AdditionalInfo additionalInfo = findByCustomerId(customerId);
        if (additionalInfo==null){
            JinluException exception = new JinluException();
            exception.setErrorCode("additionalInfoService.additional.NotExist");
            exception.addParameter("additional","null");
            throw exception;
        }
        return additionalInfo;
    }
    public void deleteByCustomerId(Long customerId){
        Map<String,Object> searchParams = new HashMap<String,Object>();
        searchParams.put("customerId_EQ_INTEGER",customerId);
        Specification<AdditionalInfo> specification = JpaSpecificationUtils.createSpecification(searchParams,AdditionalInfo.class);
        List<AdditionalInfo> all = additionalInfoRepository.findAll(specification);
        for (AdditionalInfo additionalInfo :
                all) {
            additionalInfo.setDeleteOrNot(true);
        }
    }
    public void deleteById(Long id){
        AdditionalInfo additionalInfo = assertInfoExitByCustomerId(id);
        additionalInfo.setDeleteOrNot(true);
        update(id,additionalInfo);
    }




    public AdditionalInfo findByCustomerId(Long customerId){
        Map<String,Object> searchParas = new HashMap<String,Object>();
        searchParas.put("customerId_EQ_INTEGER",customerId);
        Specification<AdditionalInfo> specification = JpaSpecificationUtils.createSpecification(searchParas,AdditionalInfo.class);
        AdditionalInfo one = additionalInfoRepository.findOne(specification);
        if (one==null){
            return null;
        }else if (one.isDeleteOrNot()){
            return null;
        }
        return one;
    }
}

