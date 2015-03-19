import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class Hyperlink {
	public int id;
	public String value;
	public Timestamp created;
	public Timestamp lastEdited;
	public List<MetaTag> metaTags;
	public List<Comment> comments;
	
	public Hyperlink (int _id, String _value, Timestamp _created, Timestamp _lastEdited) {
		id = _id;
		value = _value;
		created = _created;
		lastEdited = _lastEdited;
		metaTags = new ArrayList<MetaTag>();
		comments = new ArrayList<Comment>();
	}
	
	public Hyperlink (int _id, String _value, Timestamp _created, Timestamp _lastEdited, List<MetaTag> _metaTags, List<Comment> _comments) {
		id = _id;
		value = _value;
		created = _created;
		lastEdited = _lastEdited;
		metaTags = _metaTags;
		comments = _comments;
	}
}
