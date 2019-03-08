package com.lii.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.lii.dao.BookInfoDao;
import com.lii.entity.BookInfo;

@Repository("bookInfoDAO")
public class BookInfoDaoImpl implements BookInfoDao{

	@Autowired
	SessionFactory sessionFactory;
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<BookInfo> getByPage(int pageIndex, int pageSize,BookInfo bi) {
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(BookInfo.class);	
		if (bi != null) {
			if (bi.getCode() != null && !"".equals(bi.getCode())) {
				c.add(Restrictions.eq("code", bi.getCode()));
			} else {
				if (bi.getName() != null && !"".equals(bi.getName())) {
					c.add(Restrictions.like("name", bi.getName(),
							MatchMode.ANYWHERE));
				}
				if (bi.getPress() != null && !"".equals(bi.getPress())) {
					c.add(Restrictions.like("press", bi.getPress(),
							MatchMode.ANYWHERE));
				}
				if (bi.getAuthor() != null && !"".equals(bi.getAuthor())) {
					c.add(Restrictions.like("author", bi.getAuthor(),
							MatchMode.ANYWHERE));
				}
				if (bi.getType() != null && bi.getType().getId() > 0) {
					c.add(Restrictions.eq("type.id", bi.getType().getId()));
				}
				if (bi.getPriceFrom() > 0) {
					c.add(Restrictions.gt("price", bi.getPriceFrom()));
				}
				if (bi.getPriceTo() > 0) {
					c.add(Restrictions.le("price", bi.getPriceTo()));
				}
			}
		}
		int startIndex = (pageIndex - 1) * pageSize;
		c.setFirstResult(startIndex);
		c.setMaxResults(pageSize);
		return c.list();
	}

	@Override
	public int getTotalPages(int pageSize, BookInfo bi) {
		int count = 0;
		int totalPagas = 0;
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(BookInfo.class);
		ProjectionList pList = Projections.projectionList();
		pList.add(Projections.rowCount());
		c.setProjection(pList);
		if (bi != null) {
			if (bi.getCode() != null && !"".equals(bi.getCode())) {
				c.add(Restrictions.eq("code", bi.getCode()));
			} else {
				if (bi.getName() != null && !"".equals(bi.getName())) {
					c.add(Restrictions.like("name", bi.getName(),
							MatchMode.ANYWHERE));
				}
				if (bi.getPress() != null && !"".equals(bi.getPress())) {
					c.add(Restrictions.like("press", bi.getPress(),
							MatchMode.ANYWHERE));
				}
				if (bi.getAuthor() != null && !"".equals(bi.getAuthor())) {
					c.add(Restrictions.like("author", bi.getAuthor(),
							MatchMode.ANYWHERE));
				}
				if (bi.getType() != null && bi.getType().getId() > 0) {
					c.add(Restrictions.eq("type.id", bi.getType().getId()));
				}
				if (bi.getPriceFrom() > 0) {
					c.add(Restrictions.gt("price", bi.getPriceFrom()));
				}
				if (bi.getPriceTo() > 0) {
					c.add(Restrictions.le("price", bi.getPriceTo()));
				}
			}
		}
		count = ((Long) c.uniqueResult()).intValue();
		totalPagas = (count % pageSize == 0) ? (count / pageSize) : (count/ pageSize + 1);
		return totalPagas;
	}

	@Override
	public int getTotalCount(BookInfo bi) {
		int count = 0;
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(BookInfo.class);
		ProjectionList pList = Projections.projectionList();
		pList.add(Projections.rowCount());
		c.setProjection(pList);

		if (bi != null) {
			if (bi.getCode() != null && !"".equals(bi.getCode())) {
				c.add(Restrictions.eq("code", bi.getCode()));
			} else {
				if (bi.getName() != null && !"".equals(bi.getName())) {
					c.add(Restrictions.like("name", bi.getName(),
							MatchMode.ANYWHERE));
				}
				if (bi.getPress() != null && !"".equals(bi.getPress())) {
					c.add(Restrictions.like("press", bi.getPress(),
							MatchMode.ANYWHERE));
				}
				if (bi.getAuthor() != null && !"".equals(bi.getAuthor())) {
					c.add(Restrictions.like("author", bi.getAuthor(),
							MatchMode.ANYWHERE));
				}
				if (bi.getType() != null && bi.getType().getId() > 0) {
					c.add(Restrictions.eq("type.id", bi.getType().getId()));
				}
				if (bi.getPriceFrom() > 0) {
					c.add(Restrictions.gt("price", bi.getPriceFrom()));
				}
				if (bi.getPriceTo() > 0) {
					c.add(Restrictions.le("price", bi.getPriceTo()));
				}
			}
		}
		count = ((Long) c.uniqueResult()).intValue();
		return count;
	}

	@Override
	public int updateStatus(String ids) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "update BookInfo bi set bi.status=0 where bi.id in "
				+ ids;
		Query query = session.createQuery(hql);
		return query.executeUpdate();
	}

	@Override
	public int add(BookInfo bi) {
		Session session = sessionFactory.getCurrentSession();
		return (Integer) session.save(bi);
	}

	@Override
	public void update(BookInfo bi) {
		Session session = sessionFactory.getCurrentSession();
		session.merge(bi);
	}

	@Override
	public BookInfo getById(int id) {
		Session session = sessionFactory.getCurrentSession();
		BookInfo bi = session.get(BookInfo.class, id);
		return bi;
	}
	
	@Override
	public List<BookInfo> getOnSaleBook() {	
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(BookInfo.class);
		c.add(Restrictions.eq("status", 1));
		return c.list();
	}

	@Override
	public BookInfo getBookInfoById(int id) {
		Session session = sessionFactory.getCurrentSession();
		BookInfo bi = session.get(BookInfo.class, id);
		return bi;
	}
	
	@Override
	public List<BookInfo> getByBids(String bids) {
		Session session=sessionFactory.getCurrentSession();	
		Query query=session.createQuery("from BookInfo bi where bi.id in"+bids);
		return query.list();
	}
	
	@Override
	public List<BookInfo> getAll() {		
		return sessionFactory.getCurrentSession().createCriteria(BookInfo.class).list();
	}
}
