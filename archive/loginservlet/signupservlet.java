import java.sql.*;
import java.io.IOException;
import java.io.PrintWriter;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

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
			String username= request.getParameter("namel");
			String email= request.getParameter("email");
			String type= request.getParameter("type");
			String password= request.getParameter("password");
			Connection conn = null;
			Statement st = null;
			ResultSet rs = null;
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://localhost/SalStocks?user=root&password=ViolaXia1998!"); 
				st = conn.createStatement();
				
				if(type.equals("Yes")){
					String SQL = "INSERT INTO UserInfo (Username, Email, Password, IsFaculty) VALUES (?, ?, ?, ?);";
					PreparedStatement ps = conn.prepareStatement(SQL);
					ps.setString(1, username);
					ps.setString(2, email);
					ps.setString(3, password);
					ps.setString(4, type);
					ps.executeUpdate();
					out.println("1");
				}else{
					String SQL = "INSERT INTO UserInfo (Username, Email, Password, IsFaculty) VALUES (?, ?, ?, ?);";
					PreparedStatement ps = conn.prepareStatement(SQL);
					ps.setString(1, username);
					ps.setString(2, email);
					ps.setString(3, password);
					ps.setString(4, type);
					ps.executeUpdate();
					out.println("2");
				}
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