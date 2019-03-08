package com.lii.entity;

import javax.persistence.*;

@Entity
@Table(name = "admin_info", catalog = "book")
public class AdminInfo {
	private int id;
	private String name;
	private String pwd;
	private int role;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Column(name = "name", length = 16)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name = "pwd", length = 50)
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	@Column(name = "role", length = 4)
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
	@Override
	public String toString() {
		return "AdminInfo [name=" + name + ", pwd=" + pwd + "]";
	}
	public AdminInfo(String name, String pwd) {
		super();
		this.name = name;
		this.pwd = pwd;
	}
	public AdminInfo() {
		super();
	}	
}
