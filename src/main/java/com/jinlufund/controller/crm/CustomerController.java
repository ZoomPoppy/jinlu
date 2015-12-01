package com.jinlufund.controller.crm;

/**
 * Created by zz on 15/11/27.
 */

import com.jinlufund.entity.crm.*;
import com.jinlufund.entity.uc.User;
import com.jinlufund.exception.JinluException;
import com.jinlufund.protocol.ajax.AjaxFailResponse;
import com.jinlufund.protocol.ajax.AjaxSuccessResponse;
import com.jinlufund.service.crm.*;
import com.jinlufund.service.uc.UserService;
import com.jinlufund.utils.ServletRequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 个人证件信息controller
 * 每一种证件信息都有edit方法 和save方法，
 * 无论是新增还是修改都通过edit方法，在点击证件信息时会调用edit方法，
 * 如果传入id 则会将信息显示给前端，如果不传入则不会显示
 * save方法会分辨是新增还是修改，传入id是修改，不传入是新增
 * @author zz
 */ 

@Controller
@RequestMapping("/crm/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;
    @Autowired
    UserService userService;
    @Autowired
    ContactPersonSerivice contactPersonSerivice;
    @Autowired
    BankcardService bankcardService;
    @Autowired
    TelephoneService telephoneService;
    @Autowired
    CustomerAddressService addressService;
    @Autowired
    AdditionalInfoService additionalInfoService;


    @RequestMapping(value = "/edit",method = RequestMethod.GET)
    public String editCustomerInfo(@RequestParam(required = false)Long customerId, @RequestParam(required = true)Customer.CertificateType certificateType, Model model){
        if (customerId!=null){
            Customer customer = customerService.find(customerId);
            model.addAttribute("item",customer);
        }
        switch (certificateType){
            case ID_CARD:
                return "crm/customer/customer_card";
            case PASSPORT:
                return "crm/customer/customer_passport";
            case MILITARY_ID:
                return "crm/customer/customer_officerCertificate";
            case GAT_PASSPORT:
                return "crm/customer/customer_GATPassport";
            case ORGANIZATIONCODE:
                return "crm/customer/customer_organizationCode";
            case OTHERCARD:
                return "crm/customer/customer_otherCard";
        }
        // TODO: 15/11/28
        //错误页面
        return "error";
    }


    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @ResponseBody
    public String savaCertificateInfo(@RequestParam(required = false)Long customerId,@ModelAttribute Customer input){
        Customer customer = null;
        AjaxSuccessResponse ajaxSuccessResponse = new AjaxSuccessResponse();
        try {
            if (customerId!=null){
                customerService.update(customerId, input);
            }else {
                // TODO: 15/12/01 用户类型状态和用户类型没有加入
                // TODO: 15/11/28 根据证件类型的不同，检测不同字段是否存在
                User user = userService.find(input.getManagerId());
                input.setAgentName(user.getName());
                input.setAgentId(input.getManagerId());
                customer = customerService.save(input);
                ajaxSuccessResponse.setData(customer);
            }
        } catch (JinluException e){
            return new AjaxFailResponse(e.getErrorCode()).toString();
        }
        return ajaxSuccessResponse.toString();
    }

    //根据搜索传回数据
    @RequestMapping(value = "/search")
    public String search(Pageable pageable, ServletRequest request, Model model){
        Map<String,Object> searchParams = com.jinlufund.utils.ServletRequestUtils.getParamsFromRequestWithPrefix(request,"search_");
        System.out.println("searchParams:"+searchParams);
        Page<Customer> page = customerService.search(searchParams,pageable);
        model.addAttribute("page",page);
        model.addAttribute("searchparams",searchParams);
        model.addAttribute("searchParams", searchParams);
        model.addAttribute("searchParamsUrl", ServletRequestUtils.formatParamsWithPrefix(searchParams, "search_"));
        model.addAttribute("sortParamsUrl", "&sort=lastUpdate,desc");
        return "crm/customer/customer_search";
    }
    @RequestMapping(value = "/getAllInfo")
    public String getAllInfo(@RequestParam(required = false)Long customerId,Model model){
        try {
            if (customerId != null){
                Customer customer = customerService.find(customerId);
                model.addAttribute("customer",customer);
                ContactPerson contactPerson= contactPersonSerivice.findByCustomerId(customerId);
                model.addAttribute("contactPerson",contactPerson);
                AdditionalInfo additionalInfo= additionalInfoService.findByCustomerId(customerId);
                model.addAttribute("additionalInfo",additionalInfo);
                List<Bankcard> bankcards = bankcardService.findByCustomerId(customerId);
                model.addAttribute("bankCards",bankcards);
                List<Telephone> telephones = telephoneService.findByCustomerId(customerId);
                model.addAttribute("telephones",telephones);
                List<Address> addressList = addressService.findByCustmerId(customerId);
                model.addAttribute("addressList",addressList);
            }else {
                JinluException exception = new JinluException();
                exception.setErrorCode("customerController.getAllInfo.customerId.notExist");
                exception.addParameter("customerId","null");
                throw exception;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        return "crm/customer/customer_checkInformaition";

    }
}
