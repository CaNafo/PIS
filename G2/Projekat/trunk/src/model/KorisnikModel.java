package model;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import com.microsoft.sqlserver.jdbc.SQLServerResultSet;

import helpers.DatabaseConnection;

public class KorisnikModel {
	
	private static KorisnikModel korisnikModel = null;
	
	private static DatabaseConnection conn;
	private static Statement stmt;
	
	private static int ID;
	private static int ID_PoslovnogSitema;

	private static String korisnickoIme;
	private static String email;
	private static Vector<String> listaDozvola = new Vector<>();
	
	private KorisnikModel() {
		conn = DatabaseConnection.getInstance();
		try {
			stmt = conn.getDbConnection().createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static KorisnikModel getInstance() {
		if(korisnikModel == null) {
			return new KorisnikModel();
		}else {
			return korisnikModel;
		}
	}
	
	public String passwordHash(String password) {
		
			String passwordToHash = password;
	        String generatedPassword = null;
	        
	        try {
	            // Create MessageDigest instance for MD5
	            MessageDigest md = MessageDigest.getInstance("MD5");
	            //Add password bytes to digest
	            md.update(passwordToHash.getBytes());
	            //Get the hash's bytes
	            byte[] bytes = md.digest();
	            //This bytes[] has bytes in decimal format;
	            //Convert it to hexadecimal format
	            StringBuilder sb = new StringBuilder();
	            for(int i=0; i< bytes.length ;i++)
	            {
	                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	            }
	            //Get complete hashed password in hex format
	            generatedPassword = sb.toString();
	        }
	        catch (NoSuchAlgorithmException e)
	        {
	            e.printStackTrace();
	        }
		return generatedPassword;
	}
	
	public int getID() {
		return korisnikModel.ID;
	}
	public void setID(int iD) {
		korisnikModel.ID = iD;
	}
	public String getKorisnickoIme() {
		return korisnikModel.korisnickoIme;
	}
	public void setKorisnickoIme(String korisnickoIme) {
		korisnikModel.korisnickoIme = korisnickoIme;
	}
	public String getEmail() {
		return korisnikModel.email;
	}
	public void setEmail(String email) {
		korisnikModel.email = email;
	}
	public Vector<String> getListaDozvola() {
		return korisnikModel.listaDozvola;
	}
	public void setListaDozvola(Vector<String> listaDozvola) {
		korisnikModel.listaDozvola = listaDozvola;
	}
	
	public int getID_PoslovnogSitema() {
		return korisnikModel.ID_PoslovnogSitema;
	}

	public void setID_PoslovnogSitema(int iD_PoslovnogSitema) {
		korisnikModel.ID_PoslovnogSitema = iD_PoslovnogSitema;
	}
	
	public Vector<String> getListaDozvola(int ID){
		
		Vector<String>listaDozvola = new Vector<>();
		Vector<Integer>listaUloga = new Vector<>();
		Vector<Integer>dozvoleTemp = new Vector<>();
		
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM [Lista Uloga] WHERE [KOR_ID]='"+ID+"'");
			
			while (rs.next()) {
				listaUloga.add(rs.getInt("UL_ID"));
			}
			
			for(int i=0; i<listaUloga.size();i++) {
				rs = stmt.executeQuery("SELECT * FROM [Lista dozvola] WHERE [UL_ID]='"+listaUloga.get(i)+"'");
				
				while (rs.next()) {
					dozvoleTemp.add(rs.getInt("DOZ_ID"));
				}
			}
			
			for(int i=0; i<dozvoleTemp.size();i++) {
				rs = stmt.executeQuery("SELECT * FROM Dozvola WHERE DOZ_ID='"+dozvoleTemp.get(i)+"'");
				
				while (rs.next()) {
					listaDozvola.add(rs.getString("DOZ_Naziv"));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return listaDozvola;
	}
	
	@SuppressWarnings("deprecation")
	public void insertLog(int ID) {
		
		LocalDateTime now = LocalDateTime.now();
		int hour = now.getHour();
		int minute = now.getMinute();
		int second = now.getSecond();
		
		Time time = new Time(hour, minute, second);
        
		String runSP = "{ call pisg2.WriteToLog(?,?,?) }";
		try {
			 conn.getDbConnection().createStatement();
			 CallableStatement callableStatement = conn.getDbConnection().prepareCall(runSP);
			 callableStatement.setInt(1, ID);
			 callableStatement.setTime(2,time);
			 try {
				callableStatement.setString(3, Inet4Address.getLocalHost().getHostAddress());
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	         callableStatement.executeUpdate();
	         
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
		
}
