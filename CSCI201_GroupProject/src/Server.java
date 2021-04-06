import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
	public ArrayList<StudentS> mainQueue;
	public ArrayList<StudentS> studentMem;
	public HashSet<FacultyS> faculty;
	public ExecutorService executors = Executors.newCachedThreadPool();
	private PrintWriter pw;
	private BufferedReader br;
	
	public static void main(String[] args) {
		Server server = new Server(3456);
	}
	
	public Server(int port) {
		try {
			System.out.println("Binding to port " + port);
			ServerSocket ss = new ServerSocket(port);
			mainQueue = new ArrayList<StudentS>();
			studentMem = new ArrayList<StudentS>();
			faculty = new HashSet<FacultyS>();
			//connection
			while(true) {
				Socket s = ss.accept(); // blocking
				System.out.println("Connection from: " + s.getInetAddress());
				//read message
				br = new BufferedReader(new InputStreamReader(s.getInputStream()));
				String line;
				String type = null;
				String passWard = null;
				String username = "";
				if ((line = br.readLine()) != null) {
			        String[] values = line.split(",");
			        username = values[0];
			        passWard = values[1];
			        System.out.println("type is " + type);
				}
				//find type
				
				if(type.equals("S")){//student connect
					StudentS studentS = new StudentS(username,passWard,this,s);
				}else if(type.equals("F")){
					FacultyS facultyF = new FacultyS(username,passWard,this,s);
				}else {
					pw.println("Invalid username and password");
					pw.flush();
				}
			}
		} catch (IOException ioe) {
			System.out.println("ioe in ChatRoom constructor: " + ioe.getMessage());
		}
	}
	
	public void boardcastClose() {
		for(int i = 0; i < mainQueue.size(); i++) {
			mainQueue.get(i).sendMessageClose();
		}
		if(faculty.size() != 0) {
			for(FacultyS fThread : faculty) {
				fThread.sendMessageClose();
			}
		}
	}
	public void boardcastMessage(String message) {
		for(int i = 0; i < mainQueue.size(); i++) {
			mainQueue.get(i).sendMessageFaculty(message);
		}
		if(faculty.size() != 0) {
			for(FacultyS fThread : faculty) {
				fThread.sendMessageFaculty(message);
			}
		}
	}
	public void boardcast() {
		int totalStudent = mainQueue.size();
		for(int i = 0; i < mainQueue.size(); i++) {
			mainQueue.get(i).sendMessage(i,totalStudent);
		}
		if(faculty.size() != 0) {
			for(FacultyS fThread : faculty) {
				fThread.sendMessage(totalStudent);
			}
		}
	}

}
