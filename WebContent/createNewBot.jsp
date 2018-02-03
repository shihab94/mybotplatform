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
			RequestDispatcher rd = request.getRequestDispatcher("admin.jsp");
			rd.forward(request, response);
		}
	%>
	<div class="main">
		<h4>Please Enter A Name For Your Bot</h4>
		<form action="NewBotServlet" method="GET">
			<input type="text" name="botName"> <br>
			<input type="submit" value="Create">
		</form>
	</div>
</body>
</html>