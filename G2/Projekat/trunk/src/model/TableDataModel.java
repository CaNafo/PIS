package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;
import javax.swing.table.AbstractTableModel;

import dbManipulation.DBManipulation;
import helpers.DatabaseConnection;
import helpers.TableXmlParser;
import view.ObserverInterface;

@SuppressWarnings("serial")
public class TableDataModel extends AbstractTableModel implements SubjectInterface, ComboBoxModel<Object> {

	private ArrayList<ObserverInterface> views;

	private String name;
	private ArrayList<TableColumn> columns;
	private TableKey primaryKey;
	private ArrayList<TableKey> foreignKeys;
	private Vector<Vector<Object>> data;
	private Vector<String> columnDisplayNames;

	private Vector<Vector<Object>> rawData;

	private Integer selectedRow = null;
	private Integer nameIndex = null;

	// TODO Za sada filter nije iskoristen
	private String filter = "";

	public TableDataModel(String name) {
		this.name = name;
		columns = new ArrayList<>();
		foreignKeys = new ArrayList<>();
		columnDisplayNames = new Vector<>();
		views = new ArrayList<>();
	}

	public TableColumn getColumnByName(String name) {
		for (TableColumn column : columns)
			if (column.getName().equals(name))
				return column;
		return null;
	}

	public TableKey getForeignKeyByName(String name) {
		for (TableKey fk : foreignKeys)
			if (fk.getName().equals(name))
				return fk;
		return null;
	}

	@Override
	public String toString() {
		String s = "Table: " + name + "; Columns:\n";
		for (TableColumn column : columns) {
			s += column.toString() + "\n";
		}
		s += "Primary key:\n" + primaryKey.toString() + "Foreign Keys:\n";
		for (TableKey fKey : foreignKeys)
			s += fKey.toString();
		return s;
	}

	public String getName() {
		return name;
	}

	public Vector<Vector<Object>> getData() {
		return data;
	}

	public ArrayList<TableColumn> getColumns() {
		return columns;
	}

	public TableKey getPrimaryKey() {
		return primaryKey;
	}

	public ArrayList<TableKey> getForeignKeys() {
		return foreignKeys;
	}

	public void setPrimaryKey(TableKey primaryKey) {
		this.primaryKey = primaryKey;
	}

	@Override
	public int getColumnCount() {
		return columnDisplayNames.size();
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return data.get(rowIndex).get(columnIndex);
	}

