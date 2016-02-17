<%@page import="java.util.Date"%>
<%@page import="java.sql.*"%>
<%@page import="java.util.*" %>
<%@page import="java.io.*" %>
<%@page import="com.bbs.*"%>
<%@page pageEncoding="GB18030" %>

<%!
	Article pArticle = new Article();
	List<Article> articles = new ArrayList<Article>();
%>

<% //获取from信息
String url = request.getRequestURL().toString();
String suffix = request.getQueryString();
if(suffix != null){
	url = url+"?"+suffix;
}
%>

<%  //获取session中的userid 并查询数据库获取详细内容
User user = new User();
String Logined = (String)session.getAttribute("userLogined");

boolean isLogined = false;
//System.out.println("here!!!");
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
//System.out.println(isLogined);
%>

<%
	//import start
	final int PAGE_SIZE = 10;
	int pageNo = 1;
	String pageNoStr = request.getParameter("pageNo");
	
	int id = Integer.parseInt(request.getParameter("id"));
	
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
	ResultSet rs = DB.excuteQuery(stmt, "select count(*) from article where pid = "+id);
	rs.next();
	int totalRecords = rs.getInt(1);
	
	totalPages =  totalRecords/PAGE_SIZE+(totalRecords%PAGE_SIZE==0 ? 0 : 1);
	totalPages = (totalPages == 0 ? 1 : totalPages);
	if(pageNo > totalPages){
		pageNo = totalPages;
	}
//	System.out.println(totalPages);
	int startPos = (pageNo-1)*PAGE_SIZE;
	
	rs = DB.getRsFromId(stmt, id);
	rs.next();
	pArticle.initFromRs(rs);
	articles.add(pArticle);
	
	String sql = "select * from article where pid = "+ id +" order by pdata limit "+startPos+","+PAGE_SIZE;
	rs = DB.excuteQuery(stmt, sql);
	
	if(rs != null){
		while(rs.next()){
			Article article = new Article();
			article.initFromRs(rs);
			//	System.out.println(article.getReplyNumber());
			articles.add(article);
		}
	}
	
	stmt.executeUpdate("update article set readnumber = readnumber+1 where id = "+id);
	
	conn.setAutoCommit(autoCommit);
	DB.close(rs);
	DB.close(stmt);
	DB.close(conn);
	//import end
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
<title>BBS</title>
<meta http-equiv="content-type" content="text/html; charset=GBK">
<link rel="stylesheet" type="text/css" href="images/style.css" title="Integrated Styles">
<script language="JavaScript" type="text/javascript" src="images/global.js"></script>
<link rel="alternate" type="application/rss+xml" title="RSS" href="http://bbs.chinajavaworld.com/rss/rssmessages.jspa?threadID=744236">
</head>
<body>
  <table border="0" cellpadding="0" cellspacing="0" width="100%">
  <tbody>
    <tr>
      <td><img src="images/header-stretch.gif" alt="" border="0" height="57" width ="100%"></td>
      <td width="1%"><img src="images/header-right.gif" alt="" border="0"></td>
    </tr>
  </tbody>
