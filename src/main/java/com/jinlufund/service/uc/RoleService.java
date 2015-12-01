package com.jinlufund.service.uc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jinlufund.entity.uc.Role;
import com.jinlufund.entity.uc.RoleFunction;
import com.jinlufund.exception.JinluException;
import com.jinlufund.repository.uc.RoleFunctionRepository;
import com.jinlufund.repository.uc.RoleRepository;

@Service
public class RoleService {

	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	RoleFunctionRepository roleFunctionRepository;
	
	private Map<Long, Role> roleMap;
	
	@PostConstruct
	public void refresh() {
		roleMap = new ConcurrentHashMap<Long, Role>();
		Iterable<Role> roles = roleRepository.findAll();
		for (Role r : roles) {
			Long id = r.getId();
			List<RoleFunction> roleFunctions = roleFunctionRepository.findByRoleId(id);
			List<Long> functionIds = new ArrayList<Long>();
			for (RoleFunction rf : roleFunctions) {
				functionIds.add(rf.getFunctionId());
			}
			r.setFunctionIds(functionIds);
			roleMap.put(r.getId(), r);
		}
	}
	
	public String getJsonTree() {
		ObjectMapper mapper = new ObjectMapper();
		ArrayNode rootNode = mapper.createArrayNode();
		Iterable<Role> roles =  roleRepository.findAll();
		for (Role r : roles) {
			ObjectNode node = mapper.createObjectNode();
			node.put("id", r.getId());
			node.put("name", r.getName());
			rootNode.add(node);
		}
		String result = null;
		try {
			result = mapper.writeValueAsString(rootNode);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println(result);
		return result;
	}
	
	public Page<Role> findAll(Pageable pageable) {
		Page<Role> page = roleRepository.findAll(pageable);
		return page;
	}
	
	public Role find(Long id) {
		return findInternal(id);
	}
	
	public Role add(Role input) {
		Role role = roleRepository.save(input);
		roleMap.put(role.getId(), role);
		return role;
	}
	
	public void update(Long id, Role input) {
		Role role = assertRoleExist(id);
		role.setName(input.getName());
		role.setDescription(input.getDescription());
		role.setSystem(input.getSystem());
		roleRepository.save(input);
	}
	
	public void updateFunction(Long id, List<Long> functionIdList) {
		//TODO 严格的检查
		Role role = assertRoleExist(id);
		List<RoleFunction> oldRoleFunctions = roleFunctionRepository.findByRoleId(id);
		List<RoleFunction> newRoleFunctions = new ArrayList<RoleFunction>();
		for (Long functionId : functionIdList) {
			RoleFunction roleFunction = new RoleFunction();
			roleFunction.setRoleId(id);
			roleFunction.setFunctionId(functionId);
			newRoleFunctions.add(roleFunction);
		}
		roleFunctionRepository.delete(oldRoleFunctions);
		roleFunctionRepository.save(newRoleFunctions);
		role.setFunctionIds(functionIdList);
	}
	
	public void delete(Long id) {
		Role role = assertRoleExist(id);
		deleteInternal(id);
		roleRepository.delete(role);
	}
	
	private Role assertRoleExist(Long id) {
		Role role = findInternal(id);
		if (role == null) {
			JinluException e = new JinluException();
			e.setErrorCode("roleSerivce.role.notExist");
			e.addParameter("id", id);
			throw e;
		}
		return role;
	}
	
	private Role findInternal(Long id) {
		return roleMap.get(id);
	}
	
	private void deleteInternal(Long id) {
		roleMap.remove(id);
	}
	
}
