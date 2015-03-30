import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class MainWindow extends JFrame {
    
	private static final long serialVersionUID = 1L;
    
	public JPanel contentPanel;
	private ButtonListener buttonListener;
	private JScrollPane hyperlinkPane;
	public Map<Integer, Hyperlink> hyperlinkMap;
	public DefaultTableModel hyperlinkTableModel;
	public ArrayList<JButton> addButtonList, editButtonList, delButtonList;
	
	public MainWindow(ButtonListener buttonListener) {
        super("Main Window");
        
        // select the Look and Feel
        try {
            com.jtattoo.plaf.hifi.HiFiLookAndFeel.setTheme("Default", "INSERT YOUR LICENSE KEY HERE", "Hyperlink DB");
        	UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
        }
        catch (Exception ex) {
        	ex.printStackTrace();
        }
        
        this.buttonListener = buttonListener;
        
        // setup the widgets
        contentPanel = new JPanel(null);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 4, 4, 4));
        
        String[] hyperlinkColumn = {"Hyperlink", "Created", "Last edited"};  
	    JTable hyperlinkTable = new JTable();
	    hyperlinkTableModel = new  DefaultTableModel(0, 0);
	    hyperlinkTableModel.setColumnIdentifiers(hyperlinkColumn);
	    hyperlinkTable.setModel(hyperlinkTableModel);
	    
        hyperlinkMap = DBHyperlink.select();
        for (Hyperlink hyperlink : hyperlinkMap.values()) {
        	hyperlinkTableModel.addRow(new String[] {hyperlink.value,
        	hyperlink.created.toString(), hyperlink.lastEdited.toString()});
        }
        
        hyperlinkTable.setBounds(10, 40, 420, 220);
        hyperlinkPane = new JScrollPane(hyperlinkTable);
        hyperlinkPane.setBounds(10, 40, 500, 220);
        TableColumn column = null;
        for (int i = 0; i < 3; i++) {
            column = hyperlinkTable.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(100);
            } else {
                column.setPreferredWidth(40);
            }
        }        
        
        addButtonList = new ArrayList<JButton>();
        editButtonList = new ArrayList<JButton>();
        delButtonList = new ArrayList<JButton>();
        
        // setting up the buttons
        int i = 1;
        for (int id : hyperlinkMap.keySet()) {
        	createShowButton(i, id);
        	createEditButton(i, id);
        	createDelButton(i, id);
        	i++;
        }
                        
        JLabel name = new JLabel();
        name.setText("Boot SNT");
        name.setFont(new Font("Serif", Font.PLAIN, 20));
        name.setBounds(10, 8, 100, 20);
        
        JButton addNewButton = new JButton();
        addNewButton.setName("addNew");
        addNewButton.setText("Add new");        
        addNewButton.setBounds(390, 270, 100, 30);
        addNewButton.setFocusPainted(false);
        addNewButton.addActionListener(buttonListener);
        
        contentPanel.add(name);
        contentPanel.add(hyperlinkPane);
        contentPanel.add(addNewButton);
        
        setContentPane(contentPanel);
        
        // add the listeners
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        
        // edit the window
        setLocation(32, 32);
        setSize(520, 340);
        setResizable(false);
    }
    
	public void clearTable() {
		int hyperlinkTableSize = hyperlinkTableModel.getRowCount();
		for (int j = hyperlinkTableSize-1; j >= 0; j--) {
			hyperlinkTableModel.removeRow(j);
		}
		for (JButton button :  addButtonList)  { contentPanel.remove(button); }
		for (JButton button : editButtonList)  { contentPanel.remove(button); }
		for (JButton button :  delButtonList)  { contentPanel.remove(button); }
	}
	
	public void showTable() {
		for (Hyperlink hyperlink : hyperlinkMap.values()) {
        	hyperlinkTableModel.addRow(new String[] {hyperlink.value,
        	hyperlink.created.toString(), hyperlink.lastEdited.toString()});
        }
        int i = 1;
		for (int id : hyperlinkMap.keySet()) {
        	createShowButton(i, id);
        	createEditButton(i, id);
        	createDelButton(i, id);
        	i++;
        }
        setContentPane(contentPanel);
	}
	
	public void createShowButton(int i, int id) {
		ImageIcon show = new ImageIcon("res/show.png");
        JButton showButton = new JButton(show);
        showButton.setName("show");
        showButton.putClientProperty("id", id);
        showButton.setBorder(null);
        showButton.setBounds(435, 62 + 20*(i-1), 20, 20);
        showButton.setFocusPainted(false);
        showButton.addActionListener(buttonListener);
        addButtonList.add(showButton);
        hyperlinkPane.add(showButton);
	}
	
	public void createEditButton(int i, int id) {
		ImageIcon edit = new ImageIcon("res/edit.png");
        JButton editButton = new JButton(edit);
        editButton.setName("edit");
        editButton.putClientProperty("id", id);
        editButton.setBorder(null);
        editButton.setBounds(460, 62 + 20*(i-1), 20, 20);
        editButton.setFocusPainted(false);
        editButton.addActionListener(buttonListener);
        editButtonList.add(editButton);
        hyperlinkPane.add(editButton);
	}
	
	public void createDelButton(int i, int id) {
		ImageIcon del = new ImageIcon("res/del.png");
        JButton delButton = new JButton(del);
        delButton.setName("del");
        delButton.putClientProperty("id", id);
        delButton.setBorder(null);
        delButton.setBounds(488, 62 + 20*(i-1), 15, 21);
        delButton.setFocusPainted(false);
        delButton.addActionListener(buttonListener);
        delButtonList.add(delButton);
        hyperlinkPane.add(delButton); 
	}
	
	public void hideWindow() { setVisible(false); }
	
	public void showWindow() { setVisible(true);  }
	
    public static void main(String[] args) {
    	
    } // end main
    
}