package com.jinlufund.service.crm;

import com.jinlufund.entity.crm.District;
import com.jinlufund.repository.crm.DistrictRepository;
import com.jinlufund.utils.JpaSpecificationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zz on 15/11/28.
 */
@Service
public class DistrictService {
    @Autowired
    DistrictRepository districtRepository;

    public ArrayList<District> findDistrictByCityId(Long cityId) {
        Map<String,Object> searchParam = new HashMap<String,Object>();
        searchParam.put("cityId_EQ_INTEGER",cityId);
        Specification<District> specification = JpaSpecificationUtils.createSpecification(searchParam,District.class);
        ArrayList<District> all = (ArrayList<District>) districtRepository.findAll(specification);
        return  all;
    }
}
