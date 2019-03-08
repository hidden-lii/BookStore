package com.lii.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lii.entity.CartItemBean;
import com.lii.entity.OrderDetail;
import com.lii.entity.OrderInfo;
import com.lii.entity.UserInfo;
import com.lii.service.OrderDetailService;
import com.lii.service.OrderInfoService;
import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
public class OrderAction extends ActionSupport implements RequestAware,SessionAware{
	
	@Autowired
	OrderDetailService orderDetailService;
	public void setOrderDetailService(OrderDetailService orderDetailService) {
		this.orderDetailService = orderDetailService;
	}
	
	Map<String, Object> session;
	@Override
	public void setSession(Map<String, Object> session) {
		this.session=session;

	}
	Map<String, Object> request;
	@Override
	public void setRequest(Map<String, Object> request) {
		this.request=request;
	}
	
	//处理生成订单请求
	public  String addOrderInfo() {
		OrderInfo orderInfo=new OrderInfo();
		//封装orderInfo的实体对象 
		orderInfo.setStatus("未付款");
		//取得当前系统时间
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
		orderInfo.setOrdertime(sdf.format(new Date()));
		//取得当前登录的用户
		UserInfo userInfo=(UserInfo)session.get("CURRENT_USER");
		orderInfo.setUi(userInfo);
		//取得存放在session当中的sumPrice
		orderInfo.setOrderprice((Double)session.get("sumPrice"));
		//取得购物车对象
		Map cart=(HashMap)session.get("cart");
		Iterator iter=cart.keySet().iterator();
		try {
			while (iter.hasNext()) {
				Object key=  iter.next();
				CartItemBean cartItem=(CartItemBean)cart.get(key);
				//订单明细
				OrderDetail orderDetail=new OrderDetail();
				orderDetail.setBi(cartItem.getBi());
				orderDetail.setNum(cartItem.getQuantity());
				orderDetail.setOi(orderInfo);
				//调用service层的方法添加订单明细
				orderDetailService.addOrderDetail(orderDetail);				
			}		
		} catch (Exception e) {
			e.printStackTrace();
		}
		session.remove("cart");
		//返回字符串
		return "index";
	}
	
	@Autowired
	OrderInfoService orderInfoService;
	public void setOrderInfoService(OrderInfoService orderInfoService) {
		this.orderInfoService = orderInfoService;
	}
	//获取指定用户的订单列表
	public String toMyOrderInfo(){
		UserInfo userInfo=(UserInfo)session.get("CURRENT_USER");
		List myOrderInfoList=orderInfoService.getOrderInfoByUserInfoId(userInfo.getId());
		request.put("myOrderInfoList", myOrderInfoList);
		return "myOrderInfo";
	}
	
	int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	//根据订单信息表的编号获取订单明细列表
	public String toOrderDetail(){
		List orderDetailList=orderDetailService.getOrderDetailById(id);
		request.put("orderDetailList", orderDetailList);
		return "toOrderDetail";
	}
	
	//删除指定编号的订单
	public String deleteOrderInfo(){
		orderInfoService.deleteOrderInfoById(id);
		return "myOrderInfo";
	}
}
