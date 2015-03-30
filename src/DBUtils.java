import java.sql.*;

/************************************************************************
Class: DBHyperlink
Type: public abstract
Relations: DBHyperlink, DBComment and DBMetaTag extend this class
Description: General utility functions for database integration
Remarks: None.
Attributes: None.
Methods:
		# public static Connection connect ()
		# public static void closeConnection(Connection connection)
		# public static Timestamp getCurrentTimeStamp()

*************************************************************************/

public abstract class DBUtils {
	
	/**********************************************
	Function name: connect
	Description: connect to the database
	Input: None.
	Output: a Connection object
	Remarks: None.
	**********************************************/
	public static Connection connect () {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/mysql";
			String user = "root";
			String password = "root";
			return DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**********************************************
	Function name: closeConnection
	Description: disconnect to the database
	Input: 
		# Connection connection - the connection to be closed
	Output: None.
	Remarks: None.
	**********************************************/
	public static void closeConnection(Connection connection) {
		if (connection != null)
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	/**********************************************
	Function name: getCurrentTimeStamp
	Description: return the current time and date
	Input: None.
	Output: Timestamp - the current time and date
	Remarks: None.
	**********************************************/	
	public static Timestamp getCurrentTimeStamp() {
		java.util.Date today = new java.util.Date();
		return new Timestamp(today.getTime());
	}
	
}
