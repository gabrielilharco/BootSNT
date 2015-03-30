/************************************************************************
 Class: MetaTag
 Type: public 
 Relations: None
 Description: Represents a MetaTag object
 Remarks: None.
 Attributes: 
 		# public int id
 		# public int hyperlink_id
 		# public String value
 Methods:
 		# public MetaTag (int _id, int _hyperlink_id, String _value
 		
*************************************************************************/
public class MetaTag {
	
	// Attributes:
	public int id; // id of the metaTag in the database
	public int hyperlink_id; // id of the hyperlink it is associated to. (Foreign Key)
	public String value; // the value of the metaTag
	
	/**********************************************
	Function name: MetaTag
	Description: basic constructor
	Input: 
		# int _id - id of the metaTag
		# int _hyperlink_id - id of the hyperlink it is associated to
		# String value - value of the metaTag
	Output: None
	Remarks: None
	**********************************************/
	public MetaTag (int _id, int _hyperlink_id, String _value) {
		id = _id;
		hyperlink_id = _hyperlink_id;
		value = _value;
	}
	
}
