package helpers;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;


public class DodavanjeDozvola extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private String[] columnNames = {"ID dozvole", "Naziv dozvole"};
	private String[] columnNamesKorisnik = {"ID Korisnika", "Ime korisnika"};
	String[] uloge = { "Administrator", "Menadžer", "Šef", "Radnik"};
	private JTable table;
	private DatabaseConnection conn;	
	private Statement stmt;
	private JButton ok;
	
	@SuppressWarnings("rawtypes")
	private JComboBox listaUloga;
	private boolean modification;
	private boolean dozvole;
	
	Vector<Vector<String>> data = null;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public DodavanjeDozvola(boolean modification, boolean dozvole) {
		this.modification = modification;
		this.dozvole = dozvole;
		setLayout(new BorderLayout());

		try {
			conn = DatabaseConnection.getInstance();
			stmt = conn.getDbConnection().createStatement();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		
		table = new JTable(0,columnNames.length);
		ok = new JButton();
		ok.addActionListener(actionListener);
		listaUloga = new JComboBox(uloge);
		if(modification)
			listaUloga.setSelectedIndex(0);
		else {
			listaUloga.setSelectedIndex(1);
		}
		listaUloga.addActionListener(comboBoxActionListener);		
		table.setBorder(BorderFactory.createEmptyBorder(50, 10, 10, 10));		
		table.setFocusable(false);
		
		table.setIntercellSpacing(new Dimension(0, 0));
		table.setVisible(true);
		
		table.setBorder(BorderFactory.createEmptyBorder(25, 10, 10, 10));
		
		DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
		cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		
		for(int i = 0; i<table.getColumnCount();i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
		}
		
		
			table.getTableHeader().setDefaultRenderer(cellRenderer);
			table.getTableHeader().setBorder(BorderFactory.createBevelBorder(0));
			
			JScrollPane tableScroll = new JScrollPane(table);
			add(tableScroll,BorderLayout.CENTER);
			add(ok, BorderLayout.SOUTH);
			add(listaUloga, BorderLayout.NORTH);
			if(dozvole) {
				ok.setText("Dodaj dozvole");
				fillDozvoleTable();

				showDozvole("Dodavanje dozvola");
			}
			else {
				ok.setText("Dodaj ulogu");
				fillUlogeTable();
				
				showDozvole("Dodavanje uloga");
			}
			
	}
	
	private void fillDozvoleTable() {
		int DOZ_ID;
		int i = 0;
		String DOZ_Naziv;

		try {
						
			ResultSet rs = stmt.executeQuery("SELECT * FROM Dozvola");
			data = new Vector<>();
			while(rs.next()) {
				data.add(new Vector<>());
				DOZ_ID = rs.getInt("DOZ_ID");
				DOZ_Naziv = rs.getString("DOZ_Naziv");				
				
				data.get(i).add(DOZ_ID+"");
				data.get(i).add(DOZ_Naziv);
				
				i++;
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		
		for(i=0;i<table.getColumnCount();i++)
		{
		TableColumn column1 = table.getTableHeader().getColumnModel().getColumn(i);
		  
		column1.setHeaderValue(columnNames[i]);
		} 
		TableModel model = table.getModel(); 
		
		for(i=0; i<data.size();i++) {
			((DefaultTableModel) model).addRow(new String[]{data.get(i).get(0), data.get(i).get(1)});
		}
		
		table.setModel(model);
	}

	@SuppressWarnings("static-access")
	private void showDozvole(String naslov) {
		
		JDialog dialog = new JDialog();		
		
		if(data!=null) {
			dialog.setTitle(naslov);
			dialog.setModal(true);
			dialog.setDefaultCloseOperation(dialog.DISPOSE_ON_CLOSE);
			dialog.setSize(new Dimension(500, 600));
			dialog.setMinimumSize(new Dimension(450, 600));
			dialog.setLocationRelativeTo(null);	
			dialog.add(this);
			dialog.setVisible(true);
		}
	}
	
	ActionListener actionListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Vector<Integer> dozvoleKorisnici = new Vector<>();
			int ulogaID = 0;
			
			switch (listaUloga.getSelectedIndex()) {
			case 0:
				if(modification)
					ulogaID = 1;
				else {
					ulogaID = 2;
				}
				break;
			case 1:
				ulogaID = 2;
				break;
			case 2:
				ulogaID = 3;
				break;
			case 3:
				ulogaID = 4;
				break;

			default:
				break;
			}
			
			for(int i=0;i<table.getRowCount();i++) {
				if(table.isRowSelected(i) ){
					dozvoleKorisnici.add(Integer.parseInt(table.getModel().getValueAt(i, 0).toString()));
				}
			}
			
			if(DodavanjeDozvola.this.dozvole) {				
					for(int i=0;i<dozvoleKorisnici.size();i++) {
						try {
							stmt.execute("INSERT INTO [Lista dozvola] values("+dozvoleKorisnici.get(i)+","+ulogaID+")");
						} catch (SQLException e1) {

							e1.printStackTrace();
						}				
				}
				JOptionPane.showMessageDialog(null, "Dozvole uspješno dodate!");
			}else {
				for(int i=0;i<dozvoleKorisnici.size();i++) {
					try {
						stmt.execute("INSERT INTO [Lista Uloga] values("+dozvoleKorisnici.get(i)+","+ulogaID+")");
					} catch (SQLException e1) {

						e1.printStackTrace();
					}				
			}
			JOptionPane.showMessageDialog(null, "Uloga uspješno dodata!");
			}			
		}
	};
	
	ActionListener comboBoxActionListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(listaUloga.getSelectedIndex()==0 && !modification) {
				listaUloga.setSelectedIndex(1);
				JOptionPane.showMessageDialog(null, "Nemate dozvolu za upravljanje administratorskom ulogom!");				
			}
		}
	};
	
	private void fillUlogeTable() {
		int KOR_ID;
		int i = 0;
		String KOR_KorisnickoIme;

		try {
						
			ResultSet rs = stmt.executeQuery("SELECT * FROM Korisnik");
			data = new Vector<>();
			while(rs.next()) {
				data.add(new Vector<>());
				KOR_ID = rs.getInt("KOR_ID");
				KOR_KorisnickoIme = rs.getString("KOR_KorisnickoIme");				
				
				data.get(i).add(KOR_ID+"");
				data.get(i).add(KOR_KorisnickoIme);
				
				i++;
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		
		for(i=0;i<table.getColumnCount();i++)
		{
		TableColumn column1 = table.getTableHeader().getColumnModel().getColumn(i);
		  
		column1.setHeaderValue(columnNames[i]);
		} 
		TableModel model = table.getModel(); 
		
		for(i=0; i<data.size();i++) {
			((DefaultTableModel) model).addRow(new String[]{data.get(i).get(0), data.get(i).get(1)});
		}
		
		table.setModel(model);
	}
}

