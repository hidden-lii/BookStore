package com.lii.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lii.dao.OrderInfoDao;
import com.lii.entity.OrderDetail;
import com.lii.entity.OrderInfo;
import com.lii.service.OrderInfoService;

@Service("orderInfoService")
@Transactional
public class OrderInfoServiceImpl implements OrderInfoService{
	
	@Autowired
	OrderInfoDao orderInfoDAO;	
	public void setOrderInfoDAO(OrderInfoDao orderInfoDAO) {
		this.orderInfoDAO = orderInfoDAO;
	}

	@Override
	public List<OrderInfo> getAllOrderInfoByPage(int pageIndex, int pageSize, OrderInfo oi) {
		return orderInfoDAO.getAllOrderInfoByPage(pageIndex,pageSize,oi);
	}

	@Override
	public int getTotalPages(int pageSize, OrderInfo oi) {
		return orderInfoDAO.getTotalPages(pageSize,oi);
	}

	@Override
	public int getTotalCount(OrderInfo oi) {		
		return orderInfoDAO.getTotalCount(oi);
	}

	@Override
	public int addOrder(OrderInfo oi) {
		return orderInfoDAO.addOrder(oi);
	}

	@Override
	public int deleteOrder(String oids) {
		return orderInfoDAO.deleteOrder(oids);
	}

	@Override
	public OrderInfo getOrderInfoById(int id) {
		return orderInfoDAO.getOrderInfoById(id);
	}

	@Override
	public List<OrderDetail> getOrderDetailByOid(int oid) {		
		return orderInfoDAO.getOrderDetailByOid(oid);
	}

	@Override
	public int modifyOrder(OrderInfo oi) {
		return orderInfoDAO.modifyOrder(oi);
	}

	@Override
	public int deleteOrderDetail(OrderDetail od) {
		// TODO Auto-generated method stub
		return orderInfoDAO.deleteOrderDetail(od);
	}
	
	@Override
	public List getOrderInfoByUserInfoId(int userInfoId) {
		
		return orderInfoDAO.getOrderInfoByUserInfoId(userInfoId);
	}

	@Override
	public void deleteOrderInfoById(int id) {
		OrderInfo orderInfo=orderInfoDAO.getOrderInfoById(id);
		orderInfoDAO.deleteOrderInfo(orderInfo);
	}
}
