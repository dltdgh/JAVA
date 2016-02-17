<%@page import="java.util.Date"%>
<%@page import="java.util.*"%>
<%@page import="java.io.Writer"%>
<%@page import="java.sql.*"%>
<%@page import="com.bbs.*"%>
<%@page pageEncoding="GB18030" %>

<%!  //声明
List<Article> articles = new ArrayList<Article>();
%>

<%  //获取session中的userid 并查询数据库获取详细内容
User user = new User();
String Logined = (String)session.getAttribute("userLogined");

boolean isLogined = false;
//System.out.println("here!!!");
//System.out.println(Logined);
if(Logined == null){
	isLogined = false;
}
else{
	isLogined = Logined.equals("true");	
//	System.out.println(userId);
	if(isLogined){
		int userId = (Integer)session.getAttribute("userId");
		user = DB.getUserById(userId);
	}
}

%>

<% //获取from信息
String url = request.getRequestURL().toString();
String suffix = request.getQueryString();
if(suffix != null){
	url = url+"?"+suffix;
}
%>

<%   //获取分页及帖子内容
final int PAGE_SIZE = 10;
int pageNo = 1;
String pageNoStr = request.getParameter("pageNo");
if(pageNoStr != null && !pageNoStr.trim().equals("")){
	try{
		pageNo = Integer.parseInt(pageNoStr);
	}catch(Exception e){
		pageNo = 1;
	}
}

if(pageNo <= 0){
	pageNo = 1;
}

int totalPages = 0;

Connection conn = DB.getConnection();
boolean autoCommit = conn.getAutoCommit();
conn.setAutoCommit(false);

Statement stmt = DB.getStatement(conn);
ResultSet rs = DB.excuteQuery(stmt, "select count(*) from article where pid = 0");
rs.next();
int totalRecords = rs.getInt(1);

totalPages =  totalRecords/PAGE_SIZE+(totalRecords%PAGE_SIZE==0 ? 0 : 1);
if(pageNo > totalPages){
	pageNo = totalPages;
}

int startPos = (pageNo-1)*PAGE_SIZE;

String sql = "select * from article where pid = 0 order by pdata desc limit "+startPos+","+PAGE_SIZE;
rs = DB.excuteQuery(conn, sql);
if(rs != null){
	while(rs.next()){
		Article article = new Article();
		article.initFromRs(rs);
	//	System.out.println(article.getReplyNumber());
		articles.add(article);
	}
}
conn.setAutoCommit(autoCommit);
DB.close(rs);
DB.close(conn);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
<title>BBS</title>
<meta http-equiv="content-type" content="text/html; charset=GBK">
<link rel="stylesheet" type="text/css" href="images/style.css" title="Integrated Styles">
<script language="JavaScript" type="text/javascript" src="images/global.js"></script>
<link rel="alternate" type="application/rss+xml" title="RSS" href="http://bbs.chinajavaworld.com/rss/rssmessages.jspa?forumID=20">
<script language="JavaScript" type="text/javascript" src="images/common.js"></script>
</head>
<body>
<table border="0" cellpadding="0" cellspacing="0" width="100%">
  <tbody>
    <tr>
      <td><img src="images/header-stretch.gif" alt="" border="0" height="57" width="100%"></td>
      <td width="1%"><img src="images/header-right.gif" alt="" border="0"></td>
    </tr>
  </tbody>
