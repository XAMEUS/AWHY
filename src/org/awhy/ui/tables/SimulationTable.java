package org.awhy.ui.tables;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.awhy.core.objects.Simulation;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class SimulationTable extends Table<Simulation>{

	public SimulationTable(ResultSet res) throws SQLException {
		super(new Simulation(), res);

		TableColumn<Simulation, Integer> numDossierCol = new TableColumn<Simulation, Integer>("N° de dossier");
		numDossierCol.setCellValueFactory(new PropertyValueFactory<Simulation, Integer>("numDossier"));
		
		this.setItems(this.data);
		this.getColumns().add(numDossierCol);
	}

	
}
