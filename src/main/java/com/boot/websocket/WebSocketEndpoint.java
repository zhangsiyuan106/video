package com.boot.websocket;

import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/websocket/{userId}",encoders = ServerEncoder.class)
@Component
public class WebSocketEndpoint {

	// 与某个客户端的连接会话，需要通过它来给客户端发送数据
	private Session session;

	/**
	 * 连接建立成功调用的方法
	 */
	@OnOpen
	public void onOpen(Session session, @PathParam("userId") String userId) {
		// 分解获取的参数,把参数信息，放入到session key中, 以方便后续使用
		// 把会话存入到连接池中
		this.session = session;
		session.setMaxIdleTimeout(3600000);
		SessionPool.sessions.put(userId, session);
	}

	/**
	 * 关闭连接
	 */
	@OnClose
	public void onClose(Session session) {
		SessionPool.sessions.remove(session.getId());
	}

	/**
	 * 收到客户端消息后调用的方法
	 *
	 * @param message
	 *            客户端发送过来的消息
	 */
	@OnMessage
	public void onMessage(String message, Session session) {

	}
}
