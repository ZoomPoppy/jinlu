package com.jinlufund.controller.uc;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jinlufund.constant.SessionConstant;
import com.jinlufund.entity.uc.User;
import com.jinlufund.exception.JinluException;
import com.jinlufund.service.uc.UserService;

@Controller
@RequestMapping(value = "")
public class LoginController {

	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "/login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestParam(required = false) String username, @RequestParam(required = false) String password, HttpSession session, Model model) {
		if (username == null || password == null) {
			model.addAttribute("errorMessage", "loginController.login.usernameOrPasswordIsNull");
			return "/login";
		}
		
		try {
			User user = userService.login(username, password);
			session.setAttribute(SessionConstant.USER, user);
			return "redirect:/home";
		} catch (JinluException e) {
			model.addAttribute("errorMessage", e.getErrorCode());
			return "/login";
		}
	}
	
	@RequestMapping(value = "/logout")
	public String logout(HttpSession session) {
		session.removeAttribute(SessionConstant.USER);
		return "redirect:/login";
	}
	
}
