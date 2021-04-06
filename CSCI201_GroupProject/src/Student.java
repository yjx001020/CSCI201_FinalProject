
public class Student extends Person{
	public Student(String n,String i,Client c) {
		super(n, i,c);
		inQueue=false;
	}
	public void JoinQ() {
		//can only join if not in queue
		if(!inQueue) {
			sendM("Join");
			inQueue=true;
		}
	}
	public void LeaveQ() {
		if(inQueue) {
			sendM("Leave");
			inQueue=false;
		}
	}
	public void check() {
		System.out.println("Sorry, permission denied");
	}
	public void announce(String m) {
		System.out.println("Sorry, permission denied");
	}
	public void close() {
		System.out.println("Sorry, permission denied");
	}
}
