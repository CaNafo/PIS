package renderers;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;


public class StabloCellRenderer extends DefaultTreeCellRenderer
{
	Icon dbIcon = new ImageIcon("icons/database-icon.png");
	Icon packageIcon = new ImageIcon("icons/package-icon.png");
	Icon tableIcon = new ImageIcon("icons/table-icon.png");
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean sel, boolean expanded, boolean leaf, int row,
			boolean hasFocus)
	{
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
				row, hasFocus);
		if (leaf) 
		{
			setIcon(tableIcon);
		}
		else if (tree.getModel().getRoot() == value) 
		{
			setIcon(dbIcon);
		}
		else 
		{
			setIcon(packageIcon);
		}
		
		return this;
	}

}
