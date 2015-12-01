package com.jinlufund.controller.crm;

import com.jinlufund.entity.crm.City;
import com.jinlufund.entity.crm.District;
import com.jinlufund.entity.crm.Province;
import com.jinlufund.protocol.ajax.AjaxSuccessResponse;
import com.jinlufund.service.crm.CityServince;
import com.jinlufund.service.crm.DistrictService;
import com.jinlufund.service.crm.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

/**
 * Created by zz on 15/11/28.
 */
@Controller
@RequestMapping(value = "/crm")
public class AddressController {
    @Autowired
    ProvinceService provinceService;
    @Autowired
    CityServince cityServince;
    @Autowired
    DistrictService districtService;

    @RequestMapping(value = "/getProvince",method = RequestMethod.POST)
    @ResponseBody
    public String getProvince(){
        AjaxSuccessResponse ajaxSuccessResponse = new AjaxSuccessResponse();
        ArrayList<Province> provinces = provinceService.getAll();
        ajaxSuccessResponse.setData(provinces);
        return ajaxSuccessResponse.toString();
    }
    @RequestMapping(value = "/getCity",method = RequestMethod.POST)
    @ResponseBody
    public String getCity(@RequestParam(required = true) Long provinceId){
        AjaxSuccessResponse ajaxSuccessResponse = new AjaxSuccessResponse();
        ArrayList<City> citysByProvinceId = cityServince.findCitysByProvinceId(provinceId);
        ajaxSuccessResponse.setData(citysByProvinceId);
        return ajaxSuccessResponse.toString();
    }
    @RequestMapping(value = "/getDistrict",method = RequestMethod.POST)
    @ResponseBody
    public String getDistrict(@RequestParam(required = true) Long cityId){
        AjaxSuccessResponse ajaxSuccessResponse = new AjaxSuccessResponse();
        ArrayList<District> arrayList = districtService.findDistrictByCityId(cityId);
        ajaxSuccessResponse.setData(arrayList);
        return ajaxSuccessResponse.toString();
    }
}
