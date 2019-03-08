package com.lii.dao.impl;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lii.dao.TypeDao;
import com.lii.entity.Type;

@Repository("typeDAO")
public class TypeDaoImpl implements TypeDao{
	

	@Autowired
	SessionFactory sessionFactory;
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<Type> getAll() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(Type.class);
		Iterator<Type> it=c.list().iterator();
		while(it.hasNext()) {
			System.out.println(it.next());
		}
		return c.list();
	}

	@Override
	public Type getById(int id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Type type = session.get(Type.class, id);
		return type;
	}

	@Override
	public List<Type> getByPage(int pageIndex, int pageSize) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(Type.class);
		int startIndex = (pageIndex - 1) * pageSize;
		c.setFirstResult(startIndex);
		c.setMaxResults(pageSize);
		return c.list();
	}

	@Override
	public int getTotalPages(int pageSize) {
		// TODO Auto-generated method stub
		int count = 0;
		int totalPagas = 0;
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(Type.class);
		ProjectionList tList = Projections.projectionList();
		tList.add(Projections.rowCount());
		c.setProjection(tList);
		count = ((Long) c.uniqueResult()).intValue();
		totalPagas = (count % pageSize == 0) ? (count / pageSize) : (count
				/ pageSize + 1);
		return totalPagas;
	}

	@Override
	public int getTotalCount() {
		// TODO Auto-generated method stub
		int count = 0;
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(Type.class);
		ProjectionList tList = Projections.projectionList();
		tList.add(Projections.rowCount());
		c.setProjection(tList);
		count = ((Long) c.uniqueResult()).intValue();
		return count;
	}

	@Override
	public int add(Type type) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return (Integer) session.save(type);
	}

	@Override
	public void update(Type type) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.merge(type);
	}

}
