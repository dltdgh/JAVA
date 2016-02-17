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
		
	String title = request.getParameter("title");
//	System.out.println(title);
	String content = request.getParameter("content");
//	System.out.println(content);
	Connection conn = DB.getConnection();
	
	boolean autoCommit = conn.getAutoCommit();
	conn.setAutoCommit(false);
	/*
	对article表进行修改
	*/
	String sql = "insert into article values(null, ?, ?, ?, ?, now(), ?, ?, 0, 0)";
	PreparedStatement pstmt = DB.getPreparedStatement(conn, sql, Statement.RETURN_GENERATED_KEYS);
	pstmt.setInt(1, 0);
	pstmt.setInt(2, -1);
	pstmt.setString(3, title);
	pstmt.setString(4, content);
	pstmt.setInt(5, 0);
	pstmt.setInt(6, userId);
	pstmt.executeUpdate();
	
	ResultSet rsKeys = pstmt.getGeneratedKeys();
	rsKeys.next();
	int rootId = rsKeys.getInt(1);
	
	Statement stmt = DB.getStatement(conn);
	stmt.executeUpdate("update article set rootid = "+rootId+" where id = "+rootId);
	
	/*
	对user表进行更新
	*/
	DB.excuteUpdate(stmt, "update user set articlenumber = articlenumber + 1 where userid="+userId); //修改user数据库的发帖数
	
	conn.commit();
	conn.setAutoCommit(autoCommit);
	
	DB.close(rsKeys);
	DB.close(stmt);
	DB.close(conn);
	
%>

<h1>发布成功，3秒后跳转。。。<br/>
<a href="articleFlat.jsp">如果没有跳转请点击</a></h1>

<script language="JavaScript1.2" type="text/javascript">
<!--
//  Place this in the 'head' section of your page.  

function delayURL(url, time) {
    setTimeout("top.location.href='" + url + "'", time);
}

//-->

</script>

<script>
delayURL("articleFlat.jsp", 3000);
</script>
