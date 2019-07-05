package com.carlosarroyoam.api.development.websockets;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * 
 * @author Carlos Alberto Arroyo Martínez <carlosarroyoam@gmail.com>
 */
@ServerEndpoint("/websockets/notificationservice")
public class WebSocketServer {
    
    private Map<String, Session> sessions = new HashMap<>();
    
    @OnOpen
    public void open(Session session) throws IOException {
        sessions.put(session.getId(), session);
        System.out.println("Opening socket connection: " + session.getId());
        System.out.println("Sessions after socket open: " + sessions.size());
        session.getBasicRemote().sendText(session.getId() + ": has been connected to socket server");
    }

    @OnClose
    public void close(Session session) throws IOException {
        sessions.remove(session.getId());
        System.out.println("Closing socket connection: " + session.getId());
        System.out.println("Sessions after socket closed: " + sessions.size());
    }

    @OnError
    public void onError(Throwable error) {
        Logger.getLogger(WebSocketServer.class.getName()).log(Level.SEVERE, null, error);
    }

    @OnMessage
    public void handleMessage(String message, Session session) {
    }
}
