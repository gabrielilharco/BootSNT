package hyperlink;
//import java.util.Map;

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
		
		/*try {
			Map<Integer, Hyperlink> map = DBHyperlink.selectComplete();
			for (Map.Entry<Integer, Hyperlink> iterator : map.entrySet())
			{
				if (iterator.getValue().comments.size() > 0)
					System.out.println("hp: " + iterator.getValue().value
							         + "comment " + iterator.getValue().comments.size());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
}
