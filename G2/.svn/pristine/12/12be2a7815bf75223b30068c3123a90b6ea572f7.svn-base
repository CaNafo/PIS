package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import helpers.DatabaseConnection;
import view.DetaljiGradjeView;

public class DetaljiGradjeController {
	private DetaljiGradjeView detaljiGradjeView;
	private DatabaseConnection conn;
	private Statement stmt;
	
	public DetaljiGradjeController(DetaljiGradjeView detaljiGradjeView, int gradjaID) {
		
		conn = DatabaseConnection.getInstance();
		try {
			stmt = conn.getDbConnection().createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.detaljiGradjeView = detaljiGradjeView;
		getDetaljigradje(gradjaID);
	}
	
	private void getDetaljigradje(int gradjaID) {
		String nazivGradje = "";
		String nazivTipaGradje = "";
		int TG_ID = -1;
		Vector<Integer> setAtributa = new Vector<>();
		Vector<String> tipAtributa = new Vector<>();
		Vector<Vector<String>> atribut = null;
		int brojAtributa = 0;
		try {
			ResultSet rs = stmt.executeQuery("SELECT GR_Naziv, TG_ID FROM Gradja WHERE GR_ID="+gradjaID);
			
			while(rs.next()) {
				nazivGradje = rs.getString("GR_Naziv");
				TG_ID = rs.getInt("TG_ID");
			}
			
			if(TG_ID != -1) {
				rs = stmt.executeQuery("SELECT TG_Naziv FROM TipGradje WHERE TG_ID="+TG_ID);
				while(rs.next()) {
					nazivTipaGradje = rs.getString("TG_Naziv");
				}
			}
				
			
			if(TG_ID != -1) {
				rs = stmt.executeQuery("SELECT TA_ID FROM [Set Atributa] WHERE TG_ID="+TG_ID);
				while(rs.next()) {
					setAtributa.add(rs.getInt("TA_ID"));
				}
			}
			
			if(setAtributa.size()>0) {
				for(int i=0;i<setAtributa.size();i++) {
					rs = stmt.executeQuery("SELECT TA_Naziv FROM TipAtributa WHERE TA_ID="+setAtributa.get(i));
					while(rs.next()) {
						tipAtributa.add(rs.getString("TA_Naziv"));
					}
				}
			}
			
			if(setAtributa.size()>0) {
				atribut = new Vector<>();
				atribut.add(new Vector<>());
				
				for(int i=0; i<setAtributa.size();i++) {
					if(i!=0 && setAtributa.get(i)!=setAtributa.get(i-1)) {
						atribut.add(new Vector<>());
						brojAtributa++;
					}
					rs = stmt.executeQuery("SELECT AG_Sadrzaj FROM AtributGradje WHERE Tip_atributa_ID="+setAtributa.get(i)+" AND GR_ID="+gradjaID);
					while(rs.next()) {
						atribut.get(brojAtributa).add(rs.getString("AG_Sadrzaj"));
					}
				}
			}
			
			detaljiGradjeView.uptadeDetalji(nazivGradje+" - "+nazivTipaGradje.toUpperCase(), tipAtributa, atribut, nazivGradje);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
