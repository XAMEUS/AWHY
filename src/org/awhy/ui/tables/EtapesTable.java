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
		
		TableColumn<Etapes, Integer> ordreCol = new TableColumn<Etapes, Integer>("Ordre");
		ordreCol.setCellValueFactory(new PropertyValueFactory<Etapes, Integer>("ordre"));
		
		TableColumn<Etapes, String> lieuCol = new TableColumn<Etapes, String>("Lieu");
		lieuCol.setCellValueFactory(new PropertyValueFactory<Etapes, String>("nomLieu"));
		
		TableColumn<Etapes, String> nomVilleCol = new TableColumn<Etapes, String>("Ville");
		nomVilleCol.setCellValueFactory(new PropertyValueFactory<Etapes, String>("ville"));

		TableColumn<Etapes, String> paysCol = new TableColumn<Etapes, String>("Pays");
		paysCol.setCellValueFactory(new PropertyValueFactory<Etapes, String>("pays"));
		
		TableColumn<Etapes, Integer> nbJoursCol = new TableColumn<Etapes, Integer>("Jours");
		nbJoursCol.setCellValueFactory(new PropertyValueFactory<Etapes, Integer>("nbJours"));
		
		this.setItems(this.data);
		this.getColumns().add(idCircuitCol);
		this.getColumns().add(ordreCol);
		this.getColumns().add(lieuCol);
		this.getColumns().add(nomVilleCol);
		this.getColumns().add(paysCol);
		this.getColumns().add(nbJoursCol);
	}

	
}
