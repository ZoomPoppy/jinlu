package com.jinlufund.entity.uc;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.jinlufund.entity.IdEntity;

@Entity
@Table(name = "uc_function")
public class Function extends IdEntity {

	@Column
	private String name = "";

	@Column
	private String description = "";

	@Column
	@Enumerated(EnumType.ORDINAL)
	private FunctionType type;

	public enum FunctionType {
		MODULE("模块"), PAGE("页面"), BUTTON("按钮");
		
		private final String description;
		
		private FunctionType(String description) {
			this.description = description;
		}

		public String getDescription() {
			return description;
		}
	}

	@Column
	@Enumerated(EnumType.ORDINAL)
	private SystemType system;

	@Column
	private Long moduleId;

	@Column
	private Long pageId;

	@Column
	private String url = "";

	@Column
	private int orderNo;
	
	@Transient
	private Function parent;
	
	@Transient
	private List<Function> children = new ArrayList<Function>();

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

	public FunctionType getType() {
		return type;
	}

	public void setType(FunctionType type) {
		this.type = type;
	}

	public SystemType getSystem() {
		return system;
	}

	public void setSystem(SystemType system) {
		this.system = system;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public Long getPageId() {
		return pageId;
	}

	public void setPageId(Long pageId) {
		this.pageId = pageId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public Function getParent() {
		return parent;
	}

	public void setParent(Function parent) {
		this.parent = parent;
	}

	public List<Function> getChildren() {
		return children;
	}

	public void setChildren(List<Function> children) {
		this.children = children;
	}

}
