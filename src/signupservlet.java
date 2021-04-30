import java.sql.*;
import java.io.IOException;
import java.io.PrintWriter;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/RegisterServlet")
public class signupservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public signupservlet() {
		super();
		System.out.println("register servlet connection!");
	}

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		String username = request.getParameter("username");
		String email = request.getParameter("emailaddress");
		String type = request.getParameter("type");
		System.out.println(email);
		System.out.println(type);
		String password = request.getParameter("password");
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/FinalProject?user=root&password=vincent0124!");
			st = conn.createStatement();
			Connection conn2 = DriverManager.getConnection("jdbc:mysql://localhost/FinalProject?user=root&password=vincent0124!");
			Statement st2=conn2.createStatement();
			ResultSet rs2=st2.executeQuery("SELECT * from Users Where username=\'"+username+"\' OR email=\'"+email+"\';");
			if(rs2.next()) {
				out.print("Exists");
				return;
			}
			if (type.equals("Yes")) {
				String SQL = "INSERT INTO Users (UserName, email, UserPassword, Fname, Lname, IsFaculty) VALUES (?, ?, ?, ?, ?, ?);";
				PreparedStatement ps = conn.prepareStatement(SQL);
				ps.setString(1, username);
				ps.setString(2, email);
				ps.setString(3, password);
				ps.setString(4, username);
				ps.setString(5, username);
				ps.setInt(6, 1);
				ps.executeUpdate();
				out.println("facultyPage.html");
			} else {
				String SQL = "INSERT INTO Users (UserName, email, UserPassword, Fname, Lname, IsFaculty) VALUES (?, ?, ?, ?, ?, ?);";
				PreparedStatement ps = conn.prepareStatement(SQL);
				ps.setString(1, username);
				ps.setString(2, email);
				ps.setString(3, password);
				ps.setString(4, username);
				ps.setString(5, username);
				ps.setInt(6, 0);
				ps.executeUpdate();
				out.println("studentPage.html");
			}
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
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