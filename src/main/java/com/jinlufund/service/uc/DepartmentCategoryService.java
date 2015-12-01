package com.jinlufund.service.uc;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jinlufund.entity.uc.DepartmentCategory;
import com.jinlufund.exception.JinluException;
import com.jinlufund.repository.uc.DepartmentCategoryRepository;

@Service
public class DepartmentCategoryService {

	@Autowired
	private DepartmentCategoryRepository departmentCategoryRepository;
	
	private Map<Long, DepartmentCategory> departmentCategoryMap;
	
	@PostConstruct
	public void refresh() {
		departmentCategoryMap = new ConcurrentHashMap<Long, DepartmentCategory>();
		Iterable<DepartmentCategory> departmentCategories = departmentCategoryRepository.findAll();
		for (DepartmentCategory dc : departmentCategories) {
			departmentCategoryMap.put(dc.getId(), dc);
		}
	}
	
	public DepartmentCategory find(Long id) {
		return assertDepartmentCategoryExist(id);
	}
	
	public Iterable<DepartmentCategory> findAll() {
		return departmentCategoryRepository.findAll();
	}
	
	public Page<DepartmentCategory> findAll(Pageable pageable) {
		return departmentCategoryRepository.findAll(pageable);
	}
	
	public Map<Long, DepartmentCategory> getDepartmentCategoryMap() {
		return Collections.unmodifiableMap(departmentCategoryMap);
	}
	
	public DepartmentCategory add(DepartmentCategory input) {
		DepartmentCategory category = new DepartmentCategory();
		category.setName(input.getName());
		category.setDescription(input.getDescription());
		category = departmentCategoryRepository.save(category);
		departmentCategoryMap.put(category.getId(), category);
		return category;
	}
	
	public DepartmentCategory update(Long id, DepartmentCategory input) {
		DepartmentCategory category = assertDepartmentCategoryExist(id);
		category.setName(input.getName());
		category.setDescription(input.getDescription());
		category = departmentCategoryRepository.save(category);
		return category;
	}
	
	public void delete(Long id) {
		DepartmentCategory category = assertDepartmentCategoryExist(id);
		deleteInternal(id);
		departmentCategoryRepository.delete(category);
	}
	
	private DepartmentCategory assertDepartmentCategoryExist(Long id) {
		DepartmentCategory departmentCategory = findInternal(id);
		if (departmentCategory == null) {
			JinluException e = new JinluException();
			e.setErrorCode("departmentCategorySerivce.departmentCategory.notExist");
			e.addParameter("id", id);
			throw e;
		}
		return departmentCategory;
	}
	
	private DepartmentCategory findInternal(Long id) {
		return departmentCategoryMap.get(id);
	}
	
	private void deleteInternal(Long id) {
		departmentCategoryMap.remove(id);
	}
	
}
