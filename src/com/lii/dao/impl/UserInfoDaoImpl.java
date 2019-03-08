package com.lii.dao.impl;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lii.dao.UserInfoDao;
import com.lii.entity.UserInfo;

@Repository("userInfoDAO")
public class UserInfoDaoImpl implements UserInfoDao{

	@Autowired
	SessionFactory sessionFactory;
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public List<UserInfo> search(UserInfo cond) {
		// TODO Auto-generated method stub
		List<UserInfo> uiList = null;
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(UserInfo.class);
		Example example = Example.create(cond);
		c.add(example);
		uiList = c.list();
		return uiList;
	}
	
	@Override
	public List<UserInfo> getValidUser() {
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(UserInfo.class);
		c.add(Restrictions.eq("status", "1"));
		return c.list();
	}

	@Override
	public UserInfo getUserInfoById(int id) {
		String hql = "from UserInfo ui where ui.id="+id; 
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql); 
		return (UserInfo)query.uniqueResult();
	}

	@Override
	public int reg(UserInfo ui) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();	
		return (Integer) session.save(ui);
	}
}
