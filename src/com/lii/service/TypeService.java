package com.lii.service;

import java.util.List;

import com.lii.entity.Type;

public interface TypeService {
	public List<Type> getAll();
	public Type getById(int id);
	public List<Type> getByPage(int pageIndex,int pageSize);	
	public int getTotalPages(int pageSize);
	public int getTotalCount();
	public int addType(Type type);
	public void updateType(Type type);
	
	public List<Type> getAllWithDistinctPress();
	List<Type> removeDuplicate(List<Type> list);
}
