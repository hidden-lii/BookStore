package com.lii.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lii.dao.OrderDetailDao;
import com.lii.entity.OrderDetail;
import com.lii.service.OrderDetailService;

@Service("orderDetailService")
@Transactional
public class OrderDetailServiceImpl implements OrderDetailService{
	
	@Autowired
	OrderDetailDao orderDetailDAO;
	public void setOrderDetailDAO(OrderDetailDao orderDetailDAO) {
		this.orderDetailDAO = orderDetailDAO;
	}
	
	@Override
	public void addOrderDetail(OrderDetail orderDetail) {
		orderDetailDAO.addOrderDetail(orderDetail);
	}

	@Override
	public List getOrderDetailById(int id) {	
		return orderDetailDAO.getOrderDetailById(id);
	}
}
