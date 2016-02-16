<%@page import="com.dlt.db.DB_DAO"%>
<%@page import="pojo.User"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%

//用户修改操作

User user = (User)session.getAttribute("user");
Integer userid = user.getId();
String username = request.getParameter("username");
String password = request.getParameter("password");
String sql = "update tb_user set user_name='"+username+"', user_password='"+password+"' where id="+userid+";";
System.out.println(sql);
DB_DAO dbDao = new DB_DAO();
dbDao.excuteUpdate(sql);
dbDao.close();
response.sendRedirect("logout.jsp");
%>