package com.dlt.db;

import java.sql.*;

/*
 * 封装对数据库的一系列基本操作
 */

public abstract class DB_Utils {
	protected String url = null;
	protected String user = null;
	protected String password = null;
	protected Connection conn = null;
	protected Statement stmt = null;
	protected PreparedStatement pstmt = null;
	protected ResultSet rs = null;
	
	public DB_Utils(String url, String user, String password) {
		this.url = url;
		this.user = user;
		this.password = password;
		conn = getConnection();
		stmt = getStatement();
	}
	
	protected Connection getConnection(){
		conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	
	protected Statement getStatement() {
		stmt = null;
		try {
			stmt = conn.createStatement();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return stmt;
	}
	
	public PreparedStatement getPreparedStatement(String sql) {
		pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return pstmt;
	}
	
	public ResultSet excuteQuery(String sql) {
		rs = null;
		try {
			rs = stmt.executeQuery(sql);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return rs;
	}
	
	public int excuteUpdate(String sql) {
		try {
			return stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public int getLastInsertId(){
		int id = -1;
		try {
			rs = stmt.executeQuery("select LAST_INSERT_ID();");
			while (rs.next()) {
				id = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}
	
	public void close(){
		try {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if(pstmt != null){
				pstmt.close();
			}
			if(conn != null){
				conn.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
