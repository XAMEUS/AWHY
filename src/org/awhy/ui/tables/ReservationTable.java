package org.awhy.ui.tables;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.awhy.core.objects.Reservation;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class ReservationTable extends Table<Reservation> {

	public ReservationTable(ResultSet res) throws SQLException {
		super(new Reservation(), res);
		
		TableColumn<Reservation, Integer> numDossierCol = new TableColumn<Reservation, Integer>("N° de dossier");
		numDossierCol.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("numDossier"));
		
		TableColumn<Reservation, Date> datePaiementCol = new TableColumn<Reservation, Date>("Date");
		datePaiementCol.setCellValueFactory(new PropertyValueFactory<Reservation, Date>("datePaiement"));
		
		TableColumn<Reservation, String> infoPaiementCol = new TableColumn<Reservation, String>("Paiement");
		infoPaiementCol.setCellValueFactory(new PropertyValueFactory<Reservation, String>("infoPaiement"));
		
		TableColumn<Reservation, Integer> idClientCol = new TableColumn<Reservation, Integer>("ID Client");
		idClientCol.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("idClient"));
		
		this.setItems(this.data);
		this.getColumns().add(numDossierCol);
		this.getColumns().add(datePaiementCol);
		this.getColumns().add(infoPaiementCol);
		this.getColumns().add(idClientCol);
	}
	

}
