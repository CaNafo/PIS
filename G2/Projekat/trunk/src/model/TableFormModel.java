package model;

import java.util.ArrayList;

import helpers.TableXmlParser;
import view.ObserverInterface;

public class TableFormModel implements SubjectInterface {

	private TableDataModel tableDataModel;
	private ArrayList<ObserverInterface> views;

	public TableFormModel(String tableName) {
		TableXmlParser parser = new TableXmlParser();
		this.tableDataModel = parser.getTable(tableName);
		views = new ArrayList<>();
		tableDataModel.fetchData();
	}

	public void setTableDataModel(TableDataModel tableDataModel) {
		this.tableDataModel = tableDataModel;
	}

	public void changeTableDataModel(String tableName) {
		TableXmlParser parser = new TableXmlParser();
		this.tableDataModel = null;
		this.tableDataModel = parser.getTable(tableName);
		views = new ArrayList<>();
		tableDataModel.fetchData();
	}
	
	public TableDataModel getTableDataModel() {
		return tableDataModel;
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
		for(ObserverInterface observer:views)
			observer.update();
	}
	
}
