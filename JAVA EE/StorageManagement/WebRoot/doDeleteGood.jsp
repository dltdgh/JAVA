<%@page import="com.dlt.db.DB_DAO"%>
<%
String id = (String)request.getParameter("id");
String sql = "delete from tb_good where id="+id;
System.out.println(sql);
DB_DAO dao = new DB_DAO();
dao.excuteUpdate(sql);
dao.close();
response.sendRedirect("index.jsp");
%>
