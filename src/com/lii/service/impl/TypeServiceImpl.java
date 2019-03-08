package com.lii.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lii.dao.TypeDao;
import com.lii.entity.BookInfo;
import com.lii.entity.Type;
import com.lii.service.TypeService;

@Service("typeService")
@Transactional
public class TypeServiceImpl implements TypeService{
	
	@Autowired
	TypeDao typeDAO;
	public void setTypeDAO(TypeDao typeDAO) {
		this.typeDAO = typeDAO;
	}

	@Override
	public List<Type> getAll() {
		// TODO Auto-generated method stub
		return typeDAO.getAll();
	}

	@Override
	public List<Type> getByPage(int pageIndex, int pageSize) {
		// TODO Auto-generated method stub
		return typeDAO.getByPage(pageIndex, pageSize);
	}

	@Override
	public int getTotalPages(int pageSize) {
		// TODO Auto-generated method stub
		return typeDAO.getTotalPages(pageSize);
	}

	@Override
	public int getTotalCount() {
		// TODO Auto-generated method stub
		return typeDAO.getTotalCount();
	}

	@Override
	public int addType(Type type) {
		// TODO Auto-generated method stub
		return typeDAO.add(type);
	}

	@Override
	public void updateType(Type type) {
		typeDAO.update(type);		
	}

	@Override
	public Type getById(int id) {
		return typeDAO.getById(id);
	}
	
	@Override
	public List<Type> removeDuplicate(List<Type> list){
		HashSet<Type> hs=new HashSet<>(list);
		list.clear();
		list.addAll(hs);
		return list;
	}
	
	@Override
	public List<Type> getAllWithDistinctPress() {
		List<Type> typeList=removeDuplicate(typeDAO.getAll());
		for (Type type : typeList) {
			List<String> presses=new ArrayList<String>();
			Object[] bis=type.getBis().toArray();
			for (int i = 0; i < bis.length; i++) {
				BookInfo bi=(BookInfo)bis[i];
				if(presses.contains(bi.getPress())){
					type.getBis().remove(bi);					
				}else {
					presses.add(bi.getPress());
				}
			}
		}		
		return typeList;
	}
}
