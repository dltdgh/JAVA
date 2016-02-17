
<%
String userLogined = (String)session.getAttribute("userLogined");
//System.out.println(userLogined);
if(userLogined == null || !userLogined.trim().equals("true")){
	String url = request.getRequestURL().toString();
	String suffix = request.getQueryString();
	if(suffix != null){
		url = url+"?"+suffix;
	}
	response.sendRedirect("login.jsp?from="+url);
	return;
}
%>