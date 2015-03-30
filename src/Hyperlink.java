import java.sql.*;
import java.util.List;
import java.util.ArrayList;

/************************************************************************
Class: Hyperlink
Type: public 
Relations: None
Description: Represents a Hyperlink object
Remarks: None.
Attributes: 
		# public int id
		# public String value
		# public Timestamp created
		# public Timestamp lastEdited
		# public List<MetaTag> metaTags
		# public List<Comment> comments
Methods:
		# public Hyperlink (int _id, String _value, Timestamp _created, Timestamp _lastEdited)
		# public Hyperlink (int _id, String _value, Timestamp _created, Timestamp _lastEdited, List<MetaTag> _metaTags, List<Comment> _comments)
		
*************************************************************************/

public class Hyperlink {
	
	// Attributes:
	public int id; // id of the hyperlink in the database
	public String value; // string that represents the value of the hyperlink
	public Timestamp created; // date and time of creation
	public Timestamp lastEdited; // date and time of last edition
	public List<MetaTag> metaTags; // list of associated metaTags
	public List<Comment> comments; // list of associated comments
	
	/**********************************************
	Function name: Hyperlink
	Description: basic constructor
	Input: 
		# int _id - id of the hyperlink
		# String _value - value of the hyperlink
		# Timestamp _created - date and time of creation
		# Timestamp _lastEdited - date and time of lastEdition
	Output: None
	Remarks: Initialize metaTags and comments list as new lists
	**********************************************/
	public Hyperlink (int _id, String _value, Timestamp _created, Timestamp _lastEdited) {
		id = _id;
		value = _value;
		created = _created;
		lastEdited = _lastEdited;
		metaTags = new ArrayList<MetaTag>();
		comments = new ArrayList<Comment>();
	}
	/**********************************************
	Function name: Hyperlink
	Description: basic constructor
	Input: 
		# int _id - id of the hyperlink
		# String _value - value of the hyperlink
		# Timestamp _created - date and time of creation
		# Timestamp _lastEdited - date and time of lastEdition
		# public List<MetaTag> _metaTags - list of metaTags
		# public List<Comment> _comments - list of comments
	Output: None
	Remarks: None
	**********************************************/
	public Hyperlink (int _id, String _value, Timestamp _created, Timestamp _lastEdited, List<MetaTag> _metaTags, List<Comment> _comments) {
		id = _id;
		value = _value;
		created = _created;
		lastEdited = _lastEdited;
		metaTags = _metaTags;
		comments = _comments;
	}
}
