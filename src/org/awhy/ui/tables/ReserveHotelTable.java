package org.awhy.ui.tables;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.awhy.core.objects.Hotel;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class ReserveHotelTable extends Table<Hotel> {

	public ReserveHotelTable(ResultSet res) throws SQLException {
		super(new Hotel(), res);

		TableColumn<Hotel, String> nomCol = new TableColumn<Hotel, String>("Nom");
		nomCol.setCellValueFactory(new PropertyValueFactory<Hotel, String>("nomLieu"));

		TableColumn<Hotel, String> nomVilleCol = new TableColumn<Hotel, String>("Ville");
		nomVilleCol.setCellValueFactory(new PropertyValueFactory<Hotel, String>("ville"));

		TableColumn<Hotel, String> paysCol = new TableColumn<Hotel, String>("Pays");
		paysCol.setCellValueFactory(new PropertyValueFactory<Hotel, String>("pays"));

		TableColumn<Hotel, Integer> dossierCol = new TableColumn<Hotel, Integer>("Dossier");
		dossierCol.setCellValueFactory(new PropertyValueFactory<Hotel, Integer>("numDossier"));

		TableColumn<Hotel, Date> departCol = new TableColumn<Hotel, Date>("Arrivée");
		departCol.setCellValueFactory(new PropertyValueFactory<Hotel, Date>("dateDepartHotel"));

    TableColumn<Hotel, Date> arriveeCol = new TableColumn<Hotel, Date>("Départ");
		arriveeCol.setCellValueFactory(new PropertyValueFactory<Hotel, Date>("dateArriveeHotel"));

    TableColumn<Hotel, Integer> nbChambreCol = new TableColumn<Hotel, Integer>("Nombre chambres");
		nbChambreCol.setCellValueFactory(new PropertyValueFactory<Hotel, Integer>("nbChambresReservees"));

		TableColumn<Hotel, Integer> nbDejCol = new TableColumn<Hotel, Integer>("Nombre petit dejeuner");
		nbDejCol.setCellValueFactory(new PropertyValueFactory<Hotel, Integer>("nbPetitDejReserves"));

		this.setItems(this.data);
		this.getColumns().add(nomCol);
		this.getColumns().add(nomVilleCol);
		this.getColumns().add(paysCol);
		this.getColumns().add(dossierCol);
		this.getColumns().add(departCol);
    this.getColumns().add(arriveeCol);
    this.getColumns().add(nbChambreCol);
		this.getColumns().add(nbDejCol);
	}

}
