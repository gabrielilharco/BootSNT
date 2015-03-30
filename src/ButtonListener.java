import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ButtonListener implements ActionListener {
		
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		Manager.process(button);
	}
}    	

