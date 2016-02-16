<%@page import="com.dlt.db.DB_DAO"%>
<%@page import="pojo.Type"%>
<%@page import="pojo.Info"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%
/*根据flag控制返回跳转 1 返回上页不刷新 返回上页并刷新*/
String flag = request.getParameter("flag");
String infoId = request.getParameter("infoid");
DB_DAO dbDao = new DB_DAO();
String sql = "select * from tb_info where id="+infoId+";";
Info tInfo = dbDao.getInfosBySql(sql).get(0);
sql = "select * from tb_type where type_sign="+tInfo.getInfoType()+";";
Type tType = dbDao.getTypesBySql(sql).get(0);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>首页关键字检索</title>
<style>
td{
	border:dotted 1px;
}
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
        <span>■</span><span style="margin-left:4px;"><b>详细内容</b></span>
    	</div>
        <table style="margin-top:20px; width:90%; border:solid RGB(240,240,240);" align="center">
        	<tr><td width="20%">信息ID:</td><td><%= tInfo.getId() %></td></tr>
        	<tr><td>信息类型:</td><td><%= tType.getTypeIntro() %></td></tr>
        	<tr><td>信息标题:</td><td><%= tInfo.getInfoTitle() %></td></tr>
        	<tr><td>发布时间:</td><td><%= DB_DAO.formatDate(tInfo.getInfoDate()) %></td></tr>
        	<tr><td>信息内容:</td><td><%= tInfo.getInfoContent() %></td></tr>
        	<tr><td>联系电话:</td><td><%= tInfo.getInfoPhone() %></td></tr>
        	<tr><td>联系人:</td><td><%= tInfo.getInfoLinkman() %></td></tr>
        	<tr><td>E-mail:</td><td><%= tInfo.getInfoEmail() %></td></tr>
        	<%
        	if(tInfo.getInfoState().equals("0")){
        	%>
        	<tr><td colspan="2" align="center" style="color: red;">信息还未通过审核哦~等经过管理员审核通过后就可以在网站上看到你发布的信息了~~</td></tr>
        	<%
        	}
        	%>
			<tr><td colspan="2" align="center"><input name="back" type="button" value="返回" onclick="<%= flag.equals("1") ? "javascript:history.back();" : "javascript:self.location=document.referrer;"%>"/></td></tr>
        </table>
    </div>
</div>

</body>
</html>
