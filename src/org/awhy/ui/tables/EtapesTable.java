package org.awhy.ui.tables;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.awhy.core.objects.Etapes;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class EtapesTable extends Table<Etapes> {

	public EtapesTable(ResultSet res) throws SQLException {
		super(new Etapes(), res);
		
		TableColumn<Etapes, String> idCircuitCol = new TableColumn<Etapes, String>("ID");
		idCircuitCol.setCellValueFactory(new PropertyValueFactory<Etapes, String>("idCircuit"));
		
		TableColumn<Etapes, String> ordreCol = new TableColumn<Etapes, String>("Ordre");
		ordreCol.setCellValueFactory(new PropertyValueFactory<Etapes, String>("ordre"));
		
		TableColumn<Etapes, String> nomCol = new TableColumn<Etapes, String>("Lieu");
		nomCol.setCellValueFactory(new PropertyValueFactory<Etapes, String>("nomLieu"));
		
		TableColumn<Etapes, String> nomVilleCol = new TableColumn<Etapes, String>("Ville");
		nomVilleCol.setCellValueFactory(new PropertyValueFactory<Etapes, String>("ville"));

		TableColumn<Etapes, String> paysCol = new TableColumn<Etapes, String>("Pays");
		paysCol.setCellValueFactory(new PropertyValueFactory<Etapes, String>("pays"));
		
		TableColumn<Etapes, String> nbJoursCol = new TableColumn<Etapes, String>("Jours");
		nbJoursCol.setCellValueFactory(new PropertyValueFactory<Etapes, String>("nbJours"));
		
		this.setItems(this.data);
		this.getColumns().add(idCircuitCol);
		this.getColumns().add(ordreCol);
		this.getColumns().add(nomVilleCol);
		this.getColumns().add(paysCol);
		this.getColumns().add(nbJoursCol);
	}

	
}
