package com.example.webchatserver;

import org.apache.tomcat.websocket.server.DefaultServerEndpointConfigurator;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.*;
public class ChatroomConfig extends DefaultServerEndpointConfigurator{
public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request,HandshakeResponse response )
{
	sec.getUserProperties().put("username", (String)((HttpSession)request.getHttpSession()).getAttribute("username"));
	
	
}
	

}
