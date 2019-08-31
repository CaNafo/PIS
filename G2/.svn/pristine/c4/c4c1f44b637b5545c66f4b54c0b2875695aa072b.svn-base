package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import model.KorisnikModel;

public class StatusBarView extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public JTextPane textPane;
	
	public StatusBarView() {

		setLayout(new BorderLayout());

		textPane = new JTextPane();
		textPane.setText("");
		textPane.setEditable(false);
		textPane.setFont(new Font("Consolas", Font.BOLD, 12));
		
		textPane.setForeground(Color.decode("#1B3E1B"));
		
		add(textPane);

		JScrollPane scrollPane = new JScrollPane(textPane);
	
		add(scrollPane,BorderLayout.CENTER);
	}
		
	public JTextPane getTextPane() {
		return textPane;
	}

	public void setTextPane(JTextPane textPane) {
		this.textPane = textPane;
	}
	
	public void clearTextPane() {
	  textPane.setText("");	
	}
	
	public void addTxtToTextPane(String txt) {
		textPane.setText(textPane.getText()+" "+txt);
	}
	
	
}
