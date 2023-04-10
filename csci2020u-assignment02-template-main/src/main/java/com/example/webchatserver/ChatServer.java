package com.example.webchatserver;

import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value="/ws/{roomID}",configurator = ChatroomConfig.class)
public class ChatServer {
	static Map<String,  Set<Session>> roomsusers = new HashMap<>();
	static Set<Session> chatroomusers= Collections.synchronizedSet(new HashSet<Session>());
	Set<Session> roomusers= new HashSet<Session>();
	static String id="";
	@OnOpen
	public void open(@PathParam("roomID") String roomID,EndpointConfig endpointConfig,Session userSession)
	{
	userSession.getUserProperties().put("username", endpointConfig.getUserProperties().get("username"));
	//chatroomusers.add(userSession);
	System.out.println("open room id :"+roomID);
	id=roomID;
	chatroomusers= new HashSet<>();
	if(roomsusers.containsKey(roomID))
    {
		roomsusers.get(roomID).add(userSession);
    }else
    { 
    	chatroomusers.add(userSession);
    	roomsusers.put(roomID,chatroomusers);
    }
	
	handleMessage("joined the chat!", userSession);
	
	}
	@OnMessage
	
	public void handleMessage(String message, Session userSession)
	{
		String username = (String) userSession.getUserProperties().get("username");
		LocalDateTime date = LocalDateTime.now();
		
		if(username!=null)
		{
			roomsusers.get(id).stream().forEach(x ->{
				//System.out.println((String)x.getUserProperties().get("username"));
				//System.out.println(username);
				if(username.equals((String)x.getUserProperties().get("username")))
						{
					        roomusers=roomsusers.get(id);
						}
			});
		
			roomusers.stream().forEach(x ->{
				try 
				{
					//System.out.println((String)x.getUserProperties().get("username"));
					x.getBasicRemote().sendText(buildJsonData("["+date.getHour()+":"+date.getMinute()+"]"+username, message));
						
			    }catch (Exception e) {
					e.printStackTrace();				}
				
		});
			
		}
		}
				
	
	@OnClose
	public void close(Session userSession)
	{
		chatroomusers.remove(userSession);
	}
	
	private String buildJsonData(String username, String message)
	{
		JsonObject jsonObject = Json.createObjectBuilder().add("message",username+": "+message).build();
		StringWriter stringWriter=new StringWriter();
		try(JsonWriter jsonwriter=Json.createWriter(stringWriter))
		{
			jsonwriter.write(jsonObject);
			return stringWriter.toString();
		}
		
				
	}
	

	
}
