package com.dlt.pojo;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Storage {
	Integer id;
	String address;
	
	public Storage(){
		id = -1;
		address = "";
	}
	
	public Storage(Integer id, String address) {
		this.id = id;
		this.address = address;
	}
	
	public Storage(ResultSet rs){
		try {
			this.id = rs.getInt("id");
			this.address = rs.getString("address");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("使用rs初始化Storage失败！");
			e.printStackTrace();
		}
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Override
	public String toString() {
		return "Storage [id=" + id + ", address=" + address + "]";
	}
}
