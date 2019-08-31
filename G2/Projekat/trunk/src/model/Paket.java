package model;

import java.util.Vector;

public class Paket
{
	private String ime = null;
	private Vector<StabloTabelaModel> tabele = new Vector<>();
	
	public Paket()
	{
		
	}

	public Paket(String ime, Vector<StabloTabelaModel> tabele)
	{
		setIme(ime);
		setTabele(tabele);
	}
	
	public String getIme()
	{
		return ime;
	}

	public void setIme(String ime)
	{
		this.ime = ime;
	}
	
	@Override
	public String toString()
	{
		return ime;
	}

	public Vector<StabloTabelaModel> getTabele()
	{
		return tabele;
	}

	public void setTabele(Vector<StabloTabelaModel> tabele)
	{
		this.tabele = tabele;
	}

}
