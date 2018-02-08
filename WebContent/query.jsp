<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>MyBot</title>
<script type="text/javascript">
	function add(){
		var textBox = document.createElement("input");
		textBox.setAttribute("type","text");
		textBox.setAttribute("name","query");
		textBox.setAttribute("placeholder","add new query");
		var newLine = document.createElement("BR");
		var span = document.getElementById("newRow");
		span.appendChild(newLine);
		span.appendChild(textBox);
	}
</script>
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
			<label> Pre-generated Queries </label> <br>
			<ul>
 				<c:forEach items="${queries}" var="val">
  					<li>  <c:out value="${val}"/> </li>
 				</c:forEach>
			</ul>
			<form action="QueryInsert" method="POST">
				<input type="text" name="query" placeholder="add new query">
				<span id="newRow"> </span>
				<input type="button" name="addRow" value="AddRow" onclick="add();"> <br> <br>
				<input type="hidden" name="paramT" value="${ param.paramT }">
				<input type="hidden" name="dbName" value="${ dbName }">
				<!-- Answers for query section -->
				<label>Set Answer For Query</label>
				<input type="text" name="answer">
				<input type="submit" name="newQuery" value="Add New">
			</form>
		</div>
	</div>
	<div class="footer"></div>
</body>
</html>