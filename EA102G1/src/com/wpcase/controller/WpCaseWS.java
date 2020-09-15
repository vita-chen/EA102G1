package com.wpcase.controller;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

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


@ServerEndpoint("/WpCaseWS/{vender_id}")
public class WpCaseWS {
		private static Map<String, Session> sessionsMap = new ConcurrentHashMap<>();
		
		@OnOpen
		public void onOpen(@PathParam("vender_id") String vender_id, Session userSession) throws IOException {
			sessionsMap.put(vender_id, userSession);
			System.out.println("new connection:" + vender_id);
		}
		
		@OnMessage
		public void onMessage(Session userSession, String message) {
			System.out.println(123);
			JSONObject obj = new JSONObject(message);
			String vender_id = obj.getString("vender_id");
			String wed_photo_order_no = obj.getString("wed_photo_order_no");
			System.out.println(vender_id+"廠商ID"+wed_photo_order_no);
			Session receiverSession = sessionsMap.get(vender_id);
			if (receiverSession != null && receiverSession.isOpen()) {
				receiverSession.getAsyncRemote().sendText(message); //傳給對方
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

	}

