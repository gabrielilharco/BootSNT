/************************************************************************
 Class: Comment
 Type: public 
 Relations: None
 Description: Represents a Comment object
 Remarks: None.
 Attributes: 
 		# public int id
 		# public int hyperlink_id
 		# public String value
 Methods:
 		# public Comment (int _id, int _hyperlink_id, String _value
 		
*************************************************************************/
public class Comment {
	
	// Attributes:
	public int id; // id of the comment in the database
	public int hyperlink_id; // id of the hyperlink it is associated to. (Foreign Key)
	public String value; // the value of the comment
	
	/**********************************************
	Function name: Comment
	Description: basic constructor
	Input: 
		# int _id - id of the comment
		# int _hyperlink_id - id of the hyperlink it is associated to
		# String value - value of the comment
	Output: None
	Remarks: None
	**********************************************/
	public Comment (int _id, int _hyperlink_id, String _value) {
		id = _id;
		hyperlink_id = _hyperlink_id;
		value = _value;
	}
	
}
