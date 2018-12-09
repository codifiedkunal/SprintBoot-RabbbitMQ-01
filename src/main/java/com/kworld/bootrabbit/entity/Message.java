package com.kworld.bootrabbit.entity;

import java.io.Serializable;

public class Message implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String uuid;
	private String msg;
	
	public Message() {
	}
	
	public Message(String uuid, String msg) {
		super();
		this.uuid = uuid;
		this.msg = msg;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
