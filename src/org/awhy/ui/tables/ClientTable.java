package org.awhy.ui.tables;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.awhy.core.objects.Client;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class ClientTable extends Table<Client> {

	public ClientTable(ResultSet res) throws SQLException {
		super(new Client(), res);

		TableColumn<Client, String> idClientCol = new TableColumn<Client, String>("ID");
		idClientCol.setCellValueFactory(new PropertyValueFactory<Client, String>("idClient"));
		TableColumn<Client, String> nomClientCol = new TableColumn<Client, String>("Nom");
		nomClientCol.setCellValueFactory(new PropertyValueFactory<Client, String>("nomClient"));
		TableColumn<Client, String> prenomClientCol = new TableColumn<Client, String>("Prénom");
		prenomClientCol.setCellValueFactory(new PropertyValueFactory<Client, String>("prenomClient"));
		TableColumn<Client, String> typeClientCol = new TableColumn<Client, String>("Type");
		typeClientCol.setCellValueFactory(new PropertyValueFactory<Client, String>("typeClient"));
		TableColumn<Client, String> adresseClientCol = new TableColumn<Client, String>("Adresse");
		adresseClientCol.setCellValueFactory(new PropertyValueFactory<Client, String>("adresseClient"));
		TableColumn<Client, String> emailClientCol = new TableColumn<Client, String>("Email");
		emailClientCol.setCellValueFactory(new PropertyValueFactory<Client, String>("emailClient"));
		TableColumn<Client, String> telClientCol = new TableColumn<Client, String>("Téléphone");
		telClientCol.setCellValueFactory(new PropertyValueFactory<Client, String>("telClient"));
		TableColumn<Client, String> anneeEnregistrementCol = new TableColumn<Client, String>("Année");
		anneeEnregistrementCol.setCellValueFactory(new PropertyValueFactory<Client, String>("anneeEnregistrement"));

		this.setItems(this.data);
		this.getColumns().add(idClientCol);
		this.getColumns().add(nomClientCol);
		this.getColumns().add(prenomClientCol);
		this.getColumns().add(typeClientCol);
		this.getColumns().add(adresseClientCol);
		this.getColumns().add(emailClientCol);
		this.getColumns().add(telClientCol);
		this.getColumns().add(anneeEnregistrementCol);
	}

}
