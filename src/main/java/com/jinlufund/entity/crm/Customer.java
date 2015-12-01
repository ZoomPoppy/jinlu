package com.jinlufund.entity.crm;

import com.jinlufund.entity.IdEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "crm_customer")
public class Customer extends IdEntity {

	@Column
	private String name;
	@Column 
	private String sex;
	
	@Column
	@Enumerated(EnumType.ORDINAL)
	private CertificateType certificateType;

	public enum CertificateType {
		ID_CARD("身份证"), PASSPORT("护照"),MILITARY_ID("军官证"),GAT_PASSPORT("港澳台"),ORGANIZATIONCODE("机构组织代码"),OTHERCARD("其他");

		private final String description;

		private CertificateType(String description) {
			this.description = description;
		}

		public String getDescription() {
			return description;
		}
	}
	@Column
	private Date birthday;
	@Column
	private String certificateNo;
	/**
	 * 在经理创建用户的时候，会将这个客户的managerId设置为user的id 也就是managerId
	 *
	 * 同时会将agentName设置为经理的名字，也就是客服姓名，是不会更改的
	 *
	 * agentId 和 managerId 是一个东西，但是无法更改的
	 */
	@Column
	private Long managerId;

	@Column
	private Long agentId;
	//客服姓名 不会更改
	@Column
	private String agentName;

	@Column
	private Date createDate;

	@Column
	private Date lastUpdate;
	// 地址 营业执照的住所
	@Column
	private String address;
	//有效期
	@Column
	private Date validityPeriod;
	/**
	 * 分为三种用户，最开始注册时新用户，买了两件东西之后改成 什么什么用户， 。。忘记了
	 *
	 */
	//客户状态和客户类型 不记得
	@Column
	private String customerType;
	//客户状态
	@Column
	private CustomerStatus customerStatus;
	//该用户是否已经被删除  true 代表删除 false 代表未删除
	@Column
	private boolean deleteOrNot;
	@Column
	private String imageUrl;

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getLegalRepressentative() {
		return legalRepressentative;
	}

	public void setLegalRepressentative(String legalRepressentative) {
		this.legalRepressentative = legalRepressentative;
	}

	public String getAxpayerName() {
		return axpayerName;
	}

	public void setAxpayerName(String axpayerName) {
		this.axpayerName = axpayerName;
	}

	public String getRegisterType() {
		return registerType;
	}

	public void setRegisterType(String registerType) {
		this.registerType = registerType;
	}

	//国籍
	@Column
	private String nationality;
	//代码
	@Column
	private String code;
	//机构名称  同时也是  营业执照名称
	@Column
	private String organizationName;
	//营业类型
	@Column
	private String businessType;
	//法定代表人 == 企业税务登记证
	@Column
	private String legalRepressentative;
	//纳税人名称
	@Column
	private String axpayerName;
	//登记注册类型
	@Column
	private String registerType;




	// TODO: 15/11/29 enum
	public enum CustomerStatus {
		ID_CARD("身份证"), PASSPORT("护照"),MILITARY_ID("军官证"),GAT_PASSPORT("港澳台"),ORGANIZATIONCODE("机构组织代码"),OTHERCARD("其他");
		private final String description;
		private CustomerStatus(String description) {
			this.description = description;
		}
		public String getDescription() {
			return description;
		}
	}

	public CustomerStatus getCustomerStatus() {
		return customerStatus;
	}

	public void setCustomerStatus(CustomerStatus customerStatus) {
		this.customerStatus = customerStatus;
	}

	public boolean isDeleteOrNot() {
		return deleteOrNot;
	}

	public void setDeleteOrNot(boolean deleteOrNot) {
		this.deleteOrNot = deleteOrNot;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CertificateType getCertificateType() {
		return certificateType;
	}

	public void setCertificateType(CertificateType certificateType) {
		this.certificateType = certificateType;
	}

	public String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}

	public Long getManagerId() {
		return managerId;
	}

	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}

	public Long getAgentId() {
		return agentId;
	}

	public void setAgentId(Long agentId) {
		this.agentId = agentId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getValidityPeriod() {
		return validityPeriod;
	}

	public void setValidityPeriod(Date validityPeriod) {
		this.validityPeriod = validityPeriod;
	}
	
}
