package com.jinlufund.service.crm;

import com.jinlufund.entity.crm.City;
import com.jinlufund.repository.crm.CityRepository;
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
public class CityServince {
    @Autowired
    CityRepository cityRepository;

    public ArrayList<City> findCitysByProvinceId(Long provinceId) {
        Map<String,Object> searchParams = new HashMap<String,Object>();
        searchParams.put("provinceId_EQ_INTEGER",provinceId);
        Specification<City> specification = JpaSpecificationUtils.createSpecification(searchParams,City.class);
        ArrayList<City> arrayList = (ArrayList<City>) cityRepository.findAll(specification);
        return  arrayList;
    }
}
