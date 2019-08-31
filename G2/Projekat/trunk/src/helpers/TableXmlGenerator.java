package helpers;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class TableXmlGenerator {
	private String xml="<!--XML table parser by PISG2 2019-->\n";
	DatabaseMetaData databaseMetaData;
	DatabaseConnection conn;
	
	public TableXmlGenerator() {
		conn = DatabaseConnection.getInstance();
		try {
			databaseMetaData = conn.getDbConnection().getMetaData();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getTable(String tableName, String schema) {
		
		
		
		xml="<table name=\""+tableName+"\">\n";
		Vector<String> PK_Keys = new Vector<>();
		
		try {
			
			ResultSet columns = databaseMetaData.getColumns(null,schema, tableName, null);
			while(columns.next())
			{
			    String columnName = columns.getString("COLUMN_NAME");
			    String datatype = columns.getString("DATA_TYPE");
			    String columnsize = columns.getString("COLUMN_SIZE");
			    String isNullable = columns.getString("IS_NULLABLE");
			    String is_autoIncrment = columns.getString("IS_AUTOINCREMENT");
			    
			    switch (datatype) {
			    case "-7":  datatype="BIT";
			    	break;
			    case "-6":	datatype="TINYINT";
			    	break;
			    case "-5":	datatype="BIGINT";
			    	break;
			    case "-4":	datatype="LONGVARBINARY";
			    	break;
			    case "-3":	datatype="VARBINARY";
			    	break;
			    case "-2":	datatype="BINARY";
			    	break;
			    case "-1":	datatype="LONGVARCHAR";
			    	break;
			    case "0":	datatype="NULL";
			    	break;
			    case "1":	datatype="CHAR";
			    	break;
			    case "2":	datatype="NUMERIC";
			    	break;
			    case "3":	datatype="DECIMAL";
			    	break;
			    case "4":	datatype="INTEGER";
		    		break;
			    case "5":	datatype="SMALLINT";
		    		break;
			    case "6":	datatype="FLOAT";
			    	break;
			    case "7":	datatype="REAL";
			    	break;
			    case "8":	datatype="DOUBLE";
			    	break;
			    case "12":	datatype="VARCHAR";  
			   		break;
			    case "91":	datatype="DATE";
			    	break;
			    case "92":	datatype="TIME";
			    	break;
			    case "93":	datatype="TIMESTAMP";
			    	break;	
			    case "1111": 	datatype="OTHER";
			    	break;
				}
			    
			    if(columnName.toUpperCase().contains("NAZIV") || columnName.toUpperCase().contains("PREZIME") || columnName.toUpperCase().contains("KORISNICKOIME")) {
			    	if(datatype.equals("VARCHAR") || datatype.equals("CHAR")) {
				    	xml+="<column name=\""+columnName+"\" data_type=\""+datatype+"\" null=\""+isNullable+"\" autoincrement=\""+is_autoIncrment+"\" isName=\"true\" length=\""+columnsize+"\" />\n";
				    }else
				    	xml+="<column name=\""+columnName+"\" data_type=\""+datatype+"\" null=\""+isNullable+"\" autoincrement=\""+is_autoIncrment+"\" isName=\"true\" />\n";
			    }else {
			    	if(datatype.equals("VARCHAR") || datatype.equals("CHAR")) {
			    		xml+="<column name=\""+columnName+"\" data_type=\""+datatype+"\" null=\""+isNullable+"\" autoincrement=\""+is_autoIncrment+"\" isName=\"false\" length=\""+columnsize+"\" />\n";
				    }else
			    		xml+="<column name=\""+columnName+"\" data_type=\""+datatype+"\" null=\""+isNullable+"\" autoincrement=\""+is_autoIncrment+"\" isName=\"false\" />\n";
			    }
			    
			    
			    
			}
			
			ResultSet PK = databaseMetaData.getPrimaryKeys(null,schema, tableName);
			
			boolean firstPKattempt = true;
			while(PK.next())
			{
				if(firstPKattempt) {
					xml+="<pk name=\""+PK.getString("PK_NAME")+"\">\n";
					firstPKattempt = false;
				}
			    xml+="<column name=\""+PK.getString("COLUMN_NAME")+"\" />\n";
			    PK_Keys.add(PK.getString("COLUMN_NAME"));

			}			 
			if(!firstPKattempt)
				xml+="</pk>\n";

			ResultSet FK = databaseMetaData.getImportedKeys(null, schema, tableName);

			boolean firstFKattempt = true;
			String tempFK = "";
			
			while(FK.next())
			{
					if(tempFK.length()>0 && !tempFK.equals(FK.getString("FK_NAME"))) {
						xml+="</fk>\n";
						firstFKattempt = true;
					}
					if(firstFKattempt) {
						tempFK = FK.getString("FK_NAME");
						xml+="<fk name=\""+FK.getString("FK_NAME")+"\" parent_table=\""+FK.getString("PKTABLE_NAME")+"\" >\n";
						firstFKattempt = false;
					}
					
					xml+="<column parent_name=\""+FK.getString("PKCOLUMN_NAME")+"\" child_name=\""+FK.getString("FKCOLUMN_NAME")+"\" />\n";		    
				    
			}
			if(!firstFKattempt)
				xml+="</fk>\n";
			
			xml = getReference(xml,PK_Keys,schema, tableName);
			
			xml+="</table>\n";	
			
		} catch (SQLException e) {

			e.printStackTrace();
		}
		
		return xml;
		
	}
	
	private String getReference(String xml,Vector<String> PK, String schema, String tableName) {
		
		Vector<String> FK_temp = new Vector<>();
		String FK_name = "";
		String tableNameTemp = "";
		try {
			
			ResultSet resultSet = databaseMetaData.getTables(null, schema, null, new String[]{"TABLE"});
			boolean firstPKattempt = true;
			
			while(resultSet.next()){

			    if(!tableName.toUpperCase().equals(resultSet.getString("TABLE_NAME").toUpperCase())) {
				    ResultSet FK = databaseMetaData.getImportedKeys(null, schema, resultSet.getString("TABLE_NAME"));
					    
				    while(FK.next())
					{			
				    	if(FK_name.length() > 0 && !FK_name.equals(FK.getString("FK_NAME"))) {
				    		if(PK.size() <= FK_temp.size()) {
				    			int tempCount = 0;
				    			for(int i=0; i < PK.size(); i++) {
				    				for(int j=0; j<FK_temp.size();j++) {
				    					if(PK.get(i).equals(FK_temp.get(j))) {
				    						tempCount++;
				    						break;
				    					}else if((++j)==FK_temp.size()) {
				    						i = PK.size();
				    						break;
				    					}
				    				}
				    			}
				    			if(tempCount == PK.size()) {
				    				xml+="<reference table_name=\""+tableNameTemp+"\">\n";
				    				for(int i=0;i<FK_temp.size();i++) {
				    					xml+="<column name=\""+FK_temp.get(i)+"\" />\n";
				    				}
				    				xml+="</reference>\n";
				    			}
				    			FK_temp.removeAllElements();
				    		}else {
				    			FK_temp.removeAllElements();				    			
				    		}
				    		firstPKattempt = true;
				    	}
				    	if(firstPKattempt) {
				    		tableNameTemp = resultSet.getString("TABLE_NAME");
				    		FK_name = FK.getString("FK_NAME");
				    		firstPKattempt = false;
				    	}

			    		FK_temp.add(FK.getString("PKCOLUMN_NAME"));	   
					}
				    	
				    firstPKattempt = true;
			    }
			    
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		
		
		return xml;
	}

}
