package com.lii.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.lii.entity.BookInfo;
import com.lii.entity.OrderDetail;
import com.lii.entity.OrderInfo;
import com.lii.service.BookInfoService;
import com.lii.service.OrderInfoService;
import com.lii.service.UserInfoService;
import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
public class OrderInfoAction extends ActionSupport implements RequestAware,ServletRequestAware, ServletResponseAware, SessionAware{
	
	OrderInfo oi;
	public OrderInfo getOi() {
		return oi;
	}

	public void setOi(OrderInfo oi) {
		this.oi = oi;
	}

	@Autowired
	OrderInfoService orderInfoService;
	@Autowired
	UserInfoService userInfoService;
	@Autowired
	BookInfoService BookInfoService;
	
	
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
	
	String page;
	String rows;

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

	private int id;
	private String status;
	private String ordertime;
	private double orderprice;	
	private String orderTimeFrom;
	private String orderTimeTo;
	private int uid;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOrdertime() {
		return ordertime;
	}

	public void setOrdertime(String ordertime) {
		this.ordertime = ordertime;
	}

	public double getOrderprice() {
		return orderprice;
	}

	public void setOrderprice(double orderprice) {
		this.orderprice = orderprice;
	}

	public String getOrderTimeFrom() {
		return orderTimeFrom;
	}

	public void setOrderTimeFrom(String orderTimeFrom) {
		this.orderTimeFrom = orderTimeFrom;
	}

	public String getOrderTimeTo() {
		return orderTimeTo;
	}

	public void setOrderTimeTo(String orderTimeTo) {
		this.orderTimeTo = orderTimeTo;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	@Action(value = "/getAllOrderInfo")
	public void getAllOrderInfo() throws Exception{
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		int pageIndex = 1;
		if (page != null) {
			pageIndex = Integer.parseInt(page);
		}
		int pageSize = Integer.parseInt(rows);
		int totalCount =orderInfoService.getTotalCount(oi);
		List<OrderInfo> oiList=orderInfoService.getAllOrderInfoByPage(pageIndex, pageSize, oi);
		String jsonPiList = JSON.toJSONString(oiList,SerializerFeature.DisableCircularReferenceDetect);
		out.println("{\"total\":" + totalCount + ",\"rows\":" + jsonPiList+ "}");
	}
	
	@Action(value = "/getOrderInfo")
	public ModelAndView getOrderInfo(String oid) {
		String viewName = "modifyorder";
		ModelAndView mv = new ModelAndView(viewName);
		OrderInfo oi = orderInfoService.getOrderInfoById(Integer.parseInt(oid));
		// System.out.print(oi.getOds().iterator().next().getNum());
		mv.addObject("oi", oi);
		return mv;
	}
	
	@Action(value = "/getOrderDetails")
	public List<OrderDetail> getOrderDetails(String oid) {
		List<OrderDetail> ods = orderInfoService.getOrderDetailByOid(Integer
				.parseInt(oid));
		for (OrderDetail od : ods) {
			od.setBid(od.getBi().getId());
			od.setPrice(od.getBi().getPrice());
			od.setTotalprice(od.getBi().getPrice() * od.getNum());
		}
		return ods;
	}
	
	@Action(value = "/commitOrder")
	public String commitOrder(String inserted, String orderinfo) throws JsonParseException, JsonMappingException, IOException {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
			OrderInfo oi = mapper.readValue(orderinfo, OrderInfo[].class)[0];
			oi.setUi(userInfoService.getUserInfoById(oi.getUid()));
			List<OrderDetail> odList = mapper.readValue(inserted,
					new TypeReference<ArrayList<OrderDetail>>() {
					});
			BookInfo bi = null;
			double orderPrice = 0;
			for (OrderDetail od : odList) {
				bi = BookInfoService.getBookInfoById(od.getBid());
				orderPrice += bi.getPrice() * od.getNum();
				od.setOi(oi);
				od.setBi(bi);
				oi.getOds().add(od);
			}
			oi.setOrderprice(orderPrice);
			if (orderInfoService.addOrder(oi) > 0)
				return "success";
			else
				return "failure";
		} catch (Exception e) {
			return "failure";
		}
	}
	
