package com.lii.dao.impl;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lii.dao.OrderDetailDao;
import com.lii.entity.OrderDetail;

@Repository("orderDetailDAO")
public class OrderDetailDaoImpl implements OrderDetailDao{
	
	@Autowired
	SessionFactory sessionFactory;
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public void addOrderDetail(OrderDetail orderDetail) {
		Session session=sessionFactory.getCurrentSession();
		session.save(orderDetail);
	}

	@Override
	public List getOrderDetailById(int id) {
		Session session=sessionFactory.getCurrentSession();
		Criteria c=session.createCriteria(OrderDetail.class);
		c.add(Restrictions.eq("oi.id", id));
		return c.list();
	}
}
