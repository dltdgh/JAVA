<%@page import="pojo.Info"%>
<%@page import="com.dlt.db.DB_DAO"%>
<%@page import="java.util.Date"%>
<%@page import="pojo.Type"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
//将发布信息存入数据库

String infoType = new String(request.getParameter("infotype").getBytes("iso-8859-1"), "utf-8");
/*  String sql = "select * from tb_type where type_sign="+infoType+";";
Type type = DB.getTypesBySql(sql).get(0); */ 
String title = new String(request.getParameter("title").getBytes("iso-8859-1"), "utf-8");
String content = new String(request.getParameter("content").getBytes("iso-8859-1"), "utf-8");
String phone = new String(request.getParameter("phone").getBytes("iso-8859-1"), "utf-8");
String linkman = new String(request.getParameter("linkman").getBytes("iso-8859-1"), "utf-8");
String email = new String(request.getParameter("email").getBytes("iso-8859-1"), "utf-8");
System.out.println(infoType+" "+title+" "+content+" "+phone+" "+linkman+" "+email);
DB_DAO dbDao = new DB_DAO();
String sql = "insert into tb_info (id,info_type,info_title,info_content,info_linkman,info_phone,info_email,info_date,info_state,info_payfor) VALUES (null, "+infoType+", '"+title+"', '"+content+"', '"+linkman+"', '"+phone+"', '"+email+"', now(), '0', '0');";
System.out.println(sql);
dbDao.excuteUpdate(sql);
int lastId = dbDao.getLastInsertId();
System.out.println(lastId);
dbDao.close();
response.sendRedirect("showdetail.jsp?infoid="+lastId+"&flag=2");
%>