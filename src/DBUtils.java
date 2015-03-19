import java.sql.*;

public class DBUtils {
	
	public static Connection connect () {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:mysql://localhost:3306/mysql";
			String user = "root";
			String password = "root";
			return DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void closeConnection(Connection connection) {
		if (connection != null)
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	public static Timestamp getCurrentTimeStamp() {
		java.util.Date today = new java.util.Date();
		return new Timestamp(today.getTime());
	}
	
}
