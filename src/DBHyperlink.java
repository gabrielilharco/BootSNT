// importing necessary classes;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/************************************************************************
 Class: DBHyperlink
 Type: public abstract
 Relations: extends DBUtils
 Description: Manages the integration of the application with the 
 Hyperlink table in the database.
 Remarks: None.
 Attributes: 
 		# private static Connection connection
 		# private static PreparedStatement preparedStatement
 Methods:
 		# public static boolean add (String value)
 		# public static boolean update (int id, String value)
 		# public static boolean delete (int id)
 		# public static Map<Integer, Hyperlink> select ()
 		# public static ArrayList<Hyperlink> selectComplete ()
 		# public static Hyperlink select (int id)
 		# public static Hyperlink selectComplete (int id)
 		# public static ArrayList<Hyperlink> search (String question)
 		# private static ArrayList<Hyperlink> extractData(ResultSet rs)
 		
*************************************************************************/
public abstract class DBHyperlink extends DBUtils {
	
	// Attributes:
	private static Connection connection; // connection with the database
	private static PreparedStatement preparedStatement; // prepared statement for escaped sql queries
	
	/**********************************************
	Function name: add
	Description: add a hyperlink to database
	Input: 
		# String value - value of the hyperlink
	Output: boolean - whether or not operation 
	was succesfull
	Remarks: None.
	**********************************************/
	public static boolean add (String value) {
		try {
			// begin connection with db
			connection = connect(); 
			
			// sql insert statement
			String query = "INSERT INTO Hyperlink VALUES (?,?,?,?)"; 
			
			// prepare the statement
			preparedStatement = connection.prepareStatement(query); 
			
			// replacing the ? with the data we want
			preparedStatement.setInt(1,0);
			preparedStatement.setString(2,value);
			preparedStatement.setTimestamp(3, getCurrentTimeStamp());
			preparedStatement.setTimestamp(4, getCurrentTimeStamp());
			
			// execute query
			preparedStatement.executeUpdate();
			
			// end connection with db
			closeConnection(connection); 
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**********************************************
	Function name: update
	Description: update a hyperlink in database
	Input: 
		# int id - id of the hyperlink to be updated
		# String value - update value of the hyperlink
	Output: boolean - whether or not operation 
	was succesfull
	Remarks: None.
	**********************************************/
	public static boolean update (int id, String value) {
		try{
			// begin connection with db
			connection = connect();
			
			// sql update statement
			String updateQuery = "UPDATE Hyperlink"
					+ " SET value = ?" 
					+ ", lastEdited = ?"
					+ " WHERE id = ?";
			
			// prepare the statement
			preparedStatement = connection.prepareStatement(updateQuery);
			
			// replacing the ? with the data we want
			preparedStatement.setString(1,value);
			preparedStatement.setTimestamp(2, getCurrentTimeStamp());
			preparedStatement.setInt(3,id);
			
			
			// execute query
			preparedStatement.executeUpdate();
			
			// end connection with db
			closeConnection(connection); 
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**********************************************
	Function name: delete
	Description: delete a hyperlink in database
	Input: 
		# int id - id of the hyperlink to be deleted
	Output: boolean - whether or not operation 
	was succesfull
	Remarks: For there to be cascade delete in MetaTag
	and Comment tables, this functions expects that both
	of those tables were already created with the FK in
	ON DELETE CASCADE mode.
	**********************************************/
	public static boolean delete (int id) {
		try{
			// begin connection with db
			connection = connect();
			
			// sql delete statement
			String deleteQuery = "DELETE FROM Hyperlink WHERE id = ?";
			
			// prepare the statement
			preparedStatement = connection.prepareStatement(deleteQuery);
			
			// replacing the ? with the data we want
			preparedStatement.setInt(1,id);
			
			// execute query
			preparedStatement.executeUpdate();
			
			// end connection with db
			closeConnection(connection);
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**********************************************
	Function name: select
	Description: select all hyperlinks in database
	Input: None
	Output: Map<Integer, Hyperlink>  - a map containing
	all hyperlinks retrieved from the database. 
	Remarks: The hyperlinks retrieved do not come with 
	the associated comments and metatags.
	**********************************************/
	public static Map<Integer, Hyperlink> select () {
		try{
			// begin connection with db
			connection = connect();
			
			// creating a statement
			Statement statement = connection.createStatement();
			
			// sql select statement
			String selectQuery = "SELECT * FROM Hyperlink";
			
			// execute query
			ResultSet rs = statement.executeQuery(selectQuery);
			
			// iterate trough the resultSet and put the results in a map
			Map<Integer,Hyperlink> map = new HashMap<Integer, Hyperlink>();
			while (rs.next()){ //while there are still results to be processed
				// create a hyperlink object with the data of the resultSet
				Hyperlink hyperlink = new Hyperlink(rs.getInt(1), rs.getString("value"), rs.getTimestamp(3), rs.getTimestamp(4));
				
				// put it in the mat
				map.put(hyperlink.id, hyperlink);
			}
			
			// end connection with the db
			closeConnection(connection);
			
			// return the map with all the hyperlinks
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**********************************************
	Function name: selectComplete
	Description: select all hyperlinks in database, 
	including its corresponding metaTags and comments.
	Input: None
	Output: ArrayList<Hyperlink> - a list containing
	all hyperlinks retrieved from the database. 
	Remarks: None
	**********************************************/
	public static ArrayList<Hyperlink> selectComplete () {
		try{
			// begin connection with the database
			connection = connect();
			
			// sql select query
			// we use union to get the results from both joins and a marker field
			// is_comment, which is 1 if the result came from the join with the 
			// Comment table and 0 otherwise. 
			String selectQuery = "SELECT hyp.id hyperlink_id, hyp.value, hyp.created, hyp.lastedited, com.id att_id, com.value att_value, com.hyperlink_id hypid, 1 is_comment FROM Hyperlink hyp "
					+ "LEFT JOIN Comment AS com ON (com.hyperlink_id = hyp.id) " 
					+ "UNION ALL " 
					+ "SELECT hyp.id hyperlink_id, hyp.value, hyp.created, hyp.lastedited, mtg.id att_id, mtg.value att_value, mtg.hyperlink_id hypid, 0 is_comment FROM Hyperlink hyp "
					+ "LEFT JOIN MetaTag mtg ON (mtg.hyperlink_id = hyp.id)";
			
			// execute query
			preparedStatement = connection.prepareStatement(selectQuery);
			ResultSet rs = preparedStatement.executeQuery();
			
			// extract data from the resultSet
			ArrayList<Hyperlink> hyperlinks = extractData(rs);
			
			// close connection with the database
			closeConnection(connection);
			
			// return the ArrayList we got
			return hyperlinks;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**********************************************
	Function name: select
	Description: select a given hyperlink in database
	Input: 
		# int id - id of the hyperlink to be queried
	Output: Hyperlink - the hyperlink retrieved
	Remarks: The hyperlink is returned without the 
	associated comments and metatags. Returns null if 
	there is not any hyperlink with the given id in
	the database.
	**********************************************/
	public static Hyperlink select (int id) {
		try{
			// begin connection with the database
			connection = connect();
			
			// sql select query
			String selectQuery = "SELECT * FROM Hyperlink WHERE id = ?";
			
			// prepare statement replacing the ? with the data we want and execute the query
			preparedStatement = connection.prepareStatement(selectQuery);
			preparedStatement.setInt(1,id);
			ResultSet rs = preparedStatement.executeQuery();
			
			// close connection with the database
			closeConnection(connection);
			
			// necessary to point rs to the first result
			if (rs.next()) {
				// return the object created with the first result of the resultSet
				return new Hyperlink(rs.getInt(1), rs.getString("value"), rs.getTimestamp(3), rs.getTimestamp(4));
			}
			
			// if we did not get anything
			return null;
			 
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**********************************************
	Function name: selectComplete
	Description: select a given hyperlink in database,
	including its corresponding metaTags and comments.
	Input: 
		# int id - id of the hyperlink to be queried
	Output: Hyperlink - the hyperlink retrieved
	Remarks: The hyperlink is returned with the 
	associated comments and metatags. Returns null if 
	there is not any hyperlink with the given id in
	the database.
	**********************************************/
	public static Hyperlink selectComplete (int id) {
		try{
			
			// begin connection with the database
			connection = connect();
			
			// sql select query
			// we use union to get the results from both joins and a marker field
			// is_comment, which is 1 if the result came from the join with the 
			// Comment table and 0 otherwise. 
			String selectQuery = "SELECT hyp.id hyperlink_id, hyp.value, hyp.created, hyp.lastedited, com.id att_id, com.value att_value, com.hyperlink_id hypid, 1 is_comment FROM Hyperlink hyp "
					+ "LEFT JOIN Comment AS com ON (com.hyperlink_id = hyp.id) WHERE hyp.id = ? " 
					+ "UNION ALL " 
					+ "SELECT hyp.id hyperlink_id, hyp.value, hyp.created, hyp.lastedited, mtg.id att_id, mtg.value att_value, mtg.hyperlink_id hypid, 0 is_comment FROM Hyperlink hyp "
					+ "LEFT JOIN MetaTag mtg ON (mtg.hyperlink_id = hyp.id) WHERE hyp.id = ?";
			
			// prepare statement replacing the ? with the data we want and execute the query
			preparedStatement = connection.prepareStatement(selectQuery);
			preparedStatement.setInt(1, id);
			preparedStatement.setInt(2, id);
			ResultSet rs = preparedStatement.executeQuery();			
			
			// extract data from the resultSet
			ArrayList<Hyperlink> extractedData = extractData(rs);
			
			// end connection with the database
			closeConnection(connection);
			
			// if we got something
			if (extractedData != null)
				return extractedData.get(0); // return the first result
			
			// otherwise return null
			return null;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**********************************************
	Function name: search
	Description: searches for a given string in the
	database
	Input: 
		# String question - string to be searched
	Output: ArrayList<Hyperlink> - a list containing
	all hyperlinks retrieved from the database. 
	Remarks: The hyperlinks are returned with the 
	associated comments and metatags. Returns null if 
	there search did not find anything.
	**********************************************/
	public static ArrayList<Hyperlink> search (String question) {
		try{
			
			// begin connection with the database
			connection = connect();
			
			// sql select query
			// we use union to get the results from both joins and a marker field
			// is_comment, which is 1 if the result came from the join with the 
			// Comment table and 0 otherwise. We search if the given string is a substring
			// of the values of the metaTag, Comment or Hyperlink
			String selectQuery = "SELECT hyp.id hyperlink_id, hyp.value, hyp.created, hyp.lastedited, com.id att_id, com.value att_value, com.hyperlink_id hypid, 1 is_comment FROM Hyperlink hyp "
					+ "LEFT JOIN Comment AS com ON (com.hyperlink_id = hyp.id) WHERE hyp.value LIKE ? OR com.value LIKE ?" 
					+ "UNION ALL " 
					+ "SELECT hyp.id hyperlink_id, hyp.value, hyp.created, hyp.lastedited, mtg.id att_id, mtg.value att_value, mtg.hyperlink_id hypid, 0 is_comment FROM Hyperlink hyp "
					+ "LEFT JOIN MetaTag mtg ON (mtg.hyperlink_id = hyp.id) WHERE hyp.value LIKE ? OR mtg.value LIKE ?";
			
			// prepare statement
			preparedStatement = connection.prepareStatement(selectQuery);
			
			// replace the question marks in our query string. The '%'
			// are necessary to check for substrings
			preparedStatement.setString(1, "%" + question + "%");
			preparedStatement.setString(2, "%" + question + "%");
			preparedStatement.setString(3, "%" + question + "%");
			preparedStatement.setString(4, "%" + question + "%");
			
			// execute query
			ResultSet rs = preparedStatement.executeQuery();
			
			
			// end connection with the database
			closeConnection(connection);
			
			// extract data from the resultSet and return the arraylist retrieved
			return extractData(rs);
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**********************************************
	Function name: extractData
	Description: given a resultSet, extract data 
	from it.
	Input: 
		# ResultSet rs - resultSet from which we
		extract the data.
	Output: ArrayList<Hyperlink> - a list containing
	all hyperlinks extracted from the resultset. 
	Remarks: None
	**********************************************/
	private static ArrayList<Hyperlink> extractData(ResultSet rs) throws SQLException {
         
		// map to store our hyperlinks. The key is the id of the hyperlink and the value
		// is the hyperlink itself.
		Map<Integer, Hyperlink> map = new HashMap<Integer, Hyperlink>();

	    while (rs.next()) {
	    	
	    	// het the id of the hyperlink in the resultSet
	    	int id = rs.getInt("hyperlink_id");
	    	
	    	// get the hyperlink with that id in the map.
			Hyperlink hyperlink = map.get(id);
			
			// if there is no hyperlink with that id in the map yet
			if (hyperlink == null) {
				// we create a new hyperlink with the data from the resulSet
				hyperlink = new Hyperlink(id, rs.getString("value"), rs.getTimestamp("created"), rs.getTimestamp("lastEdited"));
			    // and put it in the map
				map.put(id, hyperlink);
			}
			
			// if the rs row comes from a Comment or MetaTag join
			int type = rs.getInt("is_comment");
			 
			if (type == 1) { //comment
				if (rs.getInt("att_id") != 0) { // if attribute is not null
			        // create a new comment, update the comment list in the hyperlink and
					// update the hyperlink in the map
					Comment comment = new Comment (rs.getInt("att_id"), rs.getInt("hyperlink_id"), rs.getString("att_value"));
			        hyperlink.comments.add(comment);
			    	map.put(id, hyperlink);
				}
			} 
			else if (type == 0) { // meta-tag
				if (rs.getInt("att_id") != 0) {// if attribute is not null
			        // create a new meta-tag, update the meta-tag list in the hyperlink and
					// update the hyperlink in the map
					MetaTag metaTag = new MetaTag (rs.getInt("att_id"), rs.getInt("hyperlink_id"), rs.getString("att_value"));
			    	hyperlink.metaTags.add(metaTag);
			    	map.put(id, hyperlink);
				}
			}
         
	    }
	     
	    // return an ArrayList with all Hyperlinks in the map
	    return new ArrayList<Hyperlink>(map.values());
	}
}
