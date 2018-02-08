<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>MyBot</title>
</head>
<body>
	<!-- Session checking -->
	<% 
		String ses = (String) session.getAttribute("user");
		if(ses == "" || ses == null){
			response.sendRedirect("admin.jsp");
		}
	%>
	<div class="header">
		<a href="Logout">LogOut</a>
	</div>
	<div class="main">
		<div class="left" style="float: left">
			<label> Your Bots</label>
			<ul>
 				<c:forEach items="${bots}" var="value">
  					<li> <a href="SingleBotHandler?botName=${ value }"> <c:out value="${value}"/> </a> </li>
 				</c:forEach>
			</ul>
		</div>
		<div class="right" style="float: right">
			<a href="createNewBot.jsp">Create A New Bot</a> <br>
			<label> Keywords </label> <br>
			<ul>
 				<c:forEach items="${entities}" var="val">
  					<li> <a href="QueryHandler?paramT=${ val }"> <c:out value="${val}"/> </a> </li>
 				</c:forEach>
			</ul>
			<a href="keywordAndQuery.jsp?dbName=${param.dbName}">Create New Entity</a>
		</div>
	</div>
	<div class="footer"></div>
</body>
</html>