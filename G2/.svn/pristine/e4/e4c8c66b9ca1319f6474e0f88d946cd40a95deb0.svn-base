package view;

import java.awt.BorderLayout;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.DocumentListener;

import controller.RezervisiIzdajGradjuController;
import controller.SearchGradjaController;
import model.TableFormModel;

public class SearchView extends JPanel{

	private static final long serialVersionUID = 1L;

	private JTextField search;
	private TableView tableView;
	
	public SearchView(TableView tableView, TableFormModel tableFromModel) {
		setLayout(new BorderLayout());
		setLookAndFeel();
		
		this.tableView = tableView;
		search = new JTextField();
		search.setText("Unesite pojam za pretragu");
		add(search,BorderLayout.NORTH);
		search.setColumns(10);
		add(tableView,BorderLayout.CENTER);
		
		setVisible(true);

		new SearchGradjaController(this, tableFromModel);
	}
	
	public void addListeners(DocumentListener documentListener, MouseListener mouseListener) {
		search.getDocument().addDocumentListener(documentListener);
		search.addMouseListener(mouseListener);
	}

	public JTextField getSearch() {
		return search;
	}
	
	public void updateTable() {
		remove(tableView);
		add(tableView,BorderLayout.CENTER);
		revalidate();
	}

	private void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
