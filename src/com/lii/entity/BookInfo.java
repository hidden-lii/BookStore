package com.lii.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "book_info", catalog = "book")
public class BookInfo {
	private int id; 
	private String code; 
	private String name; 
	private String intro; 
	private String press; 
	private double price; 
	private int status;  
	private String pic;   
	private String bigpic;   
	private int num;      
	private double priceFrom;	
	private double priceTo;	
	private Type type;
	private String author;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "tid")	
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	@Transient
	public double getPriceFrom() {
		return priceFrom;
	}

	public void setPriceFrom(double priceFrom) {
		this.priceFrom = priceFrom;
	}

	@Transient
	public double getPriceTo() {
		return priceTo;
	}

	public void setPriceTo(double priceTo) {
		this.priceTo = priceTo;
	}	

	@Column(name = "code", nullable = false, length = 16)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "name", nullable = false, length = 255)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "intro", nullable = false)
	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}
	
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}	

	public String getBigpic() {
		return bigpic;
	}

	public void setBigpic(String bigpic) {
		this.bigpic = bigpic;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getPress() {
		return press;
	}
	
	public void setPress(String press) {
		this.press = press;
	}
	
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public BookInfo() {
	}

	public BookInfo(String code, String name, String intro) {
		this.code = code;
		this.name = name;
		this.intro = intro;
	}

}
