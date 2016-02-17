<%@page import="com.bbs.*"%>
<%@page import="java.sql.*"%>
<%@page language="java" import="java.util.*" pageEncoding="GB18030"%>

<%@include file="_SessionCheck.jsp" %>

<%
int userId = (Integer)session.getAttribute("userId");
//User user = DB.getUserById(userId);
%>

<%
	request.setCharacterEncoding("GBK");
	
	int pId = Integer.parseInt(request.getParameter("pId"));
	int rootId = Integer.parseInt(request.getParameter("rootId"));
	
	String title = request.getParameter("title");
//	System.out.println(title);
	String content = request.getParameter("content");
//	System.out.println(content);
	Connection conn = DB.getConnection();
	
	boolean autoCommit = conn.getAutoCommit();
	conn.setAutoCommit(false);
	
	/*
	对 article 表进行修改
	*/
	
	String sql = "insert into article values(null, ?, ?, ?, ?, now(), ?, ?, 0, 0)";
	PreparedStatement pstmt = DB.getPreparedStatement(conn, sql);
	pstmt.setInt(1, pId);
	pstmt.setInt(2, rootId);
	pstmt.setString(3, title);
	pstmt.setString(4, content);
	pstmt.setInt(5, 0);
	pstmt.setInt(6, userId);
	pstmt.executeUpdate();
	
	Statement stmt = DB.getStatement(conn);
	stmt.executeUpdate("update article set isleaf = 1 where id = "+pId);
	stmt.executeUpdate("update article set replynumber = replynumber + 1 where id = "+pId);   //修改article回复人数
	/*
	对 user 表进行修改
	*/
	DB.excuteUpdate(stmt, "update user set articlenumber = articlenumber + 1 where userid="+userId); //修改user数据库的发帖数
	
	conn.commit();
	conn.setAutoCommit(autoCommit);
	
	DB.close(stmt);
	DB.close(conn);
	
%>

<h1>回复成功，3秒后跳转。。。<br/>
<a href="article_detailFlat.jsp?id=<%= pId %>">如果没有跳转请点击</a></h1>

<script language="JavaScript1.2" type="text/javascript">
<!--
//  Place this in the 'head' section of your page.  

function delayURL(url, time) {
    setTimeout("top.location.href='" + url + "'", time);
}

//-->

</script>

<script>
delayURL("article_detailFlat.jsp?id=<%= pId %>", 3000);
</script>
