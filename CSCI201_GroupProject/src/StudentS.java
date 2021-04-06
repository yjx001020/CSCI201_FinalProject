import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

public class StudentS extends Thread{
	public ArrayList<StudentS> mainQueue;
	public ArrayList<StudentS> studentMem;
	private int id;
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
	
	public StudentS(int id,String passWord,Server server,Socket s) {
		this.id = id;
		this.passWord = passWord;
		this.mainQueue = server.mainQueue;
		this.server = server;
		this.s = s;
		this.executors = server.executors;
		this.isStart = false;
		this.studentMem = server.studentMem;
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
			String studentName = passWord;
			System.out.println("studentName is " + studentName);
			if(studentName == null){//not find student in database
				pw.println("Invalid username and password");
				pw.flush();
			}else {
				String tempString = "Success,"+studentName;
				System.out.println(tempString);
				pw.println(tempString);
				pw.flush();
				studentMem.add(this);
				isStart = true;
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
					if(line.equals("Join")) {
						goOH();
					}else if(line.equals("Leave")) {
						leaveQ();
					}else {
						
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void goOH() {
		mainQueue.add(this);
		server.boardcast();
	}
	public void getChecked() {
		pw.println("-1");
		pw.flush();
		isStart = false;
	}
	public void leaveQ() {
		//close the client
		mainQueue.remove(this);
		server.boardcast();
	}
	
	//send messages
	public void sendMessage(int pos,int total) {
		String tempString = "upDateMessage: ,"+pos + "," + total;
		pw.println(tempString);
		pw.flush();
	}
	public void sendMessageFaculty(String message) {
		pw.println(message);
		pw.flush();
	}
	public void sendMessageClose() {
		isStart = false;
		pw.println("Office Hour is closed");
		pw.flush();
	}
}
