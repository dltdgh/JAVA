<%@page import="com.dlt.db.DB_DAO"%>
<%@page import="com.dlt.pojo.Good"%>
<%
String id = (String)request.getParameter("id");
System.out.println(id);
String name = (String)request.getParameter("name");
String size = (String)request.getParameter("size");
String color = (String)request.getParameter("color");
String num = (String)request.getParameter("num");
String storageid = (String)request.getParameter("address");
String passman = (String)request.getParameter("passman");

if(name == "" || size == "" || color == "" || num == "" || storageid == "" || passman == ""){
	response.sendRedirect("index.jsp");
}
else{
	Good tGood = new Good(null, name, Integer.parseInt(size), color, Integer.parseInt(num), Integer.parseInt(storageid), passman);
	DB_DAO dao = new DB_DAO();
	String sql = "update tb_good set name='"+name+"', size="+size+", color='"+color+"', num="+num+", storageid="+storageid+", passman='"+passman+"' where id="+id+";";
	System.out.println(sql);
	dao.excuteUpdate(sql);
	dao.close();
	response.sendRedirect("index.jsp");
}
%>