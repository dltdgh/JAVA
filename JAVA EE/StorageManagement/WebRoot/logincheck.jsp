<%@page import="java.util.List"%>
<%@page import="com.dlt.db.DB_DAO"%>
<%@page import="com.dlt.pojo.User"%>
<%
String loginState = (String)session.getAttribute("loginstate");
User user = (User)session.getAttribute("user");
String username = (String)request.getParameter("username");
String password = (String)request.getParameter("password");

System.out.println("hhh");

if(loginState == null || loginState.equals("false")){
	if(username == null || password == null){
		response.sendRedirect("login.jsp");
		return;
	}
	else{
		DB_DAO dao = new DB_DAO();
		String sql = "select * from tb_user where name='"+username+"';";
		List<User> userList = dao.getUsersBySql(sql);
		dao.close();
		if(userList.size() == 0){
			response.sendRedirect("login.jsp");
			return;
		}
		else{
			User tUser = userList.get(0);
			if(!tUser.getPassword().equals(password)){
				response.sendRedirect("login.jsp");
				return;
			}
			else{
				session.setAttribute("loginstate", "true");
				session.setAttribute("user", tUser);
			}
		}
	}
}
%>