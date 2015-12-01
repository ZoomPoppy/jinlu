package com.jinlufund.service.uc;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jinlufund.entity.uc.Function;
import com.jinlufund.entity.uc.SystemType;
import com.jinlufund.exception.JinluException;
import com.jinlufund.repository.uc.FunctionRepository;

@Service
public class FunctionService {

	@Autowired
	private FunctionRepository functionRepository;
	
	// 每个system一个虚拟的功能根节点
	private Map<SystemType, Function> systemRootMap;
	
	// 按照system对所有function进行分类，然后按照id索引
	private Map<SystemType, Map<Long, Function>> systemFunctionMap;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@PostConstruct
	public void refresh() {
		systemRootMap = new ConcurrentHashMap<SystemType, Function>();
		systemFunctionMap = new ConcurrentHashMap<SystemType, Map<Long, Function>>();
		for (SystemType st : SystemType.values()) {
			Function systemFunction = new Function();
			systemFunction.setName(st.name());
			systemRootMap.put(st, systemFunction);
			systemFunctionMap.put(st, new ConcurrentHashMap<Long, Function>());
		}
		
		// 将数据库中的数据导入systemFunctionMap
		Iterable<Function> functions = functionRepository.findAll();
		for (Function f : functions) {
			SystemType st = f.getSystem();
			Map<Long, Function> functionMap = systemFunctionMap.get(st);
			functionMap.put(f.getId(), f);
		}
		
		// 给function创建双向关联
		for (SystemType st : SystemType.values()) {
			Map<Long, Function> functionMap = systemFunctionMap.get(st);
			for (Function f : functionMap.values()) {
				Long parentId = null;
				if (f.getType() == Function.FunctionType.PAGE) {
					parentId = f.getModuleId();
				} else if (f.getType() == Function.FunctionType.BUTTON) {
					parentId = f.getPageId();
				}
				Function parent = null;
				if (parentId == null) { // 说明是module，用虚拟的根节点
					parent = systemRootMap.get(st);
				} else {
					parent = functionMap.get(parentId);
				}
				associate(parent, f);
			}
		}
		
	}
	
	private void associate(Function parent, Function child) { // 调用该方法时已经保证parent和child合法。
		List<Function> children = parent.getChildren();
		children.add(child);
		Collections.sort(children, new Comparator<Function>() {
			@Override
			public int compare(Function o1, Function o2) {
				return o1.getOrderNo() - o2.getOrderNo();
			}
		});
		child.setParent(parent);
	}
	
	public String getFunctionTree(SystemType systemType) {
		ArrayNode rootNode = mapper.createArrayNode();
		ObjectNode systemNode = getSystemNode(systemType);
		rootNode.add(systemNode);
		return nodeToString(rootNode);
	}
	
	public String getFunctionTree() {
		ArrayNode rootNode = mapper.createArrayNode();
		for (SystemType st : SystemType.values()) {
			ObjectNode systemNode = getSystemNode(st);
			rootNode.add(systemNode);
		}
		return nodeToString(rootNode);
	}

	private ObjectNode getSystemNode(SystemType systemType) {
		Function system = systemRootMap.get(systemType);
		ObjectNode systemNode = mapper.createObjectNode();
		systemNode.put("system", system.getName());
		systemNode.put("name", system.getName());
		systemNode.put("open", true);
		ArrayNode systemChildrenNode = mapper.createArrayNode();
		systemNode.set("children", systemChildrenNode);
		List<Function> modules = system.getChildren();
		for (Function module : modules) {
			ObjectNode moduleNode = mapper.createObjectNode();
			moduleNode.put("id", module.getId());
			moduleNode.put("name", module.getName());
			moduleNode.put("open", true);
			ArrayNode moduleChildrenNode = mapper.createArrayNode();
			moduleNode.set("children", moduleChildrenNode);
			systemChildrenNode.add(moduleNode);
			List<Function> pages = module.getChildren();
			for (Function page : pages) {
				ObjectNode pageNode = mapper.createObjectNode();
				pageNode.put("id", page.getId());
				pageNode.put("name", page.getName());
				pageNode.put("open", true);
				ArrayNode pageChildrenNode = mapper.createArrayNode();
				pageNode.set("children", pageChildrenNode);
				moduleChildrenNode.add(pageNode);
				List<Function> buttons = page.getChildren();
				for (Function button : buttons) {
					ObjectNode buttonNode = mapper.createObjectNode();
					buttonNode.put("id", button.getId());
					buttonNode.put("name", button.getName());
					pageChildrenNode.add(buttonNode);
				}
			}
		}
		return systemNode;
	}
	
