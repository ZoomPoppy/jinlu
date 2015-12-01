package com.jinlufund.entity.uc;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.jinlufund.entity.IdEntity;

@Entity
@Table(name = "uc_rank")
public class Rank extends IdEntity {

	@Column
	private String name = "";

	@Column
	private String description = "";

	@Column
	private Long parentId;
	
	@Column
	private int orderNo;
	
	@Transient
	private Rank parent;
	
	@Transient
	private List<Rank> children = new ArrayList<Rank>();

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

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public Rank getParent() {
		return parent;
	}

	public void setParent(Rank parent) {
		this.parent = parent;
	}

	public List<Rank> getChildren() {
		return children;
	}

	public void setChildren(List<Rank> children) {
		this.children = children;
	}

}
