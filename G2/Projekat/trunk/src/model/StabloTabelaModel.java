package model;

public class StabloTabelaModel
{
	private String kod = null;
	private String naziv = null;
	
	public StabloTabelaModel(String kod, String naziv)
	{
		setKod(kod);
		setNaziv(naziv);
	}
	
	public String getKod()
	{
		return kod;
	}
	public void setKod(String kod)
	{
		this.kod = kod;
	}
	public String getNaziv()
	{
		return naziv;
	}
	public void setNaziv(String naziv)
	{
		this.naziv = naziv;
	}
	

	@Override
	public String toString()
	{
		return this.naziv;
	}
	
}
