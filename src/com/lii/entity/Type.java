package com.lii.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "type", catalog = "book")
public class Type {
	private int id;
	private String name;	
	
	@JSONField(serialize=false)
	@JsonIgnoreProperties(value = {"type"})
	private Set<BookInfo> bis=new HashSet(0);
	@OneToMany(fetch=FetchType.EAGER,cascade={ CascadeType.ALL })
	@JoinColumn(name = "tid")
	public Set<BookInfo> getBis() {
		return bis;
	}
	public void setBis(Set<BookInfo> bis) {
		this.bis = bis;
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

	@Column(name = "name", nullable = false, length = 20)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Type(String name) {
		this.name = name;
	}

	public Type() {
	}

	@Override
	public String toString() {
		return "Type [id=" + id + ", name=" + name + "]";
	}

}
