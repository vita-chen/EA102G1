//
//package com.wpcase.controller;
//
//import java.io.IOException;
//import java.util.Collection;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.concurrent.ConcurrentHashMap;
//
//import javax.websocket.CloseReason;
//import javax.websocket.OnClose;
//import javax.websocket.OnError;
//import javax.websocket.OnMessage;
//import javax.websocket.OnOpen;
//import javax.websocket.Session;
//import javax.websocket.server.PathParam;
//import javax.websocket.server.ServerEndpoint;
//
//import com.google.gson.Gson;
//
//
//
//
//@ServerEndpoint("/WedPhotoWS/{userName}")
//public class WedPhotoWS {
//	private static Map<String, Session> sessionsMap = new ConcurrentHashMap<>();//並行
//	Gson gson = new Gson();//google出的 處理json更方便
//
//	
//
//	@OnOpen
//	public void onOpen(@PathParam("userName") String userName, Session userSession) throws IOException {
//		System.out.println(userName);
//	}
//
//	@OnMessage
//	public void onMessage(Session userSession, String message) {
////		ChatMessage chatMessage = gson.fromJson(message, ChatMessage.class);//叫gson去參考指定class 並且直接包成此類別的物件(VO)
////		String sender = chatMessage.getSender();
////		String receiver = chatMessage.getReceiver();
////		
////		if ("history".equals(chatMessage.getType())) {//類別 . 方法 
////			List<String> historyData = JedisHandleMessage.getHistoryMsg(sender, receiver);
////			String historyMsg = gson.toJson(historyData);
////			ChatMessage cmHistory = new ChatMessage("history", sender, receiver, historyMsg);
////			if (userSession != null && userSession.isOpen()) {
////				userSession.getAsyncRemote().sendText(gson.toJson(cmHistory));
////				System.out.println("history = " + gson.toJson(cmHistory));
////				return;
////			}
////		}
////		
////		
////		Session receiverSession = sessionsMap.get(receiver);
////		if (receiverSession != null && receiverSession.isOpen()) {
////			receiverSession.getAsyncRemote().sendText(message);
////			userSession.getAsyncRemote().sendText(message);
////			JedisHandleMessage.saveChatMessage(sender, receiver, message);
////		}
////		System.out.println("Message received: " + message);
//	}
////
//	@OnError
//	public void onError(Session userSession, Throwable e) {
//		System.out.println("Error: " + e.toString());
//	}
////
//	@OnClose
//	public void onClose(Session userSession, CloseReason reason) {
////		String userNameClose = null;
////		Set<String> userNames = sessionsMap.keySet();
////		for (String userName : userNames) {
////			if (sessionsMap.get(userName).equals(userSession)) {
////				userNameClose = userName;
////				sessionsMap.remove(userName);
////				break;
////			}
////		}
////
////		if (userNameClose != null) {
////			State stateMessage = new State("close", userNameClose, userNames);
////			String stateMessageJson = gson.toJson(stateMessage);
////			Collection<Session> sessions = sessionsMap.values();
////			for (Session session : sessions) {
////				session.getAsyncRemote().sendText(stateMessageJson);
////			}
////		}
////
////		String text = String.format("session ID = %s, disconnected; close code = %d%nusers: %s", userSession.getId(),
////				reason.getCloseCode().getCode(), userNames);
////		System.out.println(text);
//	}
//}
