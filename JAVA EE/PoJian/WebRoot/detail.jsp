<%@page import="com.dlt.db.DB_DAO"%>
<%@page import="pojo.Info"%>
<%@page import="pojo.Type"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%
	//根据typeid 检索info里infotype=id的所有数据并予以显示
	
	int id = Integer.parseInt((String)request.getParameter("id"));
	Type type = null;
	List<Info> infoPayedList = null;
	List<Info> infoUnpayList = null;
	DB_DAO dbDao = new DB_DAO();
	String sql = "select * from tb_type where id="+id+";";
	System.out.println(sql);
	type = dbDao.getTypesBySql(sql).get(0);
	sql = "select * from tb_info where info_type="+id+" and info_payfor='1' and info_state='1' order by info_date desc;";
	System.out.println(sql);
	infoPayedList = dbDao.getInfosBySql(sql);
	sql = "select * from tb_info where info_type="+id+" and info_payfor='0' and info_state='1' order by info_date desc;";
	System.out.println(sql);
	infoUnpayList = dbDao.getInfosBySql(sql);
	dbDao.close();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>首页导航检索</title>
<style>
</style>
</head>
<body>
<!--上部菜单栏-->
<div style="width:920px; height:auto; margin-left:auto; margin-right:auto;">
	<iframe src="header.html" frameborder="0" scrolling="no" width="920px" height="213px"></iframe>
</div>
<div style="width:920px; height:1000px; margin-left:auto; margin-right:auto;">
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
	<div style="width:695px; height:auto; float:right; border:solid 0px;">
	    <!--付费内容区-->
	    <div style="width:695px; height:auto; float:right; border:solid 0px;">
	    	<div style="width:695px;height:18px; float:right; color:blue; background-color:RGB(240,240,240); margin-top:20px">
	        <span>■</span><span style="margin-left:4px;"><b>推荐<%= type.getTypeIntro() %>【缴费专区】</b></span>
	    	</div>
	        <!--推荐信息区-->
	        <%
	        for(int i = 0; i < (infoPayedList.size() > 5 ? 5 : infoPayedList.size()); i++){
	        	Info tInfo = infoPayedList.get(i);
	        %>
	        <div style="margin-top:24px; float:right; width:695px; height:auto; border:solid 0px; background-color:#f7f7f7; font-size:12px;">
	        	<p><span style="text-align:left">【<%= tInfo.getInfoTitle() %>】</span><span style="float:right;">发布时间： 【<%= DB_DAO.formatDate(tInfo.getInfoDate()) %>】</span></p>
	            <p><%= tInfo.getInfoContent() %></p>
	            <table>
	            	<tr><td width="183px">联系电话：<%= tInfo.getInfoPhone() %></td><td width="183px">联系人：<%= tInfo.getInfoLinkman() %></td><td width="183px">E-mail：<%= tInfo.getInfoEmail() %></td></tr>
	            </table>
	        </div>
	        <%
	        }
	        %>
	    </div>
	    <!--免费内容区-->
	    <div style="width:695px; height:auto; float:right; border:solid 0px;">
	    	<div style="width:695px;height:18px; float:right; color:blue; background-color:RGB(240,240,240); margin-top:24px;">
	        <span>■</span><span style="margin-left:4px;"><b>最新<%= type.getTypeIntro() %>【免费专区】</b></span>
	    	</div>
	        <!-- 单条信息 -->
	        <%
	        for(int i = 0; i < (infoUnpayList.size() > 5 ? 5 : infoUnpayList.size()); i++){
	        	Info tInfo = infoUnpayList.get(i);
	        %>
	        <div style="float:right; margin-top:24px; width:695px; height:auto; border:solid 0px; background-color:#f7f7f7; font-size:12px;">
	        	<p><span style="text-align:left">【<%= tInfo.getInfoTitle() %>】</span><span style="float:right;">发布时间： 【<%= DB_DAO.formatDate(tInfo.getInfoDate()) %>】</span></p>
	            <p><%= tInfo.getInfoContent() %></p>
	            <table>
	            	<tr><td width="183px">联系电话：<%= tInfo.getInfoPhone() %></td><td width="183px">联系人：<%= tInfo.getInfoLinkman() %></td><td width="183px">E-mail：<%= tInfo.getInfoEmail() %></td></tr>
	            </table>
	        </div>
	       <%
	        }
	       %>
	    </div>
	</div>
</div>
</body>
</html>
