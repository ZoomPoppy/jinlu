package com.jinlufund.entity.uc;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.jinlufund.entity.IdEntity;

@Entity
@Table(name = "uc_department_category")
public class DepartmentCategory extends IdEntity {

	@Column
	private String name;

	@Column
	private String description;

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

}
