package com.jinlufund.controller.uc;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jinlufund.entity.uc.Role;
import com.jinlufund.entity.uc.SystemType;
import com.jinlufund.exception.JinluException;
import com.jinlufund.protocol.ajax.AjaxFailResponse;
import com.jinlufund.protocol.ajax.AjaxSuccessResponse;
import com.jinlufund.service.uc.FunctionService;
import com.jinlufund.service.uc.RoleService;
import com.jinlufund.utils.StringUtils;

@Controller
@RequestMapping("uc/role")
public class RoleController {

	@Autowired
	FunctionService functionService;
	
	@Autowired
	RoleService roleService;
	
	@RequestMapping("")
	public String index(Pageable pageable, Model model) {
		Page<Role> page = roleService.findAll(pageable);
		model.addAttribute("page", page);
		return "uc/role/role_list";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(@RequestParam(required = false) Long id, Model model) {
		model.addAttribute("systemTypes", SystemType.values());
		if (id != null) {
			Role role = roleService.find(id);
			model.addAttribute("item", role);
		}
		return "uc/role/role_edit";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public String save(@RequestParam(required = false) Long id, @ModelAttribute Role input) {
		try {
			if (id != null) {
				roleService.update(id, input);
			} else {
				roleService.add(input);
			}
		} catch (JinluException e) {
			return new AjaxFailResponse(e.getErrorCode()).toString();
		}
		return new AjaxSuccessResponse().toString();
	}
	
	@RequestMapping(value = "/editFunction", method = RequestMethod.GET)
	public String editFunction(@RequestParam Long id, Model model) {
		Role role = roleService.find(id);
		model.addAttribute("item", role);
		List<Long> functionIdList = role.getFunctionIds();
		String functionIds = StringUtils.join(functionIdList, ",");
		model.addAttribute("functionIds", functionIds);
		SystemType systemType = role.getSystem();
		String functionJson = functionService.getFunctionTree(systemType);
		model.addAttribute("functionJson", functionJson);
		return "uc/role/role_editFunction";
	}
	
	@RequestMapping(value = "/saveFunction", method = RequestMethod.POST)
	@ResponseBody
	public String saveFunction(@RequestParam Long id, @RequestParam String functionIds, Model model) {
		String[] functionIdArray = functionIds.split(",");
		List<Long> functionIdList = new ArrayList<Long>();
		for (String s : functionIdArray) {
			Long functionId = Long.valueOf(s);
			functionIdList.add(functionId);
		}
		try {
			roleService.updateFunction(id, functionIdList);
		} catch (JinluException e) {
			return new AjaxFailResponse(e.getErrorCode()).toString();
		}
		return new AjaxSuccessResponse().toString();
	}
	
	@RequestMapping(value = "/deleteConfirm", method = RequestMethod.GET)
	public String deleteConfirm(@RequestParam Long id, Model model) {
		Role role = roleService.find(id);
		model.addAttribute("item", role);
		return "uc/role/role_deleteConfirm";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public String delete(@RequestParam Long id) {
		try {
			roleService.delete(id);
		} catch (JinluException e) {
			return new AjaxFailResponse(e.getErrorCode()).toString();
		}
		return new AjaxSuccessResponse().toString();
	}
	
}
