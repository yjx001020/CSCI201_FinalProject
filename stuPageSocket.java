import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;

@ServerEndpoint(value = "/studentPage")
public class stuPageSocket {

	private static Vector<Session> sessionVector = new Vector<Session>();
	// connection to database
	private static Connection conn = null;
	private static Statement st = null;
	private static ResultSet rs = null;
	
	@OnOpen
	public void open(Session session) {
		System.out.println("Connection made!");
		try {
			session.getBasicRemote().sendText(getReturnRes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sessionVector.add(session);
	}
	
	@OnMessage
	public void onMessage(String message, Session session) {
		System.out.println(message);
		Gson g = new Gson();  
		studentClass student = g.fromJson(message, studentClass.class); 
		String studentName = student.getStudentName();
		String studentId = student.getStudentID();
		String topic = student.getTopic();
		String description = student.getDescription();
		if(student.getLeave().equals("false")) {//add to queue
			addToQueue(studentName,studentId,topic,description);
		}else {//remove from queue
			delFromQueue(studentName);
		}
		try {
			for(Session s : sessionVector) {
				// getBasicRemote() is for synchronous communication
				// getAsyncRemote() is for asynchronous communication
				s.getBasicRemote().sendText(getReturnRes());
			}
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
			close(session);
		}
	}
	
	@OnClose
	public void close(Session session) {
		System.out.println("Disconnecting!");
		sessionVector.remove(session);
	}
	
	@OnError
	public void error(Throwable error) {
		System.out.println("Error!");
	}
	public void addToQueue(String studentName,String studentId,String topic,String description) {
		//add to SQL
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/FinalProject?user=root&password=QAZwsxedc123");		
			st = conn.createStatement();
			String addQueryString = "INSERT INTO StudentInQueue (idStudent, studentName, topic,description)\n"
					+ "VALUES ('"+studentId+"',\""+studentName+"\",\""+topic+"\",\""+description+"\");";
			System.out.println(addQueryString);
			st. executeUpdate(addQueryString);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//	public static void main(String[] args) {
//		delFromQueue("Fiona");
//	}
	public static void delFromQueue(String studentName) {
		//add to SQL
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/FinalProject?user=root&password=QAZwsxedc123");		
			st = conn.createStatement();
			String delQueryString = "DELETE FROM StudentInQueue\n"
					+ "WHERE studentName = \""+studentName+"\";";
			System.out.println(delQueryString);
			st. executeUpdate(delQueryString);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static String getReturnRes() {
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
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/FinalProject?user=root&password=QAZwsxedc123");		
			st = conn.createStatement();
			//get Student Info
			String getNumString = "SELECT COUNT(*) FROM FinalProject.StudentInQueue;";
			rs = st.executeQuery(getNumString);
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
		        inputMap.put("idStudent", idStudent);
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
		        inputMap.put("username", username);
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
		return reString;
	}
}
