package com.lii.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lii.dao.AdminInfoDao;
import com.lii.entity.AdminInfo;
import com.lii.service.AdminInfoService;

@Service("adminInfoService")
@Transactional
public class AdminInfoServiceImpl implements AdminInfoService{

	@Autowired
	AdminInfoDao adminInfoDAO;	
	
	@Override
	public List<AdminInfo> login(AdminInfo cond) {
		return adminInfoDAO.search(cond);
	}
}
