package com.lii.service;

import java.util.List;

import com.lii.entity.AdminInfo;

public interface AdminInfoService {
	public List<AdminInfo> login(AdminInfo cond);
}
