package org.awhy.ui.tables;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.awhy.core.objects.Circuit;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class ReserveCircuitTable extends Table<Circuit> {

	public ReserveCircuitTable(ResultSet res) throws SQLException {
		super(new Circuit(), res);

		TableColumn<Circuit, String> idCol = new TableColumn<Circuit, String>("ID");
		idCol.setCellValueFactory(new PropertyValueFactory<Circuit, String>("idCircuit"));

		TableColumn<Circuit, Date> departCol = new TableColumn<Circuit, Date>("Date");
		departCol.setCellValueFactory(new PropertyValueFactory<Circuit, Date>("dateDepartCircuit"));

		TableColumn<Circuit, Integer> numDossierCol = new TableColumn<Circuit, Integer>("Dossier");
		numDossierCol.setCellValueFactory(new PropertyValueFactory<Circuit, Integer>("numDossier"));

		TableColumn<Circuit, Integer> nbPersonnesCol = new TableColumn<Circuit, Integer>("Nombre personnes");
		nbPersonnesCol.setCellValueFactory(new PropertyValueFactory<Circuit, Integer>("nbPersonnesCircuit"));

		this.setItems(this.data);
		this.getColumns().add(idCol);
		this.getColumns().add(departCol);
		this.getColumns().add(numDossierCol);
		this.getColumns().add(nbPersonnesCol);
	}

}
