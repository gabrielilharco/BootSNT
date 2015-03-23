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
			String selectQuery = "SELECT hyp.value, hyp.created, hyp.lastedited, com.id comment_id, com.value, com.hyperlink_id, 1 is_comment FROM Hyperlink hyp "
					+ "JOIN Comment com ON (com.hyperlink_id = hyp.id) " 
					+ "UNION ALL " 
					+ "SELECT hyp.value, hyp.created, hyp.lastedited, mtg.id metatag_id, mtg.value, mtg.hyperlink_id, 0 is_comment FROM Hyperlink hyp "
					+ "JOIN MetaTag mtg ON (mtg.hyperlink_id = hyp.id)";
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
			String selectQuery = "SELECT hyp.*, com.id comment_id, com.value, com.hyperlink_id hyperlink_id, 1 is_comment FROM Hyperlink hyp WHERE id = ?"
					+ "JOIN Comment com ON (com.hyperlink_id = hyp.id)" 
					+ "UNION ALL" 
					+ "SELECT hyp.*, mtg.id metatag_id, mtg.value, mtg.hyperlink_id hyperlink_id, 0 is_comment FROM Hyperlink hyp"
					+ "JOIN MetaTag mtg ON (mtg.hyperlink_id = hyp.id)";
			preparedStatement = connection.prepareStatement(selectQuery);
			preparedStatement.setInt(1,id);
			ResultSet rs = preparedStatement.executeQuery();
			Hyperlink hyperlink = extractData(rs).get(0);
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
	         if(hyperlink == null){
	             hyperlink = new Hyperlink(id, rs.getString("value"), rs.getTimestamp("created"), rs.getTimestamp("lastEdited"));
	             map.put(id, hyperlink);
	         }
	
	         int type = rs.getInt("is_comment");
	         
	         if (type == 1) { //comment
	             Comment comment = new Comment (rs.getInt("comment_id"), rs.getInt("hyperlink_id"), rs.getString("value"));
	        	 hyperlink.comments.add(comment);
	         } 
	         else if(type == 0) { // meta-tag
	        	 MetaTag metaTag = new MetaTag (rs.getInt("metatag_id"), rs.getInt("hyperlink_id"), rs.getString("value"));
	        	 hyperlink.metaTags.add(metaTag);
	         }
	     }
	     return new ArrayList<Hyperlink>(map.values());
	}
}
