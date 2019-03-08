package com.lii.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lii.entity.UserInfo;
import com.lii.service.UserInfoService;
import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
public class UserInfoAction  extends ActionSupport implements SessionAware{
	UserInfo ui;

	public UserInfo getUi() {
		return ui;
	}

	public void setUi(UserInfo ui) {
		this.ui = ui;
	}

	@Autowired
	UserInfoService userInfoService;


	@Action(value = "/doLogin", results = {
			@Result(name = "index", type="redirectAction", location = "list"),
			@Result(name = "login", type="redirect", location = "/login.jsp") })
	public String doLogin() throws Exception {
		List<UserInfo> uiList = userInfoService.login(ui);
		if (uiList.size() > 0) {
			session.put("CURRENT_USER",uiList.get(0));
			return "index";
		} else {
			return "login";
		}

	}
	
	@Action(value ="/getValidUser")
	public List<UserInfo> getValidUser() {
		UserInfo ui=new UserInfo();
		ui.setId(0);
		ui.setUserName("Lii");
		//List<UserInfo> tempUiList=new ArrayList<UserInfo>();
		//tempUiList.add(ui);
		List<UserInfo> uiList = userInfoService.getValidUser();
		uiList.add(0, ui);
		return uiList;
	}
	
	Map<String, Object> session;
	@Override
	public void setSession(Map<String, Object> session) {
		this.session=session;		
	}
	
	public String logOut(){
		UserInfo userInfo=(UserInfo)session.get("CURRENT_USER");
		if (userInfo!=null) {
			session.remove("CURRENT_USER");
		}
		return "index";
	}
	
	@Action(value = "/doReg", results = {
			@Result(name = "success", type="redirect", location = "/login.jsp"),
			@Result(name = "failed", type="redirect", location = "/reg.jsp") })
	public String getReg() {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(ui!=null) {
			ui.setRegDate(sdf.format(new Date()));
		}
		int i=userInfoService.reg(ui);
		if(i==0)
			return "failed";
		else
			return "success";
	}
}
