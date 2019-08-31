package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import model.InsertUpdateModel;
import model.TableFormModel;
import view.InsertUpdateView;
import view.TableToolbar;

public class TableToolbarController implements ActionListener {

	private TableFormModel model;
	private TableToolbar view;

	public TableToolbarController(TableFormModel model, TableToolbar view) {
		this.model = model;
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		switch (e.getActionCommand()) {
		case "insert":
			new InsertUpdateView(new InsertUpdateModel(model.getTableDataModel(), InsertUpdateModel.INSERT));
			model.getTableDataModel().notifyAllObservers();
			break;
		case "update":
			try {
				if(model.getTableDataModel().getSelectedIndex()==null)
					throw new NullPointerException();
				new InsertUpdateView(new InsertUpdateModel(model.getTableDataModel(), InsertUpdateModel.UPDATE));
				model.getTableDataModel().notifyAllObservers();				
			} catch (NullPointerException e2) {
				String message = "Molimo vas izaberite red koji želite mijenjati.";
				JOptionPane.showMessageDialog(view, message, "Red nije izabran", JOptionPane.INFORMATION_MESSAGE);
			}
			break;
		case "delete":
			try {
				model.getTableDataModel().deleteEntry();
			} catch (NullPointerException e1) {
				String message = "Molimo vas izaberite red koji želite brisati.";
				JOptionPane.showMessageDialog(view, message, "Red nije izabran", JOptionPane.INFORMATION_MESSAGE);
			} catch (SQLException e2) {
				String warningMessage = "Nije moguæe obrisati unos.\nRazlog je vjerovatno taj što se dati unos koristi u drugim tabelama.";
				JOptionPane.showMessageDialog(view, warningMessage, "Brisanje nije moguæe",
						JOptionPane.INFORMATION_MESSAGE);
			}
			break;
		case "refresh":

			break;
		}
	}

}
