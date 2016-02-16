<%@page import="com.dlt.db.DB_DAO"%>
<%@page import="pojo.Info"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%

//查询所有info信息并将首条存入数组

Info[] payedInfos = new Info[20];
Info[] unpayInfos = new Info[20];
int[] payedMark = new int[20];
int[] unpayMark = new int[20];
DB_DAO dbDao = new DB_DAO();
String sql = "select * from tb_info where info_state='1' and info_payfor='1' order by info_date desc;";
List<Info> payedList = dbDao.getInfosBySql(sql);
for(Info tInfo : payedList){
	int infoType = tInfo.getInfoType();
	if(payedMark[infoType] == 0){
		payedInfos[infoType] = tInfo;
		payedMark[infoType] = 1;
	}
}
sql = "select * from tb_info where info_state='1' and info_payfor='0' order by info_date desc;";
List<Info> unpayList = dbDao.getInfosBySql(sql);
for(Info tInfo : unpayList){
	int infoType = tInfo.getInfoType();
	if(unpayMark[infoType] == 0){
		unpayInfos[infoType] = tInfo;
		unpayMark[infoType] = 1;
	}
}
dbDao.close();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>首页</title>
<style>
a{
	text-decoration: none;
}
</style>
</head>
<body style="background-color:RGB(250,250,250)">
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
    <div style="width:695px; height:240px; float:right; border:solid 0px;">
    	<div style="width:695px;height:18px; float:left; color:blue; background-color:RGB(240,240,240); margin-top:10px;">
        <span>■</span><span style="margin-left:4px;"><b>推荐信息【缴费专区】</b></span>
    	</div>
    	<div style="width:347px; height:9em; float:left; background-color:#f7f7f7; font-size:12px;">
        <ul style="list-style:none;">
        <li>【招聘信息】<a href="detail.jsp?id=1"><%= payedInfos[1] == null ? "目前无招聘信息" : payedInfos[1].getInfoTitle()%></a></li>
        <li>【培训信息】<a href="detail.jsp?id=2"><%= payedInfos[2] == null ? "目前无培训信息" : payedInfos[2].getInfoTitle()%></a></li>
        <li>【房屋信息】<a href="detail.jsp?id=3"><%= payedInfos[3] == null ? "目前无房屋信息" : payedInfos[3].getInfoTitle()%></a></li>
        <li>【求购信息】<a href="detail.jsp?id=4"><%= payedInfos[4] == null ? "目前无求购信息" : payedInfos[4].getInfoTitle()%></a></li>
        <li>【招商引资】<a href="detail.jsp?id=5"><%= payedInfos[5] == null ? "目前无招商信息" : payedInfos[5].getInfoTitle()%></a></li>
        <li>【公寓信息】<a href="detail.jsp?id=6"><%= payedInfos[6] == null ? "目前无公寓信息" : payedInfos[6].getInfoTitle()%></a></li>
        </ul>
        </div>
        <div style="width:347px; height:9em; float:left; background-color:#f7f7f7; font-size:12px;">
        <ul style="list-style:none;">
        <li>【求职信息】<a href="detail.jsp?id=7"><%= payedInfos[7] == null ? "目前无求职信息" : payedInfos[7].getInfoTitle()%></a></li>
        <li>【家教信息】<a href="detail.jsp?id=8"><%= payedInfos[8] == null ? "目前无家教信息" : payedInfos[8].getInfoTitle()%></a></li>
        <li>【车辆信息】<a href="detail.jsp?id=9"><%= payedInfos[9] == null ? "目前无车辆信息" : payedInfos[9].getInfoTitle()%></a></li>
        <li>【出售信息】<a href="detail.jsp?id=10"><%= payedInfos[10] == null ? "目前无出售信息" : payedInfos[10].getInfoTitle()%></a></li>
        <li>【寻找启示】<a href="detail.jsp?id=11"><%= payedInfos[11] == null ? "目前无寻找启示" : payedInfos[11].getInfoTitle()%></a></li>
        </ul>
        </div>
        <!--广告图片-->
        <div style="width:695px; height:82px; float:left;">
        <img src="img/pcard2.jpg" style="float:left; width:695px; height:82px;"/>
    	</div>	
    </div>
    <!--免费内容区-->
    <div style="width:695px; height:auto; float:right; border:solid 0px;">
    	<div style="width:695px;height:18px; float:left; color:blue; background-color:RGB(240,240,240);">
        <span>■</span><span style="margin-left:4px;"><b>最新信息【免费专区】</b></span>
    	</div>
    	
        <!--信息区域-->
        <div style="width:347px; height:200px; float:left; background-color:#f7f7f7; font-size:12px; border:solid 0px;">
        	<table style="width:347px; height:200px;">
            	<tr><td style="background-color:#00b4b4; height:25px">△招聘信息</td></tr>
                <tr><td style="height:150px; text-align:center">☆<%= unpayInfos[1] == null ? "目前无招聘信息" : unpayInfos[1].getInfoTitle() %></td></tr>
                <tr><td style="height:12px; text-align:right"><a href="detail.jsp?id=1">更多...</a></td></tr>
            </table>
        </div>
        
        <div style="width:347px; height:200px; float:left; background-color:#f7f7f7; font-size:12px; border:solid 0px;">
        	<table style="width:347px; height:200px;">
            	<tr><td style="background-color:#00b4b4; height:25px">△培训信息</td></tr>
                <tr><td style="height:150px; text-align:center">☆<%= unpayInfos[2] == null ? "目前无培训信息" : unpayInfos[2].getInfoTitle() %></td></tr>
                <tr><td style="height:12px; text-align:right"><a href="detail.jsp?id=2">更多...</a></td></tr>
            </table>
        </div>
        
        <div style="width:347px; height:200px; float:left; background-color:#f7f7f7; font-size:12px; border:solid 0px;">
        	<table style="width:347px; height:200px;">
            	<tr><td style="background-color:#00b4b4; height:25px">△房屋信息</td></tr>
                <tr><td style="height:150px; text-align:center">☆<%= unpayInfos[3] == null ? "目前无房屋信息" : unpayInfos[3].getInfoTitle()%></td></tr>
                <tr><td style="height:12px; text-align:right"><a href="detail.jsp?id=3">更多...</a></td></tr>
            </table>
        </div>
        
        <div style="width:347px; height:200px; float:left; background-color:#f7f7f7; font-size:12px; border:solid 0px;">
        	<table style="width:347px; height:200px;">
            	<tr><td style="background-color:#00b4b4; height:25px">△求购信息</td></tr>
                <tr><td style="height:150px; text-align:center">☆<%= unpayInfos[4] == null ? "目前无求购信息" : unpayInfos[4].getInfoTitle()%></td></tr>
                <tr><td style="height:12px; text-align:right"><a href="detail.jsp?id=4">更多...</a></td></tr>
            </table>
        </div>
        
        <div style="width:347px; height:200px; float:left; background-color:#f7f7f7; font-size:12px; border:solid 0px;">
        	<table style="width:347px; height:200px;">
            	<tr><td style="background-color:#00b4b4; height:25px">△招商引资</td></tr>
                <tr><td style="height:150px; text-align:center">☆<%= unpayInfos[5] == null ? "目前无招商信息" : unpayInfos[5].getInfoTitle()%></td></tr>
                <tr><td style="height:12px; text-align:right"><a href="detail.jsp?id=5">更多...</a></td></tr>
            </table>
        </div>
        
        <div style="width:347px; height:200px; float:left; background-color:#f7f7f7; font-size:12px; border:solid 0px;">
        	<table style="width:347px; height:200px;">
            	<tr><td style="background-color:#00b4b4; height:25px">△公寓信息</td></tr>
                <tr><td style="height:150px; text-align:center">☆<%= unpayInfos[6] == null ? "目前无公寓信息" : unpayInfos[6].getInfoTitle()%></td></tr>
                <tr><td style="height:12px; text-align:right"><a href="detail.jsp?id=6">更多...</a></td></tr>
            </table>
        </div>
        
        <div style="width:347px; height:200px; float:left; background-color:#f7f7f7; font-size:12px; border:solid 0px;">
        	<table style="width:347px; height:200px;">
            	<tr><td style="background-color:#00b4b4; height:25px">△求职信息</td></tr>
                <tr><td style="height:150px; text-align:center">☆<%= unpayInfos[7] == null ? "目前无求职信息" : unpayInfos[7].getInfoTitle()%></td></tr>
                <tr><td style="height:12px; text-align:right"><a href="detail.jsp?id=7">更多...</a></td></tr>
            </table>
        </div>
        
        <div style="width:347px; height:200px; float:left; background-color:#f7f7f7; font-size:12px; border:solid 0px;">
        	<table style="width:347px; height:200px;">
            	<tr><td style="background-color:#00b4b4; height:25px">△家教信息</td></tr>
                <tr><td style="height:150px; text-align:center">☆<%= unpayInfos[8] == null ? "目前无家教信息" : unpayInfos[8].getInfoTitle()%></td></tr>
                <tr><td style="height:12px; text-align:right"><a href="detail.jsp?id=8">更多...</a></td></tr>
            </table>
        </div>
        
        <div style="width:347px; height:200px; float:left; background-color:#f7f7f7; font-size:12px; border:solid 0px;">
        	<table style="width:347px; height:200px;">
            	<tr><td style="background-color:#00b4b4; height:25px">△车辆信息</td></tr>
                <tr><td style="height:150px; text-align:center">☆<%= unpayInfos[9] == null ? "目前无车辆信息" : unpayInfos[9].getInfoTitle()%></td></tr>
                <tr><td style="height:12px; text-align:right"><a href="detail.jsp?id=9">更多...</a></td></tr>
            </table>
        </div>
        
        <div style="width:347px; height:200px; float:left; background-color:#f7f7f7; font-size:12px; border:solid 0px;">
        	<table style="width:347px; height:200px;">
            	<tr><td style="background-color:#00b4b4; height:25px">△出售信息</td></tr>
                <tr><td style="height:150px; text-align:center">☆<%= unpayInfos[10] == null ? "目前无出售信息" : unpayInfos[10].getInfoTitle()%></td></tr>
                <tr><td style="height:12px; text-align:right"><a href="detail.jsp?id=10">更多...</a></td></tr>
            </table>
        </div>
        
        <div style="width:347px; height:200px; float:left; background-color:#f7f7f7; font-size:12px; border:solid 0px;">
        	<table style="width:347px; height:200px;">
            	<tr><td style="background-color:#00b4b4; height:25px">△寻找启示</td></tr>
                <tr><td style="height:150px; text-align:center">☆<%= unpayInfos[11] == null ? "目前无寻找启示" : unpayInfos[11].getInfoTitle()%></td></tr>
                <tr><td style="height:12px; text-align:right"><a href="detail.jsp?id=11">更多...</a></td></tr>
            </table>
        </div>
    </div>
</div>
<div style="width:920px; height:auto; margin:0 auto;">
	<img src="img/end.jpg" width="920px"/>
</div>
</body>
</html>
