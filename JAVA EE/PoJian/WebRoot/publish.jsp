<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>发布</title>
<style>
</style>
<script>
	//进行发布信息验证
	var num = 500;
	function check(){
		var content = document.getElementById("content").value;
		var used = document.getElementById("used");
		used.innerHTML = content.length;
		var left = document.getElementById("left");
		left.innerHTML = num-content.length;
		if(content.length > 500){
			alert("字数超限");
		}
	}
	function checksubmit(){
		
		var title = document.getElementById("title").value;
		var content = document.getElementById("content").value;
		var phone = document.getElementById("phone").value;
		var linkman = document.getElementById("linkman").value;
		var email = document.getElementById("email").value;
		var reg = /^.*@.+\.[a-zA-Z]+$/;
		if(title.length == 0 || content.length == 0 || phone.length == 0 || linkman.length == 0 || email.length == 0){
			alert("数据输入不能为空！！！");
			return false;
		}
		else if(title.length > 40 || content.length > 500){
			alert("数据输入长度超限！！！");
			return false;
		}
		else if(!reg.test(email)){
			alert("邮箱格式错误！！！");
			return false;
		}
		else{
			return true;
		}
	}
</script>
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
    <!--右侧-->
    <div style="width:695px; height:240px; float:right; border:solid 0px;">
    	<!--右侧输入框-->
        <div style="width:695px;height:auto; float:left; margin-top:10px; border:solid 0px;">
            <!--圆角表格头部-->
            <div style="width:690px;height:auto; margin: 0px auto; border:solid 0px;">
                <img src="img/default_t.jpg" />
            </div>
            <!--列表-->
            <div style="width:690px;height:auto; margin: 0px auto; background-image:url(img/default_m.jpg); background-repeat:repeat-y; border:solid 0px;">
            	<div style="width:650px;height:18px; margin: 0px auto; color:blue; background-color:RGB(240,240,240);">
        			<span>■</span><span style="margin-left:4px;"><b>发布信息</b></span>
    			</div>
    			<div style="width:650px;height:auto; margin: 0px auto; ">
    			<br/>
    			<form action="publish_deal.jsp" method="post" onsubmit="return checksubmit()">
	    			<table style="width:100%;">
	    				<tr><td width="15%">信息类别:</td><td><select name="infotype" style="width:80px;"><option value="1">招聘信息</option><option value="2">培训信息</option><option value="3">房屋信息</option><option value="4">求购信息</option><option value="5">招商引资</option><option value="6">公寓信息</option><option value="7">求职信息</option><option value="8">家教信息</option><option value="9">车辆信息</option><option value="10">出售信息</option><option value="11">寻找启示</option></select>[请选择信息类别]</td></tr>
	    				<tr><td>信息标题:</td><td><input type="text" name="title" id="title"/>[信息标题最多不超过40个字符]</td></tr>
	    				<tr><td>信息内容:</td><td>已用:<span id="used" style="margin:0px 5px;">0</span>个&nbsp;剩余:<span id="left" style="margin:0px 5px;">500</span>个</td></tr>
	    				<tr><td colspan="2" align="center"><textarea style="width:100%;" rows="10" name="content" onkeyup="check()" id="content"></textarea></td></tr>
	    				<tr><td>联系电话:</td><td><input id="phone" type="text" name="phone"/></td></tr>
	    				<tr><td>联系人:</td><td><input id="linkman" type="text" name="linkman"/></td></tr>
	    				<tr><td>E-mail:</td><td><input id="email" type="text" name="email"/></td></tr>
	    				<tr><td colspan="2" align="center"><input type="submit" name="submit" value="发布" style="margin:0px 10px;"/><input type="reset" name="reset" value="重填" style="margin:0px 10px;"/></td></tr>
	    			</table>	
    			</form>
    			</div>
            </div>
            <!--列表底部图片-->
            <div style="width:690px;height:auto; margin: 0px auto; border:solid 0px;">
                <img src="img/default_e.jpg" />
            </div>
        </div>
    </div>
</div>
<div style="width:920px; height:auto; margin:0 auto;">
	<img src="img/end.jpg" width="920px"/>
</div>
</body>
</html>
