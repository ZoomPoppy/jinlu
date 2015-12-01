package com.jinlufund.entity.uc;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.jinlufund.entity.IdEntity;

@Entity
@Table(name = "uc_user_role")
public class UserRole extends IdEntity {

	@Column
	private Long userId;

	@Column
	private Long roleId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

}
