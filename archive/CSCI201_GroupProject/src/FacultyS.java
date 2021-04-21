import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;

import javax.print.attribute.standard.Severity;

public class FacultyS extends Thread{
	public HashSet<FacultyS> faculty;
	public ArrayList<StudentS> mainQueue;
	private String username;
	private String passWord;
	private Boolean isStart;
	//server - client info
	private PrintWriter pw;
	private BufferedReader br;
	private Socket s;
	private Server server;
	public ExecutorService executors;
	//database connection
	Connection conn = null;
	Statement st = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	public FacultyS(String username,String passWord,Server server,Socket s) {
		this.username = username;
		this.passWord = passWord;
		this.faculty = server.faculty;
		this.s = s;
		this.server = server;
		this.mainQueue = server.mainQueue;
		this.executors = server.executors;
		this.isStart = true;
		try {
			pw = new PrintWriter(s.getOutputStream());
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		confirmConnection();
	}
	
	public void confirmConnection() {
		try {
			//Data base fetch name
//			conn = DriverManager.getConnection("jdbc:mysql://localhost/lab9?user=root&password=QAZwsxedc123");
//			st = conn.createStatement();
//			rs = st.executeQuery("SELECT idUser, COUNT(ID) as 'Number of Students'\n"
//					+ "FROM Lab9.Grades\n"
//					+ "Group BY ClassName\n"
//					+ "Order BY COUNT(ID);");
			String facultyName = passWord;
			if(facultyName == null){//not find student in database
				pw.println("Invalid username and password");
				pw.flush();
			}else {
				pw.println("Success,F,"+facultyName);
				pw.flush();
				faculty.add(this);
				executors.execute(this);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void run() {
		while (isStart) {
			String line = null;
			try {
				while ((line = br.readLine()) != null) {
					if(line.equals("Check")) {
						popStudent();
					}else if(line.equals("Close")) {
						closeOH();
					}else {
						boardcastMessage(line);
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	//popStudent
	public void popStudent() {
		if(!mainQueue.isEmpty()) {
			mainQueue.get(0).getChecked();
			mainQueue.remove(0);
			server.boardcast();
		}
	}
	//faculty boardcast
	public void boardcastMessage(String message) {
		server.boardcastMessage(message);
	}
	//close OH
	public void closeOH() {
		server.boardcastClose();
		executors.shutdown();
		while(!executors.isTerminated()) {
			Thread.yield();
		}
	}
	
	//send Message
	public void sendMessage(int totalStudent) {
		pw.println(-1 + "," + totalStudent);
		pw.flush();
	}
	public void sendMessageFaculty(String message) {
		pw.println(message);
		pw.flush();
	}
	public void sendMessageClose() {
		pw.println("Office Hour is closed");
		pw.flush();
	}
}
