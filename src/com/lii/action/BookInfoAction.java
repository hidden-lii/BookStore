package com.lii.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lii.entity.BookInfo;
import com.lii.entity.Type;
import com.lii.service.BookInfoService;
import com.lii.service.TypeService;
import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
public class BookInfoAction extends ActionSupport implements RequestAware,
ServletRequestAware, ServletResponseAware, SessionAware {

	BookInfo bi;
	public BookInfo getBi() {
		return bi;
	}
	public void setBi(BookInfo bi) {
		this.bi = bi;
	}

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
	BookInfoService bookInfoService;

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

	private String code;
	private String name;
	private String press;
	private double priceFrom;
	private double priceTo;
	private int tid;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPress() {
		return press;
	}

	public void setPress(String press) {
		this.press = press;
	}

	public double getPriceFrom() {
		return priceFrom;
	}

	public void setPriceFrom(double priceFrom) {
		this.priceFrom = priceFrom;
	}

	public double getPriceTo() {
		return priceTo;
	}

	public void setPriceTo(double priceTo) {
		this.priceTo = priceTo;
	}

	@Action(value = "/bookList")
	public void BookList() throws Exception {
		// SearchBookInfo sbi=new SearchBookInfo();
		// BeanUtils.copyProperties(this, sbi);
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		int pageIndex = 1;
		if (page != null) {
			pageIndex = Integer.parseInt(page);
		}
		int pageSize = Integer.parseInt(rows);
		int totalCount = bookInfoService.getTotalCount(bi);
		List<BookInfo> biList = bookInfoService.getByPage(pageIndex,pageSize, bi);
		String jsonPiList = JSON.toJSONString(biList,SerializerFeature.DisableCircularReferenceDetect);
		out.println("{\"total\":" + totalCount + ",\"rows\":" + jsonPiList+ "}");
	}

	@Action(value = "/updateStatus")
	public void updateStatus() throws Exception {
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		String id = req.getParameter("id");
		id = "(" + id.substring(0, id.length() - 1) + ")";
		int result = bookInfoService.updateStatus(id);
		String str = "";
		if (result > 0)
			str = "{\"success\":\"true\",\"message\":\"修改成功!\"}";
		else
			str = "{\"success\":\"false\",\"message\":\"修改失败!\"}";
		out.write(str);
	}

	private File pic;
	private String picFileName;
	private String picContentType;

	public File getPic() {
		return pic;
	}

	public void setPic(File pic) {
		this.pic = pic;
	}

	public String getPicFileName() {
		return picFileName;
	}

	public void setPicFileName(String picFileName) {
		this.picFileName = picFileName;
	}

	public String getPicContentType() {
		return picContentType;
	}

	public void setPicContentType(String picContentType) {
		this.picContentType = picContentType;
	}

	private File bigPic;
	private String bigPicFileName;
	private String bigPicContentType;

	public File getBigPic() {
		return bigPic;
	}

	public void setBigPic(File bigPic) {
		this.bigPic = bigPic;
	}

	public String getBigPicFileName() {
		return bigPicFileName;
	}

	public void setBigPicFileName(String bigPicFileName) {
		this.bigPicFileName = bigPicFileName;
	}

	public String getBigPicContentType() {
		return bigPicContentType;
	}

	public void setBigPicContentType(String bigPicContentType) {
		this.bigPicContentType = bigPicContentType;
	}

	@Action(value = "/addBook")
	public void addBook() throws IOException {
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		String str = "";
		if(bi.getPic()!=null) {
			String picFileExtName = getExtension(picFileName);
			String bigPicFileExtName = getExtension(bigPicFileName);
			boolean flag=(!".png".equals(picFileExtName) && !".jpeg".equals(picFileExtName)
					&& !".jpg".equals(picFileExtName) && !".gif".equals(picFileExtName))
					|| (!".png".equals(bigPicFileExtName) && !".jpeg".equals(bigPicFileExtName)
							&& !".jpg".equals(bigPicFileExtName) && !".gif".equals(bigPicFileExtName));
			if (flag) {
				str = "{\"success\":\"false\",\"message\":\"图片类型有误，请重新添加！\"}";
			}else{
				if (picFileName != null) {
					String targetDirectory = ServletActionContext.getServletContext().getRealPath("/Book_images");
					String targetFileName = generateFileName(picFileName, false);
					File target = new File(targetDirectory, targetFileName);
					FileUtils.copyFile(pic, target);
					bi.setPic(targetFileName);
				}
				if (bigPicFileName != null) {
					String targetDirectory = ServletActionContext.getServletContext().getRealPath("/Book_big_images");
					String targetFileName = generateFileName(bigPicFileName, true);
					File target = new File(targetDirectory, targetFileName);
					FileUtils.copyFile(bigPic, target);
					bi.setBigpic(targetFileName);
				}
			}		
		}
		int result = bookInfoService.addBookInfo(bi);			
		if (result > 0)
			str = "{\"success\":\"true\",\"message\":\"添加成功！\"}";
		else
			str = "{\"success\":\"false\",\"message\":\"添加失败!\"}";
		out.write(str);
	}

	public String generateFileName(String fileName, boolean isBig) {
		String extension = getExtension(fileName);
		if (isBig) {
			return "b" + bi.getCode() + extension;
		} else {
			return bi.getCode() + extension;
		}
	}

	public String getExtension(String fileName) {
		int position = fileName.lastIndexOf(".");
		return fileName.substring(position);
	}

	@Action(value = "/updateBook")
	public void updateBook() throws IOException {
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		BookInfo editPi = bookInfoService.getById(bi.getId());
		editPi.setType(bi.getType());
		editPi.setName(bi.getName());
		editPi.setCode(bi.getCode());
		editPi.setPress(bi.getPress());
		editPi.setNum(bi.getNum());
		editPi.setPrice(bi.getPrice());
		editPi.setIntro(bi.getIntro());
		editPi.setStatus(bi.getStatus());
		editPi.setAuthor(bi.getAuthor());
		if (picFileName != null) {
			String targetDirectory = ServletActionContext.getServletContext().getRealPath("/Book_images");
			String targetFileName = generateFileName(picFileName, false);
			File target = new File(targetDirectory, targetFileName);
			FileUtils.copyFile(pic, target);
			editPi.setPic(targetFileName);
		}
		if (bigPicFileName != null) {
			String targetDirectory = ServletActionContext.getServletContext().getRealPath("/Book_big_images");
			String targetFileName = generateFileName(bigPicFileName, true);
			File target = new File(targetDirectory, targetFileName);
			FileUtils.copyFile(bigPic, target);
			editPi.setBigpic(targetFileName);
		}
		String str = "";
		try {
			bookInfoService.updateBookInfo(editPi);
			str = "{\"success\":\"true\",\"message\":\"修改成功！\"}";
		} catch (Exception e) {
			str = "{\"success\":\"false\",\"message\":\"修改失败！\"}";
		}
		out.write(str);
	}

	@Action("/getOnSaleBook")
	public String getOnSaleBook() throws IOException {
		List<BookInfo> biList = bookInfoService.getOnSaleBook();
		//保存到session集合中输出
		session.put("getOnSaleBook", biList);
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		String jsonPiList = JSON.toJSONString(biList,SerializerFeature.DisableCircularReferenceDetect);
		out.println("{\"rows\":" + jsonPiList+ "}");
		return "bookinfo";
	
	}

	@Action("/getPriceById")
	public String getPriceById(String pid) {
		if (pid != null && !"".equals(pid)) {
			BookInfo pi = bookInfoService.getBookInfoById(Integer.parseInt(pid));
			return bi.getPrice() + "";
		}else{
			return "";
		}
	}
	
	@Autowired
	TypeService typeService;
	public void setTypeService(TypeService typeService) {
		this.typeService = typeService;
	}
	
	@SuppressWarnings("unchecked")
	public String list() throws Exception{
		List<Type> tList =(List<Type>) request.get("typeList");
		if(tList!=null)
			request.put("typeList",tList);
		else {
			List<Type> typeList=typeService.getAllWithDistinctPress();
			if (typeList.size()>0) {
				request.put("typeList", typeList);
			}
		}

		List<BookInfo> piList=bookInfoService.getAllBookInfo();
		if (piList.size()>0) {
			request.put("piList", piList);
		}
		//添加浏览历史信息
		Cookie[] cookies=req.getCookies();
		Cookie cookie=null;
		String ids="";
		if (cookies!=null) {
			boolean flag=true;
			for (Cookie c: cookies) {
				if ("BrowsingSample".equals(c.getName())) {
					ids="("+ c.getValue().substring(0, c.getValue().toString().length()-1) +")";
					break;
				}
			}
		}
		if (!"".equals(ids)) {
			List<BookInfo> browsePiList=bookInfoService.getBrowsingBookInfo(ids);
			if (browsePiList.size()>0) {
				//为了使多个页面取得这样的浏览过的信息，我们用Session存储浏览过的商品列表
				session.put("browsePiList", browsePiList);
			}
		}		
		return "index";
	}
	
	public String show() throws Exception{
		BookInfo detialBookInfo=bookInfoService.getById(bi.getId());
		request.put("detialBookInfo", detialBookInfo);		
		
		//处理cookies，用来保存浏览排行榜的信息，暂未处理，后面讲解
	/*	Cookie[] cookies=req.getCookies();
		Cookie cookie=null;
		String ids="";
		if (cookies!=null) {
			boolean flag=true;
			for (Cookie c : cookies) {
				if ("BrowsingSample".equals(c.getName())) {
					flag=false;
					cookie=c;
					break;
				}
			}
			if (flag) {
				//如果Cookies中不存在BrowsingSample，则创建
				cookie=new Cookie("BrowsingSample","");
				cookie.setMaxAge(24*60*60);
			}
		}
		if ("".equals(cookie.getValue()) || String.valueOf(bi.getId()).indexOf(cookie.getValue())<0 ) {
			ids += cookie.getValue()+bi.getId()+",";
			cookie.setValue(ids);
		}
		resp.addCookie(cookie);		*/
		
		return "show";
	}
}
