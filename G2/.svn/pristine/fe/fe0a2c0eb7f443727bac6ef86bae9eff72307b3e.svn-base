package helpers;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import model.TableColumn;
import model.TableDataModel;
import model.TableKey;

public class TableXmlParser extends DefaultHandler {
	private TableDataModel table = null;
	private String columnType = "";
	private int currentFK = 0;
	private String lastFkName = "";
	private String lastTableName = "";

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		switch (qName) {
		case "table":
			table = new TableDataModel(attributes.getValue("name"));
			break;
		case "column":
			if (columnType == "") {
				String name = attributes.getValue("name");
				String data_type = attributes.getValue("data_type");
				Boolean isNullable = (attributes.getValue("null").equals("YES")) ? true : false;
				Boolean isAutoincrement = (attributes.getValue("autoincrement").equals("YES")) ? true : false;
				Boolean isName = (attributes.getValue("isName").equals("true")) ? true : false;
				TableColumn temp = new TableColumn(name, data_type, isNullable, isAutoincrement, isName);
				Integer length = null;
				try {
					length = Integer.valueOf(attributes.getValue("length"));					
					temp.setLength(length);
				} catch (Exception e) {
					//Za tipove koji nemaju duzinu length ce ostati null
				}
				table.getColumns().add(temp);
			} else if (columnType == "fk") {
//				table.getColumnByName(attributes.getValue("child_name"))
//						.setParrentName(attributes.getValue("parent_name"));
//				table.getForeignKeys().get(currentFK).getColumns()
//						.add(table.getColumnByName(attributes.getValue("child_name")));	

				TableColumn temp = new TableColumn(attributes.getValue("child_name"), null, null, false, false);
				temp.setParrentName(attributes.getValue("parent_name"));
				table.getForeignKeyByName(lastFkName).getColumns().add(temp);
			} else if (columnType == "pk") {
				table.getPrimaryKey().getColumns().add(table.getColumnByName(attributes.getValue("name")));
			}
			break;
		case "pk":
			columnType = "pk";
			table.setPrimaryKey(new TableKey(attributes.getValue("name")));
			break;
		case "fk":
			lastFkName = attributes.getValue("name");
			columnType = "fk";
			table.getForeignKeys().add(new TableKey(attributes.getValue("name")));
			table.getForeignKeyByName(attributes.getValue("name")).setParentTable(attributes.getValue("parent_table"));
			break;
		case "reference":
			columnType = "reference";

			break;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (columnType != "" && qName != "column")
			columnType = "";
		if (qName == "fk")
			currentFK++;
	}

	@Override
	public void endDocument() throws SAXException {
		for(TableKey fKey :table.getForeignKeys()) {
			for(TableColumn fkColumn : fKey.getColumns()) {
				for(TableColumn column:table.getColumns()) {
					if(fkColumn.getName().equals(column.getName()))
						fkColumn.setIsNullable(column.getIsNull());
				}
			}
		}
	}

	private void parse(String tableName) {
		SAXParserFactory parserFactory = SAXParserFactory.newInstance();
		parserFactory.setValidating(false);
		parserFactory.setNamespaceAware(false);
		try {
			SAXParser parser = parserFactory.newSAXParser();
			parser.parse("resources" + File.separator + tableName + ".xml", this);
		} catch (ParserConfigurationException | org.xml.sax.SAXException | IOException e) {
			e.printStackTrace();
		}
	}

	public TableDataModel getTable(String tableName) {
		if (table == null && lastTableName != tableName)
			parse(tableName);
		lastTableName = tableName;
		return table;
	}
}
