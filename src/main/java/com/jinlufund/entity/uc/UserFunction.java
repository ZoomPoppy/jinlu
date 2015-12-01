package com.jinlufund.entity.uc;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.jinlufund.entity.IdEntity;

@Entity
@Table(name = "uc_user_function")
public class UserFunction extends IdEntity {

	@Column
	private Long userId;

	@Column
	private Long functionId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getFunctionId() {
		return functionId;
	}

	public void setFunctionId(Long functionId) {
		this.functionId = functionId;
	}

}
