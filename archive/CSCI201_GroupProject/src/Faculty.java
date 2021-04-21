
public class Faculty extends Person{
	public Faculty(String n,String i,Client c) {
		super(n,i,c);
	}
	public void check() {
		sendM("Check");
	}
	public void announce(String m) {
		sendM(m);
	}
	public void close() {
		sendM("Close");
	}
	public void JoinQ() {
		System.out.println("Invalid operation");
	}
	public void LeaveQ() {
		System.out.println("Invalid operation");
	}
}
