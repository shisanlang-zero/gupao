package com.skynet.zero.test;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class Company implements Serializable {
	public String name;
	public String address;
	private Date date;
	private String group[];
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String[] getGroup() {
		return group;
	}
	public void setGroup(String[] group) {
		this.group = group;
	}
	
	
}
