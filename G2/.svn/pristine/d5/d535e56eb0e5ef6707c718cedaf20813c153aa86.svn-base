package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import helpers.DatabaseConnection;
import view.RezervisiIzdajGradjuView;

public class RezervisiIzdajGradjuController {

	private DatabaseConnection conn;
	private Statement stmt;
	private Vector<Vector<String>> data;
	private RezervisiIzdajGradjuView rezervisiIzdajGradjuView;
	private String[] columnNames = { "Èlanski broj", "Ime i prezime", "Broj iznajmljenih artikala",
			"Broj rezervisanih artikala", "Dozvoljeno izdavanje", "Dozvoljeno rezervisanje" };

	private int clanskiBrojTemp;
	private String moguceIzdati = "";
	private String moguceRezervisati = "";

	int gradjaID;

	private boolean firstTextAttempt = true;

	public RezervisiIzdajGradjuController(int gradjaID, RezervisiIzdajGradjuView rezervisiIzdajGradjuView) {
		this.rezervisiIzdajGradjuView = rezervisiIzdajGradjuView;
		this.gradjaID = gradjaID;

		conn = DatabaseConnection.getInstance();
		try {
			stmt = conn.getDbConnection().createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		searchKorisnik("*", true);
		rezervisiIzdajGradjuView.addActionListeners(documentListener, mouseListener, actionListener);
	}

	MouseListener tableListener = new MouseListener() {

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 1) {
				final JTable jTable = (JTable) e.getSource();
				final int row = jTable.getSelectedRow();
				final int column = 0;
				final String valueInCell = (String) jTable.getValueAt(row, column);

				clanskiBrojTemp = Integer.parseInt(valueInCell);
				moguceIzdati = (String) jTable.getValueAt(row, 4);
				moguceRezervisati = (String) jTable.getValueAt(row, 5);
			}

		}
	};

	MouseListener mouseListener = new MouseListener() {

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub

		}
	};

	DocumentListener documentListener = new DocumentListener() {

		@Override
		public void removeUpdate(DocumentEvent e) {
			if (rezervisiIzdajGradjuView.getSearch().getText().length() > 0)
				searchKorisnik(rezervisiIzdajGradjuView.getSearch().getText(), false);
			else
				searchKorisnik("*", true);
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			if (rezervisiIzdajGradjuView.getSearch().getText().length() > 0)
				searchKorisnik(rezervisiIzdajGradjuView.getSearch().getText(), false);
			else
				searchKorisnik("*", true);
		}

		@Override
		public void changedUpdate(DocumentEvent e) {

		}
	};

	ActionListener actionListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("izdaj")) {
				if (!moguceIzdati.equals("NE")) {
					izdajRezervisiGradju(true);
				}
			} else if (e.getActionCommand().equals("rezervisi")) {
				if (!moguceRezervisati.equals("NE")) {
					izdajRezervisiGradju(false);
				}
			} else if (e.getActionCommand().equals("ukloniRezervaciju")) {
				ukloniRezervacijuIzdavanje(false);
			} else {
				ukloniRezervacijuIzdavanje(true);
			}
		}
	};

	private void searchKorisnik(String search, boolean update) {
		int izdatoTemp, rezervisanoTemp;

		ResultSet kolicinaIznajmljenogMaterijala;
		ResultSet kolicinaRezervisanogMaterijala;
		ResultSet pravila;

		String clanskiBroj = "";

		if (data != null)
			data.removeAllElements();

		for (int i = 0; i < search.length(); i++) {
			if (search.toCharArray()[i] >= '0' && search.toCharArray()[i] <= '9')
				clanskiBroj += search.toCharArray()[i];
		}
		if (clanskiBroj.length() == 0)
			clanskiBroj = "-1";
		try {
			String runSP = "{ call pisg2.SearchClan(?,?) }";
			CallableStatement callableStatement = conn.getDbConnection().prepareCall(runSP);

			callableStatement.setString(1, search);
			callableStatement.setInt(2, Integer.parseInt(clanskiBroj));

			callableStatement.executeQuery();
			ResultSet rs = callableStatement.getResultSet();

			int i = 0;
			data = new Vector<>();

			while (rs.next()) {
				data.add(new Vector<>());
				data.get(i).add(rs.getInt("Cl_ClanskiBroj") + "");
				data.get(i).add(rs.getString("Cl_ImePrezime"));

				kolicinaIznajmljenogMaterijala = stmt
						.executeQuery("SELECT COUNT(*) FROM [Istorija izdavanja] WHERE Cl_ID='" + rs.getString("Cl_ID")
								+ "' AND II_Datum_vracanja IS NULL");

				if (kolicinaIznajmljenogMaterijala.next()) {
					data.get(i).add(kolicinaIznajmljenogMaterijala.getInt(1) + "");
					izdatoTemp = kolicinaIznajmljenogMaterijala.getInt(1);
				} else {
					data.get(i).add(0 + "");
					izdatoTemp = 0;
				}

				kolicinaRezervisanogMaterijala = stmt
						.executeQuery("SELECT COUNT(*) FROM RezervisaniMaterijal WHERE Cl_ID=" + rs.getString("Cl_ID"));

				if (kolicinaRezervisanogMaterijala.next()) {
					data.get(i).add(kolicinaRezervisanogMaterijala.getInt(1) + "");
					rezervisanoTemp = kolicinaRezervisanogMaterijala.getInt(1);
				} else {
					data.get(i).add(0 + "");
					rezervisanoTemp = 0;
				}

				pravila = stmt.executeQuery("SELECT * FROM Pravila WHERE GR_ID=" + gradjaID);

				if (pravila.next()) {
					if (pravila.getInt("PI_KolicinaIzdatih") < izdatoTemp)
						data.get(i).add("DA");
					else
						data.get(i).add("NE");

					if (pravila.getInt("PI_KolicinaRezervisanih") < rezervisanoTemp)
						data.get(i).add("DA");
					else
						data.get(i).add("NE");
				} else {
					pravila = stmt.executeQuery("SELECT * FROM Pravila WHERE Organizaciona_jedinica_ID="
							+ rs.getInt("Organizaciona_jedinica_ID"));
					if (pravila.next()) {
						if (pravila.getInt("PI_KolicinaIzdatih") < izdatoTemp)
							data.get(i).add("DA");
						else
							data.get(i).add("NE");

						if (pravila.getInt("PI_KolicinaRezervisanih") < rezervisanoTemp)
							data.get(i).add("DA");
						else
							data.get(i).add("NE");
					} else {
						data.get(i).add("Ne postoji ogranièenje");
						data.get(i).add("Ne postoji ogranièenje");
					}
				}
				i++;
			}

			if (!update) {
				if (data.size() > 0)
					rezervisiIzdajGradjuView.update(data, columnNames, tableListener);
			} else {
				rezervisiIzdajGradjuView.update(data, columnNames, tableListener);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void izdajRezervisiGradju(boolean izdavanje) {
		String PSTipOznaka = "";
		String OJTipzOznaka = "";
		String DRZoznaka = "";
		int PS_ID = -1;
		int TC_ID = -1;
		int OJ_ID = -1;
		int CL_ID = -1;
		int KG_ID = -1;

		Date date = Date.valueOf(LocalDate.now());

		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM Clan WHERE Cl_ClanskiBroj=" + clanskiBrojTemp);

			while (rs.next()) {
				PSTipOznaka = rs.getString("ORG_PS_Tip_oznaka");
				OJTipzOznaka = rs.getString("OJ_Tip_oznaka");
				DRZoznaka = rs.getString("Drzava_oznaka");
				PS_ID = rs.getInt("Poslovni_sistem_ID");
				OJ_ID = rs.getInt("Organizaciona_jedinica_ID");
				CL_ID = rs.getInt("Cl_ID");
				TC_ID = rs.getInt("TC_ID");
			}

			if (CL_ID != -1) {
				rs = stmt.executeQuery(
						"SELECT KG_ID FROM KonkretnaGradja WHERE KG_Izdato = 0 AND KG_Rezervisano = 0 AND GR_ID="
								+ gradjaID);
				while (rs.next()) {
					KG_ID = rs.getInt("KG_ID");
					break;
				}

				if (izdavanje) {
					if (KG_ID != -1) {
						String runSP = "{ call pisg2.IzdavanjeGradje(?,?,?,?,?,?,?,?,?,?) }";
						conn.getDbConnection().createStatement();
						CallableStatement callableStatement = conn.getDbConnection().prepareCall(runSP);
						callableStatement.setString(1, PSTipOznaka);
						callableStatement.setString(2, OJTipzOznaka);
						callableStatement.setInt(3, PS_ID);
						callableStatement.setString(4, DRZoznaka);
						callableStatement.setInt(5, OJ_ID);
						callableStatement.setInt(6, TC_ID);
						callableStatement.setInt(7, CL_ID);
						callableStatement.setInt(8, KG_ID);
						callableStatement.setDate(9, date);
						callableStatement.setByte(10, (byte) 0);

						callableStatement.execute();
						JOptionPane.showMessageDialog(rezervisiIzdajGradjuView, "Artikal je uspješno izdat!");

						if (rezervisiIzdajGradjuView.getSearch().getText().length() > 0)
							searchKorisnik(rezervisiIzdajGradjuView.getSearch().getText(), true);
						else
							searchKorisnik("*", true);
					} else {
						JOptionPane.showMessageDialog(rezervisiIzdajGradjuView,
								"Nema artikala dostupnih za izdavanje!");
					}
				} else {
					if (KG_ID != -1) {
						String runSP = "{ call pisg2.RezervisanjeGradje(?,?,?,?,?,?,?,?,?) }";
						conn.getDbConnection().createStatement();
						CallableStatement callableStatement = conn.getDbConnection().prepareCall(runSP);
						callableStatement.setString(1, PSTipOznaka);
						callableStatement.setString(2, OJTipzOznaka);
						callableStatement.setInt(3, PS_ID);
						callableStatement.setString(4, DRZoznaka);
						callableStatement.setInt(5, OJ_ID);
						callableStatement.setInt(6, TC_ID);
						callableStatement.setInt(7, CL_ID);
						callableStatement.setInt(8, KG_ID);
						callableStatement.setDate(9, date);

						callableStatement.execute();
						JOptionPane.showMessageDialog(rezervisiIzdajGradjuView, "Artikal je uspješno rezervisan!");
						
						if (rezervisiIzdajGradjuView.getSearch().getText().length() > 0)
							searchKorisnik(rezervisiIzdajGradjuView.getSearch().getText(), true);
						else
							searchKorisnik("*", true);
					} else {
						JOptionPane.showMessageDialog(rezervisiIzdajGradjuView,
								"Nema artikala dostupnih za rezervisanje!");
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("resource")
	private void ukloniRezervacijuIzdavanje(boolean izdavanje) {
		ResultSet rs;
		int CL_ID = -1;
		int KG_ID = -1;

		try {
			rs = stmt.executeQuery("SELECT Cl_ID FROM Clan WHERE Cl_ClanskiBroj=" + clanskiBrojTemp);
			while (rs.next()) {
				CL_ID = rs.getInt("Cl_ID");
			}
			if (izdavanje == false) {
				if (CL_ID != -1) {
					rs = stmt.executeQuery("SELECT KG_ID FROM RezervisaniMaterijal WHERE Cl_ID=" + CL_ID);
					while (rs.next()) {
						KG_ID = rs.getInt("KG_ID");
						break;
					}
					if (KG_ID != -1) {
						stmt.execute("DELETE FROM RezervisaniMaterijal WHERE Cl_ID=" + CL_ID);
						stmt.execute("UPDATE KonkretnaGradja SET KG_Rezervisano = 0 WHERE KG_ID=" + KG_ID);
						JOptionPane.showMessageDialog(rezervisiIzdajGradjuView, "Uspješno uklonjena rezervacija!");

						if (rezervisiIzdajGradjuView.getSearch().getText().length() > 0)
							searchKorisnik(rezervisiIzdajGradjuView.getSearch().getText(), true);
						else
							searchKorisnik("*", true);
					} else {
						JOptionPane.showMessageDialog(rezervisiIzdajGradjuView,
								"Korisnik nema rezervisanih artikala ove graðe!");
					}
				}

			} else if (izdavanje == true) {
				if (CL_ID != -1) {
					rs = stmt.executeQuery(
							"SELECT [Istorija izdavanja].Konkretna_gradja_ID, KonkretnaGradja.GR_ID FROM [Istorija izdavanja]\r\n"
									+ "INNER JOIN KonkretnaGradja ON KonkretnaGradja.KG_ID = [Istorija izdavanja].Konkretna_gradja_ID  WHERE CL_ID = "
									+ CL_ID + " AND II_Datum_vracanja IS NULL");
					while (rs.next()) {
						if (rs.getInt("GR_ID") == gradjaID) {
							KG_ID = rs.getInt("Konkretna_gradja_ID");
							break;
						}
					}
					if (KG_ID != -1) {
						stmt.execute("UPDATE [Istorija izdavanja] SET II_Placeno=1, II_Datum_vracanja=CAST('"
								+ Date.valueOf(LocalDate.now()) + "' AS DATETIME) WHERE Cl_ID=" + CL_ID
								+ " AND Konkretna_gradja_ID=" + KG_ID);
						stmt.execute("UPDATE KonkretnaGradja SET KG_Izdato = 0 WHERE KG_ID=" + KG_ID);
						JOptionPane.showMessageDialog(rezervisiIzdajGradjuView, "Uspješno vraæen artikal!");

						if (rezervisiIzdajGradjuView.getSearch().getText().length() > 0)
							searchKorisnik(rezervisiIzdajGradjuView.getSearch().getText(), true);
						else
							searchKorisnik("*", true);
					} else {
						JOptionPane.showMessageDialog(rezervisiIzdajGradjuView,
								"Korisnik nema iznajmljenih artikala ove graðe!");
					}
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
