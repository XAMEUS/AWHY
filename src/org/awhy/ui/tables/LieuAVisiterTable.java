package org.awhy.ui.tables;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.awhy.core.objects.LieuAVisiter;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class LieuAVisiterTable extends Table<LieuAVisiter> {

	public LieuAVisiterTable(ResultSet res) throws SQLException {
		super(new LieuAVisiter(), res);

		TableColumn<LieuAVisiter, String> nomCol = new TableColumn<LieuAVisiter, String>("Nom");
		nomCol.setCellValueFactory(new PropertyValueFactory<LieuAVisiter, String>("nomLieu"));
		
		TableColumn<LieuAVisiter, String> nomVilleCol = new TableColumn<LieuAVisiter, String>("Ville");
		nomVilleCol.setCellValueFactory(new PropertyValueFactory<LieuAVisiter, String>("ville"));

		TableColumn<LieuAVisiter, String> paysCol = new TableColumn<LieuAVisiter, String>("Pays");
		paysCol.setCellValueFactory(new PropertyValueFactory<LieuAVisiter, String>("pays"));
		
		TableColumn<LieuAVisiter, String> adresseCol = new TableColumn<LieuAVisiter, String>("Adresse");
		adresseCol.setCellValueFactory(new PropertyValueFactory<LieuAVisiter, String>("adresseLieu"));

		TableColumn<LieuAVisiter, String> descriptionCol = new TableColumn<LieuAVisiter, String>("Description");
		descriptionCol.setCellValueFactory(new PropertyValueFactory<LieuAVisiter, String>("descriptifLieu"));
		
		TableColumn<LieuAVisiter, Integer> prixCol = new TableColumn<LieuAVisiter, Integer>("Prix");
		prixCol.setCellValueFactory(new PropertyValueFactory<LieuAVisiter, Integer>("prix"));

		this.setItems(this.data);
		this.getColumns().add(nomCol);
		this.getColumns().add(nomVilleCol);
		this.getColumns().add(paysCol);
		this.getColumns().add(adresseCol);
		this.getColumns().add(descriptionCol);
		this.getColumns().add(prixCol);
	}

}
