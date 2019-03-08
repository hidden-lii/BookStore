package com.lii.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lii.dao.AdminInfoDao;
import com.lii.entity.AdminInfo;

@Repository("adminInfoDAO")
public class AdminInfoDaoImpl implements AdminInfoDao{
	
	@Autowired
	SessionFactory sessionFactory;
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AdminInfo> search(AdminInfo cond) {
		List<AdminInfo> aiList = null;
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(AdminInfo.class);
		//Example example = Example.create(cond);
		//c.add(example);
		System.out.println(cond);
		c.add(Restrictions.eq("name", cond.getName())).add(Restrictions.eq("pwd", cond.getPwd()));
		aiList=c.list();
		return aiList;
	}
}
