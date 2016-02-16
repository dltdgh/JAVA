<%@page import="com.dlt.pojo.Storage"%>
<%@page import="com.dlt.db.DB_DAO"%>
<%
String address = (String)request.getParameter("address");
System.out.println(address);
if(address != null && !address.equals("")){
	Storage tStorage = new Storage(null, address);
	DB_DAO dao = new DB_DAO();
	dao.storeStorage(tStorage);
}
response.sendRedirect("index.jsp");
%>