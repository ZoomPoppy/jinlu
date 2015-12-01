package com.jinlufund.entity.crm;

import com.jinlufund.entity.IdEntity;

import javax.persistence.*;

@Entity
@Table(name = "crm_address")
public class Address extends IdEntity {

	@Column
	private Long customerId;
	
	@Column
	@Enumerated(EnumType.ORDINAL)
	private UsageType usage;

	@Column
	@Enumerated(EnumType.ORDINAL)
	private AddressType type;

	public enum AddressType {
		HOME("住宅"), COMPANY("公司");

		private final String description;

		private AddressType(String description) {
			this.description = description;
		}

		public String getDescription() {
			return description;
		}
	}
	
	@Column
	private Long provinceId;
	
	@Column
	private Long cityId;
	
	@Column
	private Long districtId;
	
	@Column
	private String street;
	
	@Column
	private String detail;
	
	@Column
	private String postcode;
	
	@Column
	@Enumerated(EnumType.ORDINAL)
	private StatusType status;

	public enum StatusType {
		DISABLED("无效"), ENABLED("有效");

		private final String description;

		private StatusType(String description) {
			this.description = description;
		}

		public String getDescription() {
			return description;
		}
	}
	@Column
	private boolean deleteOrNot;

	public boolean isDeleteOrNot() {
		return deleteOrNot;
	}

	public void setDeleteOrNot(boolean deleteOrNot) {
		this.deleteOrNot = deleteOrNot;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public UsageType getUsage() {
		return usage;
	}

	public void setUsage(UsageType usage) {
		this.usage = usage;
	}

	public AddressType getType() {
		return type;
	}

	public void setType(AddressType type) {
		this.type = type;
	}

	public Long getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public Long getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public StatusType getStatus() {
		return status;
	}

	public void setStatus(StatusType status) {
		this.status = status;
	}

}
