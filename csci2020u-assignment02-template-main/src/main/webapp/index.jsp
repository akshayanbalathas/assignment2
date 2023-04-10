<%@page import="java.util.ArrayList"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="com.example.webchatserver.ChatServlet"%>
<%@page import="java.util.HashSet"%>
<%@page import="com.example.webchatserver.ChatRoom"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="css/style.css" type="text/css"/>
<link rel="stylesheet" href="css/normalize.css" type="text/css"/>
<style>
</style>
<title>Chat Server</title>
</head>
<body>
<%
//String username=request.getParameter("username");
String username= (String)session.getAttribute("username");
String msg =username+ "joined the chat";
String roomID=request.getParameter("room");
//ChatRoom rooms =(ChatRoom)session.getAttribute("username");
System.out.println("rooms number : "+ ChatServlet.rooms.size() );
//System.out.println(roomID);
%>
<h1 >Chat server</h1>
<div class="sidenav">
<h3>List of chat rooms</h3>
<script type="text/javascript">

function refresh()
{
	var node = document.createElement('li');
	node.appendChild(document.createTextNode('Scooter'));
	 
	document.querySelector('ul').appendChild(node);
}
</script>
<%
ArrayList<String> roomsarray = new ArrayList<>();
ChatServlet.rooms.stream().forEach(r->{
	System.out.println("room id : "+r);
	roomsarray.add(r);
});
%>
<ul>
<%
for(String r:roomsarray)
{
%>
<li><a href="http://localhost:8080/csci2020u-assignment02-template-main/joinRoom.jsp?room=<%=r%>"><%=r%></a></li>

<%} %>

</ul>	

  <a href="http://localhost:8080/csci2020u-assignment02-template-main/about.html">About</a>

  <a href="http://localhost:8080/csci2020u-assignment02-template-main/index.html"><button  type="button" >Create and join new room</button></a>
</div>

<div class="main">
 <h2>Welcome <%=username %>!you are chating in room <b><%=roomID %></b></h2>
<textarea id="messagesTextArea" rows="10" cols="45" readonly="readonly"></textarea>
<br>
<input type="text" id="messageText" size="50">
<input type="button" id="send" value="send" onclick="sendMessage();" />
<br>
</div>

<script type="text/javascript">
//create the web socket
var websocket = new WebSocket("ws://localhost:8080/csci2020u-assignment02-template-main/ws/<%=roomID%>");
// parse messages received from the server and update the UI accordingly
websocket.onmessage=function processMessage(message){
	// parsing the server's message as json
	var jsonData =JSON.parse(message.data);
	if(jsonData.message!=null)
		messagesTextArea.value += jsonData.message + "\n"
}
//handle message
function sendMessage(){
	websocket.send(messageText.value);
	messageText.value="";
	
}
//Enter key handler
var input = document.getElementById("messageText");
input.addEventListener("keypress", function(event) {
  if (event.key === "Enter") {
    event.preventDefault();
    document.getElementById("send").click();
  }
});
</script>


   
</body>
<footer>
<div align="center"><small>@2023</small></div>
</footer>
</html> 