package com.jinlufund.entity.crm;

import com.jinlufund.entity.IdEntity;

import javax.persistence.*;

@Entity
@Table(name = "crm_bankcard")
public class Bankcard extends IdEntity {

	@Column
	private Long customerId;
	
	@Column
	@Enumerated(EnumType.ORDINAL)
	private UsageType usage;
	
	@Column
	private String bankName;

	@Column
	private String accountName;

	@Column
	private String accountNo;

	@Column
	@Enumerated(EnumType.ORDINAL)
	private AccountType accountType;

	public enum AccountType {
		DEBIT_CARD("借记卡"), CREDIT_CARD("信用卡");

		private final String description;

		private AccountType(String description) {
			this.description = description;
		}

		public String getDescription() {
			return description;
		}
	}
	@Column
	private BankCardStatus bankCardStatus;
	public enum BankCardStatus{
		VALID("有效"),INVALID("无效");
		private String status;
		private BankCardStatus(String status){
			this.status = status;
		}
	}
	@Column
	private Long accountBankProvinceId;

	@Column
	private String imageUrl;

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Column
	private Long accountBankCityId;
	//支行名称
	@Column
	private String accountBankName;

	@Column
	private boolean deleteOrNot;

	public BankCardStatus getBankCardStatus() {
		return bankCardStatus;
	}

	public void setBankCardStatus(BankCardStatus bankCardStatus) {
		this.bankCardStatus = bankCardStatus;
	}

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

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public Long getAccountBankProvinceId() {
		return accountBankProvinceId;
	}

	public void setAccountBankProvinceId(Long accountBankProvinceId) {
		this.accountBankProvinceId = accountBankProvinceId;
	}

	public Long getAccountBankCityId() {
		return accountBankCityId;
	}

	public void setAccountBankCityId(Long accountBankCityId) {
		this.accountBankCityId = accountBankCityId;
	}

	public String getAccountBankName() {
		return accountBankName;
	}

	public void setAccountBankName(String accountBankName) {
		this.accountBankName = accountBankName;
	}

}
