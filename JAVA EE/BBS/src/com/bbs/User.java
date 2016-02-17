package com.bbs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;



public class User {
	
	private int userId;
	private String userName;
	private String password;
	private int permission;
	private Date signDate;
	private int articleNumber;
	
	public User(){
		userId = 0;
		userName = "Ghost";
		password = null;
		permission = 0;
		signDate = null;
		articleNumber = 0;
	}
	
	public void initByRs(ResultSet rs){
		try {
			userId = rs.getInt("userid");
			userName = rs.getString("username");
			password = rs.getString("password");
			permission = rs.getInt("permission");
			signDate = rs.getTimestamp("signdate");
			articleNumber = rs.getInt("articleNumber");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getPermission() {
		return permission;
	}
	public void setPermission(int permission) {
		this.permission = permission;
	}
	public Date getSignDate() {
		return signDate;
	}
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}
	public int getArticleNumber() {
		return articleNumber;
	}
	public void setArticleNumber(int articleNumber) {
		this.articleNumber = articleNumber;
	}
	
}
