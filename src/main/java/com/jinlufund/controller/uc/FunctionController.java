package com.jinlufund.controller.uc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jinlufund.entity.uc.Function;
import com.jinlufund.entity.uc.Function.FunctionType;
import com.jinlufund.entity.uc.SystemType;
import com.jinlufund.exception.JinluException;
import com.jinlufund.protocol.ajax.AjaxFailResponse;
import com.jinlufund.protocol.ajax.AjaxSuccessResponse;
import com.jinlufund.service.uc.FunctionService;

@Controller
@RequestMapping("uc/function")
public class FunctionController {

	@Autowired
	private FunctionService functionService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String index(Pageable pageable, Model model) {
		return "redirect:/uc/function/tree";
	}

	@RequestMapping("/tree")
	public String tree(@RequestParam(required = false) Long currentFunctionId, @RequestParam(required = false) String currentSystem, Model model) {
		String jsonData = functionService.getFunctionTree();
		model.addAttribute("jsonData", jsonData);
		model.addAttribute("currentFunctionId", currentFunctionId);
		model.addAttribute("currentSystem", currentSystem);
		return "uc/function/function_tree";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String item(@PathVariable Long id) {
		Function function = functionService.find(id);
		Function mirror = new Function(); // 防止生成JSON的循环依赖。
		mirror.setId(function.getId());
		mirror.setName(function.getName());
		mirror.setDescription(function.getDescription());
		mirror.setType(function.getType());
		mirror.setSystem(function.getSystem());
		mirror.setOrderNo(function.getOrderNo());
		mirror.setUrl(function.getUrl());
		return new AjaxSuccessResponse(mirror).toString();
	}

	@RequestMapping("/refresh")
	public String refresh() {
		functionService.refresh();
		return "redirect:/uc/function/tree";
	}

	@RequestMapping(value = "/addModuleEdit", method = RequestMethod.GET)
	public String addModuleEdit(@RequestParam SystemType system, Model model) {
		model.addAttribute("system", system);
		return "uc/function/function_addModuleEdit";
	}

	@RequestMapping(value = "/addModule", method = RequestMethod.POST)
	@ResponseBody
	public String addModule(@RequestParam SystemType system, @ModelAttribute Function input) {
		Function function = null;
		try {
			function = functionService.addChild(system, null, input);
		} catch (JinluException e) {
			return new AjaxFailResponse(e.getErrorCode()).toString();
		}
		return new AjaxSuccessResponse(function.getId()).toString();
	}

	@RequestMapping(value = "/addChildEdit", method = RequestMethod.GET)
	public String addChildEdit(@RequestParam Long parentId, Model model) {
		Function parent = functionService.find(parentId);
		SystemType parentSystem = parent.getSystem();
		FunctionType parentType = parent.getType();
		FunctionType type = null;
		Function module = null;
		Function page = null;
		if (parentType == FunctionType.MODULE) {
			type = FunctionType.PAGE;
			module = parent;
		} else if (parentType == FunctionType.PAGE) {
			type = FunctionType.BUTTON;
			page = parent;
			module = parent.getParent();
		}
		model.addAttribute("parentId", parent.getId());
		model.addAttribute("system", parentSystem);
		model.addAttribute("type", type);
		model.addAttribute("module", module);
		model.addAttribute("page", page);
		return "uc/function/function_addChildEdit";
	}

	@RequestMapping(value = "/addChild", method = RequestMethod.POST)
	@ResponseBody
	public String addChild(@RequestParam Long parentId, @ModelAttribute Function input) {
		Function function = null;
		try {
			function = functionService.addChild(null, parentId, input);
		} catch (JinluException e) {
			return new AjaxFailResponse(e.getErrorCode()).toString();
		}
		return new AjaxSuccessResponse(function.getId()).toString();
	}

	@RequestMapping(value = "/updateEdit", method = RequestMethod.GET)
	public String updateEdit(@RequestParam Long id, Model model) {
		if (id != null) {
			Function function = functionService.find(id);
			model.addAttribute("item", function);
		}
		return "uc/function/function_updateEdit";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@RequestParam Long id, @ModelAttribute Function input) {
		try {
			functionService.update(id, input);
		} catch (JinluException e) {
			return new AjaxFailResponse(e.getErrorCode()).toString();
		}
		return new AjaxSuccessResponse().toString();
	}

	@RequestMapping(value = "/deleteConfirm", method = RequestMethod.GET)
	public String deleteConfirm(@RequestParam Long id, Model model) {
		Function function = functionService.find(id);
		model.addAttribute("item", function);
		return "uc/function/function_deleteConfirm";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public String delete(@RequestParam Long id) {
		try {
			functionService.delete(id);
		} catch (JinluException e) {
			return new AjaxFailResponse(e.getErrorCode()).toString();
		}
		return new AjaxSuccessResponse().toString();
	}

}
