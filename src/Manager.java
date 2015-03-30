import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JOptionPane;


public class Manager {
	
	public static MainWindow mainWindow;
	public static EditWindow editWindow;
	
	public static void process(JButton button) {
		String buttonName = button.getName();
		int hyperlinkId;
		int response;
		switch(buttonName) {
		case "addNew":	editWindow.showWindow();
						mainWindow.hideWindow();												
						break;
		case "back"  :	JOptionPane.setDefaultLocale(Locale.ENGLISH);
						response = JOptionPane.showConfirmDialog(null, "Do you want to go back?", "Confirm",
		        		JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
						if (response == JOptionPane.YES_OPTION) {							
							mainWindow.showWindow();
							editWindow.hideWindow();
							editWindow.clearAll();
						}
						break;
		case "edit"  :
		case "show"  :  hyperlinkId = (int) button.getClientProperty("id");
						Hyperlink hyperlink = DBHyperlink.selectComplete(hyperlinkId);
						editWindow.addAll(hyperlink);												
						editWindow.showWindow();
						mainWindow.hideWindow();						
						break;
		case "del"   :  JOptionPane.setDefaultLocale(Locale.ENGLISH);
						response = JOptionPane.showConfirmDialog(null, "Do you want to delete the hyperlink?", "Confirm",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
						if (response == JOptionPane.YES_OPTION) {							
							hyperlinkId = (int) button.getClientProperty("id");
							mainWindow.hyperlinkMap.remove(hyperlinkId);
							//DBHyperlink.delete(hyperlinkId);
							mainWindow.clearTable();
							mainWindow.showTable();
						}						
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
