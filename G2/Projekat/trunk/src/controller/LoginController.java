package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import application.MainWindow;
import helpers.DatabaseConnection;
import model.KorisnikModel;
import view.LoginView;

public class LoginController {
	
	private DatabaseConnection conn;
	private Statement stmt;
	
	private String username;
	private String password;
	private LoginView login;
	private KorisnikModel korisnikModel;
	
	public LoginController(LoginView login, KorisnikModel korisnikModel) {

		this.korisnikModel = korisnikModel;
		conn = DatabaseConnection.getInstance();
		try {
			stmt = conn.getDbConnection().createStatement();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Provjerite vezu sa internetom!");
		}
		login.setActionListener(loginActionListener, keyListener);
		this.login = login;
	}
	
	KeyListener keyListener = new KeyListener() {
		
		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void keyPressed(KeyEvent e) {
			 if(e.getKeyCode() == 10) {
				 username = login.getUsername().getText().trim();
					password = String.valueOf(login.getPassword().getPassword());
					if(username.length()>0 && password.length()>0) {
						checkLogin();
					}else {
						JOptionPane.showMessageDialog(null, "Popunite oba polja pa pokušajte opet!");
					}
	            }
			
		}
	};
	
	ActionListener loginActionListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {	
			
			if(e.getActionCommand().equals("close")) {
				login.dispose();
			}else {
				username = login.getUsername().getText().trim();
				password = String.valueOf(login.getPassword().getPassword());
				if(username.length()>0 && password.length()>0) {
					checkLogin();
				}else {
					JOptionPane.showMessageDialog(null, "Popunite oba polja pa pokušajte opet!");
				}
			}
			
		}
	};
	
	private void checkLogin() {
		String tempPass="";
		String tempEmail="";
		int ID=-1;
		int temp_PS_ID = -1;
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM Korisnik WHERE [KOR_KorisnickoIme]='"+username+"'");
			boolean korisnikExist = false;
			while (rs.next()) {
				  ID = rs.getInt("KOR_ID");
				  tempPass = rs.getString("KOR_LOZINKA");
				  tempEmail = rs.getString("KOR_email");
				  temp_PS_ID = rs.getInt("Poslovni_sistem_ID");
				  
				  korisnikExist = true;
				}

			password = korisnikModel.passwordHash(password);
			
			if(!korisnikExist) {
				JOptionPane.showMessageDialog(null, "Korisnik ne postoji!");
			}else {
				if(tempPass.equals(password)) {
					JOptionPane.showMessageDialog(null, "Uspješno logovanje!");
					

					korisnikModel.setID_PoslovnogSitema(temp_PS_ID);
					korisnikModel.setID(ID);
					korisnikModel.setKorisnickoIme(username);
					korisnikModel.setEmail(tempEmail);
					korisnikModel.setListaDozvola(korisnikModel.getListaDozvola(ID));
					korisnikModel.insertLog(ID);
					login.dispose();
					new MainWindow();
				}else {
					JOptionPane.showMessageDialog(null, "Pogrešna šifra!");
				}
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Provjerite vezu sa internetom!");
		}
	}
	
	
	
}
