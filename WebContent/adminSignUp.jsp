<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SignUp</title>
</head>
<body>
	<div class="main">
		<h4>Note: Username and Password Can't Be Empty</h4>
		<form action="AdminSignUp" method="POST">
			<input type="text" name="name" placeholder="Full Name"> <br>
			<input type="text" name="userName" placeholder="user name"> <br>
			<input type="email" name="email" placeholder="email"> <br>
			<input type="password" name="password" placeholder="password"> <br>
			<input type="submit" name="create" value="Create">
		</form>
	</div>
</body>
</html>