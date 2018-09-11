package webSocket;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/message")
public class messageSocket {
	// 记录所有的连接对象
	private static Set<messageSocket> set = new HashSet<>();

	// 记录自己,这里的session为一个socket连接，当第一次连接时生成一个session，连接关闭后session销毁
	private Session session;

	@OnOpen
	public void OnOpen(Session session) {
		this.session = session;
		set.add(this);
	}

	@OnClose
	public void OnClose() {
		set.remove(this);
	}

	@OnMessage
	public void OnMessage(String message, Session session) {
		try {
			for (messageSocket item : set) {
				item.session.getBasicRemote().sendText(message);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@OnError
	public void OnError(Session session,Throwable error) {
		error.printStackTrace();
	}
}
