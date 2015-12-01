package com.jinlufund.protocol.ajax;

public class AjaxSuccessResponse extends AjaxResponse {

	private Object data;

	public AjaxSuccessResponse() {
		super();
		this.status = 0;
	}

	public AjaxSuccessResponse(Object data) {
		super();
		this.status = 0;
		this.data = data;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
