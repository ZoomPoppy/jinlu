package com.jinlufund.entity.uc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.jinlufund.entity.IdEntity;

@Entity
@Table(name = "uc_user")
public class User extends IdEntity {

	@Column
	private String name;

	@Column
	private String email;

	@Column
	private String username;

	@Column
	private String passwordEncrypt;

	@Column
	private Date createDate;

	@Column
	private Date lastUpdate;

	@Column
	private Date lastLogin;

	@Column
	@Enumerated(EnumType.ORDINAL)
	private StatusType status;

	public enum StatusType {
		DISABLED("禁用"), ENABLED("启用"), TEMPORARY("临时启用");

		private final String description;

		private StatusType(String description) {
			this.description = description;
		}

		public String getDescription() {
			return description;
		}
	}

	@Column
	private Long departmentId;

	@Column
	private Long rankId;

	@Transient
	private Department department;

	@Transient
	private Rank rank;

	@Transient
	private List<Long> roleIds = new ArrayList<Long>();

	@Transient
	private List<Long> functionIds = new ArrayList<Long>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswordEncrypt() {
		return passwordEncrypt;
	}

	public void setPasswordEncrypt(String passwordEncrypt) {
		this.passwordEncrypt = passwordEncrypt;
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

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public StatusType getStatus() {
		return status;
	}

	public void setStatus(StatusType status) {
		this.status = status;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public Long getRankId() {
		return rankId;
	}

	public void setRankId(Long rankId) {
		this.rankId = rankId;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Rank getRank() {
		return rank;
	}

	public void setRank(Rank rank) {
		this.rank = rank;
	}

	public List<Long> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(List<Long> roleIds) {
		this.roleIds = roleIds;
	}

	public List<Long> getFunctionIds() {
		return functionIds;
	}

	public void setFunctionIds(List<Long> functionIds) {
		this.functionIds = functionIds;
	}

}
