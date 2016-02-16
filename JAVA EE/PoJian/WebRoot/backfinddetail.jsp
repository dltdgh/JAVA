<%@page import="com.dlt.db.DB_DAO"%>
<%@page import="pojo.Type"%>
<%@page import="pojo.Info"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!-- 登录验证 -->
<%@include file="logincheck.jsp"%>

<%

//根据info的id在tb_info表里进行查询，获取所需的info对象

String payInfo = request.getParameter("payinfo");
System.out.println(payInfo);

Info tInfo = null;
Type type = null;
DB_DAO dbDao = new DB_DAO();
String sql = "select * from tb_info where";
if(payInfo != null && payInfo != ""){
	sql = sql+" id="+payInfo+";";
	System.out.println(sql);
	List<Info> infoList = dbDao.getInfosBySql(sql);
	if(infoList.size() == 1){
		tInfo = infoList.get(0);
		sql = "select * from tb_type where type_sign="+tInfo.getInfoType()+";";
		type = dbDao.getTypesBySql(sql).get(0);
	}
}
dbDao.close();

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>后台已付费信息id检索</title>
</head>
<body>
	<!--头部-->
	<div style="width:920px; height:100px; margin-left:auto; margin-right:auto">
    	<iframe src="backheader.html" width="920px" height="100px" frameborder="0" scrolling="no"></iframe>
    </div>
    <!--总框架-->
    <div style="width:920px; height:auto; margin-left:auto; margin-right:auto">
		<!--左侧-->
        <div style="width:690px;height:auto; float:left; margin-top:10px; border:solid 0px;">
            <!--圆角表格头部-->
            <div style="width:690px;height:auto; float:left; border:solid 0px;">
                <img src="img/default_t.jpg" />
            </div>
            <!--列表-->
            <div style="width:690px;height:auto; float:left; background-image:url(img/default_m.jpg); background-repeat:repeat-y; border:solid 0px;">
               	<%
               	if(tInfo == null || type == null){
               	%>
               	<div style="width:600px; height:auto; border:solid 0px; margin-left:auto; margin-right:auto; font-size:15px;">
               		未找到信息！
               	</div>
               	<%
               	}
               	else{
               		//System.out.println(tInfo.getInfoPayfor());
               	%>
                <div style="width:600px; height:auto; border:solid 0px; margin-left:auto; margin-right:auto; font-size:15px;">
                	<div><b>付费设置[ID值:<span style="color:red;"><%= tInfo.getId() %></span>]</b></div>
                    <table width="100%" style="margin-top:5px;">
                    	<caption style="padding:5px; background-color:RGB(240,240,240);">详细信息内容</caption>
                        <tr><td>信息类别：</td><td><span>【<%= type.getTypeIntro() %>】</span></td><td>付费状态：<span>★<%= tInfo.getInfoPayfor().equals("1") ? "已付费" : "未付费" %></span></td></tr>
                        <tr><td>发布时间：</td><td><span><%= DB_DAO.formatDate(tInfo.getInfoDate()) %></span></td><td>审核状态：★<span><%= tInfo.getInfoState().equals("1") ? "已审核" : "未审核" %></span></td></tr>
                        <tr><td>信息标题：</td><td colspan="2"><%= tInfo.getInfoTitle() %></td></tr>
                        <tr style="background-color:RGB(240,240,240); padding:3px;"><td>信息内容：</td><td><input type="button" name="setpayed" value="√设为付款" onclick="javascript:window.location='checkinfo.jsp?infoid=<%= tInfo.getId() %>&cop=2';" <%= tInfo.getInfoPayfor().equals("1") ? "disabled=\"disabled\"" : "" %>/></td><td><input type="button" name="deleteinfo" onclick="javascript:window.location='deleteinfo.jsp?infoid=<%= tInfo.getId() %>';" value="×删除信息"/></td></tr>
                    </table>
                </div>
                <div style="width:600px; height:auto; border:solid 1px RGB(240,240,240); margin-left:auto; margin-right:auto; font-size:15px;">
                	<!--标题-->
                    <div style="margin-top:8px; margin-bottom:5px;"><span style="color:black;"><%= type.getTypeIntro() %>内容</span></div>
                    <div style="border:solid 0px;">
                    	<%= tInfo.getInfoContent() %>
                   </div>
                   	<table style="width:100%">
                    	<tr><td>联系电话：<span><%= tInfo.getInfoPhone() %></span></td><td>联系人：<span><%= tInfo.getInfoLinkman() %></span></td><td>E-mail：<span><%= tInfo.getInfoEmail() %></span></td></tr>
                    </table>
                </div>
                <%
               	}
                %>
            </div>
            <!--列表底部图片-->
            <div style="width:690px;height:auto; float:left; border:solid 0px;">
                <img src="img/default_e.jpg" />
            </div>
        </div>
		<!--右部-->        
        <div style="width:230px;height:auto; float:right; margin-top:15px; border:solid 0px;">
        	<!--用户管理标题-->
            <div style="width:230px;height:auto; float:right; color:blue; background-color:RGB(240,240,240); border:solid 0px;">
                <span>■</span><span style="margin-left:4px;"><b>用户管理</b></span>
            </div>
            <!-- 用户管理内容 -->
            <div style="width:230px;height:auto; float:right; margin-top:8px; border:solid 0px; font-size:15px;">
				<table align="center">
            		<tr><td><input style="margin: 10px 15px;" type="button" name="usercheck" value="信息查看" onclick="javascript:window.location='useroption.jsp?option=1'"/></td><td><input style="margin: 10px 15px;" name="usermodify" type="button" value="信息修改" onclick="javascript:window.location='useroption.jsp?option=2'"/></td></tr>
            	</table>            
            </div>
        	<!--显示方式标题-->
            <div style="width:230px;height:auto; float:right; color:blue; background-color:RGB(240,240,240); border:solid 0px;">
                <span>■</span><span style="margin-left:4px;"><b>显示方式</b></span>
            </div>
            <!--付费状态表格-->
            <div style="width:230px;height:auto; float:right; margin-top:8px; border:solid 0px; font-size:15px;">
            	<form action="background.jsp" method="post">
                	<fieldset>
                		<legend>★付费状态</legend>
                        <input type="radio" name="paystate" value="unpay"/>未付费
                        <input type="radio" name="paystate" value="payed"/>已付费
                        <input type="radio" name="paystate" value="all" checked="checked"/>全部
                	</fieldset>
            		<fieldset>
                		<legend>★审核状态</legend>
                        <input type="radio" name="check" value="uncheck"/>未审核
                        <input type="radio" name="check" value="checked"/>已审核
                        <input type="radio" name="check" value="all" checked="checked"/>全部
                	</fieldset>
                    <table style="width:100%; background-color:RGB(200,200,200);">
                    	<tr><td>信息类别：</td><td><select name="infotype" style="width:80px;"><option value="0">所有信息</option><option value="1">招聘信息</option><option value="2">培训信息</option><option value="3">房屋信息</option><option value="4">求购信息</option><option value="5">招商引资</option><option value="6">公寓信息</option><option value="7">求职信息</option><option value="8">家教信息</option><option value="9">车辆信息</option><option value="10">出售信息</option><option value="11">寻找启示</option></select></td><td><input type="submit" name="submit" value="提交" /></td></tr>
                    </table>
                </form>
            </div>
            <!--付费设置标题-->
            <div style="width:230px;height:auto; float:right; color:blue; background-color:RGB(240,240,240); margin-top:10px;">
                <span>■</span><span style="margin-left:4px;"><b>付费设置</b></span>
            </div>
            <!--付费信息检索表格-->
            <div style="width:230px;height:auto; float:right; margin-top:5px;">
				 <p align="center">请输入要设为付费状态的信息ID</p>
                 <form action="backfinddetail.jsp" method="post">
                 	<input type="text" size="22" name="payinfo"/><input style="float:right;" type="submit" name="submit" value="查询"/>
                 </form>   
            </div>
            
            <!--日历标题-->
            <div style="width:230px;height:auto; float:right; color:blue; background-color:RGB(240,240,240); margin-top:10px;">
                <span>■</span><span style="margin-left:4px;"><b>日历</b></span>
            </div>
            <!--日历-->
			<div style="width:230px; height:250px; float:left; border:solid 0px;">
				<iframe src="backcalender.html" frameborder="0" scrolling="no" width="230" height="250"></iframe>
			</div>
        </div>
    </div> 
</body>
</html>