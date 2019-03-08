package com.lii.dao.impl;

import java.util.List;

import javax.transaction.Transaction;

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

import com.lii.dao.OrderInfoDao;
import com.lii.entity.OrderDetail;
import com.lii.entity.OrderInfo;

@Repository("orderInfoDAO")
public class OrderInfoDaoImpl implements OrderInfoDao{
	
	@Autowired
	SessionFactory sessionFactory;
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<OrderInfo> getAllOrderInfoByPage(int pageIndex, int pageSize,OrderInfo oi) {
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(OrderInfo.class);
		if (oi != null) {
			if (oi.getId() != 0) {
				c.add(Restrictions.eq("id", oi.getId()));
			} else {
				if (oi.getStatus() != null) {
					c.add(Restrictions.like("status", oi.getStatus(),MatchMode.ANYWHERE));
				}
				if (oi.getOrderTimeFrom() != null&& !"".equals(oi.getOrderTimeFrom())) {
					c.add(Restrictions.like("orderTimeFrom", oi.getOrderTimeFrom(),MatchMode.ANYWHERE));
				}
				if (oi.getOrderTimeTo() != null&& !"".equals(oi.getOrderTimeTo())) {
					c.add(Restrictions.like("author",oi.getOrderTimeTo(),MatchMode.ANYWHERE));
				}
				if (oi.getOrderTimeTo() != null&& !"".equals(oi.getOrderTimeTo())) {
					c.add(Restrictions.eq("orderTimeTo", oi.getOrderTimeTo()));
				}
				if (oi.getUid() > 0) {
					c.add(Restrictions.gt("uid", oi.getUid()));
				}	
			}
		}
		int startIndex = (pageIndex - 1) * pageSize;
		c.setFirstResult(startIndex);
		c.setMaxResults(pageSize);
		return c.list();
	}

	@Override
	public int getTotalPages(int pageSize, OrderInfo oi) {
		int count = 0;
		int totalPagas = 0;
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(OrderInfo.class);
		if (oi != null) {
			if (oi.getId() != 0) {
				c.add(Restrictions.eq("id", oi.getId()));
			} else {
				if (oi.getStatus() != null) {
					c.add(Restrictions.like("status", oi.getStatus(),MatchMode.ANYWHERE));
				}
				if (oi.getOrderTimeFrom() != null&& !"".equals(oi.getOrderTimeFrom())) {
					c.add(Restrictions.like("orderTimeFrom", oi.getOrderTimeFrom(),MatchMode.ANYWHERE));
				}
				if (oi.getOrderTimeTo() != null&& !"".equals(oi.getOrderTimeTo())) {
					c.add(Restrictions.like("author",oi.getOrderTimeTo(),MatchMode.ANYWHERE));
				}
				if (oi.getOrderTimeTo() != null&& !"".equals(oi.getOrderTimeTo())) {
					c.add(Restrictions.eq("orderTimeTo", oi.getOrderTimeTo()));
				}
				if (oi.getUid() > 0) {
					c.add(Restrictions.gt("uid", oi.getUid()));
				}	
			}
		}
		count = ((Long) c.uniqueResult()).intValue();
		totalPagas = (count % pageSize == 0) ? (count / pageSize) : (count/ pageSize + 1);
		return totalPagas;
	}

	@Override
	public int getTotalCount(OrderInfo oi) {
		int count = 0;
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(OrderInfo.class);
		ProjectionList pList = Projections.projectionList();
		pList.add(Projections.rowCount());
		c.setProjection(pList);
		if (oi != null) {
			if (oi.getId() != 0) {
				c.add(Restrictions.eq("id", oi.getId()));
			} else {
				if (oi.getStatus() != null) {
					c.add(Restrictions.like("status", oi.getStatus(),MatchMode.ANYWHERE));
				}
				if (oi.getOrderTimeFrom() != null&& !"".equals(oi.getOrderTimeFrom())) {
					c.add(Restrictions.like("orderTimeFrom", oi.getOrderTimeFrom(),MatchMode.ANYWHERE));
				}
				if (oi.getOrderTimeTo() != null&& !"".equals(oi.getOrderTimeTo())) {
					c.add(Restrictions.like("author",oi.getOrderTimeTo(),MatchMode.ANYWHERE));
				}
				if (oi.getOrderTimeTo() != null&& !"".equals(oi.getOrderTimeTo())) {
					c.add(Restrictions.eq("orderTimeTo", oi.getOrderTimeTo()));
				}
				if (oi.getUid() > 0) {
					c.add(Restrictions.gt("uid", oi.getUid()));
				}	
			}
		}
		count = ((Long) c.uniqueResult()).intValue();
		return count;
	}

	@Override
	public int addOrder(OrderInfo oi) {
		Session session = sessionFactory.getCurrentSession();
		return (Integer) session.save(oi);
	}

	@Override
	public int deleteOrder(String oids) {	
		Session session = sessionFactory.getCurrentSession();
		Query query =session.createQuery("delete OrderInfo where id in "+oids);
		return query.executeUpdate();
	}

	@Override
	public OrderInfo getOrderInfoById(int id) {
		Session session = sessionFactory.getCurrentSession();
		OrderInfo oi = session.get(OrderInfo.class, id);
		return oi;
	}

	@Override
	public List<OrderDetail> getOrderDetailByOid(int oid) {
		String hql = "from OrderDetail od where od.oi.id=" + oid;
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
		return query.list();
	}
	
	@Override
	public int modifyOrder(OrderInfo oi) {		
		Session session = sessionFactory.getCurrentSession();
		try {
			session.saveOrUpdate(oi);
		} catch (Exception e) {
			return 0;
		}
		return 1;		
	}

	@Override
	public int deleteOrderDetail(OrderDetail od) {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.delete(od);
		} catch (Exception e) {
			return 0;
		}
		return 1;
	}
	
	@Override
	public List getOrderInfoByUserInfoId(int userInfoId) {
		Session session=sessionFactory.getCurrentSession();
		Criteria c=session.createCriteria(OrderInfo.class);
		c.add(Restrictions.eq("ui.id",userInfoId));
		return c.list();
	}
	
	@Override
	public void deleteOrderInfo(OrderInfo orderInfo) {
		Session session=sessionFactory.getCurrentSession();
		session.delete(orderInfo);
	}
}
