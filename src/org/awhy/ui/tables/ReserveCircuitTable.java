package org.awhy.ui.tables;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.awhy.core.objects.ReserveCircuit;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class ReserveCircuitTable extends Table<ReserveCircuit> {

	public ReserveCircuitTable(ResultSet res) throws SQLException {
		super(new ReserveCircuit(), res);

		TableColumn<ReserveCircuit, String> idCol = new TableColumn<ReserveCircuit, String>("ID");
		idCol.setCellValueFactory(new PropertyValueFactory<ReserveCircuit, String>("idCircuit"));

		TableColumn<ReserveCircuit, Date> departCol = new TableColumn<ReserveCircuit, Date>("Date");
		departCol.setCellValueFactory(new PropertyValueFactory<ReserveCircuit, Date>("dateDepartCircuit"));

		TableColumn<ReserveCircuit, Integer> numDossierCol = new TableColumn<ReserveCircuit, Integer>("Dossier");
		numDossierCol.setCellValueFactory(new PropertyValueFactory<ReserveCircuit, Integer>("numDossier"));

		TableColumn<ReserveCircuit, Integer> nbPersonnesCol = new TableColumn<ReserveCircuit, Integer>("Places");
		nbPersonnesCol.setCellValueFactory(new PropertyValueFactory<ReserveCircuit, Integer>("nbPersonnesCircuit"));

		this.setItems(this.data);
		this.getColumns().add(idCol);
		this.getColumns().add(departCol);
		this.getColumns().add(numDossierCol);
		this.getColumns().add(nbPersonnesCol);
	}

}