	public void fetchData() {
		boolean isFirst = true;
		String query = "SELECT ";
		columnDisplayNames.clear();

		// Izbor vlastitih kolona
		for (TableColumn column : columns) {
			Boolean isFK = false;
			for (TableKey fk : foreignKeys) {
				for (TableColumn fkColumn : fk.getColumns()) {
					if (column.getName().equals(fkColumn.getName()))
						isFK = true;
				}
			}
			Boolean isPK = false;
			for (TableColumn col : primaryKey.getColumns()) {
				if (column.equals(col))
					isPK = true;
			}
			if (!isFK || (primaryKey.getColumns().size() == 1 && isPK)) {
				if (column.getName().toString().toLowerCase().contains("obrisano"))
					continue;
				if (isFirst) {
					query += "[" + name + "].[" + column.getName() + "]";
					isFirst = false;
				} else
					query += ", [" + name + "].[" + column.getName() + "]";
				columnDisplayNames.add(column.getName());
			}
		}

		// Izbor kolona preko stranih klju�eva
		for (TableKey fk : foreignKeys) {
			String nameField = "";
			TableDataModel parrentTable = new TableXmlParser().getTable(fk.getParentTable());
			for (TableColumn column : parrentTable.getColumns()) {
				if (column.getIsName())
					nameField = column.getName();
			}
			if (isFirst) {
				query += "[" + fk.getName() + "].[" + nameField + "] AS " + "[" + fk.getName() + "]";
				isFirst = false;
			} else
				query += ", [" + fk.getName() + "].[" + nameField + "] AS " + "[" + fk.getName() + "]";
			columnDisplayNames.add(fk.getName().substring(3, fk.getName().length()));

		}
		query += " FROM [" + this.getName() + "] ";

		// Join
		for (TableKey fk : foreignKeys) {
			query += " LEFT JOIN [" + fk.getParentTable() + "] [" + fk.getName() + "] ON ";
			Boolean isFirstInJoin = true;
			for (TableColumn column : fk.getColumns()) {
				if (isFirstInJoin) {
					query += "[" + getName() + "].[" + column.getName() + "]=[" + fk.getName() + "].["
							+ column.getParrentName() + "]";
					isFirstInJoin = false;
				} else
					query += " AND [" + getName() + "].[" + column.getName() + "]=[" + fk.getName() + "].["
							+ column.getParrentName() + "]";
			}
		}

		query += filter;

		DBManipulation DBM = DatabaseConnection.getInstance();
		ResultSet rSet = null;
		try {
			Statement statement = DBM.getDbConnection().createStatement();
			rSet = statement.executeQuery(query);
			int i = 0;
			data = new Vector<>();
			while (rSet.next()) {
				data.add(new Vector<>());
				for (int j = 0; j < columnDisplayNames.size(); j++) {
					data.get(i).add(rSet.getObject(j + 1));
				}
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public void fetchRawData() {
		String query = "SELECT * FROM [" + this.getName() + "] ";

		ResultSet rSet = null;
		try {
			Statement statement = DatabaseConnection.getInstance().getDbConnection().createStatement();
			rSet = statement.executeQuery(query);
			int i = 0;
			rawData = new Vector<>();
			while (rSet.next()) {
				rawData.add(new Vector<>());
				for (int j = 0; j < columns.size(); j++) {
					rawData.get(i).add(rSet.getObject(j + 1));
				}
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getColumnName(int arg0) {
		return columnDisplayNames.get(arg0);
	}

	public Vector<String> getColumnDisplayNames() {
		return columnDisplayNames;
	}

	public void setData(Vector<Vector<Object>> data) {
		this.data = data;
	}

	public void setColumnDisplayNames(Vector<String> columnDisplayNames) {
		this.columnDisplayNames = columnDisplayNames;
	}

	public void updateSelection() {
		if (selectedRow >= data.size() - 1)
			selectedRow = null;
	}

	public Integer getSelectedIndex() {
		return selectedRow;
	}

	public void setSelectedIndex(Integer selectedIndex) {
		this.selectedRow = selectedIndex;
	}

	@Override
	public void addObserver(ObserverInterface observer) {
		views.add(observer);
	}

	@Override
	public void removeObserver(ObserverInterface observer) {
		if (views.size() > 0)
			for (ObserverInterface oi : views)
				if (oi == observer) {
					views.remove(observer);
					break;
				}
	}

	@Override
	public void notifyAllObservers() {
		for (ObserverInterface observer : views)
			observer.update();
	}

	public String makeWhereClause() throws NullPointerException {
		if (getSelectedIndex() == null)
			throw new NullPointerException();
		fetchRawData();
		String where = " WHERE ";
		Boolean isFirst = true;

		for (int i = 0; i < columns.size(); i++) {
			for (int j = 0; j < primaryKey.getColumns().size(); j++) {
				if (columns.get(i).getName().equals(primaryKey.getColumns().get(j).getName())) {
					String colName = columns.get(i).getName();
					String colValue = "";
					try {
						colValue = "'" + rawData.get(selectedRow).get(i).toString() + "'";
						if (isFirst) {
							where += "[" + colName + "]=" + colValue;
							isFirst = false;
						} else
							where += " AND [" + colName + "]=" + colValue;
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}
		}
		return where;
	}

	public void deleteEntry() throws NullPointerException, SQLException {
		String query = "DELETE FROM [" + getName() + "]" + makeWhereClause();
		Statement statement = null;
		statement = DatabaseConnection.getInstance().getDbConnection().createStatement();
		statement.execute(query);

		updateSelection();
		notifyAllObservers();
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object getElementAt(int index) {
		if (nameIndex == null)
			for (TableColumn column : columns)
				if (column.getIsName())
					for (int i = 0; i < columnDisplayNames.size(); i++)
						if (columnDisplayNames.get(i).equals(column.getName()))
							nameIndex = i;

		selectedRow = index;
		return data.get(index).get(nameIndex);
	}

	@Override
	public int getSize() {
		return data.size();
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object getSelectedItem() {
		if (selectedRow != null) {
			return data.get(selectedRow).get(nameIndex);
		} else
			return null;
	}

	public Object getSelectedItem(int column) {
		if (selectedRow != null) {
			return data.get(selectedRow).get(column);
		} else
			return null;
	}

	@Override
	public void setSelectedItem(Object anItem) {
		// TODO implement
	}

}