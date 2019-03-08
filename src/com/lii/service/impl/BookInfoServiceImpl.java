package com.lii.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lii.dao.BookInfoDao;
import com.lii.entity.BookInfo;
import com.lii.service.BookInfoService;

@Service("bookInfoService")
@Transactional
public class BookInfoServiceImpl implements BookInfoService{
	
	@Autowired
	BookInfoDao bookInfoDAO;
	public void setBookInfoDAO(BookInfoDao bookInfoDAO) {
		this.bookInfoDAO = bookInfoDAO;
	}

	@Override
	public List<BookInfo> getByPage(int pageIndex, int pageSize,BookInfo bi) {
		return bookInfoDAO.getByPage(pageIndex, pageSize,bi);
	}

	@Override
	public int getTotalPages(int pageSize,BookInfo bi) {		
		return bookInfoDAO.getTotalPages(pageSize,bi);
	}

	@Override
	public int getTotalCount(BookInfo bi) {		
		return bookInfoDAO.getTotalCount(bi);
	}

	@Override
	public int updateStatus(String ids) {
		return bookInfoDAO.updateStatus(ids);
	}

	@Override
	public int addBookInfo(BookInfo bi) {
		return bookInfoDAO.add(bi);
	}

	@Override
	public void updateBookInfo(BookInfo bi) {
		bookInfoDAO.update(bi);		
	}

	@Override
	public BookInfo getById(int id) {
		return bookInfoDAO.getById(id);
	}
	
	@Override
	public List<BookInfo> getOnSaleBook() {
		return bookInfoDAO.getOnSaleBook();
	}

	@Override
	public BookInfo getBookInfoById(int id) {
		return bookInfoDAO.getBookInfoById(id);
	}
	
	@Override
	public List<BookInfo> getBrowsingBookInfo(String ids) {	
		return bookInfoDAO.getByBids(ids);
	}

	@Override
	public List<BookInfo> getAllBookInfo() {
		// TODO Auto-generated method stub
		return bookInfoDAO.getAll();
	}
}
