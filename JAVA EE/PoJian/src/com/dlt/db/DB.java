package com.dlt.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pojo.Info;
import pojo.Type;
import pojo.User;

public class DB {
	static String url = "jdbc:mysql://localhost:3306/pojian";
	static String user = "root"; 
	static String password = "1234";
	public static Connection getConnection(){
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	
	public static Statement getStatement(Connection conn) {
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return stmt;
	}
	
	public static ResultSet excuteQuery(Statement stmt, String sql) {
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return rs;
	}
	
	public  static boolean excuteUpdate(Statement stmt, String sql) {
		try {
			return stmt.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static void close(Connection conn){
		try {
			conn.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public static void close(Statement stmt){
		try {
			stmt.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static void close(ResultSet rs){
		try {
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static List<User> getUsersBySql(String sql) {
		List<User> list = new ArrayList<User>();
		Connection conn = getConnection();
		Statement stmt = getStatement(conn);
		ResultSet rs = excuteQuery(stmt, sql);
		try {
			while(rs != null && rs.next()){
				User tUser = new User(rs);
				list.add(tUser);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			close(stmt);
			close(conn);
		}
		return list;
	}
	
	public static List<Type> getTypesBySql(String sql) {
		List<Type> list = new ArrayList<Type>();
		Connection conn = getConnection();
		Statement stmt = getStatement(conn);
		ResultSet rs = excuteQuery(stmt, sql);
		try {
			while(rs != null && rs.next()){
				Type tType = new Type(rs);
				list.add(tType);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			close(stmt);
			close(conn);
		}
		return list;
	}
	
	public static List<Info> getInfosBySql(String sql) {
		List<Info> list = new ArrayList<Info>();
		Connection conn = getConnection();
		Statement stmt = getStatement(conn);
		ResultSet rs = excuteQuery(stmt, sql);
		try {
			while(rs != null && rs.next()){
				Info tInfo = new Info(rs);
				list.add(tInfo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			close(stmt);
			close(conn);
		}
		return list;
	}
	
	public static boolean excuteUpdate(String sql){
		Connection conn = getConnection();
		Statement stmt = getStatement(conn);
		boolean flag = excuteUpdate(stmt, sql);
		close(stmt);
		close(conn);
		return flag;
	}
	
	public static String formatDate(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}
	
	public static void main(String[] args) {
		List<Type> typeList = DB.getTypesBySql("select * from tb_type order by type_sign asc;");
		for(Type tType:typeList){
			System.out.println(tType.toString());
		}
	}
}
