package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import controller.LoginController;
import model.KorisnikModel;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class LoginView extends JDialog {

private static final long serialVersionUID = 1L;
	
	private JButton btnLogin;
	private JButton x;
	
	private final JPanel contentPanel = new JPanel(){
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
	private JTextField username;
	private JPasswordField password;

    public LoginView() {
    
      Font titleFont = new Font("Segoe UI", Font.PLAIN, 16);
      setLookAndFeel();
    	
      setBounds(100, 100, 450, 300);
      setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
      setUndecorated(true);
      setLocationRelativeTo(null);
      
      getContentPane().setLayout(new BorderLayout());

      getContentPane().add(contentPanel, BorderLayout.CENTER);
      
      x = new JButton();
      x.setActionCommand("close");
      x.setFont(new Font("Segoe UI", Font.BOLD, 20));
      x.setForeground(Color.WHITE);
      x.setMargin(new Insets(0,0,0,0));
      x.setBounds(5, 5, 50, 50); 
      x.setContentAreaFilled(false);
      x.setFocusPainted(false);
      x.setText("X");
      
      x.addMouseListener(new java.awt.event.MouseAdapter() {
  	    public void mouseEntered(java.awt.event.MouseEvent evt) {
  	        x.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
  	    }

  	});
      
      contentPanel.add(x);
      
      contentPanel.setLayout(null);
      {
        JLabel lblUsername = new JLabel("Ime");
        lblUsername.setBounds(105, 108, 63, 20);
        lblUsername.setFont(titleFont);
        lblUsername.setForeground(Color.WHITE);
        contentPanel.add(lblUsername);
      }
      {
        JLabel lblPassword = new JLabel("�ifra");
        lblPassword.setBounds(105, 152, 63, 20);
        lblPassword.setFont(titleFont);
        lblPassword.setForeground(Color.WHITE);
        contentPanel.add(lblPassword);
      }
      
      username = new JTextField();
  
      username.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
      username.setBounds(173, 103, 165, 30);
      contentPanel.add(username);
      username.setColumns(10);
     
      
      
      
      password = new JPasswordField();
      password.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
      // password.setBorder(BorderFactory.createLineBorder(Color.BLACK,2,true));
      password.setBounds(173, 146, 165, 30);
      contentPanel.add(password);
      
      btnLogin = new JButton("Prijava");
      btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 15));
      btnLogin.setBounds(195, 210, 120, 40);
      btnLogin.setForeground(Color.WHITE);
     btnLogin.setContentAreaFilled(false);
     btnLogin.setBorder( new LineBorder(Color.WHITE,2) );

     btnLogin.addMouseListener(new java.awt.event.MouseAdapter() {
    	    public void mouseEntered(java.awt.event.MouseEvent evt) {
    	        btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    	    }

    	});
      
      contentPanel.add(btnLogin);
      
      JLabel lblLogin = new JLabel("Prijava na sistem");
      lblLogin.setFont(new Font("Segoe UI", Font.BOLD, 18));
      lblLogin.setForeground(Color.WHITE);
      lblLogin.setHorizontalAlignment(SwingConstants.CENTER);
      lblLogin.setBounds(124, 20, 230, 23);
      contentPanel.add(lblLogin);

      KorisnikModel korisnikModel = KorisnikModel.getInstance();
      new LoginController(this, korisnikModel);
      setVisible(true);

      contentPanel.setBackground(new Color(0,191,255));
      username.grabFocus();
    }
    
    public void setActionListener(ActionListener actionListener, KeyListener keyListener) {
    	btnLogin.addActionListener(actionListener);
    	x.addActionListener(actionListener);
    	password.addKeyListener(keyListener);
    }

	public JTextField getUsername() {
		return username;
	}

	public void setUsername(JTextField username) {
		this.username = username;
	}

	public JPasswordField getPassword() {
		return password;
	}

	public void setPassword(JPasswordField password) {
		this.password = password;
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