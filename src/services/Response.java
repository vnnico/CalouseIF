package services;

public class Response<T>{

	// Response Structure consist of message, isSuccess, and data.
	private String messages;
	private Boolean isSuccess;
	private T data;
	
	public Response() {
		
	}

	public String getMessages() {
		return messages;
	}

	public void setMessages(String messages) {
		this.messages = messages;
	}

	public Boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
