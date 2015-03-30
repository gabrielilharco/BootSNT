import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class DBHyperlink extends DBUtils {

	public static Connection connection;
	private static PreparedStatement preparedStatement;
	
	public static boolean add (String value) {
		try {
			connection = connect();
			String query = "INSERT INTO Hyperlink VALUES (?,?,?,?)";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1,0);
			preparedStatement.setString(2,value);
			preparedStatement.setTimestamp(3, getCurrentTimeStamp());
			preparedStatement.setTimestamp(4, getCurrentTimeStamp());
			preparedStatement.executeUpdate();
			closeConnection(connection);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static boolean update (int id, String value) {
		try{
			connection = connect();
			String updateQuery = "UPDATE Hyperlink"
					+ " SET value = ?" 
					+ ", lastEdited = ?"
					+ " WHERE id = ?";
			preparedStatement = connection.prepareStatement(updateQuery);
			preparedStatement.setString(1,value);
			preparedStatement.setTimestamp(2, getCurrentTimeStamp());
			preparedStatement.setInt(3,id);
			preparedStatement.executeUpdate();
			closeConnection(connection);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static boolean delete(int id) {
		try{
			connection = connect();
			String deleteQuery = "DELETE FROM Hyperlink WHERE id = ?";
			preparedStatement = connection.prepareStatement(deleteQuery);
			preparedStatement.setInt(1,id);
			preparedStatement.executeUpdate();
			closeConnection(connection);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static Map<Integer, Hyperlink> select () {
		try{
			connection = connect();
			Statement statement = connection.createStatement();
			String selectQuery = "SELECT * FROM Hyperlink";
			ResultSet rs = statement.executeQuery(selectQuery);
			Map<Integer,Hyperlink> map = new HashMap<Integer, Hyperlink>();
			while (rs.next()){
				Hyperlink hyperlink = new Hyperlink(rs.getInt(1), rs.getString("value"), rs.getTimestamp(3), rs.getTimestamp(4));
				map.put(hyperlink.id, hyperlink);
			}
			closeConnection(connection);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static ArrayList<Hyperlink> selectComplete () {
		try{
			connection = connect();
			String selectQuery = "SELECT hyp.id hyperlink_id, hyp.value, hyp.created, hyp.lastedited, com.id att_id, com.value att_value, com.hyperlink_id hypid, 1 is_comment FROM Hyperlink hyp "
					+ "LEFT JOIN Comment AS com ON (com.hyperlink_id = hyp.id) " 
					+ "UNION ALL " 
					+ "SELECT hyp.id hyperlink_id, hyp.value, hyp.created, hyp.lastedited, mtg.id att_id, mtg.value att_value, mtg.hyperlink_id hypid, 0 is_comment FROM Hyperlink hyp "
					+ "LEFT JOIN MetaTag mtg ON (mtg.hyperlink_id = hyp.id)";
			preparedStatement = connection.prepareStatement(selectQuery);
			ResultSet rs = preparedStatement.executeQuery();
			ArrayList<Hyperlink> hyperlinks = extractData(rs);
			closeConnection(connection);
			return hyperlinks;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Hyperlink select (int id) {
		try{
			connection = connect();
			String selectQuery = "SELECT * FROM Hyperlink WHERE id = ?";
			preparedStatement = connection.prepareStatement(selectQuery);
			preparedStatement.setInt(1,id);
			ResultSet rs = preparedStatement.executeQuery();
			rs.next();
			Hyperlink hyperlink = new Hyperlink(rs.getInt(1), rs.getString("value"), rs.getTimestamp(3), rs.getTimestamp(4));
			closeConnection(connection);
			return hyperlink;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Hyperlink selectComplete (int id) {
		try{
			connection = connect();
			String selectQuery = "SELECT hyp.id hyperlink_id, hyp.value, hyp.created, hyp.lastedited, com.id att_id, com.value att_value, com.hyperlink_id hypid, 1 is_comment FROM Hyperlink hyp "
					+ "LEFT JOIN Comment AS com ON (com.hyperlink_id = hyp.id) WHERE hyp.id = ? " 
					+ "UNION ALL " 
					+ "SELECT hyp.id hyperlink_id, hyp.value, hyp.created, hyp.lastedited, mtg.id att_id, mtg.value att_value, mtg.hyperlink_id hypid, 0 is_comment FROM Hyperlink hyp "
					+ "LEFT JOIN MetaTag mtg ON (mtg.hyperlink_id = hyp.id) WHERE hyp.id = ?";
			preparedStatement = connection.prepareStatement(selectQuery);
			preparedStatement.setInt(1, id);
			preparedStatement.setInt(2, id);
			ResultSet rs = preparedStatement.executeQuery();
			ArrayList<Hyperlink> extractedData = extractData(rs);
			Hyperlink hyperlink = null;
			if (extractedData != null)
				hyperlink = extractedData.get(0);
			closeConnection(connection);
			return hyperlink;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static ArrayList<Hyperlink> extractData(ResultSet rs) throws SQLException {
         
		 Map<Integer, Hyperlink> map = new HashMap<Integer, Hyperlink>();

	     while (rs.next()) {
	
	         int id = rs.getInt("hyperlink_id");
	         Hyperlink hyperlink = map.get(id);
	         if (hyperlink == null) {
	             hyperlink = new Hyperlink(id, rs.getString("value"), rs.getTimestamp("created"), rs.getTimestamp("lastEdited"));
	             map.put(id, hyperlink);
	         }
	
	         int type = rs.getInt("is_comment");
	         
	         if (type == 1) { //comment
	        	 if (rs.getInt("att_id") != 0) {
		             Comment comment = new Comment (rs.getInt("att_id"), rs.getInt("hyperlink_id"), rs.getString("att_value"));
		        	 hyperlink.comments.add(comment);
		        	 map.put(id, hyperlink);
	        	 }
	         } 
	         else if (type == 0) { // meta-tag
	        	 if (rs.getInt("att_id") != 0) {
		        	 MetaTag metaTag = new MetaTag (rs.getInt("att_id"), rs.getInt("hyperlink_id"), rs.getString("att_value"));
		        	 hyperlink.metaTags.add(metaTag);
		        	 map.put(id, hyperlink);
	        	 }
	         }
	         
	     }
	     
	     return new ArrayList<Hyperlink>(map.values());
	}
}
