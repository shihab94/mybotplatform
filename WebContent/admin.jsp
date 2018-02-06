<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Admin</title>
</head>
<body>
	<div class="main">
		<h4>Welcome To MyBot Platform</h4>
		<form action="AdminLogin" method="POST">
			<input type="text" name="userName" placeholder="user_name"> <br> 
			<input type="password" name="password" placeholder="password"> <br>
			<input type="submit" name="action" value="Login">
			<input type="submit" name="action" value="SignUp">
		</form>
	</div>
</body>
</html>