package controller;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.TableFormModel;
import view.TableView;

public class TableSelectionController implements ListSelectionListener {

	private TableFormModel model;
	private TableView view;

	public TableSelectionController(TableFormModel model, TableView view) {
		this.model = model;
		this.view = view;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		model.getTableDataModel().setSelectedIndex(view.getjTable().getSelectedRow());
	}
}
