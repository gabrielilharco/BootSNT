import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DBComment extends DBUtils {
	
	public static Connection connection;
	private static PreparedStatement preparedStatement;
	
	public static boolean add (int hyperlink_id, String value) {
		try {
			connection = connect();
			String query = "INSERT INTO Comment VALUES (?,?,?)";
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
			String updateQuery = "UPDATE Comment"
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
			String deleteQuery = "DELETE FROM Comment WHERE id = ?";
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
	
	public static Map<Integer, Comment> select () {
		try{
			connection = connect();
			Statement statement = connection.createStatement();
			String selectQuery = "SELECT * FROM Comment";
			ResultSet rs = statement.executeQuery(selectQuery);
			Map<Integer, Comment> map = new HashMap<Integer, Comment>();
			while (rs.next()){
				Comment comment = new Comment(rs.getInt(1), rs.getInt(2), rs.getString("value"));
				map.put(comment.id, comment);
			}
			closeConnection(connection);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Comment select (int id) {
		try{
			connection = connect();
			String selectQuery = "SELECT * FROM Comment WHERE id = ?";
			preparedStatement = connection.prepareStatement(selectQuery);
			preparedStatement.setInt(1,id);
			ResultSet rs = preparedStatement.executeQuery();
			rs.next();
			Comment comment = new Comment(rs.getInt(1), rs.getInt(2), rs.getString("value"));
			closeConnection(connection);
			return comment;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
