package org.awhy.ui.tables;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.awhy.core.objects.Hotel;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class HotelTable extends Table<Hotel> {

	public HotelTable(ResultSet res) throws SQLException {
		super(new Hotel(), res);
		
		TableColumn<Hotel, String> nomHotelCol = new TableColumn<Hotel, String>("Hotel");
		nomHotelCol.setCellValueFactory(new PropertyValueFactory<Hotel, String>("nomHotel"));
		
		TableColumn<Hotel, String> nomVilleCol = new TableColumn<Hotel, String>("Ville");
		nomVilleCol.setCellValueFactory(new PropertyValueFactory<Hotel, String>("ville"));

		TableColumn<Hotel, String> paysCol = new TableColumn<Hotel, String>("Pays");
		paysCol.setCellValueFactory(new PropertyValueFactory<Hotel, String>("pays"));
		
		TableColumn<Hotel, String> adresseHotelCol = new TableColumn<Hotel, String>("Adresse");
		adresseHotelCol.setCellValueFactory(new PropertyValueFactory<Hotel, String>("adresseHotel"));
		
		TableColumn<Hotel, Integer> nbChambresTotalCol = new TableColumn<Hotel, Integer>("Chambres");
		nbChambresTotalCol.setCellValueFactory(new PropertyValueFactory<Hotel, Integer>("nbChambresTotal"));
		
		TableColumn<Hotel, Integer> prixChambreCol = new TableColumn<Hotel, Integer>("Prix");
		prixChambreCol.setCellValueFactory(new PropertyValueFactory<Hotel, Integer>("prixChambre"));
		
		TableColumn<Hotel, Integer> prixPetitDejeunerCol = new TableColumn<Hotel, Integer>("Petit Déjeuner");
		prixPetitDejeunerCol.setCellValueFactory(new PropertyValueFactory<Hotel, Integer>("prixPetitDejeuner"));

		this.setItems(this.data);
		this.getColumns().add(nomHotelCol);
		this.getColumns().add(nomVilleCol);
		this.getColumns().add(paysCol);
		this.getColumns().add(adresseHotelCol);
		this.getColumns().add(nbChambresTotalCol);
		this.getColumns().add(prixChambreCol);
		this.getColumns().add(prixPetitDejeunerCol);
	}

}
