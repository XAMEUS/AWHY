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
			dialog.setHeaderText("Réservation n°" + numDossier + " de " + res.getString(2).trim() + " " + res.getString(3).trim());
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

		query = "SELECT sum(prixChambre * nbChambresReservees + prixPetitDejeuner * nbPetitDejReserves) FROM ReserveHotel R, Hotel H WHERE numDossier=? and R.nomHotel = H.nomHotel and R.ville = H.ville and R.pays = H.pays";
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

		// vérifier (nombre places circuits, nombre places hotels, dates)
		boolean possible = true;
		query = "SELECT nbPersonnesCircuit, idCircuit, dateDepartCircuit FROM ReserveCircuit WHERE numDossier=?";
		pS = c.prepareStatement(query);
		pS.setInt(1, numDossier);
		res = pS.executeQuery();
		while (res.next()) {
			if (!possible)
				break;
			String check = "select (dc.nbPersonnes - sum(rcd.nbPersonnesCircuit)) as nbPlaces from DateCircuit dc, (select idCircuit, dateDepartCircuit, nbPersonnesCircuit from ReserveCircuit rc, Reservation r where r.numDossier = rc.numDossier and rc.idCircuit = ? and rc.dateDepartCircuit = ?) rcd where dc.idCircuit = rcd.idCircuit and dc.dateDepartCircuit = rcd.dateDepartCircuit group by dc.idCircuit, dc.dateDepartCircuit, dc.nbPersonnes";
			PreparedStatement cPS = c.prepareStatement(check);
			cPS.setString(1, res.getString(2));
			cPS.setDate(2, res.getDate(3));
			System.out.println(check);
			ResultSet cRes = cPS.executeQuery();
			while (cRes.next())
				if (cRes.getInt(1) < res.getInt(1))
					possible = false;
			cPS.close();
		}
		pS.close();

		query = "SELECT nomHotel, ville, pays, nbChambresReservees, dateDepartHotel, dateArriveeHotel FROM ReserveHotel WHERE numDossier=?";
		pS = c.prepareStatement(query);
		pS.setInt(1, numDossier);
		res = pS.executeQuery();
		while (res.next()) {
			if (!possible)
				break;
			Date curr = new Date(res.getDate(5).getTime());
			while (res.getDate(6).after(curr)) {
				if (!possible)
					break;
				String check = "select (h.nbChambresTotal - sum(rhr.nbChambresReservees)) from (select nomHotel, ville, pays, nbChambresTotal from Hotel) h, (select rh.nomHotel, rh.ville, rh.pays, rh.nbChambresReservees from ReserveHotel rh, Reservation r where rh.numDossier = r.numDossier and rh.nomHotel = ? and rh.ville = ? and rh.pays = ? and (dateDepartHotel <= ? and dateArriveeHotel > ?)) rhr where h.nomHotel = rhr.nomHotel and h.ville = rhr.ville and h.pays = rhr.pays group by h.nomHotel, h.ville, h.pays, h.nbChambresTotal";
				PreparedStatement cPS = c.prepareStatement(check);
				cPS.setDate(4, curr);
				cPS.setDate(5, curr);
				cPS.setString(1, res.getString(1));
				cPS.setString(2, res.getString(2));
				cPS.setString(3, res.getString(3));
				ResultSet cRes = cPS.executeQuery();
				while (cRes.next())
					if (res.getInt(4) > cRes.getInt(1))
						possible = false;
				cPS.close();
				curr = new Date(curr.getTime() + ((long) 1000) * 60 * 60 * 24);
			}
		}
		pS.close();

		final ReserveCircuitTable recapCircuit= new ReserveCircuitTable(
				Controller.executeQuery("select * from reservecircuit where numDossier = '" + numDossier + "'"));
		grid.add(recapCircuit, 0, 3);
		final ReserveHotelTable recapHotel = new ReserveHotelTable(
				Controller.executeQuery("select * from reservehotel where numDossier = '" + numDossier + "'"));
		grid.add(recapHotel, 1, 3);
		final ReserveVisiteTable recapVisite= new ReserveVisiteTable(
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