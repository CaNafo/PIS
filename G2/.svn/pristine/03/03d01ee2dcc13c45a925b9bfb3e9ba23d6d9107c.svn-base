package controller;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import application.MainWindow;
import helpers.DatabaseConnection;
import helpers.DodavanjeDozvola;
import model.KorisnikModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;
import view.AboutView;
import view.LogsView;
import view.MenuBarView;

public class MenuBarController {
	
	private MenuBarView barView;
	private DatabaseConnection conn;	
	private Statement stmt;
	private KorisnikModel korisnik;
	private DefaultTableModel tableModel;
	private JTable table = new JTable();
	
	KorisnikModel korisnikModel = KorisnikModel.getInstance();
	
	public MenuBarController(MenuBarView barView) {
		conn = DatabaseConnection.getInstance();
		try {
			stmt = conn.getDbConnection().createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		korisnik = KorisnikModel.getInstance();

		this.barView = barView;
		barView.addActionListeners(menuBarActionListener);
	}
	
	ActionListener menuBarActionListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			boolean imaDozvolu = false;
			
			switch (arg0.getActionCommand()) {
			case "showLogs":
				showLogWinow();
				break;
			case "exitApplication":
				MainWindow mainVindow = (MainWindow)barView.getParent().getParent().getParent();
				mainVindow.dispose();
				break;
			case "clanoviReport":
				for(int i = 0;i<korisnikModel.getListaDozvola().size();i++) {
					if(korisnikModel.getListaDozvola().get(i).equals("ClanoviIzvjestaj")) {
						populateClanovi(korisnik.getID_PoslovnogSitema());
						displayReport("reports/Clanovi.jasper");
						table.setModel(tableModel);
						imaDozvolu = true;
						break;
					}
				}
				if(!imaDozvolu)
					JOptionPane.showMessageDialog(null, "Nemate dozvolu za generisanje izvještaja!");
				break;
			case "finansijskaKarticaReport":
				for(int i = 0;i<korisnikModel.getListaDozvola().size();i++) {
					if(korisnikModel.getListaDozvola().get(i).equals("FinansijskaKarticaIzvjestaj")) {
						populateFinancijskaKartica(korisnik.getID_PoslovnogSitema());
						displayReport("reports/FinansijskaKartica.jasper");
						table.setModel(tableModel);
						imaDozvolu = true;
						break;
					}
				}
				if(!imaDozvolu)
					JOptionPane.showMessageDialog(null, "Nemate dozvolu za generisanje izvještaja!");
				break;
			case "stanjeGradjeReport":
				for(int i = 0;i<korisnikModel.getListaDozvola().size();i++){
					if(korisnikModel.getListaDozvola().get(i).equals("StanjeGradjeIzvjestaj")) {
						populateGradja(korisnik.getID_PoslovnogSitema());
						displayReport("reports/Gradja.jasper");
						table.setModel(tableModel);
						imaDozvolu = true;
						break;
					}
				}
				if(!imaDozvolu)
					JOptionPane.showMessageDialog(null, "Nemate dozvolu za generisanje izvještaja!");
				break;
			case "istorijaIzdavanjaReport":
				for(int i = 0;i<korisnikModel.getListaDozvola().size();i++){
					if(korisnikModel.getListaDozvola().get(i).equals("IstorijaIzdavanjaIzvjestaj")) {
						populateIstorijaIzdavanja(korisnik.getID_PoslovnogSitema());
						displayReport("reports/IstorijaIzdavanjaClana.jasper");
						table.setModel(tableModel);
						imaDozvolu = true;
						break;
					}
				}
				if(!imaDozvolu)
					JOptionPane.showMessageDialog(null, "Nemate dozvolu za generisanje izvještaja!");
				break;
			case "addPermission":
				for(int i = 0;i<korisnikModel.getListaDozvola().size();i++){
					if(korisnikModel.getListaDozvola().get(i).equals("DodavanjeDozvola")) {
						new DodavanjeDozvola(false,true);
						imaDozvolu = true;
						break;
					}
				}
				if(!imaDozvolu)
					JOptionPane.showMessageDialog(null, "Nemate dozvolu za ovu komandu!");
				break;
				
			case "addRole":
				for(int i = 0;i<korisnikModel.getListaDozvola().size();i++){
					if(korisnikModel.getListaDozvola().get(i).equals("DodavanjeDozvola")) {
						new DodavanjeDozvola(false,false);
						imaDozvolu = true;
						break;
					}
				}
				if(!imaDozvolu)
					JOptionPane.showMessageDialog(null, "Nemate dozvolu za ovu komandu!");
				break;
				
			case "refresh":
					mainVindow = (MainWindow) barView.getParent().getParent().getParent();
					mainVindow.addTable(mainVindow.getCurrentTable());
					break;
			case "aboutUs":
				new AboutView();
				break;
			}
			
		}
	};
	
	private void showLogWinow() {
		int i = 0;
		JDialog logs = new JDialog();
		Vector<Vector<String>> data = null;
		int ID;
		String korisnickoIme, vrijeme, datum, IP = "";
		String[] columnNames = {"ID korisnika", "Korisnièko ime", "Vrijeme", "Datum", "IP-adresa" };
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM LogoviPoslovnogSistema ORDER BY Datum, Vrijeme ASC");
			data = new Vector<>();
			while(rs.next()) {
				data.add(new Vector<>());
				ID = rs.getInt("KorisnikID");
				korisnickoIme = rs.getString("KorisnickoIme");
				vrijeme = rs.getTime("Vrijeme").toString();
				datum = rs.getDate("Datum").toString();
				IP = rs.getString("IPadresa");
				
				if(rs.getString("IPadresa") == null)
					IP = "unknown";
				
				data.get(i).add(ID+"");
				data.get(i).add(korisnickoIme);
				data.get(i).add(vrijeme);
				data.get(i).add(datum);
				data.get(i).add(IP);
				
				i++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(data!=null) {
			logs.setTitle("Log Window");
			logs.setModal(true);
			logs.setDefaultCloseOperation(logs.DISPOSE_ON_CLOSE);
			logs.setSize(new Dimension(500, 600));
			logs.setMinimumSize(new Dimension(450, 600));
			logs.setLocationRelativeTo(null);
			logs.add(new LogsView(data,columnNames));
			logs.setVisible(true);
		}
	}
	
	
	private void displayReport(String reportPath) {
		JasperPrint jasperPrint = generateReport(reportPath);
		JasperViewer jasperViewer = new JasperViewer(jasperPrint,false);
		jasperViewer.setVisible(true);
	}
	
	
	

	private JasperPrint generateReport(String reportPath) {
		JasperPrint jasperPrint = null;

		try {
			HashMap param = new HashMap();
			param.put("ps_naziv", getPS_Naziv(korisnik.getID_PoslovnogSitema()));
			jasperPrint = JasperFillManager.fillReport(reportPath, param,
					new JRTableModelDataSource(tableModel));

		} catch (JRException e) {
			e.printStackTrace();
		}
		return jasperPrint;
	}

	
	
	
	private String getPS_Naziv(int id_ps) {
		String select = "SELECT PS_NAZIV FROM POSLOVNI_SISTEM WHERE PS_IDENTIFIKATOR="+id_ps;

		try {
			ResultSet rs = stmt.executeQuery(select);
			
		    if (rs.next()) {
		    	String naziv = rs.getString("PS_NAZIV");
		    	return naziv;
		    }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}	
	
	
	private void populateFinancijskaKartica(int id_ps) {

		String[] columnNames = {"FK_Godina", "FK_Stanje"};
				
		tableModel = new DefaultTableModel(null, columnNames);
		try {
			String runSP = "{ call pisg2.FinansijskaKarticaPoslovnogSistema(?) }";
			CallableStatement callableStatement = conn.getDbConnection().prepareCall(runSP);
			callableStatement.setInt("ps_id", id_ps);
			callableStatement.executeQuery();
			ResultSet rs = callableStatement.getResultSet();
		    while (rs.next()) {
		    	int g = rs.getDate("FK_Godina").getYear();
				String[] fin_kartica = {g+"", rs.getString("FK_Stanje")};
				tableModel.addRow(fin_kartica);
		    }
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void populateIstorijaIzdavanja(int id_ps) {

		String[] columnNames = {"Cl_ImePrezime", "Cl_ClanskiBroj", "KG_Naziv", "II_Datum izdavanja", "II_Datum vracanja", "II_Placeno" };
		tableModel = new DefaultTableModel(null, columnNames);

		try {
			String runSP = "{ call pisg2.GetIstorijaIzdavanjaPoslovnogSistema(?) }";
			CallableStatement callableStatement = conn.getDbConnection().prepareCall(runSP);
			callableStatement.setInt("ps_id", id_ps);
			callableStatement.executeQuery();
			ResultSet rs = callableStatement.getResultSet();
			
		    while (rs.next()) {
				String[] ist_izdavanja = {rs.getString("Cl_ImePrezime"), rs.getString("Cl_ClanskiBroj"), rs.getString("KG_Naziv"), rs.getString("II_Datum izdavanja"), rs.getString("II_Datum_vracanja"), rs.getString("II_Placeno").equals("1") ? "Da" : "Ne"};
				tableModel.addRow(ist_izdavanja);
		    }
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	private void populateGradja(int id_ps) {

		String[] columnNames = { "GR_Naziv", "TG_Naziv", "ukupno", "Izdato", "Rezervisano" };

		tableModel = new DefaultTableModel(null, columnNames);

		try {
			String runSP = "{ call pisg2.GetStanjeGradjePoslovnogSistema(?) }";
			CallableStatement callableStatement = conn.getDbConnection().prepareCall(runSP);
			callableStatement.setInt("ps_id", id_ps);
			callableStatement.executeQuery();
			ResultSet rs = callableStatement.getResultSet();
			
		    while (rs.next()) {
				String[] gradja = {rs.getString("GR_Naziv"), rs.getString("TG_Naziv"), rs.getString("ukupno"), rs.getString("Izdato"), rs.getString("Rezervisano")};
				tableModel.addRow(gradja);
		    }
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void populateClanovi(int id_ps) {
		
		
		String[] columnNames = { "Cl_ClanskiBroj", "Cl_ImePrezime", "Cl_DatumUclanjenja" };
		
		tableModel = new DefaultTableModel(null, columnNames);

		try {
			String runSP = "{ call pisg2.GetClanoviPoslovnogSistema(?) }";
			CallableStatement callableStatement = conn.getDbConnection().prepareCall(runSP);
			callableStatement.setInt("ps_id", id_ps);
			callableStatement.executeQuery();
			ResultSet rs = callableStatement.getResultSet();
			
		    while (rs.next()) {
				String[] clanovi = {rs.getString("Cl_ClanskiBroj"), rs.getString("Cl_ImePrezime"), rs.getString("Cl_DatumUclanjenja")};
				tableModel.addRow(clanovi);
		    }
		} catch (SQLException e) {
			e.printStackTrace();
		}
				
	}
}
