<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
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
	Data base name is <%= session.getAttribute("dbName") %>
	<div class="main">
		<form action="KeywordAndQuery" method="POST">
			<label>Keyword Name</label> <br> <br>
			<input type="text" name="keyword" placeholder="keyword"> <br>
			<label>Pre-Generated Query</label> <br>
			<label>Note : Query Should contains Keyword</label> <br> <br>
			<input type="text" name="query" placeholder="add query">
			<span id="newRow"> </span>
			<input type="button" name="addRow" value="AddRow" onclick="add();"> <br> <br>
			<label>Reply</label>
			<input type="text" name="reply" placeholder="query reply"> <br>
			<input type="submit" value="Done">
		</form>
	</div>
</body>
</html>