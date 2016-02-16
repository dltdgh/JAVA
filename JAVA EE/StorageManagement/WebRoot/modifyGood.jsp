<%@page import="com.dlt.pojo.Storage"%>
<%@page import="com.dlt.pojo.Good"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="logincheck.jsp"%>


<%
	DB_DAO dao = new DB_DAO();
 	String sql = "select * from tb_good;";
	List<Good> goodList = dao.getGoodsBySql(sql);
	sql = "select * from tb_storage;";
	List<Storage> storageList = dao.getStoragesBySql(sql);
	HashMap<String, String> storageMap = new HashMap<String, String>();
	for(Storage tStorage : storageList){
		storageMap.put(tStorage.getId().toString(), tStorage.getAddress());
	}
	dao.close();
	User tUser = (User)session.getAttribute("user");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<style>
	   .title{
		border:solid 1px;
		border-collapse:collapse;
		border-spacing:0px;
		}
		table,td, th{
			border:solid 1px;
			border-collapse:collapse;
			border-spacing:0px;
			text-align:center;
			}
</style>
</head>
<body>
	<div style="width:600px; height:auto; margin-left:auto; margin-right:auto; margin-top:20px;">
    	<hr style="border:solid 3px;"/>
    	<div style="width:600px; float:left;">
        	<p style="text-align:right">欢迎<span style="margin-left:5px;margin-right:5px; color:red;"><%= tUser == null ? "" : tUser.getName() %></span><a href="doLogout.jsp">登出</a></p>
        </div>
        <div style="width:600px; float:left;">
            <table width="600px" style="text-align:center;">
                <tr><td class="title"><a href="index.jsp">主页</a></td><% if(tUser.getPermission() == 2){ %><td class="title"><a href="insertStorage.jsp">增加仓库记录</a></td><% } %><td class="title"><a href="insertGood.jsp">增加货物记录</a></td><td class="title"><a href="searchGood.jsp">查询货物记录</a></td><td class="title"><a href="modifyGood.jsp">修改货物记录</a></td><% if(tUser.getPermission() == 2){ %><td class="title"><a href="deleteGood.jsp">删除货物记录</a></td><% } %></tr>
            </table>
        </div>
        <div style="width:600px; float:left; margin-top:15px; margin-bottom: 15px;">
        <hr style="border:solid 1px;"/>
        </div>
        <div style="width:600px; float:left;">
        	<table style="width:600px;" class="mytable">
            	<tr>
                	<th>ID</th>
                    <th>名称</th>
                    <th>尺寸</th>
                    <th>颜色</th>
                    <th>数量</th>
                    <th>仓库</th>
                    <th>经手人</th>
                    <th>修改位</th>
                </tr>
                <% 
                for(Good tGood : goodList){
                	
                %>	
                <tr>
                	<td><%= tGood.getId() %></td>
                	<td><%= tGood.getName() %></td>
                    <td><%= tGood.getSize() %></td>
                    <td><%= tGood.getColor() %></td>
                    <td><%= tGood.getNum() %></td>
                    <td><%= storageMap.get(tGood.getStorageid().toString()) %></td>
                    <td><%= tGood.getPassman() %></td>
                    <td><a href="modifyGoodDetail.jsp?id=<%= tGood.getId() %>">修改</a></td>
                </tr>
                <%
                }
                %>
            </table>
        </div>
        <div style="width:600px; float:left; margin-top:15px; margin-bottom: 15px;">
        <hr style="border:solid 3px;"/>
        </div>
    </div>
</body>
</html>
