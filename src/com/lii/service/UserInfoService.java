package com.lii.service;

import java.util.List;

import com.lii.entity.UserInfo;

public interface UserInfoService {
	public List<UserInfo> login(UserInfo cond);
	public List<UserInfo> getValidUser();
	public UserInfo getUserInfoById(int id);
	public int reg(UserInfo ui);
}
