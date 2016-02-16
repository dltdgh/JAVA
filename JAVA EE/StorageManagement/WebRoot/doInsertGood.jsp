<%@page import="com.dlt.db.DB_DAO"%>
<%@page import="com.dlt.pojo.Good"%>
<%
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
	dao.storeGood(tGood);
	dao.close();
	response.sendRedirect("index.jsp");
}
%>