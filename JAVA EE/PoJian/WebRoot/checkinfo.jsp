<%@page import="com.dlt.db.DB_DAO"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
//根据传入的infoid和cop值选择更改数据的审核或付费状态

String infoId = request.getParameter("infoid");
String cop = request.getParameter("cop");
DB_DAO dbDao = new DB_DAO();
String sql = "";
if(cop.equals("1")){
	sql = "update tb_info set info_state='1' where id="+infoId+";";
}
else if(cop.equals("2")){
	sql = "update tb_info set info_payfor='1' where id="+infoId+";";
}
System.out.println(sql);
dbDao.excuteUpdate(sql);
dbDao.close();
response.sendRedirect("backfinddetail.jsp?payinfo="+infoId);
%>