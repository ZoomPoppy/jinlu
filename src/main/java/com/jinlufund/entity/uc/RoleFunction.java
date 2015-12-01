package com.jinlufund.entity.uc;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.jinlufund.entity.IdEntity;

@Entity
@Table(name = "uc_role_function")
public class RoleFunction extends IdEntity {

	@Column
	private Long roleId;

	@Column
	private Long functionId;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getFunctionId() {
		return functionId;
	}

	public void setFunctionId(Long functionId) {
		this.functionId = functionId;
	}

}
