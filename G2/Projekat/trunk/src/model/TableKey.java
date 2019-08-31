package model;

import java.util.ArrayList;

public class TableKey {
	private String name;
	private ArrayList<TableColumn> columns;
	private String parentTable;
	
	public TableKey(String name) {
		this.name=name;
		columns=new ArrayList<>();
	}

	@Override
	public String toString() {
		String s="Key: "+name+"\tColumns:\n";
				for(TableColumn column:columns)
					s+=column.toString()+"\n";
		return s;
	}
	
	public String getName() {
		return name;
	}

	public ArrayList<TableColumn> getColumns() {
		return columns;
	}
	
	public String getParentTable() {
		return parentTable;
	}
	
	public void setParentTable(String parrentTable) {
		this.parentTable = parrentTable;
	}
}