	private String nodeToString(JsonNode rootNode) {
		String result = null;
		try {
			result = mapper.writeValueAsString(rootNode);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println(result);
		return result;
	}
	
	public Function find(Long id) {
		return findInternal(id);
	}
	
	/**
	 * 添加一个功能节点。当parentId为null时，表示添加一个模块，此时system必须指定。
	 * @param system
	 * @param parentId
	 * @param input
	 */
	public Function addChild(SystemType system, Long parentId, Function input) {
		Function function = new Function();
		function.setName(input.getName());
		function.setDescription(input.getDescription());
		Function parent = null;
		if (parentId == null) { // 创建module
			if (system == null) {
				JinluException e = new JinluException();
				e.setErrorCode("functionService.addChild.systemIsNull");
				throw e;
			}
			parent = systemRootMap.get(system);
			function.setSystem(system);
			function.setType(Function.FunctionType.MODULE);
		} else {
			parent = assertFunctionExist(parentId);
			function.setSystem(parent.getSystem());
			if (parent.getType() == Function.FunctionType.MODULE) {
				function.setModuleId(parent.getId());
				function.setType(Function.FunctionType.PAGE);
				function.setUrl(input.getUrl());
			} else if (parent.getType() == Function.FunctionType.PAGE) {
				function.setModuleId(parent.getParent().getId());
				function.setPageId(parent.getId());
				function.setType(Function.FunctionType.BUTTON);
			} else {
				JinluException e = new JinluException();
				e.setErrorCode("functionService.addChild.parentIsButton");
				throw e;
			}
		}
		List<Function> children = parent.getChildren();
		int orderNo = 0;
		if (children.size() > 0) {
			Function lastChild = children.get(children.size() - 1);
			orderNo = lastChild.getOrderNo() + 1;
		}
		function.setOrderNo(orderNo);
		function = functionRepository.save(function); // 存入数据库，获得id
		systemFunctionMap.get(function.getSystem()).put(function.getId(), function);
		associate(parent, function);
		return function;
	}
	
	/**
	 * 修改一个已存在的功能节点。
	 * @param id
	 * @param input
	 */
	public Function update(Long id, Function input) {
		Function function = assertFunctionExist(id);
		function.setName(input.getName());
		function.setDescription(input.getDescription());
		if (function.getType() == Function.FunctionType.PAGE) {
			function.setUrl(input.getUrl());
		}
		functionRepository.save(function);
		return function;
	}
	
	/**
	 * 删除一个功能节点，先删除内存，再删除数据库。注意必须没有子节点，才可以删除。
	 * @param id
	 */
	public void delete(Long id) {
		Function function = assertFunctionExist(id);
		List<Function> children = function.getChildren();
		if (children.size() > 0) {
			JinluException e = new JinluException();
			e.setErrorCode("functionService.delete.functionHasChild");
			e.addParameter("id", id);
			throw e;
		}
		Function parent = function.getParent();
		parent.getChildren().remove(function);
		deleteInternal(id);
		functionRepository.delete(function);
	}
	
	private Function assertFunctionExist(Long id) {
		Function function = findInternal(id);
		if (function == null) {
			JinluException e = new JinluException();
			e.setErrorCode("functionSerivce.function.notExist");
			e.addParameter("id", id);
			throw e;
		}
		return function;
	}
	
	private Function findInternal(Long id) {
		for (SystemType st : SystemType.values()) {
			Map<Long, Function> functionMap = systemFunctionMap.get(st);
			Function function = functionMap.get(id);
			if (function != null) {
				return function;
			}
		}
		return null;
	}
	
	private void deleteInternal(Long id) {
		for (SystemType st : SystemType.values()) {
			Map<Long, Function> functionMap = systemFunctionMap.get(st);
			Function function = functionMap.get(id);
			if (function != null) {
				functionMap.remove(id);
			}
		}
	}
}
