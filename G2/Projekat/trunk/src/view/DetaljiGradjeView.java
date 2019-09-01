package view;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import controller.DetaljiGradjeController;


public class DetaljiGradjeView extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel title;
	private JScrollPane scrollPane;
	private Box box;
	
	public DetaljiGradjeView(int IDgradje) {
		setLayout(new BorderLayout());
		setLookAndFeel();
		
		box = Box.createVerticalBox();
		box.setBorder(BorderFactory.createLineBorder(new Color(16, 160, 222),0,true));

		scrollPane = new JScrollPane(box);	
		add(scrollPane,BorderLayout.CENTER);
	 	setBackground(Color.decode("#006683"));
	 	
	 	new DetaljiGradjeController(this, IDgradje);

	 	javax.swing.SwingUtilities.invokeLater(new Runnable() {
	 	   public void run() { 
	 	       scrollPane.getVerticalScrollBar().setValue(0);
	 	   }
	 	});
	 	
	 	openJDialog();
	}
	
	 private JPanel addJPanel(String imeAtributa, Vector<String> atributi) {
		 	JPanel panel = new JPanel();
		 	panel.setMaximumSize(new Dimension(450,600));

		 	Box box = Box.createVerticalBox();
		 	panel.setLayout(new BorderLayout());		 	
		 	title = new JLabel(imeAtributa);
			title.setHorizontalAlignment(SwingConstants.LEFT);
			Font titleFont = new Font("Segoe UI", Font.PLAIN, 18);
			title.setFont(titleFont);
			title.setForeground(Color.decode("#006683"));
			title.setBorder(BorderFactory.createLineBorder(Color.WHITE,2,true));
			panel.add(title, BorderLayout.NORTH);
			JTextPane atribut = new JTextPane();
			atribut.setEditable(false);
			Font atrFont = new Font("Segoe UI", Font.PLAIN, 16);
			atribut.setFont(atrFont);
			atribut.setForeground(new Color(0, 102, 131));
			atribut.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));
			String atr_temp = "";
			for(int i=0;i<atributi.size();i++) {
				atr_temp+=atributi.get(i)+"\n";
			}
			atribut.setText(atr_temp);
			box.add(atribut);

			panel.add(box);
			
			return panel;
	 }
	 
	 private void addTitle(String naslov) {
		 	title = new JLabel(naslov);
			title.setHorizontalAlignment(SwingConstants.CENTER);
			title.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
			
			Font titleFont = new Font("Segoe UI", Font.BOLD, 20);
			title.setFont(titleFont);
			title.setForeground(Color.WHITE);
			add(title, BorderLayout.NORTH);
	 }
	 
	 public void uptadeDetalji(String nazivGradje, Vector<String> tipAtributa, Vector<Vector<String>> atribut, String imgName) {
		 	addTitle(nazivGradje);
		 	setImage(imgName);
		 	
		 	String naziv = "";
		 	Vector<String> atributiTemp = new Vector<>();
		 	
		 	for(int i = 0; i < tipAtributa.size(); i++) {
				naziv = tipAtributa.get(i);
				for(int k =0; k<atribut.get(i).size();k++) {
					atributiTemp.add(atribut.get(i).get(k));
				}
				if(atributiTemp.size()>0)
					box.add(addJPanel(naziv.toUpperCase(),atributiTemp));
				atributiTemp.removeAllElements();
			}	 	
	 }
	 
	 private void setImage(String imgName) {
		 JPanel panel = new JPanel(){
		        @Override
		        protected void paintComponent(Graphics grphcs) {
		            super.paintComponent(grphcs);
		            Graphics2D g2d = (Graphics2D) grphcs;
		            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		                    RenderingHints.VALUE_ANTIALIAS_ON);
		            GradientPaint gp = new GradientPaint(0, 0,
		                    getBackground().brighter().brighter(), 0, getHeight(),
		                    getBackground().darker().darker());
		            g2d.setPaint(gp);
		            g2d.fillRect(0, 0, getWidth(), getHeight()); 

		        }

		    };
	 	 panel.setLayout(new BorderLayout());
	 	 
		 BufferedImage image;
			try {
				File file = new File("images\\"+imgName+".jpg");
				if(file.exists()) {
					image = ImageIO.read(file);
				}else
					image = ImageIO.read(new File("images\\default.jpg"));
			    BufferedImage rounded = makeRoundedCorner(image, 0);
			 
				JLabel label = new JLabel();
				label.setIcon(new ImageIcon(rounded));
				label.setHorizontalAlignment(SwingConstants.CENTER);
				
				panel.add(label, BorderLayout.NORTH);
				panel.setBackground(Color.red);
				panel.setBackground(new Color(0,102,131));
				panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
				this.box.add(panel, CENTER_ALIGNMENT);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 }
	 
	 public static BufferedImage makeRoundedCorner(BufferedImage image, int cornerRadius) {
		    int w = image.getWidth();
		    int h = image.getHeight();
		    BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

		    Graphics2D g2 = output.createGraphics();

		    g2.setComposite(AlphaComposite.Src);
		    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		    g2.setColor(Color.WHITE);
		    g2.fill(new RoundRectangle2D.Float(0, 0, w, h, cornerRadius, cornerRadius));

		    g2.setComposite(AlphaComposite.SrcAtop);
		    g2.drawImage(image, 0, 0, null);

		    g2.dispose();

		    return output;
		}
	 
	 @Override
     protected void paintComponent(Graphics grphcs) {
         super.paintComponent(grphcs);
         Graphics2D g2d = (Graphics2D) grphcs;
         g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                 RenderingHints.VALUE_ANTIALIAS_ON);
         GradientPaint gp = new GradientPaint(0, 0,
                 getBackground().brighter().brighter(), 0, getHeight(),
                 getBackground().darker().darker());
         g2d.setPaint(gp);
         g2d.fillRect(0, 0, getWidth(), getHeight()); 

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

	 private void openJDialog() {
			JDialog detalji = new JDialog();
			detalji.setMaximumSize(new Dimension(450,600));
			detalji.setTitle("Detalji gra�e");
			detalji.setDefaultCloseOperation(detalji.DISPOSE_ON_CLOSE);
			detalji.setSize(1024,500);
			detalji.setMinimumSize(new Dimension(450, 600));
			detalji.add(this);
			detalji.setVisible(true);
			detalji.setLocationRelativeTo(null);
		}
}