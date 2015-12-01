package com.jinlufund.service.crm;

import com.jinlufund.entity.crm.Province;
import com.jinlufund.repository.crm.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by zz on 15/11/28.
 */
@Service
public class ProvinceService {
    @Autowired
    ProvinceRepository provinceRepository;

    public ArrayList<Province> getAll() {
        Iterable<Province> provinces = provinceRepository.findAll();
        ArrayList<Province> arrayList = new ArrayList<Province>();
        for (Province p :
                provinces) {
            arrayList.add(p);
        }
        return arrayList;
    }
}
