package com.jinlufund.entity.crm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.jinlufund.entity.IdEntity;

@Entity
@Table(name = "crm_city")
public class City extends IdEntity {

	@Column
	private Long provinceId;

	@Column
	private String name;

	@Column
	private String postcode;

	public Long getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

}
