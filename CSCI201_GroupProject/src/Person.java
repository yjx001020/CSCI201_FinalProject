

public abstract class Person {
	public boolean inQueue;
	public String name;
	public String id;
	public Client client;
	public Person(String n,String i, Client c) {
		name=n;
		id=i;
		client=c;
	}
	public void sendM(String message) {
		client.pw.println(message);
		client.pw.flush();
	}
	public void changepw(String newpw) {
		sendM("Changepassword,"+newpw);
	}
	abstract void JoinQ();
	abstract void LeaveQ();
	abstract void check();
	abstract void announce(String m);
	abstract void close();
}
