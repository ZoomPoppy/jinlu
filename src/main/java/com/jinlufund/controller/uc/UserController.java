package com.jinlufund.controller.uc;

import com.jinlufund.entity.uc.User;
import com.jinlufund.exception.JinluException;
import com.jinlufund.protocol.ajax.AjaxFailResponse;
import com.jinlufund.protocol.ajax.AjaxSuccessResponse;
import com.jinlufund.service.uc.*;
import com.jinlufund.utils.ServletRequestUtils;
import com.jinlufund.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("uc/user")
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
	RankService rankService;
	
	@Autowired
	DepartmentService departmentService;
	
	@Autowired
	RoleService roleService;
	
	@Autowired
	FunctionService functionService;

	@RequestMapping("")
	public String index(Pageable pageable, Model model) {
		return "redirect:/uc/user/search";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(@RequestParam(required = false) Long id, Model model) {
		model.addAttribute("statusTypes", User.StatusType.values());
		if (id != null) {
			User user = userService.find(id);
			model.addAttribute("item", user);
		}
		String rankJson = rankService.getJsonTree(false);
		model.addAttribute("rankJson", rankJson);
		String departmentJson = departmentService.getJsonTree(false);
		model.addAttribute("departmentJson", departmentJson);
		return "uc/user/user_edit";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public String save(@RequestParam(required = false) Long id, @ModelAttribute User input) {
		try {
			if (id != null) {
				userService.update(id, input);
			} else {
				userService.add(input);
			}
		} catch (JinluException e) {
			return new AjaxFailResponse(e.getErrorCode()).toString();
		}
		return new AjaxSuccessResponse().toString();
	}
	
	@RequestMapping(value = "/editPassword", method = RequestMethod.GET)
	public String editPassword(@RequestParam(required = false) Long id, Model model) {
		if (id != null) {
			User user = userService.find(id);
			model.addAttribute("item", user);
		}
		return "uc/user/user_editPassword";
	}
	
	@RequestMapping(value = "/savePassword", method = RequestMethod.POST)
	@ResponseBody
	public String savePassword(@RequestParam Long id, @RequestParam String originPassword, @RequestParam String newPassword, Model model) {
		try {
			userService.updatePassword(id, originPassword, newPassword);
		} catch (JinluException e) {
			return new AjaxFailResponse(e.getErrorCode()).toString();
		}
		return new AjaxSuccessResponse().toString();
	}

	@RequestMapping(value = "/resetPasswordConfirm", method = RequestMethod.GET)
	public String resetPasswordConfirm(@RequestParam Long id, Model model) {
		User user = userService.find(id);
		model.addAttribute("item", user);
		return "uc/user/user_resetPasswordConfirm";
	}
	
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	@ResponseBody
	public String resetPassword(@RequestParam Long id, Model model) {
		try {
			userService.resetPassword(id);
		} catch (JinluException e) {
			return new AjaxFailResponse(e.getErrorCode()).toString();
		}
		return new AjaxSuccessResponse().toString();
	}
	
	@RequestMapping(value = "/editRole", method = RequestMethod.GET)
	public String editRole(@RequestParam Long id, Model model) {
		User user= userService.find(id);
		model.addAttribute("item", user);
		List<Long> roleIdList = user.getRoleIds();
		String roleIds = StringUtils.join(roleIdList, ",");
		model.addAttribute("roleIds", roleIds);
		String roleJson = roleService.getJsonTree();
		model.addAttribute("roleJson", roleJson);
		return "uc/user/user_editRole";
	}
	
	@RequestMapping(value = "/saveRole", method = RequestMethod.POST)
	@ResponseBody
	public String saveRole(@RequestParam Long id, @RequestParam String roleIds, Model model) {
		String[] roleIdArray = roleIds.split(",");
		List<Long> roleIdList = new ArrayList<Long>();
		for (String s : roleIdArray) {
			if (StringUtils.hasText(s)) {
				Long roleId = Long.valueOf(s);
				roleIdList.add(roleId);
			}
		}
		try {
			userService.updateRole(id, roleIdList);
		} catch (JinluException e) {
			return new AjaxFailResponse(e.getErrorCode()).toString();
		}
		return new AjaxSuccessResponse().toString();
	}
	
	@RequestMapping(value = "/editFunction", method = RequestMethod.GET)
	public String editFunction(@RequestParam Long id, Model model) {
		User user= userService.find(id);
		model.addAttribute("item", user);
		List<Long> functionIdList = user.getFunctionIds();
		String functionIds = StringUtils.join(functionIdList, ",");
		model.addAttribute("functionIds", functionIds);
		String functionJson = functionService.getFunctionTree();
		model.addAttribute("functionJson", functionJson);
		return "uc/user/user_editFunction";
	}
	
	@RequestMapping(value = "/saveFunction", method = RequestMethod.POST)
	@ResponseBody
	public String saveFunction(@RequestParam Long id, @RequestParam String functionIds, Model model) {
		String[] functionIdArray = functionIds.split(",");
		List<Long> functionIdList = new ArrayList<Long>();
		for (String s : functionIdArray) {
			if (StringUtils.hasText(s)) {
				Long functionId = Long.valueOf(s);
				functionIdList.add(functionId);
			}
		}
		try {
			userService.updateFunction(id, functionIdList);
		} catch (JinluException e) {
			return new AjaxFailResponse(e.getErrorCode()).toString();
		}
		return new AjaxSuccessResponse().toString();
	}
	
	@RequestMapping(value = "/deleteConfirm", method = RequestMethod.GET)
	public String deleteConfirm(@RequestParam Long id, Model model) {
		User user = userService.find(id);
		model.addAttribute("item", user);
		return "uc/user/user_deleteConfirm";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public String delete(@RequestParam Long id) {
		try {
			userService.delete(id);
		} catch (JinluException e) {
			return new AjaxFailResponse(e.getErrorCode()).toString();
		}
		return new AjaxSuccessResponse().toString();
	}

	@RequestMapping("/search")
	public String search(Pageable pageable, ServletRequest request, Model model) {
		Map<String, Object> searchParams = ServletRequestUtils.getParamsFromRequestWithPrefix(request, "search_");
		System.out.println("searchParams:" + searchParams);
		Page<User> page = userService.search(searchParams, pageable);
		model.addAttribute("page", page);
		model.addAttribute("searchParams", searchParams);
		model.addAttribute("searchParamsUrl", ServletRequestUtils.formatParamsWithPrefix(searchParams, "search_"));
		model.addAttribute("sortParamsUrl", "&sort=lastUpdate,desc");
		return "uc/user/user_search";
	}

}
