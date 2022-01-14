package com.lemon.controller;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint(value = "/chat/websocket/{sid}")
public class WebSocketServer {
    private static int onlineCount = 0;

    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();

    private Session session;

    private String sid = "";

    @OnOpen
    public void onOpen(Session session, @PathVariable("sid") String sid){
        this.session = session;
        this.sid = sid;
        webSocketSet.add(this);
        addOnlineCount();

        System.out.println("链接成功"+sid);
    }

    @OnClose
    public void onClose(){
        webSocketSet.remove(this);
        subOnlineCount();
        System.out.println("关闭链接");
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        session.getBasicRemote().sendText(message);
    }

    public void sendMessage(String message){

    }


    private void addOnlineCount() {
        onlineCount++;
    }


    private void subOnlineCount() {
        onlineCount--;
    }

}
