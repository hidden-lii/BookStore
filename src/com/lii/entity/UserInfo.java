package com.lii.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "user_info", catalog = "book")
public class UserInfo {
	private int id;
	private String userName;
	private String password;
	private String status;
	private String realName;
	private String sex;
	private String address;
	private String question;
	private String answer;
	private String email;
	private String regDate;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "userName", length = 16)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "password", length = 16)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@JSONField(serialize=false)
	@JsonIgnoreProperties(value = { "ui","ods"}) 
	private Set<OrderInfo> orders = new HashSet<OrderInfo>();	
	@OneToMany(mappedBy = "ui",fetch=FetchType.EAGER)
	public Set<OrderInfo> getOrders() {
		return orders;
	}

	public void setOrders(Set<OrderInfo> orders) {
		this.orders = orders;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public UserInfo() {
	}

	public UserInfo(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	public UserInfo(String userName, String password, String realName, String sex, String address, String question,
			String answer, String email,String regDate, Set<OrderInfo> orders) {
		super();
		this.userName = userName;
		this.password = password;
		this.realName = realName;
		this.sex = sex;
		this.address = address;
		this.question = question;
		this.answer = answer;
		this.email = email;
		this.regDate = regDate;
		this.orders = orders;
	}
	
}
