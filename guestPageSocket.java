import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.el.parser.AstConcatenation;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@WebServlet("/StudentCount")

public class guestPageSocket extends HttpServlet {
	// connection to database
		private static Connection conn = null;
		private static Statement st = null;
		private static ResultSet rs = null;
		
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			System.out.println("Connected");
			int studentCount = -1;
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/FinalProject?user=root&password=QAZwsxedc123");		
				st = conn.createStatement();
				//get Student Info
				String getNumString = "SELECT COUNT(*) FROM FinalProject.StudentInQueue;";
				rs = st.executeQuery(getNumString);
				while (rs.next()) {
					studentCount = rs.getInt("COUNT(*)");
				}
				response.setContentType("text/html;charset=UTF-8");
				System.out.println(studentCount);
				PrintWriter out = response.getWriter();
				out.print(studentCount);
				out.flush();
			}catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

}
