import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@ServerEndpoint(value = "/ws")
public class ServerSocketFaculty {
	private static Vector<Session> sessionVector=new Vector<Session>();
	@OnOpen
	public void open(Session session) {
		//do nothing
	}
	@OnMessage
	public void onMessage(String message, Session session){
		//open mysql
		Connection conn;
		Statement st = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn=DriverManager.getConnection("jdbc:mysql://localhost/SalStocks?user=root&password=M12ike28");
			st = conn.createStatement();
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
		if(message==null) {
			//do nothing
		}
		else{
			JsonObject facultyJO=new JsonParser().parse(message).getAsJsonObject();
			if(facultyJO.has("username")) {
				String username=facultyJO.get("username").getAsString();
				String action=facultyJO.get("action").getAsString();
				if(action.compareTo("1")==0) {
					try {
						//Please change name according to database
						st.executeUpdate("Insert into Staff (username,email) values (\'"+username+"\',"
								+ "(Select email From Users Where username=\'"+username+"));");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else {
					try {
						st.executeUpdate("Delete from Faculty Where username=\'"+username+"\';");
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
			}
			else if(facultyJO.has("message")){
				String mesg=facultyJO.get("message").getAsString();
				try {
					st.executeUpdate("Update Queue Set announcement=\'"+mesg+"\' Where idQueue=1;");
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			else {
				String Sname=facultyJO.get("name").getAsString();
				try {
					st.executeUpdate("Delete from StudentInQueue Where studentName=\'"+Sname+"\';");
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}
	}
}
