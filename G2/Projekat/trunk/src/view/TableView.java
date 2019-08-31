package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import controller.TableKeyListener;
import controller.TableSelectionController;
import model.TableDataModel;
import model.TableFormModel;

@SuppressWarnings("serial")
public class TableView extends JPanel implements ObserverInterface {

	private TableDataModel dataModel;
	private JScrollPane scrollPane;
	private JTable jTable;
	
	
	public TableView(TableFormModel model) {
		setLayout(new BorderLayout());
		dataModel = model.getTableDataModel();

		// Naslov
		JLabel title = new JLabel(dataModel.getName());
		title.setHorizontalAlignment(SwingConstants.CENTER);
		Font titleFont = new Font("Arial", Font.ITALIC | Font.BOLD, 36);
		title.setFont(titleFont);
		add(title, BorderLayout.NORTH);

		// Tabela
		Vector<String> columnNames = new Vector<>();
		for (int i = 0; i < dataModel.getColumnCount(); i++)
			columnNames.add(dataModel.getColumns().get(i).getName());
		jTable = new JTable(dataModel);
		jTable.setName(model.getTableDataModel().getName());		
		
		scrollPane = new JScrollPane(jTable);
		add(scrollPane, BorderLayout.CENTER);
		
		jTable.getSelectionModel().addListSelectionListener(new TableSelectionController(model, this));
		jTable.addKeyListener(new TableKeyListener(this, dataModel));
		jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jTable.setDefaultEditor(Object.class, null);
		
		DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
		cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		
		for(int i = 0; i<jTable.getColumnCount();i++) {
			jTable.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
		}
		
		cellRenderer = new DefaultTableCellRenderer();
		cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
//		jTable.getTableHeader().setBorder(BorderFactory.createBevelBorder(0));
//		jTable.getTableHeader().setDefaultRenderer(cellRenderer);
		
		model.addObserver(this);
		model.getTableDataModel().addObserver(this);
		setVisible(true);
	}
	
//	public TableView(TableDataModel dataModel) {
//		setLayout(new BorderLayout());
//		this.dataModel = dataModel;
//		
//		// Naslov
//		JLabel title = new JLabel(dataModel.getName());
//		title.setHorizontalAlignment(SwingConstants.CENTER);
//		Font titleFont = new Font("Arial", Font.ITALIC | Font.BOLD, 36);
//		title.setFont(titleFont);
//		add(title, BorderLayout.NORTH);
//
//		// Tabela
//		scrollPane = new JScrollPane(generateTable());
//		add(scrollPane, BorderLayout.CENTER);
//
//		jTable.getSelectionModel().addListSelectionListener(new TableSelectionController(model, this));
//		jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//		jTable.setDefaultEditor(Object.class, null);
//		
//		model.addObserver(this);
//		setVisible(true);
//	}

	@Override
	public void update() {
		dataModel.fetchData();
		jTable.repaint();
		jTable.revalidate();
		repaint();
		revalidate();
	}

//	private JTable generateTable() {
//		jTable = new JTable(dataModel.getData(), dataModel.getColumnDisplayNames());
//		return jTable;
//	}
	
	public JTable getjTable() {
		return jTable;
	}
}
