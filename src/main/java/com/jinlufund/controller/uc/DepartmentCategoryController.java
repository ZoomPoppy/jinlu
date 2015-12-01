package com.jinlufund.controller.uc;

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

import com.jinlufund.entity.uc.DepartmentCategory;
import com.jinlufund.exception.JinluException;
import com.jinlufund.protocol.ajax.AjaxFailResponse;
import com.jinlufund.protocol.ajax.AjaxSuccessResponse;
import com.jinlufund.service.uc.DepartmentCategoryService;

@Controller
@RequestMapping("uc/departmentCategory")
public class DepartmentCategoryController {

	@Autowired
	DepartmentCategoryService departmentCategoryService;
	
	@RequestMapping("")
	public String index(Pageable pageable, Model model) {
		Page<DepartmentCategory> page = departmentCategoryService.findAll(pageable);
		model.addAttribute("page", page);
		return "uc/departmentCategory/departmentCategory_list";
	}
	
	@RequestMapping(value = "/refresh", method = RequestMethod.GET)
	public String refresh(Model model) {
		departmentCategoryService.refresh();
		return "redirect:/uc/departmentCategory";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(@RequestParam(required = false) Long id, Model model) {
		if (id != null) {
			DepartmentCategory departmentCategory = departmentCategoryService.find(id);
			model.addAttribute("item", departmentCategory);
		}
		return "uc/departmentCategory/departmentCategory_edit";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public String save(@RequestParam(required = false) Long id, @ModelAttribute DepartmentCategory input) {
		try {
			if (id != null) {
				departmentCategoryService.update(id, input);
			} else {
				departmentCategoryService.add(input);
			}
		} catch (JinluException e) {
			return new AjaxFailResponse(e.getErrorCode()).toString();
		}
		return new AjaxSuccessResponse().toString();
	}
	
	@RequestMapping(value = "/deleteConfirm", method = RequestMethod.GET)
	public String deleteConfirm(@RequestParam Long id, Model model) {
		DepartmentCategory departmentCategory = departmentCategoryService.find(id);
		model.addAttribute("item", departmentCategory);
		return "uc/departmentCategory/departmentCategory_deleteConfirm";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public String delete(@RequestParam Long id) {
		try {
			departmentCategoryService.delete(id);
		} catch (JinluException e) {
			return new AjaxFailResponse(e.getErrorCode()).toString();
		}
		return new AjaxSuccessResponse().toString();
	}
}
