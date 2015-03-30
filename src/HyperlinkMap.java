import java.util.HashMap;
import java.util.Iterator;

/************************************************************************
Class: Hyperlinkap
Type: public abstract
Relations: None
Description: Represents a HyperlinkMap object that stores the data we are 
currently working with in our application
Remarks: None.
Attributes: 
		# public static HashMap<Integer, Hyperlink> map
Methods:
		# public static void start ()
		# public static void putHyperlink (Hyperlink hyperlink)
		# public static void deleteHyperlink (Hyperlink hyperlink)
		# public static void insertMetaTag (MetaTag metaTag)
		# public static void insertComment(Comment comment)
		# public static void deleteMetaTag (MetaTag metaTag)
		# public static void deleteComment(Comment comment)
		
*************************************************************************/

public abstract class HyperlinkMap {
	
	// Attributes
	public static HashMap<Integer, Hyperlink> map; //stores the data we are currently working with in our application
	
	/**********************************************
	Function name: start
	Description: initialize the map
	Input: None
	Output: None
	Remarks: None
	**********************************************/
	public static void start () {
		map = new HashMap<Integer, Hyperlink>();
	}
	
	/**********************************************
	Function name: putHyperlink
	Description: insert or update a hyperlink in the map
	Input: 
`		# Hyperlink hyperlink - hyperlink to be inserted
	or updated.
	Output: None
	Remarks: None
	**********************************************/
	public static void putHyperlink (Hyperlink hyperlink) {
		map.put(hyperlink.id, hyperlink);
	}
	
	/**********************************************
	Function name: deleteHyperlink
	Description: delete a hyperlink in the map
	Input: 
`		# Hyperlink hyperlink - hyperlink to be deleted
	Output: None
	Remarks: None
	**********************************************/
	public static void deleteHyperlink (Hyperlink hyperlink) {
		map.remove(hyperlink.id);
	}
	
	/**********************************************
	Function name: insertMetaTag
	Description: insert a metaTag in the corresponding
	hyperlink in the map
	Input: 
`		# MetaTag metaTag - metaTag to be inserted
	Output: None
	Remarks: None
	**********************************************/
	public static void insertMetaTag (MetaTag metaTag) {
		Hyperlink hyperlink = map.get(metaTag.hyperlink_id);
		hyperlink.metaTags.add(metaTag);
		map.put(hyperlink.id, hyperlink);
	}
	
	/**********************************************
	Function name: insertComment
	Description: insert a comment in the corresponding
	hyperlink in the map
	Input: 
`		# Comment comment - comment to be inserted
	Output: None
	Remarks: None
	**********************************************/
	public static void insertComment(Comment comment) {
		Hyperlink hyperlink = map.get(comment.hyperlink_id);
		hyperlink.comments.add(comment);
		map.put(hyperlink.id, hyperlink);
	}
	
	/**********************************************
	Function name: deleteMetaTag
	Description: delete a metaTag in the corresponding
	hyperlink in the map
	Input: 
`		# MetaTag metaTag - metaTag to be deleted
	Output: None
	Remarks: None
	**********************************************/
	public static void deleteMetaTag (MetaTag metaTag) {
		Hyperlink hyperlink = map.get(metaTag.hyperlink_id);
		for (Iterator<MetaTag> iterator = hyperlink.metaTags.iterator(); iterator.hasNext(); ) {
			MetaTag mt = iterator.next();
			if (mt.id == metaTag.id) {
				iterator.remove();
			}
		}
	}
	
	/**********************************************
	Function name: deleteComment
	Description: delete a comment in the corresponding
	hyperlink in the map
	Input: 
`		# Comment comment - comment to be deleted
	Output: None
	Remarks: None
	**********************************************/
	public static void deleteComment(Comment comment) {
		Hyperlink hyperlink = map.get(comment.hyperlink_id);
		for (Iterator<Comment> iterator = hyperlink.comments.iterator(); iterator.hasNext(); ) {
			Comment cmt = iterator.next();
			if (cmt.id == comment.id) {
				iterator.remove();
			}
		}
	}
}
