package com.jinlufund.entity.crm;

import com.jinlufund.entity.IdEntity;

import javax.persistence.*;

@Entity
@Table(name = "crm_telephone")
public class Telephone extends IdEntity {

	@Column
	private Long customerId;

	@Column
	private String name;

	@Column
	@Enumerated(EnumType.ORDINAL)
	private TelephoneType type;

	public enum TelephoneType {
		MOBILE("手机"), LANDLINE("固定电话");

		private final String description;

		private TelephoneType(String description) {
			this.description = description;
		}

		public String getDescription() {
			return description;
		}
	}

	@Column
	private String areaCode;

	@Column
	private String telephoneNo;

	@Column
	private String extensionNo;

	@Column
	@Enumerated(EnumType.ORDINAL)
	private StatusType status;
	@Column
	private boolean deleteOrNot;

	public boolean isDeleteOrNot() {
		return deleteOrNot;
	}

	public void setDeleteOrNot(boolean deleteOrNot) {
		this.deleteOrNot = deleteOrNot;
	}

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

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TelephoneType getType() {
		return type;
	}

	public void setType(TelephoneType type) {
		this.type = type;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getTelephoneNo() {
		return telephoneNo;
	}

	public void setTelephoneNo(String telephoneNo) {
		this.telephoneNo = telephoneNo;
	}

	public String getExtensionNo() {
		return extensionNo;
	}

	public void setExtensionNo(String extensionNo) {
		this.extensionNo = extensionNo;
	}

	public StatusType getStatus() {
		return status;
	}

	public void setStatus(StatusType status) {
		this.status = status;
	}

}
