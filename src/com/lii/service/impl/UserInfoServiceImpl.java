package com.lii.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lii.dao.UserInfoDao;
import com.lii.entity.UserInfo;
import com.lii.service.UserInfoService;

@Service("userInfoService")
@Transactional
public class UserInfoServiceImpl implements UserInfoService{

	@Autowired
	UserInfoDao userInfoDAO;
	
	@Override
	public List<UserInfo> login(UserInfo cond) {
		// TODO Auto-generated method stub
		return userInfoDAO.search(cond);
	}
	
	@Override
	public List<UserInfo> getValidUser() {
		return userInfoDAO.getValidUser();
	}

	@Override
	public UserInfo getUserInfoById(int id) {
		return userInfoDAO.getUserInfoById(id);
	}

	@Override
	public int reg(UserInfo ui) {
		// TODO Auto-generated method stub
		return userInfoDAO.reg(ui);
	}
}
