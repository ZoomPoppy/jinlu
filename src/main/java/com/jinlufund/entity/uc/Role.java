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
@Table(name = "uc_role")
public class Role extends IdEntity {

	@Column
	private String name;

	@Column
	private String description;

	@Column
	@Enumerated(EnumType.ORDINAL)
	private SystemType system;

	@Transient
	private List<Long> functionIds = new ArrayList<Long>();

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

	public SystemType getSystem() {
		return system;
	}

	public void setSystem(SystemType system) {
		this.system = system;
	}

	public List<Long> getFunctionIds() {
		return functionIds;
	}

	public void setFunctionIds(List<Long> functionIds) {
		this.functionIds = functionIds;
	}

}
