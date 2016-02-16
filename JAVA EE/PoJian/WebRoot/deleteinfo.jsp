<%@page import="com.dlt.db.DB_DAO"%>
<%@page import="com.dlt.db.DB"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<% 

//根据infoid删除数据
String infoId = request.getParameter("infoid");
DB_DAO dbDao = new DB_DAO();
String sql = "delete from tb_info where id="+infoId+";";
System.out.println(sql);
dbDao.excuteUpdate(sql);
dbDao.close();
response.sendRedirect("background.jsp?paystate=all&check=all&infotype=0");
%>