<%@page pageEncoding="GB18030" %>

<%
String url = request.getParameter("from");
%>

<html>
<head>
	<meta content="text/html;charset=utf-8">
</head>
<body>
<form id="login" name="login" method="Post" action="loginCheck.jsp?from=<%= url %>">
	<table width="400" border="0" align="center">
		<tr width="100%">
			<td width="10%">”√ªß</td>
			<td width="90%"><input name="username" type="text" id="username"/></td>
		</tr>
		<tr>
			<td width="10%">√‹¬Î</td>
			<td width="90%"><input name="password" type="password" id="password"/></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td><label><input type="submit" value="login"/></label></td>
		</tr>
	</table>
</form>
</body>
</html>