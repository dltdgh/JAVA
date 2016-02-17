<%@page import="com.bbs.*"%>
<%@page import="java.sql.*"%>
<%@page pageEncoding="GB18030" %>

<%
String userName = request.getParameter("username");
String password = request.getParameter("password");
String url = request.getParameter("from");
if(userName == null || password == null){
	//out.println("<h1></h1>");
	response.sendRedirect("login.jsp?from="+url);
	return;
}

//System.out.println(userName+" "+password);

String sql = "select * from user where username = '"+userName+"'";
Connection conn = DB.getConnection();
Statement stmt = DB.getStatement(conn);
ResultSet rs = DB.excuteQuery(stmt, sql);

if(rs == null){
	response.sendRedirect("login.jsp?from="+url);
	return;
}
rs.next();

User user = new User();
user.initByRs(rs);

if(!user.getPassword().equals(password)){
	response.sendRedirect("login.jsp?from="+url);
	return;
}

session.setAttribute("userId", user.getUserId());
session.setAttribute("userLogined", "true");

DB.close(rs);
DB.close(stmt);
DB.close(conn);

response.sendRedirect(url);
%>
