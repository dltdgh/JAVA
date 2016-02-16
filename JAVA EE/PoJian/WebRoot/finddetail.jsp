<%@page import="com.dlt.db.DB_DAO"%>
<%@page import="pojo.Type"%>
<%@page import="pojo.Info"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%

//首页查询 根据传入页号进行分页

String keyStr = request.getParameter("key");
String key = new String(keyStr.getBytes("iso-8859-1"), "utf-8");
String reason = request.getParameter("reason");
String aop = request.getParameter("aop");
String pageStr = request.getParameter("page");

int currentPage = (pageStr == null) ? 0 : (Integer.parseInt(pageStr));
String urlStr = request.getRequestURL().toString();
urlStr = urlStr+"?key="+keyStr+"&reason="+reason+"&aop="+aop+"&page=";
System.out.println(urlStr); 

DB_DAO dbDao = new DB_DAO();

List<Info> infoList = null;

List<Type> typeList = dbDao.getTypesBySql("select * from tb_type order by type_sign asc;");

if(key != null && key != ""){
	if(aop == null || aop.equals("all")){
		
		if(reason.equals("1")){
			infoList = dbDao.getInfosBySql("select * from tb_info where info_title='"+key+"' and info_state='1' order by info_date desc;");
		}
		else if(reason.equals("2")){
			infoList = dbDao.getInfosBySql("select * from tb_info where info_linkman='"+key+"' and info_state='1' order by info_date desc;");
		}
	}
	else if(aop.equals("part")){
		if(reason.equals("1")){
			infoList = dbDao.getInfosBySql("select * from tb_info where info_title like '%"+key+"%' and info_state='1' order by info_date desc;");
		}
		else if(reason.equals("2")){
			infoList = dbDao.getInfosBySql("select * from tb_info where info_linkman like '%"+key+"%' and info_state='1' order by info_date desc;");
		}
	}
}
else{
	infoList = new ArrayList<Info>();
}

dbDao.close();

//根据查出的list进行分页显示

int PAGE_SIZE = 10;
int SUM_INFO = infoList.size();
int MAX_PAGE = SUM_INFO/PAGE_SIZE;
if(SUM_INFO != 0 && SUM_INFO%PAGE_SIZE == 0){
	MAX_PAGE -= 1;
}
System.out.println(SUM_INFO+" "+MAX_PAGE+" "+currentPage);
if(currentPage > MAX_PAGE){
	currentPage = MAX_PAGE;
	infoList = infoList.subList(currentPage*PAGE_SIZE, SUM_INFO);
}
else if(currentPage < 0){
	currentPage = 0;
	infoList = infoList.subList(currentPage*PAGE_SIZE, ((currentPage+1)*PAGE_SIZE) > SUM_INFO ? SUM_INFO : (currentPage+1)*PAGE_SIZE);
}
else{
	infoList = infoList.subList(currentPage*PAGE_SIZE, ((currentPage+1)*PAGE_SIZE) > SUM_INFO ? SUM_INFO : (currentPage+1)*PAGE_SIZE);
}

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>首页关键字检索</title>
<style>
</style>
</head>
<body>
<!--上部菜单栏-->
<div style="width:920px; height:auto; margin-left:auto; margin-right:auto;">
	<iframe src="header.html" frameborder="0" scrolling="no" width="920px" height="213px"></iframe>
</div>
<div style="width:920px; height:auto; margin-left:auto; margin-right:auto;">
	<!-- 左侧 -->
	<div style="width:225px; height:320px; float:left; border:solid 0px;">
		<!--日历-->
		<div style="width:225px; height:220px; float:left; border:solid 0px;">	
			<iframe src="calender.html" frameborder="0" scrolling="no" width="225px" height="220px"></iframe>
		</div>
		<!--左检索窗-->
		<div style="width:225px; height:100px; float:left; border:solid 0px;">
			<iframe src="left.html" frameborder="0" scrolling="no" width="225px" height="110px"></iframe>
		</div>
	</div>
    <!--付费内容区-->
    <div style="width:695px; height:auto; float:right; border:solid 0px;">
    	<div style="width:695px;height:18px; color:blue; background-color:RGB(240,240,240); margin-top:10px">
        <span>■</span><span style="margin-left:4px;"><b>查询结果</b></span>
    	</div>
        <table style="margin-top:20px; width:90%; border:solid RGB(240,240,240);" align="center">
        	<thead>
            	<tr><th>序号</th><th>信息类别</th><th>ID值</th><th>信息标题</th><th>发布时间</th><th>联系人</th></tr>
            </thead>
            <tbody>
            	<%
            	for(int i = 0; i < infoList.size(); i++){
            		Info tInfo = infoList.get(i);
            		if(i%2==0){
            	%>
            	<tr style="background-color:RGB(255,255,255);"><td><%= i %></td><td><%= typeList.get(tInfo.getInfoType()-1).getTypeIntro() %></td><td><%= tInfo.getId() %></td><td><a href="showdetail.jsp?infoid=<%= tInfo.getId() %>&flag=1"><%= tInfo.getInfoTitle() %></a></td><td><%= DB_DAO.formatDate(tInfo.getInfoDate()) %></td><td><%= tInfo.getInfoLinkman() %></td></tr>
                <%
            		}
            		else{
                %>
                <tr style="background-color:RGB(240,240,240);"><td><%= i %></td><td><%= typeList.get(tInfo.getInfoType()-1).getTypeIntro() %></td><td><%= tInfo.getId() %></td><td><a href="showdetail.jsp?infoid=<%= tInfo.getId() %>&flag=1"><%= tInfo.getInfoTitle() %></a></td><td><%= DB_DAO.formatDate(tInfo.getInfoDate()) %></td><td><%= tInfo.getInfoLinkman() %></td></tr>
           		<%
            		}
            	}
           		%>
            </tbody>
            <tfoot>
            	<tr><td colspan="6"><span  style="float:left;">每页显示：<span><%= infoList.size() %></span>/<span><%= SUM_INFO %></span>条记录！当前页：<span><%= currentPage+1 %></span>/<span><%= MAX_PAGE+1 %></span>页！</span><span style="float:right;"><a href="<%= urlStr+"0" %>">首页</a><a href="<%= urlStr+(currentPage-1) %>">上一页</a><a href="<%= urlStr+(currentPage+1) %>">下一页</a><a href="<%= urlStr+MAX_PAGE %>">尾页</a></span></td></tr>
            </tfoot>
        </table>
    </div>
</div>

</body>
</html>
