package com.lii.dao;

import java.util.List;

import com.lii.entity.OrderDetail;
import com.lii.entity.OrderInfo;

public interface OrderInfoDao {
	public List<OrderInfo> getAllOrderInfoByPage(int pageIndex, int pageSize,OrderInfo oi);
	public OrderInfo getOrderInfoById(int id);
	public int getTotalPages(int pageSize, OrderInfo oi);
	public int getTotalCount(OrderInfo oi);
	public int addOrder(OrderInfo oi);	
	public int modifyOrder(OrderInfo oi);
	public int deleteOrder(String oids);
	public List<OrderDetail> getOrderDetailByOid(int oid);
	public int deleteOrderDetail(OrderDetail od);
	
	//获取指定用户的订单列表
	public List getOrderInfoByUserInfoId(int userInfoId);
	//删除订单对象
	public void deleteOrderInfo(OrderInfo orderInfo);
}
