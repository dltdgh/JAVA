
<%@include file="_SessionCheck.jsp" %>

<%
//System.out.println("wolaiguo!!!");
String url = request.getParameter("from");
session.removeAttribute("userLogined");
session.removeAttribute("userId");
response.sendRedirect(url);
%>