package com.lii.dao;

import java.util.List;

import com.lii.entity.AdminInfo;

public interface AdminInfoDao {
	public List<AdminInfo> search(AdminInfo cond);
}
