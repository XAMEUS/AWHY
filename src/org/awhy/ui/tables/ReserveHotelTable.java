package org.awhy.ui.tables;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.awhy.core.objects.ReserveHotel;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class ReserveHotelTable extends Table<ReserveHotel> {

	public ReserveHotelTable(ResultSet res) throws SQLException {
		super(new ReserveHotel(), res);

		TableColumn<ReserveHotel, String> hotelCol = new TableColumn<ReserveHotel, String>("Hotel");
		hotelCol.setCellValueFactory(new PropertyValueFactory<ReserveHotel, String>("nomHotel"));

		TableColumn<ReserveHotel, String> nomVilleCol = new TableColumn<ReserveHotel, String>("Ville");
		nomVilleCol.setCellValueFactory(new PropertyValueFactory<ReserveHotel, String>("ville"));

		TableColumn<ReserveHotel, String> paysCol = new TableColumn<ReserveHotel, String>("Pays");
		paysCol.setCellValueFactory(new PropertyValueFactory<ReserveHotel, String>("pays"));

		TableColumn<ReserveHotel, Integer> dossierCol = new TableColumn<ReserveHotel, Integer>("Dossier");
		dossierCol.setCellValueFactory(new PropertyValueFactory<ReserveHotel, Integer>("numDossier"));

		TableColumn<ReserveHotel, Date> departCol = new TableColumn<ReserveHotel, Date>("Arrivée");
		departCol.setCellValueFactory(new PropertyValueFactory<ReserveHotel, Date>("dateDepartHotel"));

		TableColumn<ReserveHotel, Date> arriveeCol = new TableColumn<ReserveHotel, Date>("Départ");
		arriveeCol.setCellValueFactory(new PropertyValueFactory<ReserveHotel, Date>("dateArriveeHotel"));

		TableColumn<ReserveHotel, Integer> nbChambreCol = new TableColumn<ReserveHotel, Integer>("Chambres");
		nbChambreCol.setCellValueFactory(new PropertyValueFactory<ReserveHotel, Integer>("nbChambresReservees"));

		TableColumn<ReserveHotel, Integer> nbDejCol = new TableColumn<ReserveHotel, Integer>("Petits Déjeuners");
		nbDejCol.setCellValueFactory(new PropertyValueFactory<ReserveHotel, Integer>("nbPetitDejReserves"));

		this.setItems(this.data);
		this.getColumns().add(hotelCol);
		this.getColumns().add(nomVilleCol);
		this.getColumns().add(paysCol);
		this.getColumns().add(dossierCol);
		this.getColumns().add(departCol);
		this.getColumns().add(arriveeCol);
		this.getColumns().add(nbChambreCol);
		this.getColumns().add(nbDejCol);
	}

}