	@Action(value = "/commitModifyOrder")
	public String commitModifyOrder(String orderinfo, String inserted,
			String updated, String deleted) throws JsonParseException,
			JsonMappingException, IOException {
		try {
			List<OrderDetail> insertedOdList = null;
			List<OrderDetail> updatedOdList = null;
			List<OrderDetail> deletedOdList = null;
			ObjectMapper mapper = new ObjectMapper();
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
			OrderInfo tempoi = mapper.readValue(orderinfo, OrderInfo[].class)[0];
			OrderInfo oi = orderInfoService.getOrderInfoById(tempoi.getId());
			oi.setUi(userInfoService.getUserInfoById(tempoi.getUid()));
			oi.setStatus(tempoi.getStatus());
			oi.setOrdertime(tempoi.getOrdertime());
			oi.setOrderprice(tempoi.getOrderprice());
			if (deleted != null) {
				deletedOdList = mapper.readValue(deleted,
						new TypeReference<ArrayList<OrderDetail>>() {
						});
				for (OrderDetail dod : deletedOdList) {
					Set<OrderDetail> odset = oi.getOds();
					Iterator<OrderDetail> itor = odset.iterator();
					List<OrderDetail> delods = new ArrayList<OrderDetail>();
					while (itor.hasNext()) {						
						OrderDetail odd = itor.next();
						if (dod.getId() == odd.getId()) {
							orderInfoService.deleteOrderDetail(odd);
							delods.add(odd);
						}
					}
					for (OrderDetail delod : delods) {
						oi.getOds().remove(delod);
					}
				}
			}
			if (updated != null) {
				updatedOdList = mapper.readValue(updated,
						new TypeReference<ArrayList<OrderDetail>>() {
						});
				for (OrderDetail uod : updatedOdList) {
					Set<OrderDetail> odset = oi.getOds();
					Iterator<OrderDetail> itor = odset.iterator();
					List<OrderDetail> removeods = new ArrayList<OrderDetail>();
					List<OrderDetail> addods = new ArrayList<OrderDetail>();
					while (itor.hasNext()) {
						OrderDetail odd = itor.next();
						if (uod.getId() == odd.getId()) {
							removeods.add(odd);  
							uod.setBi(BookInfoService.getBookInfoById(uod.getBid()));
							addods.add(uod);
						}
					}
					for (OrderDetail removeod : removeods) {
						oi.getOds().remove(removeod);
					}
					for (OrderDetail addod : addods) {
						oi.getOds().add(addod);
					}
				}
			}
			if (inserted != null){
				insertedOdList = mapper.readValue(inserted,new TypeReference<ArrayList<OrderDetail>>() {});
				BookInfo bi = null;
				double orderPrice = 0;
				for (OrderDetail iod : insertedOdList) {
					bi = BookInfoService.getBookInfoById(iod.getBid());
					orderPrice += bi.getPrice() * iod.getNum();
					iod.setOi(oi);
					iod.setBi(bi);
					oi.getOds().add(iod);
				}
			}
			if(oi.getOds().size()==0){
				orderInfoService.deleteOrder(""+oi.getId());
			}else{ 
				orderInfoService.modifyOrder(oi);
			}			
		} catch (Exception e) {
			return "failure";
		}
		return "success";
	}
	
	@Action(value = "/deleteOrder")
	public void deleteOrder() throws IOException {
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		String oids = req.getParameter("oids");
		oids = oids.substring(0, oids.length() - 1);
		int result=orderInfoService.deleteOrder(oids);
		String str = "";
		if (result > 0)
			str = "{\"success\":\"true\",\"message\":\"删除成功!\"}";
		else
			str = "{\"success\":\"false\",\"message\":\"删除失败!\"}";
		out.write(str);
	}
}
