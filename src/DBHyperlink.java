import java.sql.*;
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
	
	public static Map<Integer, Hyperlink> selectComplete () {
		try{
			Map<Integer, Hyperlink> hyperlinkMap = select();
			Map<Integer, MetaTag> metaTagMap = DBMetaTag.select();
			Map<Integer, Comment> commentMap = DBComment.select();
			for (Map.Entry<Integer, MetaTag> iterator : metaTagMap.entrySet())
			{
				MetaTag metaTag = iterator.getValue();
				Hyperlink hyperlink = hyperlinkMap.get(metaTag.hyperlink_id);
				hyperlink.metaTags.add(metaTag);
				hyperlinkMap.put(hyperlink.id, hyperlink);
				
			}
			for (Map.Entry<Integer, Comment> iterator : commentMap.entrySet())
			{
				Comment comment = iterator.getValue();
				Hyperlink hyperlink = hyperlinkMap.get(comment.hyperlink_id);
				hyperlink.comments.add(comment);
				hyperlinkMap.put(hyperlink.id, hyperlink);
			}
			return hyperlinkMap;
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
			String selectQuery = "SELECT * FROM Hyperlink WHERE id = ?";
			preparedStatement = connection.prepareStatement(selectQuery);
			preparedStatement.setInt(1,id);
			ResultSet rs = preparedStatement.executeQuery();
			rs.next();
			Hyperlink hyperlink = new Hyperlink(rs.getInt(1), rs.getString("value"), rs.getTimestamp(3), rs.getTimestamp(4));
			Map<Integer, MetaTag> metaTagMap = DBMetaTag.select();
			Map<Integer, Comment> commentMap = DBComment.select();
			for (Map.Entry<Integer, MetaTag> iterator : metaTagMap.entrySet())
			{
				MetaTag metaTag = iterator.getValue();
				if (hyperlink.id == metaTag.hyperlink_id);
					hyperlink.metaTags.add(metaTag);
			}
			for (Map.Entry<Integer, Comment> iterator : commentMap.entrySet())
			{
				Comment comment = iterator.getValue();
				if (hyperlink.id == comment.hyperlink_id);
				hyperlink.comments.add(comment);
			}
			closeConnection(connection);
			return hyperlink;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	
}
