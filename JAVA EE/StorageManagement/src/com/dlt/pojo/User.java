package com.dlt.pojo;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
	Integer id;
	String name;
	Integer permission;
	String password;

	public User(){
		id = -1;
		name = "";
		permission = -1;
		password="nicai";
	}
	
	public User(Integer id, String name, Integer permission, String password){
		this.id = id;
		this.name = name;
		this.permission = permission;
		this.password = password;
	}
	
	public User(ResultSet rs){
		try {
			this.id = rs.getInt("id");
			this.name = rs.getString("name");
			this.permission = rs.getInt("permission");
			this.password = rs.getString("password");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("使用rs初始化User失败！");
			e.printStackTrace();
		}
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPermission() {
		return permission;
	}
	public void setPermission(int permission) {
		this.permission = permission;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", permission="
				+ permission + ", password=" + password + "]";
	}
}
