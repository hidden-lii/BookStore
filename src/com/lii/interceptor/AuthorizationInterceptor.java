package com.lii.interceptor;

import java.util.Map;

import com.lii.entity.UserInfo;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class AuthorizationInterceptor extends AbstractInterceptor{

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		// TODO Auto-generated method stub
		Map session=invocation.getInvocationContext().getSession();
		UserInfo userInfo=(UserInfo)session.get("CURRENT_USER");
		if (session==null) {
			return "login";
		}else {
			if (userInfo==null) {
				return Action.LOGIN;
			}else {
				return invocation.invoke();
			}
		}
	}

}
