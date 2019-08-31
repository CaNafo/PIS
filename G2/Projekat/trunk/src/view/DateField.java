package view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.toedter.calendar.JDateChooser;

import helpers.NameValuePair;
import model.TableColumn;

@SuppressWarnings("serial")
public class DateField extends JPanel implements TableFieldInterface {

	private TableColumn column;
	private JLabel label;
	private JDateChooser dateChooser;
	private JCheckBox leaveEmpty = null;

	public DateField(TableColumn column) {
		setLayout(new FlowLayout());

		this.column = column;

		label = new JLabel(column.getName() + ": ");
		dateChooser = new JDateChooser();

		if (!isNullable()) {
			JLabel mandatory = new JLabel("*");
			mandatory.setForeground(Color.RED);
			add(mandatory);
		}
		leaveEmpty = new JCheckBox("prazno");
		leaveEmpty.addActionListener(toggleDateListener);
		leaveEmpty.setSelected(false);

		add(label);
		add(dateChooser);
		if (isNullable())
			add(leaveEmpty);
	}

	@Override
	public Vector<NameValuePair> getValues() {
		Vector<NameValuePair> ret = new Vector<>();
		Date date = dateChooser.getDate();
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Sarajevo"));
		calendar.setTime(date);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int month = calendar.get(Calendar.MONTH) + 1;
		int year = calendar.get(Calendar.YEAR);

		String dateString = "'" + year + "-" + month + "-" + day + "'";
		if (isEmpty()) {
			ret.add(new NameValuePair(column.getName(), "null"));
		} else
			ret.add(new NameValuePair(column.getName(), dateString));

		return ret;
	}

	@Override
	public Boolean isEmpty() {
		return leaveEmpty.isSelected();
	}

	@Override
	public Boolean isNullable() {
		return column.getIsNull();
	}

	private ActionListener toggleDateListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (leaveEmpty.isSelected())
				dateChooser.setVisible(false);
			else
				dateChooser.setVisible(true);
		}
	};

	@Override
	public void setFieldValue(String value) {
		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(value);
			dateChooser.setDate(date);
			System.out.println(date.toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public JComponent getFieldComponent() {
		return dateChooser;
	}

}
