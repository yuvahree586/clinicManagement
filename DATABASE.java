package clinicalsys;

	import java.sql.*;

		public class DATABASE {

	    public static Connection getConnection() throws Exception {
	        String url = "jdbc:mysql://localhost:3306/clinic";
	        String user = "root"; 
	        String password = "root"; 

	        Class.forName("com.mysql.cj.jdbc.Driver");
	        return DriverManager.getConnection(url, user, password);
	    }
	}


