package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import controller.AboutController;

public class AboutView extends JDialog  {

	private Box box;
	
	public AboutView() {
		setLayout(new BorderLayout());
		box = Box.createVerticalBox();
		
		add(box, BorderLayout.CENTER);
		
		new AboutController(this);
		JLabel label = new JLabel("<html><h1><center>O nama</center></h1><p>"
				+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
				+ " Ovo je aplikacija <strong>Multiteka</strong>, raðena od strane grupe studenata èetvrte godine<br/>"
				+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
				+ "Elektrotehnièkog fakulteta Istoèno Sarajevo za potrebe projekta iz predmeta<br>"
				+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
				+ " Projektovanje informacionih sistema.<br/><br/>"
				+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
				+ "Tim:<br/>"
				+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
				+ "Projekt menadžer: Dejan Èanèar<br/>"
				+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
				+ "Èlanovi tima: Boris Boškoviæ, Marina Zubac, Nemanja Ðokiæ, Kristina Kraljeviæ</p></html>");
		setSize(new Dimension(500, 300));
		box.add(label);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);

	}
	
}
