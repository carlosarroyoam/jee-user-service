package com.carlosarroyo.api.development.websockets;

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
 * @author Carlos Alberto Arroyo Martinez – carlosarroyoam@gmail.com
 */
@ServerEndpoint("/websockets/notificationservice")
public class WebSocketServer {
    
    private static Map<String, Session> sessions = new HashMap<String, Session>();
    
    @OnOpen
    public void open(Session session) throws IOException {
        sessions.put(session.getId(), session);
        System.out.println("Opening connection: " + session.getId());
        System.out.println("After open socket sessions: " + sessions.size());
        session.getBasicRemote().sendText(session.getId() + ": Connected to socket server");
    }

    @OnClose
    public void close(Session session) throws IOException {
        sessions.remove(session.getId());
        System.out.println("Closing connection: " + session.getId());
        System.out.println("After close socket sessions: " + sessions.size());

    }

    @OnError
    public void onError(Throwable error) {
        Logger.getLogger(WebSocketServer.class.getName()).log(Level.SEVERE, null, error);
    }

    @OnMessage
    public void handleMessage(String message, Session session) throws IOException {

//        try (JsonReader reader = Json.createReader(new StringReader(message))) {
//            JsonObject jsonMessage = reader.readObject();
//
//            if ("add".equals(jsonMessage.getString("action"))) {
//
//            }
//        }
    }
}
