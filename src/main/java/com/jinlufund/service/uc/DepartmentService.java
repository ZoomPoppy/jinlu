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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jinlufund.entity.uc.Department;
import com.jinlufund.exception.JinluException;
import com.jinlufund.repository.uc.DepartmentRepository;

@Service
public class DepartmentService {

	@Autowired
	private DepartmentRepository departmentRepository;
	
	private Department root;
	
	private Map<Long, Department> departmentMap;
	
	@PostConstruct
	public void refresh() {
		departmentMap = new ConcurrentHashMap<Long, Department>();
		Iterable<Department> departments = departmentRepository.findAll();
		for (Department d : departments) {
			if (d.getParentId() == null) {
				root = d;
			}
			departmentMap.put(d.getId(), d);
		}
		
		for (Department r : departments) {
			Long parentId = r.getParentId();
			if (parentId != null) {
				Department parent = departmentMap.get(parentId);
				associate(parent, r);
			}
		}
	}
	
	private void associate(Department parent, Department child) {
		List<Department> children = parent.getChildren();
		children.add(child);
		Collections.sort(children, new Comparator<Department>() {
			@Override
			public int compare(Department o1, Department o2) {
				return o1.getOrderNo() - o2.getOrderNo();
			}
		});
		child.setParent(parent);
	}
	
	public String getJsonTree(boolean open) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode rootNode = mapper.createObjectNode();
		recursionNode(root, rootNode, mapper, open);
		String result = null;
		try {
			result = mapper.writeValueAsString(rootNode);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println(result);
		return result;
	}
	
	/**
	 * 给出当前department和一个空的JSON节点，生成完整的JSON节点，并递归。
	 * @param department
	 * @param parentNode
	 * @param mapper
	 */
	private void recursionNode(Department department, ObjectNode parentNode, ObjectMapper mapper, boolean open) {
		parentNode.put("id", department.getId());
		parentNode.put("name", department.getName());
		if (open) {
			parentNode.put("open", true);
		}
		List<Department> children = department.getChildren();
		if (children.size() == 0) {
			return;
		} else {
			ArrayNode childrenNode = mapper.createArrayNode();
			parentNode.set("children", childrenNode);
			for (Department child : children) {
				ObjectNode node = mapper.createObjectNode();
				childrenNode.add(node);
				recursionNode(child, node, mapper, open);
			}
		}
	}
	
	public Department find(Long id) {
		return findInternal(id);
	}
	
	public Department addChild(Long parentId, Department input) {
		Department department = new Department();
		department.setName(input.getName());
		department.setDescription(input.getDescription());
		department.setCategoryId(input.getCategoryId());
		department.setParentId(parentId);
		Department parent = assertDepartmentExist(parentId);
		List<Department> children = parent.getChildren();
		int orderNo = 0;
		if (children.size() > 0) {
			Department lastChild = children.get(children.size() - 1);
			orderNo = lastChild.getOrderNo() + 1;
		}
		department.setOrderNo(orderNo);
		department = departmentRepository.save(department);
		departmentMap.put(department.getId(), department);
		associate(parent, department);
		return department;
	}
	
	public Department update(Long id, Department input) {
		Department department = assertDepartmentExist(id);
		department.setName(input.getName());
		department.setDescription(input.getDescription());
		department.setCategoryId(input.getCategoryId());
		departmentRepository.save(department);
		return department;
	}
	
	public void delete(Long id) {
		Department department = assertDepartmentExist(id);
		List<Department> children = department.getChildren();
		if (children.size() > 0) {
			JinluException e = new JinluException();
			e.setErrorCode("departmentService.delete.departmentHasChild");
			e.addParameter("id", id);
			throw e;
		}
		Department parent = department.getParent();
		parent.getChildren().remove(department);
		deleteInternal(id);
		departmentRepository.delete(department);
	}
	
	private Department assertDepartmentExist(Long id) {
		Department department = findInternal(id);
		if (department == null) {
			JinluException e = new JinluException();
			e.setErrorCode("departmentSerivce.department.notExist");
			e.addParameter("id", id);
			throw e;
		}
		return department;
	}
	
	private Department findInternal(Long id) {
		return departmentMap.get(id);
	}
	
	private void deleteInternal(Long id) {
		departmentMap.remove(id);
	}
}
