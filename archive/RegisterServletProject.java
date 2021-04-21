
import java.sql.*;
import java.io.IOException;
import java.io.PrintWriter;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServletProject extends HttpServlet {
	
	
	private static final long serialVersionUID = 1L;
    public RegisterServletProject() {
    	super();
    	System.out.println("register servlet connection!");
    }
	
	
	protected void service(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			
			String username= request.getParameter("signup-username");
			String email= request.getParameter("signup-email");
			String password= request.getParameter("signup-password");
		
			Connection conn = null;
			Statement st = null;
			ResultSet rs = null;
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://localhost/SalStocks?user=root&password=ViolaXia1998!"); 
				st = conn.createStatement();
				
				// insert new user into database using prepared statement
				String SQL = "INSERT INTO UserInfo (Username, Passwords, Email, CashBalance, AccountValue) VALUES (?, ?, ?, 50000, 50000);";
				PreparedStatement ps = conn.prepareStatement(SQL);
				ps.setString(1, username);
				ps.setString(2, password);
				ps.setString(3, email);
				ps.executeUpdate();
				
	
				// registration verification
				response.setContentType("text/html");
				out.println("<html>");
				out.println("<head><title>Form Submission</title></head>");
				out.println("<body>");
				out.println("<h1>Registration Success</h1>");
				out.println("<form action=\"login.html\" method=\"POST\">");
				out.println("<input type=\"submit\" value=\"Go to Login Page\" />");
				out.println("</form>");
				out.println("</body>");
				out.println("</html>");
			} catch (SQLException sqle) {
				System.out.println (sqle.getMessage()); 
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				try {
					if (rs != null) {
						rs.close(); 
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
