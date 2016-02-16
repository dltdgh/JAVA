<%@page import="com.dlt.db.DB_DAO"%>
<%@page import="pojo.Info"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!-- 登录验证 -->
<%@include file="logincheck.jsp"%>

<%
/* 获取表单提交过来的是否付已付费，已审核等相关信息，并形成一条sql语句，通过DB_DAO工具类进行查询 */

String payState = request.getParameter("paystate");
String checkState = request.getParameter("check");
String infoType = request.getParameter("infotype");
boolean cop = true;
System.out.println(payState+" "+checkState+" "+infoType);

if(payState.equals("all")){
	cop = true;
}
else if(checkState.equals("all")){
	cop = false;
}

DB_DAO dbDao = new DB_DAO();
String sql = "select * from tb_info where";
if(checkState != null){
	if(checkState.equals("checked")){
		sql = sql+" info_state='1'";
	}
	else if(checkState.equals("uncheck")){
		sql = sql+" info_state='0'";
	}
}

if(payState != null){
	if(payState.equals("payed")){
		if(sql.endsWith("where")){
			sql = sql+" info_payfor='1'";
		}
		else{
			sql = sql+" and info_payfor='1'";
		}
	}
	else if(payState.equals("unpay")){
		if(sql.endsWith("where")){
			sql = sql+" info_payfor='0'";
		}
		else{
			sql = sql+" and info_payfor='0'";
		}
	}
}

if(!infoType.equals("0")){
	if(sql.endsWith("where")){
		sql = sql+" info_type="+infoType+";";
	}
	else{
		sql = sql+" and info_type="+infoType+";";
	}
}
else{
	if(sql.endsWith("where")){
		sql = "select * from tb_info;";
	}
	else{
		sql = sql+";";
	}
}
System.out.println(sql);
List<Info> infoList = dbDao.getInfosBySql(sql);
dbDao.close();

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>后台付费状态检索</title>
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
            <!--左侧显示-->
            <div style="width:690px;height:auto; float:left; border:solid 0px;">
                <img src="img/default_t.jpg" />
            </div>
            <!--列表-->
            <div style="width:690px;height:auto; float:left; background-image:url(img/default_m.jpg); background-repeat:repeat-y; border:solid 0px;">
                <div style="width:auto; height:auto;" align="center">
                    <table style="width:650px;" align="center">
                        <thead style="background-color:RGB(240,240,240);">
                            <tr><th>序号</th><th>信息ID</th><th>信息标题</th><th>发布时间</th><th>付费</th><th>审核</th><th>操作</th></tr>
                        </thead>
                        <tbody>
                        	<%
                        	for(int i = 0; i < infoList.size(); i++){
                        		Info tInfo = infoList.get(i);
                        		if(i%2 == 0){
                        	%>
                        	<tr style="background-color:RGB(255,2555,255);"><td><%= i %></td><td><%= tInfo.getId() %></td><td><%= tInfo.getInfoTitle() %></td><td><%= DB_DAO.formatDate(tInfo.getInfoDate()) %></td><td><%= tInfo.getInfoPayfor().equals("1") ? (cop ? "已付费" : "<span style=\"color:red;\">已付费</span>"):(cop ? "未付费" : "<span style=\"color:blue;\">未付费</span>")  %></td><td><%= tInfo.getInfoState().equals("1") ? (cop ? "<span style=\"color:red;\">已审核</span>" : "已审核"):(cop ? "<span style=\"color:blue;\">未审核</span>" : "未审核") %></td><td><a href="checkinfo.jsp?infoid=<%= tInfo.getId() %>&cop=<%= cop ? 1 : 2 %>">√审核</a><a href="deleteinfo.jsp?infoid=<%= tInfo.getId() %>">×删除</a></td></tr>
                        	<%	
                        		}
                        		else{
                        	%>
                            
                            <tr style="background-color:RGB(249,249,249);"><td><%= i %></td><td><%= tInfo.getId() %></td><td><%= tInfo.getInfoTitle() %></td><td><%= DB_DAO.formatDate(tInfo.getInfoDate()) %></td><td><%= tInfo.getInfoPayfor().equals("1") ? (cop ? "已付费" : "<span style=\"color:red;\">已付费</span>"):(cop ? "未付费" : "<span style=\"color:blue;\">未付费</span>")  %></td><td><%= tInfo.getInfoState().equals("1") ? (cop ? "<span style=\"color:red;\">已审核</span>" : "已审核"):(cop ? "<span style=\"color:blue;\">未审核</span>" : "未审核") %></td><td><a href="checkinfo.jsp?infoid=<%= tInfo.getId() %>&cop=<%= cop ? 1 : 2 %>">√审核</a><a href="deleteinfo.jsp?infoid=<%= tInfo.getId() %>">×删除</a></td></tr>
                        	<%
                        		}
                        	}
                        	%>
                        </tbody>
                    </table>
                </div>
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