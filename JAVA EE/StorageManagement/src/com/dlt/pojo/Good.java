package com.dlt.pojo;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Good {
	Integer id;
	String name;
	Integer size;
	String color;
	Integer num;
	Integer storageid;
	String passman;
	
	public Good() {
		// TODO Auto-generated constructor stub
		id = -1;
		name = "";
		size = -1;
		color = "";
		num = -1;
		storageid = -1;
		passman = "";
	}
	
	public Good(Integer id, String name, Integer size, String color,
			Integer num, Integer storageid, String passman) {
		super();
		this.id = id;
		this.name = name;
		this.size = size;
		this.color = color;
		this.num = num;
		this.storageid = storageid;
		this.passman = passman;
	}

	public Good(ResultSet rs){
		try {
			this.id = rs.getInt("id");
			this.name = rs.getString("name");
			this.size = rs.getInt("size");
			this.color = rs.getString("color");
			this.num = rs.getInt("num");
			this.storageid = rs.getInt("storageid");
			this.passman = rs.getString("passman");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("用rs初始化Good失败！");
			e.printStackTrace();
		}
		
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Integer getStorageid() {
		return storageid;
	}
	public void setStorageid(Integer storageid) {
		this.storageid = storageid;
	}
	public String getPassman() {
		return passman;
	}
	public void setPassman(String passman) {
		this.passman = passman;
	}
	
	@Override
	public String toString() {
		return "Good [id=" + id + ", name=" + name + ", size=" + size
				+ ", color=" + color + ", num=" + num + ", storageid="
				+ storageid + ", passman=" + passman + "]";
	}
}
