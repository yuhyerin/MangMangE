package com.daeng.nyang.controller;

import java.io.IOException;
import java.nio.ByteBuffer;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value="/wsServer")
public class WebSocketController {
	@OnOpen
	public void OnOpen(Session session) {
		System.out.println(session.toString()); 
	}
	
	@OnMessage
	public void onMessage(Session ss,byte[] img) {
		ByteBuffer buf = ByteBuffer.wrap(img);
		try {
			ss.getBasicRemote().sendBinary(buf);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@OnClose
	public void onClose(Session ss) {
		try {
			ss.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
