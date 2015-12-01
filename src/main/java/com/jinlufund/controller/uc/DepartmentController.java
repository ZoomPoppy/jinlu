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

import com.jinlufund.entity.uc.Department;
import com.jinlufund.entity.uc.DepartmentCategory;
import com.jinlufund.exception.JinluException;
import com.jinlufund.protocol.ajax.AjaxFailResponse;
import com.jinlufund.protocol.ajax.AjaxSuccessResponse;
import com.jinlufund.service.uc.DepartmentCategoryService;
import com.jinlufund.service.uc.DepartmentService;

@Controller
@RequestMapping("uc/department")
public class DepartmentController {

	@Autowired
	DepartmentService departmentService;
	
	@Autowired
	DepartmentCategoryService departmentCategoryService;
	
	@RequestMapping("")
	public String index(Pageable pageable, Model model) {
		return "redirect:/uc/department/tree";
	}
	
	@RequestMapping(value = "/tree", method = RequestMethod.GET)
	public String tree(@RequestParam(required = false) Long currentDepartmentId, Model model) {
		String jsonData = departmentService.getJsonTree(true);
		model.addAttribute("jsonData", jsonData);
		model.addAttribute("currentDepartmentId", currentDepartmentId);
		return "uc/department/department_tree";
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String item(@PathVariable Long id) {
		Department department = departmentService.find(id);
		Department mirror = new Department(); // 防止生成JSON的循环依赖。
		mirror.setId(department.getId());
		mirror.setName(department.getName());
		mirror.setDescription(department.getDescription());
		return new AjaxSuccessResponse(mirror).toString();
	}
	
	@RequestMapping(value = "/refresh", method = RequestMethod.GET)
	public String refresh(Model model) {
		departmentService.refresh();
		return "redirect:/uc/department/tree";
	}

	@RequestMapping(value = "/addChildEdit", method = RequestMethod.GET)
	public String addChildEdit(@RequestParam Long parentId, Model model) {
		Department parent = departmentService.find(parentId);
		model.addAttribute("parentId", parent.getId());
		Iterable<DepartmentCategory> departmentCategories = departmentCategoryService.findAll();
		model.addAttribute("departmentCategories", departmentCategories);
		return "uc/department/department_addChildEdit";
	}

	@RequestMapping(value = "/addChild", method = RequestMethod.POST)
	@ResponseBody
	public String addChild(@RequestParam Long parentId, @ModelAttribute Department input) {
		Department department = null;
		try {
			department = departmentService.addChild(parentId, input);
		} catch (JinluException e) {
			return new AjaxFailResponse(e.getErrorCode()).toString();
		}
		return new AjaxSuccessResponse(department.getId()).toString();
	}
	
	@RequestMapping(value = "/updateEdit", method = RequestMethod.GET)
	public String updateEdit(@RequestParam Long id, Model model) {
		if (id != null) {
			Department department = departmentService.find(id);
			model.addAttribute("item", department);
		}
		Iterable<DepartmentCategory> departmentCategories = departmentCategoryService.findAll();
		model.addAttribute("departmentCategories", departmentCategories);
		return "uc/department/department_updateEdit";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@RequestParam Long id, @ModelAttribute Department input) {
		try {
			departmentService.update(id, input);
		} catch (JinluException e) {
			return new AjaxFailResponse(e.getErrorCode()).toString();
		}
		return new AjaxSuccessResponse().toString();
	}
	
	@RequestMapping(value = "/deleteConfirm", method = RequestMethod.GET)
	public String deleteConfirm(@RequestParam Long id, Model model) {
		Department department = departmentService.find(id);
		model.addAttribute("item", department);
		return "uc/department/department_deleteConfirm";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public String delete(@RequestParam Long id) {
		try {
			departmentService.delete(id);
		} catch (JinluException e) {
			return new AjaxFailResponse(e.getErrorCode()).toString();
		}
		return new AjaxSuccessResponse().toString();
	}
	
}
