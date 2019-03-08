package com.lii.dao;

import java.util.List;

import com.lii.entity.BookInfo;

public interface BookInfoDao {
	public List<BookInfo> getByPage(int pageIndex,int pageSize,BookInfo bi);
	public BookInfo getById(int id);
	public int getTotalPages(int pageSize,BookInfo bi);
	public int getTotalCount(BookInfo bi);
	public int updateStatus(String ids);
	public int add(BookInfo bi);
	public void update(BookInfo bi);
	public List<BookInfo> getOnSaleBook();
	public BookInfo getBookInfoById(int id);
	//根据商品id的字符串查询商品列表
	public List<BookInfo> getByBids(String bids);
	public List<BookInfo> getAll();
}
