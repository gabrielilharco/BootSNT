package hyperlink;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonListener implements ActionListener {
		
	public void actionPerformed(ActionEvent e) {
		String button = e.getActionCommand();
		Manager.process(button);
	}
}    	

