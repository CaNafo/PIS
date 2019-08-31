package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Date;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import controller.InsertUpdateController;
import model.InsertUpdateModel;
import model.TableColumn;
import model.TableKey;

@SuppressWarnings("serial")
public class InsertUpdateView extends JDialog {

	private InsertUpdateModel model;
	private Vector<TableFieldInterface> fields;

	private JButton OK_button;
	private JButton CANCEL_button;
	private JScrollPane scrollPane;
	private Box box;

	public InsertUpdateView(InsertUpdateModel model) {
		this.model = model;
		fields = new Vector<>();

		setLayout(new BorderLayout());
		if (model.getFormTask() == 1)
			setTitle("Dodavanje: " + model.getTable().getName());
		else
			setTitle("Izmjena: " + model.getTable().getName());

		box = Box.createVerticalBox();
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(box);

		addComponents();
		add(scrollPane, BorderLayout.CENTER);

		OK_button = new JButton("Potvrdi");
		CANCEL_button = new JButton("Odustani");
		OK_button.setActionCommand("ok");
		CANCEL_button.setActionCommand("cancel");
		JPanel buttons = new JPanel();
		buttons.setLayout(new FlowLayout());
		buttons.add(OK_button);
		buttons.add(CANCEL_button);
		add(buttons, BorderLayout.SOUTH);

		new InsertUpdateController(model, this);

		if (fields.size() <= 7)
			setSize(new Dimension(500, fields.size() * 100));
		else
			setSize(new Dimension(500, 700));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void addComponents() {
		for (TableColumn column : model.getColumns()) {

			int index = 0;
			for (String colName : model.getTable().getColumnDisplayNames()) {
				if (column.getName().equals(colName))
					break;
				index++;
			}
			TableFieldInterface tempField = null;
			Object cellData = null;
			try {
				cellData = model.getTable().getData().get(model.getTable().getSelectedIndex()).get(index);
			} catch (Exception e) {
				// Dovoljno je ignorisati ovaj izuzetak
			}

			switch (column.getDataType()) {
			case "DATE":
				tempField = new DateField(column);
				if (model.getFormTask() == InsertUpdateModel.UPDATE && cellData != null)
					tempField.setFieldValue(cellData.toString());
				fields.add(tempField);
				break;
			case "BIT":
				tempField = new BooleanField(column);
				if (model.getFormTask() == InsertUpdateModel.UPDATE && cellData != null) {
					String s = (boolean) (cellData) ? "true" : "false";
					tempField.setFieldValue(s);
				}
				fields.add(tempField);
				break;
			default:
				tempField = new BasicField(column);
				if (model.getFormTask() == InsertUpdateModel.UPDATE && cellData != null)
					tempField.setFieldValue(cellData.toString());
				fields.add(tempField);
				break;
			}
		}

		for (TableKey fk : model.getForeignKeys()) {
			
			TableFieldInterface tempField = new ForeignKeyField(fk);
			if (model.getFormTask() == InsertUpdateModel.UPDATE && model.getTable().getSelectedIndex() != null) {
				tempField.setFieldValue(String.valueOf(model.getTable().getSelectedIndex()));
			}
			fields.add(tempField);
		}

		for (TableFieldInterface field : fields)
			box.add((Component) field);
	}

	public JButton getOK_button() {
		return OK_button;
	}

	public JButton getCANCEL_button() {
		return CANCEL_button;
	}

	public Vector<TableFieldInterface> getFields() {
		return fields;
	}
	
}
