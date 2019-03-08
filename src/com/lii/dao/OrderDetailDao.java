package com.lii.dao;

import java.util.List;

import com.lii.entity.OrderDetail;

public interface OrderDetailDao {
	//生成订单明细表（订单子表）
	public void addOrderDetail(OrderDetail orderDetail);	
	//根据订单信息表的编号获取订单明细列表
	public List getOrderDetailById(int id);
}
