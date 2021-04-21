
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
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			
			String username= request.getParameter("username");
			String password= request.getParameter("password");
		
			Connection conn = null;
			Statement st = null;
			ResultSet rs = null;
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				// 需要填一下
				conn = DriverManager.getConnection("jdbc:mysql://localhost/         "); 
				st = conn.createStatement();
				
				// check if the username exist
				rs = st.executeQuery("SELECT * FROM Users WHERE UserName = '" + username + "' and UserPassword = '" + password + "';");
				out.println("<html>");
				out.println("<head><title>Form Submission</title></head>");
				out.println("<body>");
				if(rs.next()) {
					// 我感觉直接跳转到home page会更好，你们到时候改一下
					out.println("<h1>Login Success</h1>");
					// （action 对应的page可能要做出相对应的更改）
					out.println("<form action=\"home.html\" method=\"POST\">");
					out.println("<input type=\"submit\" value=\"Go to Home Page\" />");
					out.println("</form>");
					out.println("<script>");
					// 这样系统就能keep track of login status
					// 在其他页面也会需要这个document.cookie
					out.println("document.cookie = \"username=" + username + "\";");
					out.println("</script>");
					out.println("</body>");
					out.println("</html>");
					
				} else {
					out.println("<h1>Credential Invalid</h1>");
				}		
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

