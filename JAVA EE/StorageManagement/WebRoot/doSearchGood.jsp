<%
String name = (String)request.getParameter("name");
String size = (String)request.getParameter("size");
String color = (String)request.getParameter("color");
String storageid = (String)request.getParameter("address");
String passman = (String)request.getParameter("passman");

String sql = "select * from good";

%>