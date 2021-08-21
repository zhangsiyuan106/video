package com.boot.websocket;

import com.boot.constant.LexJSONResult;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionPool {

	public static Map<String, Session> sessions = new ConcurrentHashMap<String, Session>();

   	public static void close(String sessionId) throws IOException {
   		Session session = sessions.get(sessionId);
   		if(session != null) {
   			sessions.get(sessionId).close();
   		}
   	}

	//消息
    public static void sendMessage(String sessionId , String message,Object o) {
		Session session = SessionPool.sessions.get(sessionId);
		if(session != null && session.isOpen())
		{
			session.getAsyncRemote().sendObject(LexJSONResult.msg(message,o));
		}
    }

    //群发消息
    public static void sendMessage(String message) {
    	for(String sessionId : SessionPool.sessions.keySet())
    	{
    		SessionPool.sessions.get(sessionId).getAsyncRemote().sendText(message);
    	}
    }
	//指定userid
	public static void sendMessageByUserId(String userId, String message) {
		Session session = SessionPool.sessions.get(userId);
		if(session != null && session.isOpen())
		{
			session.getAsyncRemote().sendText(message);
		}
	}

	public static String sendMessage(Map<String, Object> params)
	{
		String msg = params.get("msg").toString();
		String fromUserId = params.get("fromUserId").toString();

		if(params.get("toUserId") != null && !params.get("toUserId").equals("")) {

			String toUserId = params.get("toUserId").toString();
			msg = String.format("来自用户%s发送给用户%s,消息：%s",  fromUserId, toUserId, msg);
			sendMessageByUserId(toUserId, msg);
		}
		else
		{
			msg = String.format("来自用户%s发送给所有用户,消息：%s",  fromUserId, msg);
			sendMessage(msg);
		}

		return "发送成功";
	}
}
