package helpers;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class WriteXMLtoFile {
	
	/**		
	 * Klasa slu�i za upis XML struktura svih tabela iz proslije�ene �eme u folder resources
	 * @param schema 
	 * 
	 */
	public WriteXMLtoFile(String schema) {
		writeToResources(schema);
	}
	
	private void writeToResources(String schema) {
		DatabaseConnection conn = DatabaseConnection.getInstance();
		DatabaseMetaData databaseMetaData;
		Vector<String> tableList = new Vector<>();
		try {
			
			databaseMetaData = conn.getDbConnection().getMetaData();
			ResultSet resultSet = databaseMetaData.getTables(null, schema, null, new String[]{"TABLE"});
						
			TableXmlGenerator parser = new TableXmlGenerator();
			PrintWriter writer;
			
			while(resultSet.next()){
				
				tableList.add(resultSet.getString("TABLE_NAME"));				
				
			}
			
			for(int i=0;i < tableList.size(); i++) {
				try {
					writer = new PrintWriter("resources/"+tableList.get(i)+".xml", "UTF-8");
					writer.write(parser.getTable(tableList.get(i),schema));
					writer.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		
	}
}
