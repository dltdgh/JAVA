<%@page import="com.dlt.db.DB_DAO"%>
<%@page import="pojo.User"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%

//根据session及其传入数据进行登录判断

String loginState = (String)session.getAttribute("loginstate");
String userName = (String)request.getParameter("username");
String password = (String)request.getParameter("password");
String checkCode = (String)request.getParameter("checkcode");   //获取验证码输入值
String realCode = (String)session.getAttribute("code");     //验证码实际值
System.out.println(loginState);
if(loginState == null || loginState.equals("false")){
	if(userName == null || password == null){
		response.sendRedirect("login.jsp");
		return;
	}
	else{
		//	System.out.println(userName+" "+password);
		DB_DAO dbDao = new DB_DAO();
		List<User> userList = dbDao.getUsersBySql("select * from tb_user where user_name='"+userName+"';");
		dbDao.close();
		int i;
		for(i = 0; i < userList.size(); i++){
			if(userList.get(i).getPassword().equals(password)){
				System.out.println(checkCode+" "+realCode);
				if(checkCode.equals(realCode)){ 					//验证码校验
					session.setAttribute("loginstate", "true");
					session.setAttribute("user", userList.get(i));	//存储已登录user对象
					break;
				}
			}
		}
		if(i == userList.size()){
			response.sendRedirect("login.jsp");
			return;
		}
	}
	//System.out.println(loginState+" "+userName+" "+password);
}
%>