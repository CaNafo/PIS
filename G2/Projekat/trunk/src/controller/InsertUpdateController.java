package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import model.InsertUpdateModel;
import view.InsertUpdateView;
import view.TableFieldInterface;

public class InsertUpdateController implements ActionListener, KeyListener {

	private InsertUpdateModel model;
	private InsertUpdateView view;

	public InsertUpdateController(InsertUpdateModel model, InsertUpdateView view) {
		this.model = model;
		this.view = view;

		view.getOK_button().addActionListener(this);
		view.getCANCEL_button().addActionListener(this);
		view.getContentPane().addKeyListener(this);

		for (TableFieldInterface field : view.getFields()) {
			field.getFieldComponent().addKeyListener(this);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_ESCAPE:
			view.dispose();
			break;
		case KeyEvent.VK_ENTER:
			if (!checkRequirements()) {
				String message = "Molimo vas da popunite sva polja koja su oznaèena kao obavezna.";
				JOptionPane.showMessageDialog(view, message, "Nedovoljno podataka", JOptionPane.WARNING_MESSAGE);
			} else {
				model.getResultsFromField(view.getFields());
				try {
					model.executeQuery();
					model.getTable().notifyAllObservers();
					// TODO Ispisati na konzoli poruku o unosu
					view.dispose();
				} catch (SQLException e1) {
					String message = "Došlo je do greške prilikom unosa ili izmjene.";
					JOptionPane.showMessageDialog(view, message, "Neuspješno.", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
			}
		default:
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "ok":
			if (!checkRequirements()) {
				String message = "Molimo vas da popunite sva polja koja su oznaèena kao obavezna.";
				JOptionPane.showMessageDialog(view, message, "Nedovoljno podataka", JOptionPane.WARNING_MESSAGE);
			} else {
				model.getResultsFromField(view.getFields());
				try {
					model.executeQuery();
					model.getTable().notifyAllObservers();
					// TODO Ispisati na konzoli poruku o unosu
					view.dispose();
				} catch (SQLException e1) {
					String message = "Došlo je do greške prilikom unosa ili izmjene.";
					JOptionPane.showMessageDialog(view, message, "Neuspješno.", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
			}
			break;
		case "cancel":
			view.dispose();
			break;
		default:
			break;
		}
	}

	private Boolean checkRequirements() {
		Boolean statusOK = true;
		for (TableFieldInterface field : view.getFields()) {
			if (!field.isNullable() && field.isEmpty()) {
				statusOK = false;
			}
		}
		return statusOK;
	}

}
