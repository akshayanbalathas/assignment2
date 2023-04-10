<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Join an existing room</title>
</head>
<link rel="stylesheet" href="css/style.css" type="text/css"/>
<link rel="stylesheet" href="css/normalize.css" type="text/css"/>
<body>
<% 
String roomID=request.getParameter("room");
%>
<div class="welcome" align="center">
</div>
<div align="center">
<form action="http://localhost:8080/csci2020u-assignment02-template-main/ChatServlet">
<input type="text" name="room" size="50" value="<%=roomID %>" style="display: none;"><br>
<label>Please enter a user name :</label><br>
<input type="text" name="username" size="20" required="required">
<input type="submit" value="Create/Join" />
</form>
</div>


</body>
</html>