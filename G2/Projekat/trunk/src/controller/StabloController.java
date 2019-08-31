package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

import application.MainWindow;
import model.KorisnikModel;
import model.StabloTabelaModel;
import model.TableDataModel;
import model.TableFormModel;
import view.StatusBarView;


public class StabloController extends MouseAdapter implements
		TreeSelectionListener
{
	Object node = null;
	JTree tree = null;
	MainWindow mainVindow;
	StatusBarView statusBarView;

	public StabloController(MainWindow mainVindow, StatusBarView statusBarView) {
		this.statusBarView = statusBarView;
		this.mainVindow = mainVindow;
	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		tree = (JTree) e.getSource();
		node = tree.getLastSelectedPathComponent();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (tree != null) {
			int row = tree.getRowForLocation(e.getX(), e.getY());

			if (row == -1) {
				tree.clearSelection();
			} else {
				TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
				tree.setSelectionPath(selPath);
			}
		}
		if (e.getSource() instanceof JTree) {
			if (e.getClickCount() == 2) {
				boolean imaDozvolu = false;
				String listaTabela = "Imate pristup:\n";
				try {
					String tabela = ((StabloTabelaModel) node).getKod();
					KorisnikModel korisnikModel = KorisnikModel.getInstance();
					for (int i = 0; i < korisnikModel.getListaDozvola().size(); i++) {
						if (tabela.equals(korisnikModel.getListaDozvola().get(i))) {
							mainVindow.addTable(tabela);
							imaDozvolu = true;
							break;
						}
					}
					if (!imaDozvolu) {
						korisnikModel = KorisnikModel.getInstance();
						for (int i = 0; i < korisnikModel.getListaDozvola().size(); i++) {
							listaTabela +=":> "+korisnikModel.getListaDozvola().get(i) + "\n";
						}
						JOptionPane.showMessageDialog(null, "Nemate dozvolu za pristp ovoj tebeli!");
						statusBarView.clearTextPane();
						statusBarView.addTxtToTextPane(listaTabela);

					}
				} catch (Exception e2) {

				}

			} else if (e.isMetaDown()) {
				

			}
		}
	}

}
