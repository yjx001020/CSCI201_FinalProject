import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class Client {
	public BufferedReader br;
	public PrintWriter pw;
	public Socket s;
	public boolean running;
	public boolean login;
	public int position;
	public int queue_size;
	public Person user;
	private String ans;
	private String myid;
	public Client(String hostname, int port) {
		//establishing connection
		try {
			s = new Socket(hostname, port);
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			pw = new PrintWriter(s.getOutputStream());
			running=true;
			login=false;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		//logging in
		Scanner in=new Scanner(System.in);
		System.out.println("Are you a student or a faculty?S/F");
		ans= in.nextLine();
		System.out.println("Please enter your id");
		myid=in.nextLine();
		System.out.println("Please enter your password");
		String pasw=in.nextLine();
		pw.println(ans+","+myid+","+pasw);
		pw.flush();
		//start listening
		while(!login) {
			try {
				String order = br.readLine();
				if(order == null) {
					//do nothing
					System.out.println("NULLLLL");
				}
				else if(order.substring(0,7).compareTo("Success")==0) {
					System.out.println("here11");
					String[] info_list=order.split(",");
					if(ans.compareTo("S")==0) {
						System.out.println("is Student" + info_list[1]);
						user=new Student(info_list[1], myid, this);
						user.JoinQ();
					}
					else {
						user=new Faculty(info_list[1], myid, this);
						user.announce("Class Starts!!!!!!!!!!!!!");
						user.check();
						user.check();
						user.check();
					}
					login=true;
				}else {
					System.out.println("weird");
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		while(running) {
			try {
				String order=br.readLine();
				System.out.println(order);
				if(order==null) {
					System.out.println("NULL");
					//do nothing
				}else if(order.compareTo("Office Hour is closed")==0) {
					running=false;
				}else if(order.length() >= 15 && order.substring(0, 15).compareTo("upDateMessage: ")==0) {
					String[] info_list=order.split(",");
					position=Integer.parseInt(info_list[1]);
					if(position==-1) {
						user.inQueue=false;
						System.out.println("Checked off!");
					}
					queue_size=Integer.parseInt(info_list[2]);
					System.out.println("position="+position);
					System.out.println("queue size="+queue_size);
				}else {
					System.out.println(order);
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		in.close();
		try {
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Client currClient=new Client("localhost", 3456);
	}

}
