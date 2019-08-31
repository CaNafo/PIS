package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import helpers.NameValuePair;
import model.KorisnikModel;
import model.TableColumn;

@SuppressWarnings("serial")
public class BasicField extends JPanel implements TableFieldInterface {

	private TableColumn column;
	private JLabel label;
	private JTextField text;

	public BasicField(TableColumn column) {
		this.column = column;

		setLayout(new FlowLayout());

		label = new JLabel(column.getName() + ": ");
		text = new JTextField();
		text.setPreferredSize(new Dimension(150, 30));
		text.setToolTipText("Dozvoljeni su svi znakovi osim ' i ;");

		if (!isNullable()) {
			JLabel mandatory = new JLabel("*");
			mandatory.setForeground(Color.RED);
			add(mandatory);
		}
		add(label);
		add(text);
	}

	private String escapeChar(String s) {
		return s.replaceAll("[\\'\\;]", "");
	}

	@Override
	public Vector<NameValuePair> getValues() {
		Vector<NameValuePair> ret = new Vector<>();
		if (text.getText().length() > 0) {
			if (column.getName().equals("KOR_LOZINKA")) {
				String pass = KorisnikModel.getInstance().passwordHash(text.getText());
				pass = "'" + pass + "'";
				ret.add(new NameValuePair(column.getName(), pass));
			} else
				ret.add(new NameValuePair(column.getName(), "'" + escapeChar(text.getText()) + "'"));
		} else
			ret.add(new NameValuePair(column.getName(), "null"));
		return ret;
	}

	@Override
	public Boolean isEmpty() {
		if (text.getText().length() == 0)
			return true;
		else
			return false;
	}

	@Override
	public Boolean isNullable() {
		return column.getIsNull();
	}

	@Override
	public void setFieldValue(String value) {
		if (!column.getName().equals("KOR_LOZINKA"))
			text.setText(value);
	}

	@Override
	public JComponent getFieldComponent() {
		return text;
	}

}
