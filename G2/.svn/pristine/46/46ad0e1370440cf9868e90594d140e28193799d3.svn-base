package view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import helpers.AutoCompletion;
import helpers.NameValuePair;
import javafx.scene.chart.PieChart.Data;
import model.TableColumn;
import model.TableDataModel;
import model.TableFormModel;
import model.TableKey;

@SuppressWarnings("serial")
public class ForeignKeyField extends JPanel implements TableFieldInterface {

	private TableKey fKey;
	private JComboBox<Object> cBox;
	private JLabel label;
	private TableDataModel parrentTable;
	private JCheckBox leaveEmpty = null;

	public ForeignKeyField(TableKey fKey) {
		this.setLayout(new FlowLayout());

		this.fKey = fKey;
		label = new JLabel(fKey.getName());
		parrentTable = new TableFormModel(fKey.getParentTable()).getTableDataModel();
		cBox = new JComboBox<>(parrentTable);
//		AutoCompletion.enable(cBox);

		if (!isNullable()) {
			JLabel mandatory = new JLabel("*");
			mandatory.setForeground(Color.RED);
			add(mandatory);
		}
		leaveEmpty = new JCheckBox("prazno");
		leaveEmpty.addActionListener(toggleDateListener);
		leaveEmpty.setSelected(false);

		add(label);
		add(cBox);
		if (isNullable())
			add(leaveEmpty);
	}

	@Override
	public Vector<NameValuePair> getValues() {
		Vector<NameValuePair> ret = new Vector<>();
		for (TableColumn fkColumn : fKey.getColumns()) {
			String subQuery = "(SELECT DISTINCT [" + fkColumn.getParrentName() + "] FROM [" + fKey.getParentTable()
					+ "] " + parrentTable.makeWhereClause() + ")";
			if (!isEmpty())
				ret.add(new NameValuePair(fkColumn.getName(), subQuery));
			else
				ret.add(new NameValuePair(fkColumn.getName(), "null"));
		}
		return ret;
	}

	@Override
	public Boolean isEmpty() {
		return leaveEmpty.isSelected();
	}

	@Override
	public Boolean isNullable() {
		Boolean b = false;
		for (TableColumn column : fKey.getColumns()) {
			if (column.getIsNull())
				b = true;
		}
		return b;
	}

	private ActionListener toggleDateListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (leaveEmpty.isSelected())
				cBox.setVisible(false);
			else
				cBox.setVisible(true);
		}
	};

	@Override
	public void setFieldValue(String value) {
		try {
			parrentTable.setSelectedIndex(Integer.valueOf(value));
			cBox.setSelectedIndex(Integer.valueOf(value));			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public JComponent getFieldComponent() {
		return cBox;
	}

}
