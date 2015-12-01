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

import com.jinlufund.entity.uc.Rank;
import com.jinlufund.exception.JinluException;
import com.jinlufund.protocol.ajax.AjaxFailResponse;
import com.jinlufund.protocol.ajax.AjaxSuccessResponse;
import com.jinlufund.service.uc.RankService;

@Controller
@RequestMapping("uc/rank")
public class RankController {

	@Autowired
	RankService rankService;
	
	@RequestMapping("")
	public String index(Pageable pageable, Model model) {
		return "redirect:/uc/rank/tree";
	}
	
	@RequestMapping(value = "/tree", method = RequestMethod.GET)
	public String tree(@RequestParam(required = false) Long currentRankId, Model model) {
		String jsonData = rankService.getJsonTree(true);
		model.addAttribute("jsonData", jsonData);
		model.addAttribute("currentRankId", currentRankId);
		return "uc/rank/rank_tree";
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String item(@PathVariable Long id) {
		Rank rank = rankService.find(id);
		Rank mirror = new Rank(); // 防止生成JSON的循环依赖。
		mirror.setId(rank.getId());
		mirror.setName(rank.getName());
		mirror.setDescription(rank.getDescription());
		return new AjaxSuccessResponse(mirror).toString();
	}
	
	@RequestMapping(value = "/refresh", method = RequestMethod.GET)
	public String refresh(Model model) {
		rankService.refresh();
		return "redirect:/uc/rank/tree";
	}

	@RequestMapping(value = "/addChildEdit", method = RequestMethod.GET)
	public String addChildEdit(@RequestParam Long parentId, Model model) {
		Rank parent = rankService.find(parentId);
		model.addAttribute("parentId", parent.getId());
		return "uc/rank/rank_addChildEdit";
	}

	@RequestMapping(value = "/addChild", method = RequestMethod.POST)
	@ResponseBody
	public String addChild(@RequestParam Long parentId, @ModelAttribute Rank input) {
		Rank rank = null;
		try {
			rank = rankService.addChild(parentId, input);
		} catch (JinluException e) {
			return new AjaxFailResponse(e.getErrorCode()).toString();
		}
		return new AjaxSuccessResponse(rank.getId()).toString();
	}
	
	@RequestMapping(value = "/updateEdit", method = RequestMethod.GET)
	public String updateEdit(@RequestParam Long id, Model model) {
		if (id != null) {
			Rank rank = rankService.find(id);
			model.addAttribute("item", rank);
		}
		return "uc/rank/rank_updateEdit";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@RequestParam Long id, @ModelAttribute Rank input) {
		try {
			rankService.update(id, input);
		} catch (JinluException e) {
			return new AjaxFailResponse(e.getErrorCode()).toString();
		}
		return new AjaxSuccessResponse().toString();
	}
	
	@RequestMapping(value = "/deleteConfirm", method = RequestMethod.GET)
	public String deleteConfirm(@RequestParam Long id, Model model) {
		Rank rank = rankService.find(id);
		model.addAttribute("item", rank);
		return "uc/rank/rank_deleteConfirm";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public String delete(@RequestParam Long id) {
		try {
			rankService.delete(id);
		} catch (JinluException e) {
			return new AjaxFailResponse(e.getErrorCode()).toString();
		}
		return new AjaxSuccessResponse().toString();
	}
	
}
