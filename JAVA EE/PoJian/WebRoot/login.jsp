<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>后台登录</title>
<script>

//登录验证

function submitcheck(){
	username = document.getElementById("username").value;
	if(username.length == 0){
		alert("用户名不能为空");
		return false;
	}
	password = document.getElementById("password").value;
	if(password.length == 0){
		alert("密码不能为空");
		return false;
	}
	checkcode = document.getElementById("checkcode").value;
	if(checkcode.length == 0){
		alert("验证码不能为空");
		return false;
	}
	return true;
}

//刷新验证码
function reloadimg(){
	checkcode = document.getElementById("check");
	//alert("hehe");
	checkcode.src = "checkcode?random="+Math.random();
}
</script>
</head>
<body style="background-color:RGB(231,236,239);">
<div style="width:550px; margin-left:auto; margin-right:auto; margin-top:100px;">
	<div style="width:550px; height:100px; border:solid 0px;">
    	<img src="img/logon_top.gif" width="550" height="100"/>
    </div>
    <div style="width:550px; height:auto; background-color:RGB(240,240,240);">
    	<form action="backwelcome.jsp" method="get" onsubmit="javascript:return submitcheck()">
        <table style="width:350px; height:auto; padding:40px" align="center">
        	<tr><td>用户名:</td><td colspan="2"><input type="text" name="username" id="username" /></td></tr>
            <tr><td>密&nbsp;码:</td><td colspan="2"><input type="password" name="password" id="password" /></td></tr>
            <tr><td>验证码:</td><td width="80px"><input type="text" name="checkcode" size="10" id="checkcode"/></td><td align="center"><img id="check" src="checkcode" onclick="reloadimg()"/></td></tr>
            <tr><td></td><td colspan="2"><input type="submit" name="submit" value="登录"/><input type="reset"  name="reset" value="重置"/><a href="index.jsp" style="font-size:12px;">[返回首页]</a></td></tr>
        </table>
        </form>
    </div>
</div>
</body>
</html>
