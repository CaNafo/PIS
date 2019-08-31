package application;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import model.KorisnikModel;

import model.TableFormModel;
import view.DetaljiGradjeView;
import view.MenuBarView;
import view.RezervisiIzdajGradjuView;
import view.SearchView;
import view.StabloView;
import view.StatusBarView;
import view.TableToolbar;
import view.TableView;

public class MainWindow extends JFrame {


	private static final long serialVersionUID = 1L;
	
	private MenuBarView menuBarView;
	private TableFormModel tableModel;
	private TableView tableView;
	private JPanel tablePanel;
	private TableToolbar tableToolbar;
	private JSplitPane jSplitPane;
	private JButton radSaGradjom;
	private JButton detaljiGradje;
	
	private StatusBarView statusBarView;

	
	private String currentTable;

	public MainWindow() {
		setLayout(new BorderLayout());
		statusBarView = new StatusBarView();
		statusBarView.addTxtToTextPane("Uspješno ste se prijavili kao "+KorisnikModel.getInstance().getKorisnickoIme()+".");
	
		setIconImage(Toolkit.getDefaultToolkit().getImage("icons"+File.separator+"book_icon.png"));
		
		setExtendedState(MAXIMIZED_BOTH);
		tablePanel = new JPanel();
		
		radSaGradjom = new JButton("Rad sa graðom");
		radSaGradjom.setActionCommand("radsaGradjom");
		radSaGradjom.addActionListener(gradjaActionListener);
		
		detaljiGradje = new JButton("Detalji");
		detaljiGradje.setActionCommand("detaljiGradje");
		detaljiGradje.addActionListener(gradjaActionListener);
		setLookAndFeel();

		menuBarView = new MenuBarView();
		
		setJMenuBar(menuBarView);
		jSplitPane = new JSplitPane(SwingConstants.VERTICAL);
		jSplitPane.add(addStablo(this));
		jSplitPane.add(tablePanel);

		setTitle("Multiteka");
		setSize(1024, 768);
		setMinimumSize(new Dimension(800, 600));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		JSplitPane statusPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,jSplitPane,statusBarView);
		statusPane.setResizeWeight(0.85); 
		
		add(statusPane, BorderLayout.CENTER);
		setLocationRelativeTo(null);

	}

	private StabloView addStablo(MainWindow mainVindow) {
		StabloView stabloView = new StabloView(mainVindow, statusBarView);
		return stabloView;
	}

	public void addTable(String name) {
		currentTable = name;
		
		if (tableView == null) {
			tablePanel.setLayout(new BorderLayout());
			tableModel = new TableFormModel(name);
			tableView = new TableView(tableModel);
			tableToolbar = new TableToolbar(tableModel);
			tableToolbar.setFloatable(false);

			if (!name.equals("Gradja")) {
				tableModel.changeTableDataModel(name);
				tableView = new TableView(tableModel);
				tablePanel.add(tableToolbar, BorderLayout.NORTH);
				tablePanel.add(tableView, BorderLayout.CENTER);
			} else {
				tableModel.changeTableDataModel(name);
				tableView = new TableView(tableModel);
				tablePanel.add(tableToolbar, BorderLayout.NORTH);
				SearchView gradjaView = new SearchView(tableView, tableModel);
				tablePanel.add(gradjaView, BorderLayout.CENTER);
				
				JPanel buttonPanel = new JPanel();
				buttonPanel.setLayout(new GridLayout(1, 4, 4, 4));
				buttonPanel.add(radSaGradjom);
				buttonPanel.add(detaljiGradje);
				
				buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
				tablePanel.add(buttonPanel,BorderLayout.SOUTH);
			}
		} else {
			tablePanel.removeAll();
			if (!name.equals("Gradja")) {
				tableModel.changeTableDataModel(name);
				tableView = new TableView(tableModel);
				tablePanel.add(tableToolbar, BorderLayout.NORTH);
				tablePanel.add(tableView, BorderLayout.CENTER);
			} else {
				tableModel.changeTableDataModel(name);
				tableView = new TableView(tableModel);
				tablePanel.add(tableToolbar, BorderLayout.NORTH);
				SearchView gradjaView = new SearchView(tableView, tableModel);
				tablePanel.add(gradjaView, BorderLayout.CENTER);
				
				JPanel buttonPanel = new JPanel();
				buttonPanel.setLayout(new GridLayout(1, 4, 4, 4));
				buttonPanel.add(radSaGradjom);
				buttonPanel.add(detaljiGradje);
				
				buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
				tablePanel.add(buttonPanel,BorderLayout.SOUTH);
			}
			repaint();
			revalidate();
		}
	}
	

	private void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} catch (InstantiationException e) {

			e.printStackTrace();
		} catch (IllegalAccessException e) {

			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {

			e.printStackTrace();
		}
	}
	
	public String getCurrentTable() {
		return currentTable;
	}
	
	ActionListener gradjaActionListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {

			if(tableModel.getTableDataModel().getSelectedItem(0)!=null)
				switch (e.getActionCommand()) {
				case "radsaGradjom":
					new RezervisiIzdajGradjuView(Integer.parseInt(tableModel.getTableDataModel().getSelectedItem(0).toString()) );
					break;
				case "detaljiGradje":
					new DetaljiGradjeView(Integer.parseInt(tableModel.getTableDataModel().getSelectedItem(0).toString()));
					break;
				default:
					break;
				}
			else
				JOptionPane.showMessageDialog(null, "Molimo Vas da odaberete graðu iz tabele pa ponovite operaciju.");			
		}
	};
}



