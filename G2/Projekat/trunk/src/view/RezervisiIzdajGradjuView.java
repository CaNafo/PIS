package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import controller.RezervisiIzdajGradjuController;

public class RezervisiIzdajGradjuView extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JTable table;
	private JButton rezervisi, izdaj, ukloniRezervaciju, vratiArtikal;
	private JScrollPane tableScroll = null;
	private JTextField search = new JTextField();
	private JPanel tablePanel = new JPanel();
	private String[] columnNames;

	public RezervisiIzdajGradjuView(int ID) {

		setLookAndFeel();
		setLayout(new BorderLayout());
		tablePanel.setLayout(new BorderLayout());
		addTitle("Rad sa gra�om");

		tablePanel.add(search, BorderLayout.NORTH);

		search.setText("");
		JPanel buttonPanel = new JPanel();

		rezervisi = new JButton("Rezervi�i artikal");
		rezervisi.setPreferredSize(new Dimension(150, 20));
		rezervisi.setActionCommand("rezervisi");

		izdaj = new JButton("Izdaj artikal");
		izdaj.setActionCommand("izdaj");

		ukloniRezervaciju = new JButton("Ukloni rezervaciju");
		ukloniRezervaciju.setActionCommand("ukloniRezervaciju");

		vratiArtikal = new JButton("Vrati artikal");
		vratiArtikal.setActionCommand("Vrati artikal");

		buttonPanel.setLayout(new GridLayout(1, 4, 4, 4));
		buttonPanel.add(izdaj);
		buttonPanel.add(rezervisi);
		buttonPanel.add(ukloniRezervaciju);
		buttonPanel.add(vratiArtikal);
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
		add(buttonPanel, BorderLayout.SOUTH);

		setVisible(true);

		new RezervisiIzdajGradjuController(ID, this);
		openJDialog();
	}

	public void update(Vector<Vector<String>> data, String[] columnNames, MouseListener tableListener) {
		this.columnNames = columnNames;
		if (tableScroll != null) {
			tablePanel.remove(tableScroll);
			repaint();
			revalidate();
		}

		table = new JTable(0, columnNames.length);
		fillTable(table, data);
		table.setBorder(BorderFactory.createEmptyBorder(50, 10, 10, 10));
		table.setFocusable(false);

		table.setIntercellSpacing(new Dimension(0, 0));
		table.setVisible(true);

		table.setBorder(BorderFactory.createEmptyBorder(25, 10, 10, 10));

		DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
		cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);

		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
		}

		table.getTableHeader().setDefaultRenderer(cellRenderer);
		table.getTableHeader().setBorder(BorderFactory.createBevelBorder(0));

		tableScroll = new JScrollPane(table);
		tablePanel.add(tableScroll, BorderLayout.CENTER);
		add(tablePanel, BorderLayout.CENTER);

		search.grabFocus();
		table.addMouseListener(tableListener);
	}

	private void addTitle(String naslov) {
		JLabel title = new JLabel(naslov);

		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		Font titleFont = new Font("Consolas", Font.ITALIC | Font.BOLD, 20);
		title.setFont(titleFont);
		title.setForeground(new Color(16, 160, 222));
		title.setBorder(BorderFactory.createBevelBorder(0));
		add(title, BorderLayout.NORTH);
	}

	private void fillTable(JTable table, Vector<Vector<String>> data) {
		for (int i = 0; i < table.getColumnCount(); i++) {
			TableColumn column1 = table.getTableHeader().getColumnModel().getColumn(i);

			column1.setHeaderValue(columnNames[i]);
		}
		TableModel model = table.getModel();

		for (int i = 0; i < data.size(); i++) {
			((DefaultTableModel) model).addRow(new String[] { data.get(i).get(0), data.get(i).get(1),
					data.get(i).get(2), data.get(i).get(3), data.get(i).get(4), data.get(i).get(5) });
		}

		table.setModel(model);
	}

	private void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addActionListeners(DocumentListener documentListener, MouseListener listener,
			ActionListener actionListener) {
		search.getDocument().addDocumentListener(documentListener);
		search.addMouseListener(listener);
		izdaj.addActionListener(actionListener);
		rezervisi.addActionListener(actionListener);
		ukloniRezervaciju.addActionListener(actionListener);
		vratiArtikal.addActionListener(actionListener);
	}

	public JTextField getSearch() {
		return search;
	}
	
	private void openJDialog() {
		JDialog detalji = new JDialog();
		detalji.setDefaultCloseOperation(detalji.DISPOSE_ON_CLOSE);
		detalji.setSize(new Dimension(1024, 600));
		detalji.setMinimumSize(new Dimension(450, 600));
		detalji.add(this);
		detalji.setVisible(true);
		detalji.setLocationRelativeTo(null);
	}
}
