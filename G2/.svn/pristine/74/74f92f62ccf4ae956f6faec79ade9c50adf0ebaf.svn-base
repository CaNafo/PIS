package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import helpers.DatabaseConnection;
import model.TableDataModel;
import model.TableFormModel;
import view.SearchView;
import view.TableView;

public class SearchGradjaController {

	private TableFormModel tableFromModel;
	private SearchView gradjaView;
	private DatabaseConnection conn;	
	private Statement stmt;
	private Vector<Vector<Object>> data;
	private Vector<String> columnDisplayNames;
	
	private boolean firstTextAttempt = true;
	
	public SearchGradjaController(SearchView gradjaView, TableFormModel tableFromModel) {
		this.tableFromModel = tableFromModel;
		
		conn = DatabaseConnection.getInstance();
		try {
			stmt = conn.getDbConnection().createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.gradjaView = gradjaView;
		gradjaView.addListeners(documentListener,mouseListener);
		searchGradja("");
	}
	
	MouseListener mouseListener = new MouseListener() {
		
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			if(firstTextAttempt == true) {
				firstTextAttempt = false;
				gradjaView.getSearch().setText("");
			}
			
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
			
			searchGradja(gradjaView.getSearch().getText());			
		}
		
		@Override
		public void insertUpdate(DocumentEvent e) {

			searchGradja(gradjaView.getSearch().getText());	

		}
		
		@Override
		public void changedUpdate(DocumentEvent e) {
			
		}
	};
	
	private void searchGradja(String search) {
		if(data!=null)
			data.removeAllElements();
		String runSP = "{ call pisg2.SearchGradja(?) }";
		String statement=" WHERE GR_Naziv LIKE '%";
		
		for(int i = 0; i<search.length();i++) {
			if(search.toCharArray()[i]!=' ') {
				statement+=search.toCharArray()[i];
			}else if(search.toCharArray()[i]==' ' && (i+1)!=search.length() && search.toCharArray()[i+1]!=' ') {
				statement+="%' AND GR_Naziv LIKE '%";
			}
		}
		statement+="%'";
		
		/* try {
			 CallableStatement callableStatement = conn.getDbConnection().prepareCall(runSP);
			 callableStatement.setString(1, statement);
			 
			 callableStatement.executeQuery();
			 ResultSet rs = callableStatement.getResultSet();
			 
			 data = new Vector<>();
			 columnDisplayNames = new Vector<>();
			 
			 
			 columnDisplayNames .add("ID");
			 columnDisplayNames .add("Naziv");
			 columnDisplayNames .add("Količina");
			 columnDisplayNames .add("Dostupno");
			 
			 int i = 0; 
			 while(rs.next()) {				 
				 ResultSet konkretnaGradjaUkupno = stmt.executeQuery("SELECT COUNT(*) FROM KonkretnaGradja WHERE GR_ID='"+rs.getString("GR_ID")+"'");
				 data.add(new Vector<>());
				 data.get(i).add(rs.getString("GR_ID"));
				 data.get(i).add(rs.getString("GR_Naziv"));	 
				 
				 if(konkretnaGradjaUkupno.next()) {
					 data.get(i).add(konkretnaGradjaUkupno.getInt(1));

				 }else {
					 data.get(i).add(0);
				 }
				 
				 ResultSet konkretnaGradjaNaStanju= stmt.executeQuery("SELECT COUNT(*) FROM KonkretnaGradja WHERE GR_ID='"+rs.getString("GR_ID")+"' AND KG_Rezervisano = 0 AND KG_Izdato = 0");
					
				 if(konkretnaGradjaNaStanju.next()) {
					 data.get(i).add(konkretnaGradjaNaStanju.getInt(1));;
				 }else {
					 data.get(i).add(0);
				 }
				 i++;
			 }		 
			 
			 
			 TableDataModel dataModel = tableFromModel.getTableDataModel();
			 dataModel.setData(data);
			 dataModel.setColumnDisplayNames(columnDisplayNames);

			 tableFromModel.setTableDataModel(dataModel);
			 
			 gradjaView.updateTable();

			 			 
		} catch (SQLException e) {

			e.printStackTrace();
		}*/
		tableFromModel.getTableDataModel().setFilter(statement);
		tableFromModel.getTableDataModel().fetchData();
		 gradjaView.updateTable();
	}
}
