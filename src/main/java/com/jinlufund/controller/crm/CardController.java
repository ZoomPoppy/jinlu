package com.jinlufund.controller.crm;

import com.jinlufund.entity.crm.Bankcard;
import com.jinlufund.entity.crm.Customer;
import com.jinlufund.exception.JinluException;
import com.jinlufund.protocol.ajax.AjaxFailResponse;
import com.jinlufund.protocol.ajax.AjaxSuccessResponse;
import com.jinlufund.service.crm.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 银行卡信息
 * 银行卡信息controller
 * @author zz
 *
 */
@Controller
@RequestMapping("crm/customer")
public class CardController {
	@Autowired
	CustomerService customerService;
	@Autowired
	BankcardService bankcardService;
	@Autowired
	ProvinceService provinceService;
	@Autowired
	CityServince cityServince;
	@Autowired
	DistrictService districtService;


	// TODO: 15/11/27  返回信息可能有误 返回

	@RequestMapping(value = "/addOrEditBankCard",method = RequestMethod.GET)
	public String addBankCard(@RequestParam(required = false) Long customerId,@RequestParam(required = false)String accountNo, Model model){
		try {
			if (customerId==null){
                JinluException jinluException = new JinluException();
                jinluException.setErrorCode("cardController.customerId.notExist");
                jinluException.addParameter("id","null");
                throw jinluException;
            }else {
				if (accountNo != null){
					Bankcard bankcard = bankcardService.findByAccountNo(accountNo);
					model.addAttribute(bankcard);
				}
			}
		} catch (JinluException e) {
			e.printStackTrace();
			return "error";
		}
		return "crm/customer/customer_bankCard";
	}

	@RequestMapping(value = "/saveBankCard",method = RequestMethod.POST)
	@ResponseBody
	public String saveBankCard(@RequestParam(required = false) Long customerId,@ModelAttribute Bankcard bankCard){
		try {
			if (customerId == null){
				JinluException jinluException = new JinluException();
				jinluException.setErrorCode("cardController.id.notExist");
				jinluException.addParameter("id","null");
				throw jinluException;
			}else {
				Customer customer = customerService.find(customerId);
				bankcardService.add(customer,bankCard);
			}
		} catch (JinluException e) {
			return new AjaxFailResponse(e.getErrorCode()).toString();
		}
		return new AjaxSuccessResponse().toString();
	}

	// TODO: 15/11/28 暂时没有存折这个Entity类
}
