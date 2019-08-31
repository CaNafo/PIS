package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import com.sun.corba.se.impl.protocol.BootstrapServerRequestDispatcher;

public class LogsView extends JPanel {
	
	private static final long serialVersionUID = 1L;

	private DefaultTableModel tableModel;
	private JTable table;
	
	private String[] columnNames;
	
	public LogsView(Vector<Vector<String>> data, String[] columnNames) {
		setLayout(new BorderLayout());
		addTitle("Recent user logs");
		
		this.columnNames = columnNames;
				
		//tableModel = new DefaultTableModel(data, columnNames);
		table = new JTable(0,columnNames.length);
		fillTable(table,data);
		table.setBorder(BorderFactory.createEmptyBorder(50, 10, 10, 10));		
		table.setFocusable(false);
		
		table.setIntercellSpacing(new Dimension(0, 0));
		table.setRowSelectionAllowed(false);
		table.setVisible(true);
		table.setEnabled(false);
		
		table.setBorder(BorderFactory.createEmptyBorder(25, 10, 10, 10));
		
		DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
		cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		
		for(int i = 0; i<table.getColumnCount();i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
		}
		
		
			table.getTableHeader().setDefaultRenderer(cellRenderer);
			table.getTableHeader().setBorder(BorderFactory.createBevelBorder(0));
		
		JScrollPane tableScroll = new JScrollPane(table);
		add(tableScroll);
	}
	
	private void addTitle(String naslov) {
		JLabel title = new JLabel(naslov);
		
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		Font titleFont = new Font("Modern No. 20", Font.ITALIC | Font.BOLD, 20);
		title.setFont(titleFont);
		title.setForeground(new Color(16, 160, 222));
		title.setBorder(BorderFactory.createBevelBorder(0));
		add(title, BorderLayout.NORTH);
	}
	
	private void fillTable(JTable table, Vector<Vector<String>> data) {
		for(int i=0;i<table.getColumnCount();i++)
		{
		TableColumn column1 = table.getTableHeader().getColumnModel().getColumn(i);
		  
		column1.setHeaderValue(columnNames[i]);
		} 
		TableModel model = table.getModel(); 
		
		for(int i=0; i<data.size();i++) {
			((DefaultTableModel) model).addRow(new String[]{data.get(i).get(0), data.get(i).get(1),data.get(i).get(2),data.get(i).get(3),data.get(i).get(4)});
		}
		
		table.setModel(model);
	}
}
