package model;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Model JTree komponente, koji se inicijalno puni iz XML fajla parsiranog pomocu DOM parsera.
 * @author Dijana
 *
 */
public class XMLTreeModel extends DefaultTreeModel
{
	private static final long serialVersionUID = 1L;
	
	Vector<Paket> paketi = new Vector<Paket>();

	public XMLTreeModel(TreeNode root, String filepath)
	{
		super(root);
		try
		{
		File fXmlFile = new File(filepath);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		
		Document doc = dBuilder.parse(fXmlFile);
		doc.getDocumentElement().normalize();

		NodeList paketi = doc.getDocumentElement().getElementsByTagName("package");
		
		for (int i = 0; i < paketi.getLength(); i++)
		{
			Node cvor = paketi.item(i);
			if (cvor.getNodeType() == Node.ELEMENT_NODE)
			{

				Element el = (Element) cvor;
								
				Paket pomPaket = new Paket();
				
				pomPaket.setIme(el.getAttribute("name"));
				
				Vector<StabloTabelaModel> pom = new Vector<StabloTabelaModel>();
				
				NodeList tabele = el.getElementsByTagName("table");
				
				for (int j = 0; j < tabele.getLength(); j++)
				{
					pom.add(new StabloTabelaModel(((Element)tabele.item(j)).getAttribute("code"), ((Element)tabele.item(j)).getAttribute("name")));
				}

				pomPaket.setTabele(pom);
				this.paketi.add(pomPaket);
			}
		}
		
		}
		catch (ParserConfigurationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (SAXException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public boolean isLeaf(Object node)
	{
		if (node == getRoot())
		{
			return false;
		} else
		{
			for (int i = 0; i < paketi.size(); i++)
			{
				if (paketi.elementAt(i) == node)
				{
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public int getChildCount(Object parent)
	{
		if (parent == getRoot()) 
		{
			return paketi.size();
		}
		for (int i = 0; i < paketi.size(); i++)
		{
			if (parent == paketi.elementAt(i))
			{
					return paketi.elementAt(i).getTabele().size();
				
			}
		}
		return 0;
	}

	@Override
	public Object getChild(Object parent, int index)
	{
		
		for (int i = 0; i < paketi.size(); i++)
		{
			if (paketi.get(i) == parent)
			{
				return paketi.elementAt(i).getTabele().elementAt(index);
			}
		}
			if (parent == getRoot())
			{

				return paketi.elementAt(index);

			}
		
		return null;
	}


}
