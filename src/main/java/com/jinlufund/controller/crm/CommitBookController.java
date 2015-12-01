package com.jinlufund.controller.crm;

import com.jinlufund.entity.crm.CommitBook;
import com.jinlufund.entity.crm.Customer;
import com.jinlufund.exception.JinluException;
import com.jinlufund.protocol.ajax.AjaxFailResponse;
import com.jinlufund.protocol.ajax.AjaxSuccessResponse;
import com.jinlufund.service.crm.CommitBookService;
import com.jinlufund.service.crm.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Created by zz on 15/11/28.
 */
@Controller
@RequestMapping(value = "/crm/customer")
public class CommitBookController {
    @Autowired
    CommitBookService commitBookService;
    @Autowired
    CustomerService  customerService;
    @RequestMapping(value = "/editCommitBook",method = RequestMethod.GET)
    public String editCommitBook(@RequestParam(required = false)Long customerId, Model model){
        try {
            if (customerId == null){
                JinluException exception = new JinluException();
                exception.setErrorCode("editCommitBook.costumer.notExist");
                exception.addParameter("id","null");
                throw exception;
            }else {
                Customer customer = customerService.find(customerId);
                CommitBook commitBook = commitBookService.findById(customerId);
                model.addAttribute("commitBook",commitBook);
                model.addAttribute("customer",customer);
            }
        } catch (JinluException e) {
            // TODO: 15/11/30  
            e.printStackTrace();
            return "error";
        }
        return "crm/customer/customer_commitmentBookInformation";
    }

    @RequestMapping(value = "/saveCommitBook",method = RequestMethod.POST)
    @ResponseBody
    public String saveCommitBook(@RequestParam(required = false)Long customerId, @ModelAttribute CommitBook commitBook){
        try{
            if (customerId == null){
                JinluException exception = new JinluException();
                exception.setErrorCode("CommitBookCotroller.customerId.notExist");
                exception.addParameter("id","null");
                throw exception;
            }else{
                Customer customer = customerService.assertUserExistById(customerId);
                commitBookService.updateOrSave(customerId,commitBook);
            }
        }catch (JinluException  e){
            return new AjaxFailResponse(e.getErrorCode()).toString();
        }

        return new AjaxSuccessResponse().toString();
    }

}
