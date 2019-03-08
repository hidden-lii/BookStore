package com.lii.entity;

public class SearchOrderInfo {
	private int id;
	private String status;
	private String orderTimeFrom;
	private String orderTimeTo;
	private int uid;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOrderTimeFrom() {
		return orderTimeFrom;
	}
	public void setOrderTimeFrom(String orderTimeFrom) {
		this.orderTimeFrom = orderTimeFrom;
	}
	public String getOrderTimeTo() {
		return orderTimeTo;
	}
	public void setOrderTimeTo(String orderTimeTo) {
		this.orderTimeTo = orderTimeTo;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
}
