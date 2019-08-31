package model;

import java.sql.SQLException;
import java.util.Vector;

import helpers.DatabaseConnection;
import helpers.NameValuePair;
import view.TableFieldInterface;

public class InsertUpdateModel {

	public static final int INSERT = 1;
	public static final int UPDATE = 2;

	private TableDataModel table;
	private Vector<TableColumn> columns;
	private Vector<TableKey> foreignKeys;
	private int formTask;
	private Vector<NameValuePair> results;

	public InsertUpdateModel(TableDataModel table, int formTask) {
		this.table = table;
		this.formTask = formTask;
		columns = new Vector<>();
		foreignKeys = new Vector<>();

		for (TableColumn tableColumn : table.getColumns()) {
			Boolean isInFK = false;
			Boolean isInPK = false;
			for (TableKey fk : table.getForeignKeys()) {
				for (TableColumn fkColumn : fk.getColumns())
					if (tableColumn.getName().equals(fkColumn.getName()))
						isInFK = true;
			}
			for (TableColumn pkColumn : table.getPrimaryKey().getColumns()) {
				if (tableColumn.getName().equals(pkColumn.getName()))
					isInPK = true;
			}
			Boolean isOnlyColumnInPK = (table.getPrimaryKey().getColumns().size() == 1 && isInPK) ? true : false;

			if ((!isInFK || isOnlyColumnInPK) && !tableColumn.getIsAutoincrement())
				columns.add(tableColumn);
		}
		for (TableKey fKey : table.getForeignKeys()) {
			foreignKeys.add(fKey);
		}
	}

	public TableDataModel getTable() {
		return table;
	}

	public Vector<TableColumn> getColumns() {
		return columns;
	}

	public Vector<TableKey> getForeignKeys() {
		return foreignKeys;
	}

	public int getFormTask() {
		return formTask;
	}

	public void getResultsFromField(Vector<TableFieldInterface> fields) {
		results = new Vector<>();
		for (TableFieldInterface field : fields) {
			for (NameValuePair nameValue : field.getValues()) {
				Boolean isAlreadyAdded = false;
				for (NameValuePair result : results) {
					if (nameValue.getName().equals(result.getName()))
						isAlreadyAdded = true;
				}
				if (!isAlreadyAdded)
					results.add(nameValue);
			}
		}

	}

	public String makeInsertQuery() {
		String query = "INSERT INTO [" + table.getName() + "] (";
		Boolean isFirst = true;
		for (NameValuePair nv : results) {
			if (isFirst) {
				query += "[" + nv.getName() + "]";
				isFirst = false;
			} else
				query += ", [" + nv.getName() + "]";
		}
		query += ") VALUES (";
		isFirst = true;
		for (NameValuePair nv : results) {
			if (isFirst) {
				query += nv.getValue();
				isFirst = false;
			} else {
				query += ", " + nv.getValue();
			}
		}
		query += ");";
		return query;
	}

	public String makeUpdateQuery() {
		String query = "UPDATE [" + table.getName() + "] SET ";
		Boolean isFirst = true;

		for (NameValuePair nv : results) {
			if (isFirst) {
				query += "[" + nv.getName() + "]=" + nv.getValue();
				isFirst = false;
			} else {
				query += ", [" + nv.getName() + "]=" + nv.getValue();
			}
		}
		query += " " + table.makeWhereClause();

		return query;
	}

	public void executeQuery() throws SQLException {
		try {
			if (formTask == INSERT)
				DatabaseConnection.getInstance().getDbConnection().createStatement().execute(makeInsertQuery());
			else if(formTask==UPDATE)
				DatabaseConnection.getInstance().getDbConnection().createStatement().execute(makeUpdateQuery());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
