package com.lii.entity;

public class CartItemBean {
	private BookInfo bi;
	private int quantity;
	public BookInfo getBi() {
		return bi;
	}
	public void setBi(BookInfo bi) {
		this.bi = bi;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public CartItemBean(BookInfo bi, int quantity) {
		super();
		this.bi = bi;
		this.quantity = quantity;
	}
}
