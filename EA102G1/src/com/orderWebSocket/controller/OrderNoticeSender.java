package com.orderWebSocket.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONObject;

@ServerEndpoint(value = "/OrderNoticeSender/{membre_id}", configurator=ServletAwareConfig.class) 
public class OrderNoticeSender {
	private static Map<String, Session> sessionsMap = new ConcurrentHashMap<>();
	private EndpointConfig config;
	@OnOpen
	public void onOpen(@PathParam("membre_id") String membre_id, Session userSession, EndpointConfig config) throws IOException {
		sessionsMap.put(membre_id, userSession);
		this.config = config;
		System.out.println("new connection:" + membre_id);
	}
	
	@OnMessage
	public void onMessage(Session userSession, String message) {
		JSONObject jsonObj = new JSONObject(message);
		String receiver = jsonObj.getString("receiver");//MXXX
        HttpSession httpSession = (HttpSession) config.getUserProperties().get("httpSession");
        ServletContext servletContext = httpSession.getServletContext();
		Session receiverSession = sessionsMap.get(receiver);
		if (receiverSession != null && receiverSession.isOpen()) {
			receiverSession.getAsyncRemote().sendText(message); //傳給對方
		}
		else {
			System.out.println("else");
			@SuppressWarnings("unchecked")
		    Map<String, List<String>> noticeMap = (HashMap<String, List<String>>)servletContext.getAttribute("noticeMap");
		    if (noticeMap == null) {
		    	noticeMap = new HashMap<String, List<String>>();
		    }
		    List<String> noticeList = noticeMap.get(receiver);
		    if (noticeList == null) {
		    	noticeList = new ArrayList<String>();
		    }
		    noticeList.add(message);
		    System.out.println(noticeList.size());
		    noticeMap.put(receiver, noticeList);
		    
			servletContext.setAttribute("noticeMap", noticeMap);
		}
	}
	@OnError
	public void onError(Session userSession, Throwable e) {
		System.out.println("Error: " + e.toString());
	}
	
	@OnClose
	public void onClose(Session userSession, CloseReason reason) {
		Set<String> userNames = sessionsMap.keySet();
		for (String userName : userNames) {
			if (sessionsMap.get(userName).equals(userSession)) {
				sessionsMap.remove(userName);
				System.out.println("Connection closed " + userName);
				break;
			}
		}
	}
	public void sendOffLineMessage(String receiver, String message) {
		Session receiverSession = sessionsMap.get(receiver);
		
			try {
				receiverSession.getBasicRemote().sendText(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
}
