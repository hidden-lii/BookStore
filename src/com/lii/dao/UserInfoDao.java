package com.lii.dao;

import java.util.List;

import com.lii.entity.UserInfo;

public interface UserInfoDao {
	public List<UserInfo> search(UserInfo cond);
	public List<UserInfo> getValidUser();
	public UserInfo getUserInfoById(int id);
	public int reg(UserInfo ui);
}
