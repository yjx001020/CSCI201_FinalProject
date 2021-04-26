import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@ServerEndpoint(value = "/ws")
public class ServerSocketFaculty {
	private static Vector<Session> sessionVector=new Vector<Session>();
	@OnOpen
	public void open(Session session) {
		System.out.println("Connected!");
		//do nothing
	}
	@OnMessage
	public void onMessage(String message, Session session){
		//open mysql
		System.out.println(message);
		Connection conn;
		Statement st = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/FinalProject?user=root&password=QAZwsxedc123");
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
								+ "(Select email From Users Where username=\'"+username+"\'));");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else {
					try {
						st.executeUpdate("Delete from Staff Where username=\'"+username+"\';");
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
				String SID=facultyJO.get("id").getAsString();
				SID=SID.substring(12);
				try {
					System.out.println("Delete from StudentInQueue Where idStudent=\'"+SID+"\';");
					st.executeUpdate("Delete from StudentInQueue Where idStudent=\'"+SID+"\';");
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			try {
				Map<String, String> res = new HashMap<String, String>();
				String reString ="";
				String queueStatus = "";
				int studentCount = -1;
				String zoomLink = "";
				String staffs = "[";
				String announcement = "";
				String students = "[";
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					conn = DriverManager.getConnection("jdbc:mysql://localhost/FinalProject?user=root&password=QAZwsxedc123");		
					st = conn.createStatement();
					//get Student Info
					String getNumString = "SELECT COUNT(*) FROM FinalProject.StudentInQueue;";
					ResultSet rs = st.executeQuery(getNumString);
					while (rs.next()) {
						studentCount = rs.getInt("COUNT(*)");
					}
					String getStuInfoString = "SELECT studentName,idStudent,topic,description \n"
							+ "FROM FinalProject.StudentInQueue;";
					rs = st.executeQuery(getStuInfoString);
					while (rs.next()) {
						String studentName = rs.getString("studentName");
						String idStudent = rs.getString("idStudent");
						String topic = rs.getString("topic");
						String description = rs.getString("description");
						Gson gsonObj = new Gson();
				        Map<String, String> inputMap = new HashMap<String, String>();
				        inputMap.put("studentName", studentName);
				        inputMap.put("studentID", idStudent);
				        inputMap.put("topic", topic);
				        inputMap.put("description", description);
				        String jsonStr = gsonObj.toJson(inputMap);
				        students += jsonStr+",";
					}
					students = students.substring(0,students.length()-1)+"]";
					//getInfo
					st = conn.createStatement();
					String getQueueInfoString = "SELECT queueStatus,zoomLink,announcement\n"
							+ "FROM FinalProject.Queue;";
					rs = st.executeQuery(getQueueInfoString);
					while (rs.next()) {
						queueStatus = rs.getString("queueStatus");
						zoomLink = rs.getString("zoomLink");
						announcement = rs.getString("announcement");
					}
					//getStaffInfo
					String getStaffInfoString = "SELECT username,email\n"
							+ "FROM FinalProject.Staff;";
					rs = st.executeQuery(getStaffInfoString);
					while (rs.next()) {
						String username = rs.getString("username");
						String email = rs.getString("email");
						Gson gsonObj = new Gson();
				        Map<String, String> inputMap = new HashMap<String, String>();
				        inputMap.put("staffName", username);
				        inputMap.put("email", email);
				        String jsonStr = gsonObj.toJson(inputMap);
				        staffs += jsonStr+",";
					}
					staffs = staffs.substring(0,staffs.length()-1)+"]";
					
					//put in result
					res.put("queueStatus", queueStatus);
					res.put("studentCount", String.valueOf(studentCount));
					res.put("zoomLink", zoomLink);
					res.put("announcement", announcement);
					Gson gson = new Gson();
					reString = gson.toJson(res);
					
					reString = reString.substring(0,reString.length()-1)+",";
					reString += "\"staffs\": "+staffs+",";
					reString += "\"students\": "+students+"}";
					System.out.print(reString);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				session.getBasicRemote().sendText(reString);
			}
			catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	@OnClose
	public void close(Session session) {
		System.out.println("Disconnecting!");
		sessionVector.remove(session);
	}
}

