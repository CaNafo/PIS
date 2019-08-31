package view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import helpers.NameValuePair;
import model.TableColumn;

@SuppressWarnings("serial")
public class BooleanField extends JPanel implements TableFieldInterface {

	private TableColumn column;
	private JCheckBox checkBox;

	public BooleanField(TableColumn column) {
		this.column = column;
		checkBox = new JCheckBox(column.getName()+":");
		checkBox.setHorizontalTextPosition(SwingConstants.LEFT);

		setLayout(new FlowLayout());

		if(!isNullable()) {
			JLabel mandatory=new JLabel("*");
			mandatory.setForeground(Color.RED);
			add(mandatory);
		}
		add(checkBox);
	}

	@Override
	public Vector<NameValuePair> getValues() {
		Vector<NameValuePair> ret = new Vector<>();

		int value = (checkBox.isSelected()) ? 1 : 0;
		ret.add(new NameValuePair(column.getName(), String.valueOf(value)));
		return ret;
	}

	@Override
	public Boolean isEmpty() {
		return false;
	}

	@Override
	public Boolean isNullable() {
		return column.getIsNull();
	}

	@Override
	public void setFieldValue(String value) {
		if(value.equals("true"))
			checkBox.setSelected(true);
		else if(value.equals("false"))
			checkBox.setSelected(false);
	}

	@Override
	public JComponent getFieldComponent() {
		return checkBox;
	}

}
