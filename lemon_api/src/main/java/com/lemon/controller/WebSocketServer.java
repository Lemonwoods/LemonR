package com.lemon.controller;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Resource;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lemon.dao.pojo.Message;
import com.lemon.service.ChatService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@ServerEndpoint("/chat/websocket/{userId}")
@Service
public class WebSocketServer {
    private static ChatService chatService;

    private static int onlineCount = 0;

    private static ConcurrentHashMap<Long,WebSocketServer> webSocketMap = new ConcurrentHashMap<>();

    private Session session;

    private Long userId;

    @Autowired
    public void setChatService(ChatService chatService){
        WebSocketServer.chatService = chatService;
    }

    //成功建立连接时调用
    @OnOpen
    public void onOpen(Session session,@PathParam("userId") String userId) throws IOException {
        this.session = session;
        this.userId=Long.valueOf(userId);
        //判断连接是否已存在,更新连接或者加入连接
        if(webSocketMap.containsKey(this.userId)){
            webSocketMap.remove(this.userId);
            webSocketMap.put(this.userId,this);
        }else{
            webSocketMap.put(this.userId,this);
            addOnlineCount();
        }

//        // 获取未读消息列表
//        List<Message> messageList = chatService.getUnreadMessage(this.userId);
//        for(Message message:messageList){
//            sendMessage(message);
//        }

        try {
            sendMessage("连接成功");
        } catch (IOException e) {
        }
    }

    //关闭连接时调用
    @OnClose
    public void onClose() {
        //从map中删除连接
        if(webSocketMap.containsKey(userId)){
            webSocketMap.remove(userId);
            subOnlineCount();
        }
    }

    //收到客户端发送的消息时的操作
    @OnMessage
    public void onMessage(String message, Session session) {

        //根据message情况判断如何进行推送
        if(StringUtils.isNotBlank(message)){
            try {
                //解析发送的报文
                JSONObject jsonObject = JSON.parseObject(message);
                Long toUserId=jsonObject.getLong("toUserId");
                String content = jsonObject.getString("content");
                Long createDate = jsonObject.getLong("createDate");

                Message messageRecord = chatService.getEmptyMessage();
                messageRecord.setFromUserId(userId);
                messageRecord.setToUserId(toUserId);
                messageRecord.setContent(content);
                messageRecord.setCreateDate(createDate);

                chatService.savaMessage(messageRecord);
                //传送给对应toUserId用户的websocket
                if(webSocketMap.containsKey(toUserId)){
                    webSocketMap.get(toUserId).sendMessage(messageRecord);
                }else{

                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    //连接发生错误时调用
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    //实现由服务器向客户端推送信息
    public void sendMessage(Message message) throws IOException {
        this.session.getBasicRemote().sendText(JSON.toJSONString(message));
    }
    //实现由服务器向客户端推送信息
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
}
