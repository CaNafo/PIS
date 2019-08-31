package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import model.TableDataModel;
import model.TableFormModel;
import view.TableView;

public class TableKeyListener implements KeyListener {
	
	private TableView view;
	private TableDataModel model;
	
	public TableKeyListener(TableView view, TableDataModel model) {
		this.view = view;
		this.model = model;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		switch (arg0.getKeyCode()) {
		case KeyEvent.VK_DELETE:
			try {
				model.deleteEntry();
			} catch (NullPointerException e1) {
				String message = "Molimo vas izaberite red koji želite brisati ili mijenjati.";
				JOptionPane.showMessageDialog(view, message, "Red nije izabran", JOptionPane.INFORMATION_MESSAGE);
			} catch (SQLException e2) {
				String warningMessage = "Nije moguæe obrisati unos.\nRazlog je vjerovatno taj što se dati unos koristi u drugim tabelama.";
				JOptionPane.showMessageDialog(view, warningMessage, "Brisanje nije moguæe",
						JOptionPane.INFORMATION_MESSAGE);
			}
			break;

		default:
			break;
		}
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
