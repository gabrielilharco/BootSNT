import java.util.ArrayList;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;


public class Manager {
	
	public static MainWindow mainWindow;
	public static EditWindow editWindow;
	
	public static void process(JButton button) {
		String buttonName = button.getName();
		int hyperlinkId, commentId, metaTagId;
		int response;
		JOptionPane.setDefaultLocale(Locale.ENGLISH);
		Hyperlink hyperlink;
		switch(buttonName) {
		case "addNew"    : Object[] message = {"Write the new hyperlink name:"};
        				   String option = (String) JOptionPane.showInputDialog(null, message, "Add New", JOptionPane.OK_CANCEL_OPTION, new ImageIcon("res/edit.png"), null, null);
        				   if (option != null && !option.isEmpty()) {
        					   String newHyperlinkText = option;
							   DBHyperlink.add(newHyperlinkText);
							   mainWindow.hyperlinkMap = DBHyperlink.select();
							   for (Hyperlink newHyperlink : mainWindow.hyperlinkMap.values()) {
								   if (newHyperlink.value.equals(newHyperlinkText)) {
									   editWindow.currHyperlink = newHyperlink;									   
									   break;
								   }
							   }
        					   editWindow.createAllTables();
        					   editWindow.showWindow();
        					   mainWindow.hideWindow();
        				   }
						   break;		
		case "show"      :/* hyperlinkId = (int) button.getClientProperty("id");
		   				   System.out.println("show id: " + hyperlinkId);
						   response = JOptionPane.showConfirmDialog(null, "Do you want to show the hyperlink?", "Confirm",
								   	JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
						   if (response == JOptionPane.YES_OPTION) {      
							   hyperlink = DBHyperlink.selectComplete(hyperlinkId);
							   editWindow.currHyperlink = hyperlink;
							   editWindow.createAllTables();												
							   editWindow.showWindow();
							   mainWindow.hideWindow();		
						   }*/
		case "edit"      : hyperlinkId = (int) button.getClientProperty("id");
		   				   System.out.println("edit id: " + hyperlinkId);
		   				   response = JOptionPane.showConfirmDialog(null, "Do you want to " + buttonName + " the hyperlink?", "Confirm",
		   						   	JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		   				   if (response == JOptionPane.YES_OPTION) {					       
		   					   hyperlink = DBHyperlink.selectComplete(hyperlinkId);
							   editWindow.currHyperlink = hyperlink;
		   					   editWindow.createAllTables();												
							   editWindow.showWindow();
							   mainWindow.hideWindow();	
						   }		   				   					
						   break;		
		case "del"       : hyperlinkId = (int) button.getClientProperty("id");
						   System.out.println("del id: " + hyperlinkId);
						   response = JOptionPane.showConfirmDialog(null, "Do you want to delete the hyperlink?", "Confirm",
						   JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
						   if (response == JOptionPane.YES_OPTION) {					       
							   mainWindow.hyperlinkMap.remove(hyperlinkId);
							   DBHyperlink.delete(hyperlinkId);
							   mainWindow.updateHyperlinkTable();
						   }
						   break;
		case "addComment": String commentText = editWindow.commentField.getText();
						   DBComment.add(editWindow.currHyperlink.id, commentText);
						   editWindow.commentMap = DBComment.select();
						   for (Comment comment : editWindow.commentMap.values()) {
							   if (comment.value.equals(commentText)) {
								   editWindow.currHyperlink.comments.add(comment);
								   break;
							   }
						   }
						   editWindow.updateCommentTable();
						   editWindow.commentField.setText(null);
						   break;
		case "addMetaTag": String metaTagText = editWindow.metaTagField.getText();
						   DBMetaTag.add(editWindow.currHyperlink.id, metaTagText);
						   editWindow.metaTagMap = DBMetaTag.select();
						   for (MetaTag metaTag : editWindow.metaTagMap.values()) {
							   if (metaTag.value.equals(metaTagText)) {
								   editWindow.currHyperlink.metaTags.add(metaTag);
								   break;
							   }
						   }
						   editWindow.updateMetaTagTable();
						   editWindow.metaTagField.setText(null);
		   				   break;
		case "delComment": commentId = (int) button.getClientProperty("id");
						   System.out.println("del comment id: " + commentId);
						   response = JOptionPane.showConfirmDialog(null, "Do you want to delete the comment?", "Confirm",
								   	JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
						   if (response == JOptionPane.YES_OPTION) {					       
						      editWindow.commentMap.remove(commentId);
						      for (Comment comment : editWindow.currHyperlink.comments) {
						    	  if (comment.id == commentId) {
						    		  editWindow.currHyperlink.comments.remove(comment);
						    		  break;
						    	  }
						      }
						      DBComment.delete(commentId);
						      editWindow.updateCommentTable();
							}
							break;
		case "delMetaTag":  metaTagId = (int) button.getClientProperty("id");
							System.out.println("del metaTag id: " + metaTagId);
							response = JOptionPane.showConfirmDialog(null, "Do you want to delete the metaTag?", "Confirm",
									JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
							if (response == JOptionPane.YES_OPTION) {					       
								editWindow.metaTagMap.remove(metaTagId);
								for (MetaTag metaTag : editWindow.currHyperlink.metaTags) {
							    	  if (metaTag.id == metaTagId) {
							    		  editWindow.currHyperlink.metaTags.remove(metaTag);
							    		  break;
							    	  }
							    }
								DBMetaTag.delete(metaTagId);
								editWindow.removeMetaTagTable();
								editWindow.createMetaTagTable();
							}
							break;
		case "back"      : JOptionPane.setDefaultLocale(Locale.ENGLISH);
						   response = JOptionPane.showConfirmDialog(null, "Do you want to go back?", "Confirm",
						   JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
						   if (response == JOptionPane.YES_OPTION) {							
						       mainWindow.removeHyperlinkTable();
						       mainWindow.createHyperlinkTable();
						       mainWindow.showWindow();
						       editWindow.hideWindow();
						       editWindow.removeAllTables();
						       editWindow.currHyperlink = null;
						   }
						   break;
		case "search"	 : ArrayList<Hyperlink> searchResult = DBHyperlink.search(mainWindow.searchField.getText());
						   mainWindow.removeHyperlinkTable();
						   mainWindow.createResultTable(searchResult);						   
						   break;
		default: ;
		}
	}
	
	public static void main(String[] args) {

		ButtonListener buttonListener = new ButtonListener();
		mainWindow = new MainWindow(buttonListener);
		editWindow = new EditWindow(buttonListener);
		
		mainWindow.showWindow();
		
	}
}
