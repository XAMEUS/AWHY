package org.awhy.ui.tables;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.awhy.core.objects.Circuit;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class CircuitTable extends Table<Circuit>{
	
	public CircuitTable(ResultSet res) throws SQLException {
		super(new Circuit(), res);
		
		TableColumn<Circuit, String> idCircuitCol = new TableColumn<Circuit, String>("ID");
		idCircuitCol.setCellValueFactory(new PropertyValueFactory<Circuit, String>("idCircuit"));
		
		TableColumn<Circuit, String> descriptifCol = new TableColumn<Circuit, String>("Descriptif");
		descriptifCol.setCellValueFactory(new PropertyValueFactory<Circuit, String>("descriptif"));
		
		TableColumn<Circuit, String> villeDepartCol = new TableColumn<Circuit, String>("Ville de départ");
		villeDepartCol.setCellValueFactory(new PropertyValueFactory<Circuit, String>("villeDepart"));
		
		TableColumn<Circuit, String> paysDepartCol = new TableColumn<Circuit, String>("Pays de départ");
		paysDepartCol.setCellValueFactory(new PropertyValueFactory<Circuit, String>("paysDepart"));
		
		TableColumn<Circuit, String> villeArriveeCol = new TableColumn<Circuit, String>("Ville d'arrivée");
		villeArriveeCol.setCellValueFactory(new PropertyValueFactory<Circuit, String>("villeArrivee"));
		
		TableColumn<Circuit, String> paysArriveeCol = new TableColumn<Circuit, String>("Pays d'arrivée");
		paysArriveeCol.setCellValueFactory(new PropertyValueFactory<Circuit, String>("paysArrivee"));
		
		TableColumn<Circuit, Integer> nbJoursTotalCol = new TableColumn<Circuit, Integer>("Jours");
		nbJoursTotalCol.setCellValueFactory(new PropertyValueFactory<Circuit, Integer>("nbJoursTotal"));
		
		TableColumn<Circuit, Integer> prixCircuitCol = new TableColumn<Circuit, Integer>("Prix");
		prixCircuitCol.setCellValueFactory(new PropertyValueFactory<Circuit, Integer>("prixCircuit"));
		
		this.setItems(this.data);
		this.getColumns().add(idCircuitCol);
		this.getColumns().add(descriptifCol);
		this.getColumns().add(villeDepartCol);
		this.getColumns().add(paysDepartCol);
		this.getColumns().add(villeArriveeCol);
		this.getColumns().add(paysArriveeCol);
		this.getColumns().add(nbJoursTotalCol);
		this.getColumns().add(prixCircuitCol);
	}

}
