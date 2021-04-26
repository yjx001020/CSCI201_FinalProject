import java.sql.*;
import java.io.IOException;
import java.io.PrintWriter;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;


@WebServlet("/LoginServlet")
public class LoginServletProject extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    public LoginServletProject() {
    	super();
    }		
	protected void service(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
			response.setContentType("text/plain");
			PrintWriter out = response.getWriter();		
			String username= request.getParameter("email");
			String password= request.getParameter("password");
			Connection conn = null;
			Statement st = null;
			ResultSet srs = null;
			ResultSet frs = null;
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://localhost/"); 
				st = conn.createStatement();
				srs = st.executeQuery("SELECT * FROM Users WHERE UserName = '" + username + "' and UserPassword = '" + password + "';");
				frs = st.executeQuery("SELECT * FROM Faculty WHERE UserName = '" + username + "' and UserPassword = '" + password + "';");
				if(srs.next()) {
					out.println("1");	
				} else if(frs.next()) {
					out.println("2");
				}else{
					out.println("3");
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