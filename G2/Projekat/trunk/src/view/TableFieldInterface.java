package view;

import java.util.Vector;

import javax.swing.JComponent;

import helpers.NameValuePair;

public interface TableFieldInterface{
	
	public Vector<NameValuePair> getValues();
	
	public Boolean isEmpty();
	
	public Boolean isNullable();
	
	public void setFieldValue(String value);
	
	public JComponent getFieldComponent();
}
