package org.awhy.ui.tables;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.awhy.core.objects.DateCircuit;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class DateCircuitTable extends Table<DateCircuit> {

	public DateCircuitTable(ResultSet res) throws SQLException {
		super(new DateCircuit(), res);

		TableColumn<DateCircuit, String> idCircuitCol = new TableColumn<DateCircuit, String>("ID");
		idCircuitCol.setCellValueFactory(new PropertyValueFactory<DateCircuit, String>("idCircuit"));

		TableColumn<DateCircuit, Date> dateDepartCircuitCol = new TableColumn<DateCircuit, Date>("Départ");
		dateDepartCircuitCol.setCellValueFactory(new PropertyValueFactory<DateCircuit, Date>("dateDepartCircuit"));

		TableColumn<DateCircuit, Integer> nbPersonnesCol = new TableColumn<DateCircuit, Integer>("Places");
		nbPersonnesCol.setCellValueFactory(new PropertyValueFactory<DateCircuit, Integer>("nbPersonnes"));

		this.setItems(this.data);
		this.getColumns().add(idCircuitCol);
		this.getColumns().add(dateDepartCircuitCol);
		this.getColumns().add(nbPersonnesCol);
	}

}
