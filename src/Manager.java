import javax.swing.JButton;


public class Manager {
	
	public static MainWindow mainWindow;
	public static EditWindow editWindow;
	
	public static void process(JButton button) {
		String buttonName = button.getName();
		switch(buttonName) {
		case "addNew":	mainWindow.hideWindow();
						editWindow.showWindow();												
						break;
		case "back"  :	editWindow.hideWindow();
						mainWindow.showWindow();
						break;
		case "show"  :  int j = (int) button.getClientProperty("id");
						Hyperlink hyperlink = DBHyperlink.selectComplete(j);
						editWindow.hyperlinkField.setText(hyperlink.value);
						for (Comment comment : hyperlink.comments) {
							if (comment != null)
								editWindow.commentTableModel.addRow(new String[] {comment.value});
						}
						for (MetaTag metaTag : hyperlink.metaTags) {
							if (metaTag != null)
								editWindow.metaTagTableModel.addRow(new String[] {metaTag.value});
						}
						mainWindow.hideWindow();
						editWindow.showWindow();
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
