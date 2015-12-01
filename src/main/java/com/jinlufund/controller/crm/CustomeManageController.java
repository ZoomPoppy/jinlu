package com.jinlufund.controller.crm;

import com.jinlufund.entity.crm.AdditionalInfo;
import com.jinlufund.entity.crm.Bankcard;
import com.jinlufund.entity.crm.ContactPerson;
import com.jinlufund.entity.crm.Customer;
import com.jinlufund.exception.JinluException;
import com.jinlufund.protocol.ajax.AjaxFailResponse;
import com.jinlufund.protocol.ajax.AjaxSuccessResponse;
import com.jinlufund.service.crm.AdditionalInfoService;
import com.jinlufund.service.crm.BankcardService;
import com.jinlufund.service.crm.ContactPersonSerivice;
import com.jinlufund.service.crm.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 该控制器是客户管理主页的页面控制器，点击新增和编辑将会进入该页面
 * 如果是编辑，将会传入 customerId  并且会在主页上显示紧急联系人信息和补充信息
 * 如果是新增，将不会传入customerId，只有在个人证件信息写完之后才能进行下面的信息填充。
 * 同时个人信息写完之后将会返回一个customerId给前端，前端在写之后的信息时候，将会传入该id
 *
 * CustomerManager主页面，其中在添加额外信息时，会传入managerId，此时需要在additionalInfo中添加字段
 *
 * */
@Controller
@RequestMapping("crm/customer")
public class CustomeManageController {
	@Autowired
	CustomerService customerService;
	@Autowired
	ContactPersonSerivice contactPersonSerivice;
	@Autowired
	AdditionalInfoService additionalInfoService;
	@Autowired
	BankcardService bankcardService;


	//初始化页面
	@RequestMapping(value = "",method = RequestMethod.GET)
	public String index(@RequestParam(required = false) Long customerId, Model model){
		if (customerId != null){
			ContactPerson contactPerson = contactPersonSerivice.findByCustomerId(customerId);
			model.addAttribute("contactPerson",contactPerson);
			AdditionalInfo additionalInfo = additionalInfoService.findByCustomerId(customerId);
			model.addAttribute("additionalInfo",additionalInfo);
			List<Bankcard> bankcards = bankcardService.findByCustomerId(customerId);
			model.addAttribute("bankcards",bankcards);
		}
		return "crm/customer/customer_information";
	}

	//保存紧急联系人信息,必须先写证件信息，id不能为空
	@RequestMapping(value = "/saveContactPersonInfo",method = RequestMethod.POST)
	@ResponseBody
	public String saveContactPersonInfo(@RequestParam(required = false)Long customerId,@ModelAttribute ContactPerson contactPerson){
		try{
			if (customerId != null){
				// TODO: 15/11/28
				Customer customer = customerService.assertUserExistById(customerId);
				contactPersonSerivice.updateOrSave(customerId,contactPerson);
			}else {
				JinluException e = new JinluException();
				e.setErrorCode("customerController.saveContactPersonInfo.idNotExist");
				e.addParameter("id","null");
				throw e;
			}
		}catch (JinluException e){
			return new AjaxFailResponse(e.getErrorCode()).toString();
		}
		return new AjaxSuccessResponse().toString();
	}


	//保存补充信息,由于additionalInfo中的name指定之后将不能更改,每次
	@RequestMapping(value = "/saveAdditionalInfo", method = RequestMethod.POST)
	@ResponseBody
	public String saveAdditionalInfo(@RequestParam(required = false) Long customerId,@RequestParam(required = false)Long managerId, @ModelAttribute AdditionalInfo input) {
		try {
			if (customerId != null){
				if (managerId!=null){
					additionalInfoService.updateOrSave(customerId,input,managerId);
				}else {
					JinluException e = new JinluException();
					e.setErrorCode("customerController.saveAdditionalInfo.ManagerId.notExist");
					e.addParameter("managerId","null");
					throw e;
				}
			}else {
				JinluException e = new JinluException();
				e.setErrorCode("customerController.saveAdditionalInfo.idNotExist");
				e.addParameter("customerId","nul");
				throw e;
			}
		}catch (JinluException e){
			return new AjaxFailResponse(e.getErrorCode()).toString();
		}
		return new AjaxSuccessResponse().toString();
	}
}
