package com.lii.service;

import java.util.List;

import com.lii.entity.BookInfo;

public interface BookInfoService {
	public List<BookInfo> getByPage(int pageIndex,int pageSize,BookInfo bi);
	public BookInfo getById(int id);
	public int getTotalPages(int pageSize,BookInfo bi);
	public int getTotalCount(BookInfo bi);
	public int updateStatus(String ids);
	public int addBookInfo(BookInfo bi);
	public void updateBookInfo(BookInfo bi);
	public List<BookInfo> getOnSaleBook();
	public BookInfo getBookInfoById(int id);
	
	//根据商品ids字符串查询已经浏览过的商品列表
	public List<BookInfo> getBrowsingBookInfo(String ids);
	public List<BookInfo> getAllBookInfo();
}