</table>
<br>
<div id="jive-flatpage">
  <table border="0" cellpadding="0" cellspacing="0" width="100%">
    <tbody>
      <tr valign="top">
        <td width="99%">
          <p class="jive-page-title"> 主题: <%= pArticle.getTitle() %> </p></td>
        <td width="1%"><div class="jive-accountbox"></div></td>
      </tr>
    </tbody>
  </table>
  <br/>
  <div class="jive-buttons">
    <table summary="Buttons" border="0" cellpadding="0" cellspacing="0" width="100%">
      <tbody>
        <tr>
          <td class="jive-icon"  width="1%"><a href="http://bbs.chinajavaworld.com/post%21reply.jspa?threadID=744236"><img src="images/reply-16x16.gif" alt="回复本主题" border="0" height="16" width="16"></a></td>
          <td class="jive-icon-label" width="9%"><a id="jive-reply-thread" href="replyFlat.jsp?id=<%= pArticle.getId() %>&rootId=<%= pArticle.getRootId() %>">回复本主题</a> </td>
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
        <td width="99%"><div id="jive-message-holder">
            <div class="jive-message-list">
              <div class="jive-table">
                <div class="jive-messagebox">
                
                <!-- 循环开始 -->
                
                <%
                for(int i = 0; i < articles.size(); i++) {
                	Article article = articles.get(i);
                	User tUser = DB.getUserById(article.getUserId());
                %>
                  <table summary="Message" border="0" cellpadding="0" cellspacing="0" width="100%">
                    <tbody>
                      <tr id="jive-message-780144" class="jive-odd" valign="top">
                        <td class="jive-first" width="1%">
						  <table border="0" cellpadding="0" cellspacing="0" width="150">
                            <tbody>
                              <tr>
                                <td><table border="0" cellpadding="0" cellspacing="0" width="100%">
                                    <tbody>
                                      <tr valign="top">
                                        <td style="padding: 0px;" width="1%"><nobr> <a href="" title="visitor"><%= tUser.getUserName() %></a> </nobr> </td>
                                        <td style="padding: 0px;" width="99%"><img class="jive-status-level-image" src="images/level3.gif" title="世界新手" alt="" border="0"><br></td>
                                      </tr>
                                    </tbody>
                                  </table>
                                  <br/>
                                  <img class="jive-avatar" src="images/profle.png" alt="" border="0" width="80" height="80">
                                 <br/><br/>
                                  <span class="jive-description"> 
                                   	发表: <%= tUser.getArticleNumber() %><br>
                                  	注册: <%=new java.text.SimpleDateFormat("yyyy-MM-dd").format(article.getpDate()) %>
                                  </span> </td>
                              </tr>
                            </tbody>
                          </table>
						</td>
                        <td class="jive-last" width="99%"><table border="0" cellpadding="0" cellspacing="0" width="100%">
                            <tbody>
                              <tr valign="top">
                                <td width="1%"></td>
                                <td width="97%"><span class="jive-subject"><%= (i > 0 ? ((pageNo-1)*PAGE_SIZE+i+" 楼") : "楼主")+"----"+article.getTitle() %></span> </td>
                                <td class="jive-rating-buttons" nowrap="nowrap" width="1%"></td>
                                <td width="1%"><div class="jive-buttons">
                                    <table border="0" cellpadding="0" cellspacing="0">
                                      <tbody>
                                        <tr>
                                          <td>&nbsp;</td>
                                          
                                          <!--  -->
                                          
                                          <td class="jive-icon"><a href="<%= i == 0 ? "delete.jsp?id="+article.getId()+"&pId="+article.getpId() : "article_detailFlat.jsp?id="+article.getId() %>" title="查看回复"><img src="images/reply-16x16.gif" alt="查看回复" border="0" height="16" width="16"></a></td>
                                          <td class="jive-icon-label"><a href="<%= i == 0 ? "delete.jsp?id="+article.getId()+"&pId="+article.getpId() : "article_detailFlat.jsp?id="+article.getId() %>" title="查看回复"><%= i == 0 ? "DEL" : "查看回复" %>(<%= article.getReplyNumber() %>)</a> </td>
                                          
                                          <!--  -->
                                          
                                        </tr>
                                      </tbody>
                                    </table>
                                  </div></td>
                              </tr>
                              <tr>
                                <td colspan="4" style="border-top: 1px solid rgb(204, 204, 204);"><br>
                                 <%= article.getContent() %> <br>
                                  <br>
                                </td>
                              </tr>
                              <tr>
                                <td colspan="4" style="font-size: 9pt;"><img src="images/sigline.gif"><br>
                                  <font color="#568ac2">学程序是枯燥的事情，愿大家在一起能从中得到快乐！</font> <br>
                                </td>
                              </tr>
                              <tr>
                                <td colspan="4" style="border-top: 1px solid rgb(204, 204, 204); font-size: 9pt; table-layout: fixed;">·<font color="#666666"><%= article.getpDate() %></font> </td>
                              </tr>
                            </tbody>
                          </table></td>
                      </tr>
                    </tbody>
                  </table>
                 <%
                }
                 %>
                 <!-- 循环结束 -->
                 
                </div>
              </div>
            </div>
            <div class="jive-message-list-footer">
              <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tbody>
                  <tr>
                    <td nowrap="nowrap" width="1%"></td>
                    <td align="center" width="98%"><table border="0" cellpadding="0" cellspacing="0">
                        <tbody>
                          <tr>
                          	<td><span class="jive-paginator">[ 第<%= pageNo %>页  | 共 <%= totalPages %>页 ] | [ <a href="article_detailFlat.jsp?pageNo=1&id=<%= id %>">首页</a> | <a href="article_detailFlat.jsp?pageNo=<%= pageNo-1 %>&id=<%= id %>" class="">上一页</a> | <a href="article_detailFlat.jsp?pageNo=<%= pageNo+1 %>&id=<%= id %>" class="">下一页</a> | <a href="article_detailFlat.jsp?pageNo=<%= totalPages %>&id=<%= id %>" class="">尾页</a>  ]  </span> </td>
                            <td><a href="articleFlat.jsp"><img src="images/arrow-left-16x16.gif" alt="返回到主题列表" border="0" height="16" hspace="6" width="16"></a> </td>
                            <td><a href="articleFlat.jsp">返回到主题列表</a> </td>
                          </tr>
                        </tbody>
                      </table></td>
                    <td nowrap="nowrap" width="1%">&nbsp;</td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div></td>
        <td width="1%"></td>
      </tr>
    </tbody>
  </table>
</div>
</body>
</html>
<%articles.clear(); %>