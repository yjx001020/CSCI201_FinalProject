import java.sql.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;


@WebServlet("/LoginServlet")
public class loginservlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    public loginservlet() {
    	super();
    }		
	protected void service(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();		
			String username= request.getParameter("loginemail");
			String password= request.getParameter("loginpassword");
			Connection conn = null;
			
			Statement st = null;
			ResultSet srs = null;
			ResultSet frs = null;
			System.out.println(username);
			System.out.println(password);
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://localhost/FinalProject?user=root&password=vincent0124!"); 
				st = conn.createStatement();
				srs = st.executeQuery("SELECT * FROM Users WHERE email = '" + username + "' and UserPassword = '" + password + "';");
				
				if(srs.next()) {
					String user = srs.getString("UserName");
					System.out.println(srs.getByte("isFaculty"));
					if(srs.getByte("isFaculty") == 0){
						String jsonString = "{\"username\":\""+user+"\",\"page\":\"studentPage.html\"}";
						System.out.println(jsonString);
						out.print(jsonString);
					}else{
						String jsonString = "{\"username\":\""+user+"\",\"page\":\"facultyPage.html\"}";
						System.out.println(jsonString);
						out.println(jsonString);
					}
				}else {
					String jsonString = "{\"username\":\""+null+"\",\"page\":null}";
					System.out.println(jsonString);
					out.println(jsonString);
				}
			} catch (SQLException sqle) {
				System.out.println (sqle.getMessage()); 
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				try {
					if (srs != null) {
						srs.close(); 
					}
					if (frs != null) {
						frs.close(); 
					}
					if (st != null) { 
						st.close();
					}
					if (conn != null) {
						conn.close(); 
					}
				} catch (SQLException sqle) {
					System.out.println(sqle.getMessage()); 
				}
			}
			
		}

	
}