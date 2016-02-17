<%@page import="java.util.Date"%>
<%@page import="java.util.*"%>
<%@page import="java.io.*"%>
<%@page import="java.sql.*"%>
<%@page import="com.bbs.*"%>
<%@page pageEncoding="GB18030" %>

<%@include file="_SessionCheck.jsp" %>

<%!
	private void delete(Connection conn, int id){
		Statement stmt = DB.getStatement(conn);
		ResultSet rs = DB.getRsFromPId(stmt, id);
		try{
			while(rs.next()){
				if(rs.getInt("isleaf") == 1){
					delete(conn, rs.getInt("id"));
				}else{
					DB.excuteUpdate(conn, "delete from article where id = " + rs.getInt("id"));
				}
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			DB.close(rs);
			DB.close(stmt);
		}
		DB.excuteUpdate(conn, "delete from article where id = " + id);
	}
%>

<%
	String fromURL = request.getParameter("from");
	int userId = (Integer)session.getAttribute("userId");
	User user = DB.getUserById(userId);
	int id = Integer.parseInt(request.getParameter("id"));
	
	Article article = DB.getArticleById(id);
	
	int pId = Integer.parseInt(request.getParameter("pId"));
	Connection conn = DB.getConnection();
	
	ResultSet rs = DB.excuteQuery(conn, "select * from article where id="+id);
	rs.next();
	//判断是否满足删除权限
	//System.out.println(id+" "+user.getPermission()+" "+user.getUserId()+" "+article.getUserId());
	if(user.getPermission() == 0 || (user.getPermission() == 1 && user.getUserId() != article.getUserId())){
		response.sendRedirect("articleFlat.jsp");
		return;
	}
	
	if(pId != 0){
//		System.out.println(pId);
		DB.excuteUpdate(conn, "update article set isleaf = 1 where id = "+pId);
		DB.excuteUpdate(conn, "update article set replynumber = replynumber-1 where id = "+pId);
	}
	delete(conn, id);
	
	DB.close(rs);
	DB.close(conn);
%>

<h1>删除成功，3秒后跳转。。。<br/>
<a href="<%= pId == 0 ? "articleFlat.jsp" : "article_detailFlat.jsp?id="+pId %>">如果没有跳转请点击</a></h1>

<script language="JavaScript1.2" type="text/javascript">
<!--
//  Place this in the 'head' section of your page.  

function delayURL(url, time) {
    setTimeout("top.location.href='" + url + "'", time);
}

//-->

</script>

<script>
delayURL("<%= pId == 0 ? "articleFlat.jsp" : "article_detailFlat.jsp?id="+pId %>", 3000);
</script>