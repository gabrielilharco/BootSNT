import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DBMetaTag extends DBUtils {
	public static Connection connection;
	private static PreparedStatement preparedStatement;
	
	public static boolean add (int hyperlink_id, String value) {
		try {
			connection = connect();
			String query = "INSERT INTO MetaTag VALUES (?,?,?)";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1,0);
			preparedStatement.setInt(2, hyperlink_id);
			preparedStatement.setString(3,value);
			preparedStatement.executeUpdate();
			closeConnection(connection);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static boolean update (int id, int hyperlink_id, String value) {
		try{
			connection = connect();
			String updateQuery = "UPDATE MetaTag"
					+ " SET value = ?" 
					+ ", hyperlink_id = ?"
					+ " WHERE id = ?";
			preparedStatement = connection.prepareStatement(updateQuery);
			preparedStatement.setString(1,value);
			preparedStatement.setInt(2, hyperlink_id);
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
			String deleteQuery = "DELETE FROM MetaTag WHERE id = ?";
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
	
	public static Map<Integer, MetaTag> select () {
		try{
			connection = connect();
			Statement statement = connection.createStatement();
			String selectQuery = "SELECT * FROM MetaTag";
			ResultSet rs = statement.executeQuery(selectQuery);
			Map<Integer, MetaTag> map = new HashMap<Integer, MetaTag>();
			while (rs.next()){
				MetaTag metaTag = new MetaTag(rs.getInt(1), rs.getInt(2), rs.getString("value"));
				map.put(metaTag.id, metaTag);
			}
			closeConnection(connection);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static MetaTag select (int id) {
		try{
			connection = connect();
			String selectQuery = "SELECT * FROM MetaTag WHERE id = ?";
			preparedStatement = connection.prepareStatement(selectQuery);
			preparedStatement.setInt(1,id);
			ResultSet rs = preparedStatement.executeQuery();
			rs.next();
			MetaTag metaTag = new MetaTag(rs.getInt(1), rs.getInt(2), rs.getString("value"));
			closeConnection(connection);
			return metaTag;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
