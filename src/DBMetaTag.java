import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/************************************************************************
Class: DBMetaTag
Type: public abstract
Relations: extends DBUtils
Description: Manages the integration of the application with the 
MetaTag table in the database.
Remarks: None.
Attributes: 
		# private static Connection connection
		# private static PreparedStatement preparedStatement
Methods:
		# public static boolean add (int hyperlink_id, String value)
		# public static boolean update (int id, int hyperlink_id, String value)
		# public static boolean delete(int id)
		# public static Map<Integer, MetaTag> select ()
		# public static MetaTag select (int id)
		
*************************************************************************/
public abstract class DBMetaTag extends DBUtils {
	
	// Attributes:
	private static Connection connection; // connection with the database
	private static PreparedStatement preparedStatement; // prepared statement for escaped sql queries
	
	/**********************************************
	Function name: add
	Description: add a metatag to database
	Input: 
		# int _hyperlink_id - id of the hyperlink it is associated to
		# String value - value of the metatag
	Output: boolean - whether or not operation 
	was succesfull
	Remarks: None.
	**********************************************/
	public static boolean add (int hyperlink_id, String value) {
		try {
			// begin connection with the database
			connection = connect();
			
			// sql insert query
			String query = "INSERT INTO MetaTag VALUES (?,?,?)";
			
			// prepare statement
			preparedStatement = connection.prepareStatement(query);
			
			// insert the data we want in the statement
			preparedStatement.setInt(1,0);
			preparedStatement.setInt(2, hyperlink_id);
			preparedStatement.setString(3,value);
			
			// execute query
			preparedStatement.executeUpdate();
			
			// close the connection with the database
			closeConnection(connection);
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**********************************************
	Function name: update
	Description: update a metaTag in database
	Input: 
		# int id - id of the metaTag to be updated
		# int _hyperlink_id - id of the hyperlink it is associated to
		# String value - value of the metaTag
	Output: boolean - whether or not operation 
	was succesfull
	Remarks: None.
	**********************************************/
	public static boolean update (int id, int hyperlink_id, String value) {
		try{
			// begin connection with the database
			connection = connect();
			
			// sql update query
			String updateQuery = "UPDATE MetaTag"
					+ " SET value = ?" 
					+ ", hyperlink_id = ?"
					+ " WHERE id = ?";
			
			// prepare statement
			preparedStatement = connection.prepareStatement(updateQuery);
			
			// insert data we want in the statement
			preparedStatement.setString(1,value);
			preparedStatement.setInt(2, hyperlink_id);
			preparedStatement.setInt(3,id);
			
			// execute query
			preparedStatement.executeUpdate();
			
			// end connection with the database
			closeConnection(connection);
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**********************************************
	Function name: delete
	Description: delete a metaTag in database
	Input: 
		# int id - id of the metaTag to be deleted
	Output: boolean - whether or not operation 
	was succesfull
	Remarks: None.
	**********************************************/
	public static boolean delete(int id) {
		try{
			// begin connection with the database
			connection = connect();
			
			// sql delete query
			String deleteQuery = "DELETE FROM MetaTag WHERE id = ?";
			
			// prepare statement
			preparedStatement = connection.prepareStatement(deleteQuery);
			
			// insert data we want in the statement
			preparedStatement.setInt(1,id);
			
			// execute query
			preparedStatement.executeUpdate();
			
			// end connection with the database
			closeConnection(connection);
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**********************************************
	Function name: select
	Description: select all metaTags in database
	Input: None
	Output: Map<Integer, MetaTag>  - a map containing
	all metaTags retrieved from the database. 
	Remarks: None.
	**********************************************/
	public static Map<Integer, MetaTag> select () {
		try{
			// begin connection with database
			connection = connect();
			
			// create a statement with the given connection
			Statement statement = connection.createStatement();
			
			// sql select query
			String selectQuery = "SELECT * FROM MetaTag";
			
			// execute query
			ResultSet rs = statement.executeQuery(selectQuery);
			
			// iterate trough the resultSet and put the results in a map
			Map<Integer, MetaTag> map = new HashMap<Integer, MetaTag>();
			while (rs.next()){
				MetaTag metaTag = new MetaTag(rs.getInt(1), rs.getInt(2), rs.getString("value"));
				map.put(metaTag.id, metaTag);
			}
			
			// end connection with the database
			closeConnection(connection);
			
			// return data
			return map;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**********************************************
	Function name: select
	Description: select all metaTags in database
	Input: 
		# int id - id of the metaTag to be selected
	Output: MetaTag - the metaTag retrieved 
	Remarks: None.
	**********************************************/
	public static MetaTag select (int id) {
		try{
			// begin connection with the database
			connection = connect();
			
			// sql select query
			String selectQuery = "SELECT * FROM MetaTag WHERE id = ?";
			
			// prepare statement
			preparedStatement = connection.prepareStatement(selectQuery);
			
			// insert data we want in the statement
			preparedStatement.setInt(1,id);
			
			// exeute query
			ResultSet rs = preparedStatement.executeQuery();
			
			// end connection with the database
			closeConnection(connection);
			
			// necessary to point rs to the first result
			if (rs.next()) // return the object created with the first result of the resultSet
				return new MetaTag(rs.getInt(1), rs.getInt(2), rs.getString("value"));
			
			// if we did not get anything, return null
			return null;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
