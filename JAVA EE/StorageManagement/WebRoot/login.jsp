<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>login.jsp</title>

  </head>
  <body>
  	<div style="width:300px; margin-left:auto; margin-right:auto; margin-top: 200px">
  	<h2>用户登录</h2>
  	<form method="get" action="index.jsp">
  		<table>
	    	<tr>
	    		<td width="30%">用户名:</td>
	    		<td width="70%"><input type="text" name="username" id="username"/></td>
	    	</tr>
	    	<tr>
	    		<td>密码:</td>
	    		<td><input type="password" name="password" id="password"/></td>
	    	</tr>
	    	<tr>
	    	<td colspan="2" align="center"><input type="submit" value="登录" style="margin:10px;"/><input type="reset" value="重置" style="margin:10px;"/></td>
	    	</tr>
	    </table>
  	</form>
  	</div>	    
  </body>
</html>
