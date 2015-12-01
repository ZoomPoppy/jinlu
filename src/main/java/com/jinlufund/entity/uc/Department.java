package com.jinlufund.entity.uc;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.jinlufund.entity.IdEntity;

@Entity
@Table(name = "uc_department")
public class Department extends IdEntity {

	@Column
	private String name = "";

	@Column
	private String description = "";

	@Column
	private Long parentId;

	@Column
	private Long categoryId;
	
	@Column
	private int orderNo;
	
	@Transient
	private Department parent;
	
	@Transient
	private List<Department> children = new ArrayList<Department>();
	
	@Transient
	private DepartmentCategory category;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public Department getParent() {
		return parent;
	}

	public void setParent(Department parent) {
		this.parent = parent;
	}

	public List<Department> getChildren() {
		return children;
	}

	public void setChildren(List<Department> children) {
		this.children = children;
	}

	public DepartmentCategory getCategory() {
		return category;
	}

	public void setCategory(DepartmentCategory category) {
		this.category = category;
	}

}
