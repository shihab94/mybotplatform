<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>Insert title here</title>
	<script type="text/javascript">
		
		var request;
		
		function sendInfo(){
			var v=document.getElementById("query").value;
			var url="MyBotHandler?query="+v;
		
			if(window.XMLHttpRequest){
				request=new XMLHttpRequest(); // for chrome and firefox
			}else if(window.ActiveXObject){
				request=new ActiveXObject("Microsoft.XMLHTTP"); // for internet explorer
			}
		
			try{
				request.onreadystatechange=getInfo;// if state changes then getinfo fnc is called automatically
				request.open("GET",url,true);
				request.send();
			}catch(e){
				alert("Unable to connect to server");
			}
		}
		
		function getInfo(){
			if(request.readyState==4){
				var val=request.responseText;
				var para = document.createElement("p");
				var node = document.createTextNode(val);
				para.appendChild(node);
				var element = document.getElementById("message");
				element.appendChild(para);
			}
		}
	
	</script>  
</head>
<body>

	<h2>Welcome To <%= session.getAttribute("dbName") %> Bot</h2>
	
	<form name="vinform">
		<span id="message"> </span>
		<textarea rows="2" cols="30" id="query" ></textarea>
		<input type="button" value="ask" onClick="sendInfo()">
	</form>
	
</body>
</html>