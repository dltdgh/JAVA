<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
//登出操作
session.setAttribute("loginstate", "false");
response.sendRedirect("login.jsp");
%>