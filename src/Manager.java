
public class Manager {
	
	public static MainWindow mainWindow;
	public static EditWindow editWindow;
	
	public static void process(String button) {
		switch(button) {
		case "Add new": editWindow.showWindow();
						mainWindow.hideWindow();						
						break;
		case "Back"   : mainWindow.showWindow();
						editWindow.hideWindow();
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
