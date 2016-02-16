package com.dlt.db;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pojo.Info;
import pojo.Type;
import pojo.User;

/*
 * 具体工具类，继承自抽象类DB_Utils， 加入项目所需方法重新封装
 */

public class DB_DAO extends DB_Utils {
	private static final String url = "jdbc:mysql://localhost:3306/pojian";
	private static final String user = "root";
	private static final String password = "1234";
	
	public DB_DAO(){
		super(url, user, password);
	}
	
	public  List<User> getUsersBySql(String sql) {
		List<User> list = new ArrayList<User>();
		
		rs = excuteQuery(sql);
		try {
			while(rs != null && rs.next()){
				User tUser = new User(rs);
				list.add(tUser);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public List<Type> getTypesBySql(String sql) {
		List<Type> list = new ArrayList<Type>();
		rs = excuteQuery(sql);
		try {
			while(rs != null && rs.next()){
				Type tType = new Type(rs);
				list.add(tType);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public List<Info> getInfosBySql(String sql) {
		List<Info> list = new ArrayList<Info>();
		rs = excuteQuery(sql);
		try {
			while(rs != null && rs.next()){
				Info tInfo = new Info(rs);
				list.add(tInfo);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	/*
	 *时间格式转换yyyy-MM-dd HH:mm:ss
	 */
	public static String formatDate(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}
	
	public static void main(String[] args) {
		DB_DAO test = new DB_DAO();
		/*List<User> userList = test.getUsersBySql("select * from tb_user;");
		for (User tUser : userList) {
			System.out.println(tUser.toString());
		}
		List<Type> typeList = test.getTypesBySql("select * from tb_type;");
		for (Type tType : typeList) {
			System.out.println(tType.toString());
		}
		List<Info> infoList = test.getInfosBySql("select * from tb_info;");
		for (Info tInfo : infoList) {
			System.out.println(tInfo.toString());
		}*/
		System.out.println(test.excuteUpdate("update tb_info set info_payfor='1' where id=137;"));
		test.close();
	}
}
