package com.Sapo.pet.model.response;

public class RestResponse {
	private String status;
	private String message;
	private String meta = "Json Data";
	private Integer version = 1;

	/**
	 * ngăn tạo mới object từ toán tử new.
	 */
	private RestResponse( String message) {
		this.message = message;
	}
	
	/**
	 * thông báo thành công.
	 * @param message
	 * @return
	 */

	
	public static RestResponse success( String message) {
		RestResponse response = new RestResponse( message);
		response.setStatus("success");
		response.setMessage(message);
		return response;
	}
	
	
	/**
	 * thông báo lỗi.
	 * @param message
	 * @return
	 */
	public static RestResponse failed( String message) {
		RestResponse response = new RestResponse( message);
		response.setStatus("failed");
		response.setMessage(message);
		return response;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMeta() {
		return meta;
	}

	public void setMeta(String meta) {
		this.meta = meta;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	
}
