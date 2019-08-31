package view;

import java.awt.event.MouseListener;
import java.util.EventListener;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import application.MainWindow;
import controller.StabloController;
import model.TableFormModel;
import model.XMLTreeModel;
import renderers.StabloCellRenderer;

 public class StabloView extends JScrollPane
{

	private static final long serialVersionUID = 1L;
	
	public StabloView(MainWindow mainVindow, StatusBarView statusBarView)
	{
		setBorder(BorderFactory.createEmptyBorder(5, 5, 2, 2));
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Database");
		
		JTree stablo = new JTree(new XMLTreeModel(root, "files/multiteka.xml"));
		
		stablo.getSelectionModel().setSelectionMode (TreeSelectionModel.SINGLE_TREE_SELECTION);
		
		EventListener el = new StabloController(mainVindow, statusBarView);
		
		stablo.addTreeSelectionListener((TreeSelectionListener) el);
		stablo.addMouseListener((MouseListener) el);
		
		stablo.setCellRenderer(new StabloCellRenderer());
		
		setViewportView(stablo);
	}
	
}
