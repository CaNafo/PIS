package view;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

import controller.TableToolbarController;
import model.TableFormModel;

@SuppressWarnings("serial")
public class TableToolbar extends JToolBar {
	private JButton insert;
	private JButton update;
	private JButton delete;
	private JButton refresh;

	private TableFormModel model;
	
	public TableToolbar(TableFormModel model) {
		this.model=model;
		
		insert = new JButton();
		update = new JButton();
		delete = new JButton();
		//refresh = new JButton();
		
		insert.setToolTipText("Insert");
		update.setToolTipText("Update");
		delete.setToolTipText("Delete");
		//refresh.setToolTipText("Refresh");
		
		insert.setIcon(resizeImage("icons/insert.png",20,20));
		update.setIcon(resizeImage("icons/update.png",20,20));
		delete.setIcon(resizeImage("icons/delete.png",20,20));
		//refresh.setIcon(resizeImage("icons/refresh.png",20,20));
		
		insert.setActionCommand("insert");
		update.setActionCommand("update");
		delete.setActionCommand("delete");
		//refresh.setActionCommand("refresh");
		
		add(insert);
		add(update);
		add(delete);
		//add(refresh);
		
		TableToolbarController controller = new TableToolbarController(model, this);
		
		insert.addActionListener(controller);
		update.addActionListener(controller);
		delete.addActionListener(controller);
		//refresh.addActionListener(controller);
	}
	
	public TableFormModel getModel() {
		return model;
	}
	
	private ImageIcon resizeImage(String location,int h, int w) {
		Toolkit toolkit=Toolkit.getDefaultToolkit();
		Image img = toolkit.getImage(location);
		Image newimg = img.getScaledInstance(w, h,  java.awt.Image.SCALE_SMOOTH);
		
		return new ImageIcon(newimg);
	}
}
