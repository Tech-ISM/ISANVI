package com.projects.ujjwal.techism.STT.Model.Data;

/**
 * Created by ujjwal on 21/1/17.
 */
public class SttResponse {
	boolean success;
	String message;
	int x;
	int y;

	public SttResponse(boolean success, String message, int x, int y) {
		this.success = success;
		this.message = message;
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isSuccess() {
		return success;
	}

	public String getMessage() {
		return message;
	}
}
