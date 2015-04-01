import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
  
public class ButtonEditor extends DefaultCellEditor {

	private static final long serialVersionUID = 1L;
	protected JButton button;
	private String    label;
	private boolean   isPushed;
	private MainWindow mainWindow;
	private EditWindow editWindow;
	private ButtonListener buttonListener;
	private boolean buttonHasListener;
  
	public ButtonEditor(JCheckBox checkBox, MainWindow mainWindow, ButtonListener buttonListener) {
		super(checkBox);
		buttonHasListener = false;
		this.mainWindow = mainWindow;
		this.buttonListener = buttonListener;
		button = new JButton();
		button.setOpaque(true);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fireEditingStopped();
			}
		});
	}
	
	public ButtonEditor(JCheckBox checkBox, EditWindow editWindow, ButtonListener buttonListener) {
		super(checkBox);
		buttonHasListener = false;
		this.editWindow = editWindow;
		this.buttonListener = buttonListener;
		button = new JButton();
		button.setOpaque(true);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fireEditingStopped();
			}
		});
	}
  
	public Component getTableCellEditorComponent(JTable table, Object value, 
			boolean isSelected, int row, int column) {
		if (isSelected) {
			button.setForeground(table.getSelectionForeground());
			button.setBackground(table.getSelectionBackground());
		} else {
			button.setForeground(table.getForeground());
			button.setBackground(table.getBackground());
		}
		
		if (table.getName() == "hyperlinkTable") {
			label = (value == null) ? "" : value.toString();
			button.setIcon(new ImageIcon(label));
			String hyperlinkName = (String) mainWindow.hyperlinkTable.getValueAt(row, 0);
			for (Hyperlink hyperlink : mainWindow.hyperlinkMap.values()) {
				if (hyperlink.value.equals(hyperlinkName))
					button.putClientProperty("id", hyperlink.id);
			}
			if (label == "res/show.png") button.setName("show");
			else if (label == "res/edit.png") button.setName("edit");
			else if (label == "res/del.png") button.setName("del");
		}
		
		else if (table.getName() == "commentTable") {
			label = (value == null) ? "" : value.toString();
			button.setIcon(new ImageIcon(label));
			String commentName = (String) editWindow.commentTable.getValueAt(row, 0);
			for (Comment comment : editWindow.commentMap.values()) {
				if (comment.value.equals(commentName))
					button.putClientProperty("id", comment.id);
			}
			if (label == "res/save.png") button.setName("saveComment");
			else if (label == "res/del.png") button.setName("delComment");
		}
		
		else if (table.getName() == "metaTagTable") {
			label = (value == null) ? "" : value.toString();
			button.setIcon(new ImageIcon(label));
			String metaTagName = (String) editWindow.metaTagTable.getValueAt(row, 0);
			for (MetaTag metaTag : editWindow.metaTagMap.values()) {
				if (metaTag.value.equals(metaTagName))
					button.putClientProperty("id", metaTag.id);
			}
			if (label == "res/save.png") button.setName("saveMetaTag");
			else if (label == "res/del.png") button.setName("delMetaTag");
		}		
		
		if (!buttonHasListener) {
			button.addActionListener(buttonListener);
			buttonHasListener = true;
		}
		isPushed = true;
		return button;
	}
  
	public Object getCellEditorValue() {
		if (isPushed)  { }
		isPushed = false;
		return new String(label);
	}
    
	public boolean stopCellEditing() {
		isPushed = false;
		return super.stopCellEditing();
	}
  
	protected void fireEditingStopped() {
		super.fireEditingStopped();
	}
}