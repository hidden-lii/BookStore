package com.lii.action;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.lii.entity.Type;
import com.lii.service.TypeService;
import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
public class TypeAction extends ActionSupport implements RequestAware,
ServletRequestAware, ServletResponseAware, SessionAware {
	Map<String, Object> request;
	Map<String, Object> session;
	HttpServletRequest req;
	HttpServletResponse resp;
	
	@Override
	public void setServletResponse(HttpServletResponse resp) {
		this.resp = resp;
	}

	@Override
	public void setServletRequest(HttpServletRequest req) {
		this.req = req;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	@Override
	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}

	@Autowired
	TypeService typeService;
	public void setTypeService(TypeService typeService) {
		this.typeService = typeService;
	}
	
	@Action(value = "/getAllType")
	public void getAllType() throws Exception {
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter out = resp.getWriter();		
		List<Type> typeList = typeService.removeDuplicate(typeService.getAll());
		String jsonTypeList = JSON.toJSONString(typeList);
		System.out.println(jsonTypeList);
		out.println(jsonTypeList);
	}
	
	String page;
	String rows;
	Type type;
	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getRows() {
		return rows;
	}

	public void setRows(String rows) {
		this.rows = rows;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	@Action(value = "/typeList")
	public void typeList() throws Exception {
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		int pageIndex = 1;
		if (page != null) {
			pageIndex = Integer.parseInt(page);
		}
		int pageSize = Integer.parseInt(rows);
		int totalCount = typeService.getTotalCount();
		List<Type> typeList = typeService.getByPage(pageIndex,pageSize);
		String jsonTypeList = JSON.toJSONString(typeList);
		out.println("{\"total\":" + totalCount + ",\"rows\":" + jsonTypeList + "}");
	}
	
	@Action(value = "/addType")
	public void addType() throws Exception {		
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		int result = typeService.addType(type);
		String str = "";
		if (result > 0)
			str = "{\"success\":\"true\",\"message\":\"添加成功！\"}";
		else
			str = "{\"success\":\"false\",\"message\":\"添加失败!\"}";
		out.write(str);
	}
	
	@Action(value = "/updateType")
	public void updateType() throws Exception {		
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		Type editType=typeService.getById(type.getId());	
		editType.setName(type.getName());
		String str = "";
		try {
			typeService.updateType(editType);
			str = "{\"success\":\"true\",\"message\":\"修改成功!\"}";
		} catch (Exception e) {
			str = "{\"success\":\"false\",\"message\":\"修改失败!\"}";
		}
		out.write(str);		
	}
}
