package org.awhy.ui.popup;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.awhy.ui.Controller;
import org.awhy.ui.tables.ReserveCircuitTable;
import org.awhy.ui.tables.ReserveHotelTable;
import org.awhy.ui.tables.ReserveVisiteTable;

import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class PopupInfoReservation {
	public static void show(int numDossier, int idClient, Connection c) throws SQLException {
		Dialog<ButtonType> dialog = new Dialog<>();

		String query = "SELECT * FROM Simulation WHERE numDossier=?";
		PreparedStatement pS = c.prepareStatement(query);
		pS.setInt(1, numDossier);
		ResultSet res = pS.executeQuery();
		while (res.next()) {
			dialog.setTitle("Récapitulatif de la réservation");
			dialog.setHeaderText(
					"Réservation n°" + numDossier + " de " + res.getString(2).trim() + " " + res.getString(3).trim());
		}
		pS.close();

		ButtonType cancelButtonType = new ButtonType("Retour", ButtonData.CANCEL_CLOSE);
		dialog.getDialogPane().getButtonTypes().addAll(cancelButtonType);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 20, 10, 10));

		// Ajouter client si possible

		// dates départ et arrivée envisagées
		Date dateMin = null;
		Date dateMax = null;

		query = "SELECT min(dateDepartHotel), max(dateArriveeHotel) FROM ReserveHotel WHERE numDossier=?";
		pS = c.prepareStatement(query);
		pS.setInt(1, numDossier);
		res = pS.executeQuery();
		while (res.next()) {
			if (dateMin == null || (res.getDate(1) != null && dateMin.after(res.getDate(1))))
				dateMin = res.getDate(1);
			if (dateMax == null || (res.getDate(2) != null && res.getDate(2).after(dateMax)))
				dateMax = res.getDate(2);
		}
		pS.close();

		query = "SELECT min(dateDepartCircuit), max(nbJoursTotal + dateDepartCircuit) FROM ReserveCircuit R, Circuit C WHERE numDossier=? and R.idCircuit=C.idCircuit";
		pS = c.prepareStatement(query);
		pS.setInt(1, numDossier);
		res = pS.executeQuery();
		while (res.next()) {
			if (dateMin == null || (res.getDate(1) != null && dateMin.after(res.getDate(1))))
				dateMin = res.getDate(1);
			if (dateMax == null || (res.getDate(2) != null && res.getDate(2).after(dateMax)))
				dateMax = res.getDate(2);
		}
		pS.close();

		query = "SELECT min(dateVisite), max(dateVisite) FROM ReserveVisite R WHERE numDossier=?";
		pS = c.prepareStatement(query);
		pS.setInt(1, numDossier);
		res = pS.executeQuery();
		while (res.next()) {
			if (dateMin == null || (res.getDate(1) != null && dateMin.after(res.getDate(1))))
				dateMin = res.getDate(1);
			if (dateMax == null || (res.getDate(2) != null && res.getDate(2).after(dateMax)))
				dateMax = res.getDate(2);
		}
		pS.close();

		Text dateMinText = new Text("Date de départ: " + dateMin);
		grid.add(dateMinText, 0, 0);
		Text dateMaxText = new Text("Date d'arrivée: " + dateMax);
		grid.add(dateMaxText, 1, 0);

		// nombre de personnes
		int nbPersonnes = -1;
		query = "SELECT max(nbPersonnesVisite) FROM ReserveVisite WHERE numDossier=?";
		pS = c.prepareStatement(query);
		pS.setInt(1, numDossier);
		res = pS.executeQuery();
		while (res.next())
			if (res.getInt(1) > nbPersonnes)
				nbPersonnes = res.getInt(1);
		pS.close();

		query = "SELECT max(nbChambresReservees) FROM ReserveHotel WHERE numDossier=?";
		pS = c.prepareStatement(query);
		pS.setInt(1, numDossier);
		res = pS.executeQuery();
		while (res.next())
			if (res.getInt(1) > nbPersonnes)
				nbPersonnes = res.getInt(1);
		pS.close();

		query = "SELECT max(nbPersonnesCircuit) FROM ReserveCircuit WHERE numDossier=?";
		pS = c.prepareStatement(query);
		pS.setInt(1, numDossier);
		res = pS.executeQuery();
		while (res.next())
			if (res.getInt(1) > nbPersonnes)
				nbPersonnes = res.getInt(1);
		pS.close();

		Text nbPersonnesText = new Text(nbPersonnes + " personne.s");
		grid.add(nbPersonnesText, 0, 1);

		// Le pognon
		int cout = 0;
		query = "SELECT sum(prixCircuit) FROM ReserveCircuit R, Circuit C WHERE numDossier=? and R.idCircuit = C.idCircuit";
		pS = c.prepareStatement(query);
		pS.setInt(1, numDossier);
		res = pS.executeQuery();
		while (res.next())
			cout += res.getInt(1);
		pS.close();

		query = "select (sum(prixChambre * nbChambresReservees + prixPetitDejeuner * nbPetitDejReserves) * (R.dateArriveeHotel - R.dateDepartHotel)) FROM ReserveHotel R, Hotel H WHERE numDossier=? and R.nomHotel = H.nomHotel and R.ville = H.ville and R.pays = H.pays group by R.nomHotel, R.ville, R.pays, R.dateDepartHotel, R.dateArriveeHotel";
		pS = c.prepareStatement(query);
		pS.setInt(1, numDossier);
		res = pS.executeQuery();
		while (res.next())
			cout += res.getInt(1);
		pS.close();

		query = "SELECT sum(prix) FROM ReserveVisite R, LieuAVisiter L WHERE numDossier=? and R.nomLieu = L.nomLieu and R.ville = L.ville and R.pays = L.pays";
		pS = c.prepareStatement(query);
		pS.setInt(1, numDossier);
		res = pS.executeQuery();
		while (res.next())
			cout += res.getInt(1);
		pS.close();

		Text argent = new Text("Coût total: " + cout + "€");
		grid.add(argent, 1, 1);
		

		final ReserveCircuitTable recapCircuit = new ReserveCircuitTable(
				Controller.executeQuery("select * from reservecircuit where numDossier = '" + numDossier + "'"));
		grid.add(recapCircuit, 0, 3);
		final ReserveHotelTable recapHotel = new ReserveHotelTable(
				Controller.executeQuery("select * from reservehotel where numDossier = '" + numDossier + "'"));
		grid.add(recapHotel, 1, 3);
		final ReserveVisiteTable recapVisite = new ReserveVisiteTable(
				Controller.executeQuery("select * from reservevisite where numDossier = '" + numDossier + "'"));
		grid.add(recapVisite, 2, 3);

		query = "SELECT * FROM Reservation WHERE numDossier=?";
		pS = c.prepareStatement(query);
		pS.setInt(1, numDossier);
		res = pS.executeQuery();
		while (res.next()) {
			Text payele = new Text("Payé le " + res.getDate(2).toString());
			grid.add(payele, 0, 4);

			Text payecom = new Text("Info paiement " + res.getString(3).toString());
			grid.add(payecom, 1, 4, 2, 1);

		}

		pS.close();

		dialog.getDialogPane().setContent(grid);

		dialog.showAndWait();

	}

}