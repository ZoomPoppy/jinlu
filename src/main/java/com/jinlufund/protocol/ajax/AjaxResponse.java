package com.jinlufund.protocol.ajax;

import com.jinlufund.utils.JsonUtils;

public class AjaxResponse {

	protected int status;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return JsonUtils.objectToJson(this);
	}

}
