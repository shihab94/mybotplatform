<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home</title>
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
		<h4>WELCOME <%= session.getAttribute("user") %></h4>
		<a href="Logout">LogOut</a>
	</div>
	<div class="main">
		<div class="left">
			<label> Your Bots</label>
			<ul>
 				<c:forEach items="${bots}" var="value">
  					<li> <c:out value="${value}"/></li>
 				</c:forEach>
			</ul>
		</div>
		<div class="right">
			<a href="createNewBot.jsp">Create A New Bot</a>
		</div>
	</div>
	<div class="footer"></div>
</body>
</html>