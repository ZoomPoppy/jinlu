package com.jinlufund.entity.crm;

import com.jinlufund.entity.IdEntity;

import javax.persistence.*;

@Entity
@Table(name = "crm_contact_person")
public class ContactPerson extends IdEntity {

	@Column
	private Long customerId;
	@Column
	private String name;
	//	@Column
//	@Enumerated(EnumType.ORDINAL)
//	private CertificateType certificateType;
//	@Column
//	private String certificateNo;
	@Column
	@Enumerated(EnumType.ORDINAL)
	private RelationshipType relationship;
//	@Column
//	private String remark;
	//邮编
	@Column
	private String zipCode;
	//工作单位
	@Column
	private String workUnit;
	//手机号
	@Column
	private String phoneNum;
	//座机
	@Column
	private String suppleMentaryPhone;
	@Column
	private String contactAddress;
	//邮箱
	@Column
	private String email;
	@Column
	private boolean deleteOrNot;

	public boolean isDeleteOrNot() {
		return deleteOrNot;
	}

	public void setDeleteOrNot(boolean deleteOrNot) {
		this.deleteOrNot = deleteOrNot;
	}

	public enum RelationshipType {
		HUSBAND_WIFE("夫妻"), PARENT_CHILD("亲子");

		private final String description;

		private RelationshipType(String description) {
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

//	public CertificateType getCertificateType() {
//		return certificateType;
//	}
//
//	public void setCertificateType(CertificateType certificateType) {
//		this.certificateType = certificateType;
//	}

//	public String getCertificateNo() {
//		return certificateNo;
//	}
//
//	public void setCertificateNo(String certificateNo) {
//		this.certificateNo = certificateNo;
//	}

	public RelationshipType getRelationship() {
		return relationship;
	}

	public void setRelationship(RelationshipType relationship) {
		this.relationship = relationship;
	}

//	public String getRemark() {
//		return remark;
//	}
//
//	public void setRemark(String remark) {
//		this.remark = remark;
//	}


	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getWorkUnit() {
		return workUnit;
	}

	public void setWorkUnit(String wordUnit) {
		this.workUnit = wordUnit;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getSuppleMentaryPhone() {
		return suppleMentaryPhone;
	}

	public void setSuppleMentaryPhone(String suppleMentaryPhone) {
		this.suppleMentaryPhone = suppleMentaryPhone;
	}

	public String getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
