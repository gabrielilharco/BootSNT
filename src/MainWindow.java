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
	private JScrollPane hyperlinkPane;
	public Map<Integer, Hyperlink> hyperlinkMap;
	public DefaultTableModel hyperlinkTableModel;
	private ImageIcon showIcon, editIcon, delIcon;
	public JTable hyperlinkTable;
	public ButtonListener buttonListener;
	private String[] hyperlinkColumn = {"Hyperlink", "Created", "Last edited", "Show", "Edit", "Del"};
	public JTextField searchField;
	
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
        showIcon = new ImageIcon("res/show.png");
        editIcon = new ImageIcon("res/edit.png");
        delIcon = new ImageIcon("res/del.png");        
        
        // setup the widgets
        contentPanel = new JPanel(null);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 4, 4, 4));   

        hyperlinkMap = DBHyperlink.select();
        createHyperlinkTable();
        
        JLabel name = new JLabel("Boot SNT");
        name.setFont(new Font("Serif", Font.PLAIN, 20));
        name.setBounds(10, 8, 100, 20);
        
        JButton addNewButton = new JButton("Add new");
        addNewButton.setName("addNew");      
        addNewButton.setBounds(390, 270, 100, 30);
        addNewButton.setFocusPainted(false);
        addNewButton.addActionListener(buttonListener);
        
        JButton searchButton = new JButton("Search");
        searchButton.setName("search");      
        searchButton.setBounds(230, 270, 100, 30);
        searchButton.setFocusPainted(false);
        searchButton.addActionListener(buttonListener);
        
        searchField = new JTextField();
	    searchField.setBounds(20, 276, 200, 20);
	    
	    TextPrompt searchTP = new TextPrompt("Search for words...", searchField);
	    searchTP.changeAlpha(0.8f);
	    
	    contentPanel.add(searchField);
        
        contentPanel.add(name);
        contentPanel.add(addNewButton);
        contentPanel.add(searchButton);
        
        setContentPane(contentPanel);
        
        // add the listeners
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        
        // edit the window
        setLocation(400, 180);
        setSize(520, 340);
        setResizable(false);
    }
	
	public void createResultTable(ArrayList<Hyperlink> hyperlinkResult) {
		hyperlinkTable = new JTable();
	    hyperlinkTable.setName("hyperlinkTable");
	    hyperlinkTableModel = new  DefaultTableModel() {
			private static final long serialVersionUID = 1L;

			@Override
	        public boolean isCellEditable(int row, int col) {
				if (0 <= col && col <= 2)
					return false;
				return true;
	    }};
	    hyperlinkTableModel.setColumnIdentifiers(hyperlinkColumn);
	    hyperlinkTable.setModel(hyperlinkTableModel);
	    
        for (Hyperlink hyperlink : hyperlinkResult) {
        	hyperlinkTableModel.addRow(new Object[] {hyperlink.value,
        	hyperlink.created.toString(), hyperlink.lastEdited.toString(), showIcon,
        	editIcon, delIcon});
        }
        
        hyperlinkTable.getColumn("Show").setCellRenderer(new ButtonRenderer());
        hyperlinkTable.getColumn("Show").setCellEditor(new ButtonEditor(new JCheckBox(), this, buttonListener));
        
        hyperlinkTable.getColumn("Edit").setCellRenderer(new ButtonRenderer());
        hyperlinkTable.getColumn("Edit").setCellEditor(new ButtonEditor(new JCheckBox(), this, buttonListener));
        
        hyperlinkTable.getColumn("Del").setCellRenderer(new ButtonRenderer());
        hyperlinkTable.getColumn("Del").setCellEditor(new ButtonEditor(new JCheckBox(), this, buttonListener));
        
        
        hyperlinkTable.setBounds(10, 40, 495, 220);
        hyperlinkPane = new JScrollPane(hyperlinkTable);
        hyperlinkPane.setBounds(hyperlinkTable.getBounds());
        TableColumn column = null;
        for (int i = 0; i < hyperlinkColumn.length; i++) {
            column = hyperlinkTable.getColumnModel().getColumn(i);
            column.setResizable(false);
            if (i == 0) {
                column.setMaxWidth(155);
            }
            else if (i == 3 || i == 4 || i == 5) {
            	column.setMaxWidth(20);
            }
            else {
                column.setMaxWidth(140);
            }
        }
        contentPanel.add(hyperlinkPane);
        setContentPane(contentPanel);
	}
	
	public void updateHyperlinkTable() {
		removeHyperlinkTable();
		createHyperlinkTable();
	}
    
	public void removeHyperlinkTable() {
        contentPanel.remove(hyperlinkPane);
	}
	
	public void createHyperlinkTable() {
		hyperlinkTable = new JTable();
	    hyperlinkTable.setName("hyperlinkTable");
	    hyperlinkTableModel = new  DefaultTableModel() {
			private static final long serialVersionUID = 1L;

			@Override
	        public boolean isCellEditable(int row, int col) {
				if (0 <= col && col <= 2)
					return false;
				return true;
	    }};
	    hyperlinkTableModel.setColumnIdentifiers(hyperlinkColumn);
	    hyperlinkTable.setModel(hyperlinkTableModel);
	    
        for (Hyperlink hyperlink : hyperlinkMap.values()) {
        	hyperlinkTableModel.addRow(new Object[] {hyperlink.value,
        	hyperlink.created.toString(), hyperlink.lastEdited.toString(), showIcon,
        	editIcon, delIcon});
        }
        
        hyperlinkTable.getColumn("Show").setCellRenderer(new ButtonRenderer());
        hyperlinkTable.getColumn("Show").setCellEditor(new ButtonEditor(new JCheckBox(), this, buttonListener));
        
        hyperlinkTable.getColumn("Edit").setCellRenderer(new ButtonRenderer());
        hyperlinkTable.getColumn("Edit").setCellEditor(new ButtonEditor(new JCheckBox(), this, buttonListener));
        
        hyperlinkTable.getColumn("Del").setCellRenderer(new ButtonRenderer());
        hyperlinkTable.getColumn("Del").setCellEditor(new ButtonEditor(new JCheckBox(), this, buttonListener));
        
        
        hyperlinkTable.setBounds(10, 40, 495, 220);
        hyperlinkPane = new JScrollPane(hyperlinkTable);
        hyperlinkPane.setBounds(hyperlinkTable.getBounds());
        TableColumn column = null;
        for (int i = 0; i < hyperlinkColumn.length; i++) {
            column = hyperlinkTable.getColumnModel().getColumn(i);
            column.setResizable(false);
            if (i == 0) {
                column.setMaxWidth(125);
            }
            else if (i == 4 || i == 5) {
            	column.setMaxWidth(30);
            }
            else if (i == 3) {
            	column.setMaxWidth(40);
            }
            else {
                column.setMaxWidth(135);
            }
        }
        contentPanel.add(hyperlinkPane);
        setContentPane(contentPanel);
	}
	
	public void hideWindow() { setVisible(false); }
	
	public void showWindow() { setVisible(true);  }
	
    public static void main(String[] args) {
    	
    } // end main
    
}