</table>
<br>
<div id="jive-forumpage">
  <table border="0" cellpadding="0" cellspacing="0" width="100%">
    <tbody>
      <tr valign="top">
        <td width="98%"><p style="color:red;font-size:32px;">论坛: BBS For A Test</p>
          <p class="jive-description"> 希望大家一起交流 共同提高！谢绝任何形式的广告  </p>
          </td>
      </tr>
    </tbody>
  </table>
   <br/>
  <div class="jive-buttons">
    <table summary="Buttons" border="0" cellpadding="0" cellspacing="0" width="100%">
      <tbody>
        <tr>
          <td class="jive-icon" width="1%"><a href="postFlat.jsp"><img src="images/post-16x16.gif" alt="发表新主题" border="0" height="16" width="16"></a></td>
          <td class="jive-icon-label" width="9%"><a id="jive-post-thread" href="postFlat.jsp">发表新主题</a> <a href="postFlat.jsp"></a></td>
    	  <td width="70%"></td>
    	  <td width="5%" style="color:red" align="center">Welcome</td>
    	  <td width="5%" style="color:green" align="center"> <%= user.getUserName() %></td>
          <td class="jive-icon-label" width="4" align="center"><a id="jive-post-thread" href="login.jsp?from=<%= url %>">登陆</a><a href="login.jsp"></a></td>
          <td width="1%" align="center">|</td>
          <td class="jive-icon-label" width="4" align="center"><a id="jive-post-thread" href="logout.jsp?from=<%= url %>">登出</a><a href="logout.jsp"></a></td>
        </tr>
      </tbody>
    </table>
  </div>
  <br>
  
  <table border="0" cellpadding="0" cellspacing="0" width="100%">
    <tbody>
      <tr valign="top">
        <td width="99%"><div class="jive-thread-list">
            <div class="jive-table">
              <table summary="List of threads" cellpadding="0" cellspacing="0" width="100%">
                <thead>
                  <tr>
                    <th class="jive-first" colspan="3"> 主题 </th>
                    <th class="jive-author"> <nobr> 作者
                      &nbsp; </nobr> </th>
                    <th class="jive-view-count"> <nobr> 浏览
                      &nbsp; </nobr> </th>
                    <th class="jive-msg-count" nowrap="nowrap"> 回复 </th>
                    <th class="jive-last" nowrap="nowrap"> 最新帖子 </th>
                  </tr>
                </thead>
                <tbody>
                <% 
              //  System.out.println(articles.size());
                for(int i = 0; i < articles.size(); i++){
                	Article article = articles.get(i);
                	User tUser = DB.getUserById(article.getUserId());
                	if(i%2 == 0){
                 %>
                  <tr class="jive-even">
                    <td class="jive-first" nowrap="nowrap" width="1%"><div class="jive-bullet"> <img src="images/read-16x16.gif" alt="已读" border="0" height="16" width="16"></div></td> 
                    <td nowrap="nowrap" width="1%">&nbsp;<% if(user.getPermission() ==2){%><a href="delete.jsp?id=<%= article.getId() %>&pId=<%= article.getpId()%>">DEL</a><%}%>&nbsp;</td>
                    <td class="jive-thread-name" width="95%"><a id="jive-thread-1" href="article_detailFlat.jsp?id=<%= article.getId() %>"><%= article.getTitle() %></a></td>
                    <td class="jive-author" nowrap="nowrap" width="1%"><span class=""> <a href="http://bbs.chinajavaworld.com/profile.jspa?userID=226030"><%= tUser.getUserName() %></a> </span></td>
                    <td class="jive-view-count" width="1%"> <%= article.getReadNumber() %> </td>
                    <td class="jive-msg-count" width="1%"> <%= article.getReplyNumber() %> </td>
                    <td class="jive-last" nowrap="nowrap" width="1%"><div class="jive-last-post"> <%=new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(article.getpDate()) %> <br>
                        by: <a href="http://bbs.chinajavaworld.com/thread.jspa?messageID=780182#780182" title="jingjiangjun" style=""><%= tUser.getUserName() %> &#187;</a> </div></td>
                  </tr>
                  <%	
                    }
                   	else{
                  %>
                  <tr class="jive-odd">
                    <td class="jive-first" nowrap="nowrap" width="1%"><div class="jive-bullet"> <img src="images/read-16x16.gif" alt="已读" border="0" height="16" width="16">
                        <!-- div-->
                      </div></td>
                    <td nowrap="nowrap" width="1%">&nbsp;<% if(user.getPermission() ==2){%><a href="delete.jsp?id=<%= article.getId() %>&pId=<%= article.getpId()%>">DEL</a><%}%>&nbsp;</td>
                  
                    <td class="jive-thread-name" width="95%"><a id="jive-thread-2" href="article_detailFlat.jsp?id=<%= article.getId() %>"><%= article.getTitle() %></a></td>
                    <td class="jive-author" nowrap="nowrap" width="1%"><span class=""> <a href="http://bbs.chinajavaworld.com/profile.jspa?userID=226028"><%= tUser.getUserName() %></a> </span></td>
                    <td class="jive-view-count" width="1%"> <%= article.getReadNumber() %> </td>
                    <td class="jive-msg-count" width="1%"> <%= article.getReplyNumber() %> </td>
                    <td class="jive-last" nowrap="nowrap" width="1%"><div class="jive-last-post"> <%=new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(article.getpDate()) %> <br>
                        by: <a href="http://bbs.chinajavaworld.com/thread.jspa?messageID=780172#780172" title="downing114" style=""><%= tUser.getUserName() %>&#187;</a> </div></td>
                  </tr>
                <%
                  	}
                }
                %>
                </tbody>
              </table>
            </div>
          </div>
          <div class="jive-legend"></div></td>
      </tr>
    </tbody>
  </table>
  <table border="0" cellpadding="3" cellspacing="0" width="100%">
    <tbody>
      <tr valign="top">
        <td align="center"><span class="nobreak" >
        <span class="jive-paginator">[ 第<%= pageNo %>页  | 共 <%= totalPages %>页 ] | [ <a href="articleFlat.jsp?pageNo=1">首页</a> | <a href="articleFlat.jsp?pageNo=<%= pageNo-1 %>" class="">上一页</a> | <a href="articleFlat.jsp?pageNo=<%= pageNo+1 %>" class="">下一页</a> | <a href="articleFlat.jsp?pageNo=<%= totalPages %>" class="">尾页</a>  ] </span> </span> </td>
      </tr>
    </tbody>
  </table>
  <br>
  <br>
</div>
</body>
</html>

<%
articles.clear(); 
%>
