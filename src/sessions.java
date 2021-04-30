import java.io.IOException;
import java.util.Vector;

import javax.websocket.Session;

public class sessions {
	public static Vector<Session> sessionVector=new Vector<Session>();
	public static void boardcast(String message) {
		for(Session s : sessionVector) {
			try {
				s.getBasicRemote().sendText(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
