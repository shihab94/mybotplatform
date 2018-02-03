<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<!-- Session checking -->
	<% 
		String ses = (String) session.getAttribute("user");
		if(ses == "" || ses == null){
			response.sendRedirect("admin.jsp");
		}
	%>
	Data base name is <%= request.getAttribute("dbName") %>
	<div class="main">
		<form action="KeywordAndQuery" method="POST">
			<label>Keyword Name</label>
			<input type="text" name="keyword" placeholder="keyword"> <br>
			<label>Pre-Generated Query</label>
			<label>Note : Query Should contains Keyword</label>
			<input type="text" name="query" placeholder="query"> <br>
			<label>Reply</label>
			<input type="text" name="reply" placeholder="query reply"> <br>
			<input type="hidden" name="dbName" value="${ dbName }">
			<input type="submit" value="Done">
		</form>
	</div>
</body>
</html>