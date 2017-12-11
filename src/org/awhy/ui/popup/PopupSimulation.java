package org.awhy.ui.popup;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.awhy.core.objects.ReserveCircuit;
import org.awhy.core.objects.ReserveHotel;
import org.awhy.core.objects.ReserveVisite;
import org.awhy.core.objects.Simulation;
import org.awhy.ui.Controller;
import org.awhy.ui.pane.GAccordionFX;
import org.awhy.ui.tables.ReserveCircuitTable;
import org.awhy.ui.tables.ReserveHotelTable;
import org.awhy.ui.tables.ReserveVisiteTable;

import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class PopupSimulation {
	public static void show(int numDossier, String nomClient, String prenomClient, Connection c) throws SQLException {

		Dialog<ButtonType> dialog = new Dialog<>();
		dialog.setTitle("Récapitulatif de la simulation");
		dialog.setHeaderText("Simulation n°" + numDossier + " de " + nomClient + " " + prenomClient);

		ButtonType cancelButtonType = new ButtonType("Retour", ButtonData.CANCEL_CLOSE);
		dialog.getDialogPane().getButtonTypes().addAll(cancelButtonType);

		ButtonType editSimulation = new ButtonType("Changer simulation", ButtonData.OK_DONE);
		ButtonType addButtonType = new ButtonType("Client\nexistant", ButtonData.OK_DONE);
		ButtonType confirmButtonType = new ButtonType("Nouveau\n client", ButtonData.NEXT_FORWARD);
		
		List<ReserveCircuit> circuits = new ArrayList<ReserveCircuit>();
		List<ReserveHotel> hotels = new ArrayList<ReserveHotel>();
		List<ReserveCircuit> circuitsPasOk = new ArrayList<ReserveCircuit>();
		List<ReserveHotel> hotelsPasOk = new ArrayList<ReserveHotel>();
		List<ReserveVisite> visites = new ArrayList<ReserveVisite>();

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 20, 10, 10));

		// Ajouter client si possible

		// dates départ et arrivée envisagées
		Date dateMin = null;
		Date dateMax = null;

		String query = "SELECT min(dateDepartHotel), max(dateArriveeHotel) FROM ReserveHotel WHERE numDossier=?";
		PreparedStatement pS = c.prepareStatement(query);
		pS.setInt(1, numDossier);
		ResultSet res = pS.executeQuery();
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

		// Les sous
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
			cout += res.getLong(1);
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
		query = "SELECT * FROM ReserveCircuit WHERE numDossier=?";
		pS = c.prepareStatement(query);
		pS.setInt(1, numDossier);
		res = pS.executeQuery();
		while (res.next()) {
			ReserveCircuit circuit = (ReserveCircuit) new ReserveCircuit().createFromSQL(res);
			String check = "select sum(nbPersonnesCircuit) from ReserveCircuit rc, Reservation r where rc.numDossier = r.numDossier and idCircuit=? and dateDepartCircuit=?";
			PreparedStatement cPS = c.prepareStatement(check);
			cPS.setString(1, res.getString(1));
			cPS.setDate(2, res.getDate(2));
			ResultSet cRes = cPS.executeQuery();
			System.out.println(check);
			
			check = "select nbPersonnes from DateCircuit where idCircuit=? and dateDepartCircuit=?";
			cPS = c.prepareStatement(check);
			cPS.setString(1, res.getString(1));
			cPS.setDate(2, res.getDate(2));
			ResultSet ccRes = cPS.executeQuery();
			System.out.println(check);
			
			while (ccRes.next()) {
				int nbPlaces = ccRes.getInt(1);
				if (cRes.next())
					nbPlaces -= cRes.getInt(1);
				if (nbPlaces < res.getInt(4)) {
					possible = false;
					circuit.deleteSQL(c);
					circuitsPasOk.add(circuit);
					circuit = null;
					break;
				}
			}
			if(circuit != null)
				circuits.add(circuit);
			cPS.close();
			break;
		}
		pS.close();

		query = "SELECT * FROM ReserveHotel WHERE numDossier=?";
		pS = c.prepareStatement(query);
		pS.setInt(1, numDossier);
		res = pS.executeQuery();
		while (res.next()) {
			ReserveHotel hotel = (ReserveHotel) new ReserveHotel().createFromSQL(res);
			Date curr = new Date(res.getDate(5).getTime());
			while (res.getDate(6).after(curr)) {
				String check = "select sum(rh.nbChambresReservees) from ReserveHotel rh, Reservation r where rh.numDossier = r.numDossier and rh.nomHotel = ? and rh.ville = ? and rh.pays = ? and (dateDepartHotel <= ? and dateArriveeHotel >= ?)";
				PreparedStatement cPS = c.prepareStatement(check);
				cPS.setString(1, res.getString(1));
				cPS.setString(2, res.getString(2));
				cPS.setString(3, res.getString(3));
				cPS.setDate(4, curr);
				cPS.setDate(5, curr);
				ResultSet cRes = cPS.executeQuery();
				
				check = "select nbChambresTotal from Hotel h where nomHotel = ? and ville = ? and pays = ?";
				cPS = c.prepareStatement(check);
				cPS.setString(1, res.getString(1));
				cPS.setString(2, res.getString(2));
				cPS.setString(3, res.getString(3));
				ResultSet ccRes = cPS.executeQuery();
				
				while (ccRes.next()) {
					int nbPlaces = ccRes.getInt(1);
					if (cRes.next())
						nbPlaces -= cRes.getInt(1);
					if (nbPlaces < res.getInt(7)) {
						possible = false;
						hotel.deleteSQL(c);
						hotelsPasOk.add(hotel);
						hotel = null;
						break;
					}
				}
				if(hotel != null)
					hotels.add(hotel);
				cPS.close();
				curr = new Date(curr.getTime() + ((long) 1000) * 60 * 60 * 24);
			}
		}
		pS.close();

		final ReserveCircuitTable recapCircuit = new ReserveCircuitTable(
				Controller.executeQuery("select * from reservecircuit where numDossier = '" + numDossier + "'"));
		grid.add(recapCircuit, 0, 3);
		final ReserveHotelTable recapHotel = new ReserveHotelTable(
				Controller.executeQuery("select * from reservehotel where numDossier = '" + numDossier + "'"));
		grid.add(recapHotel, 1, 3);
		final ReserveVisiteTable recapVisite = new ReserveVisiteTable(
				Controller.executeQuery("select * from reservevisite where numDossier = '" + numDossier + "'"));
		grid.add(recapVisite, 2, 3);

		Text placesOK;
		//possible = false;
		if (possible) {
			placesOK = new Text("Réservation possible");
			dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType);
			dialog.getDialogPane().getButtonTypes().addAll(addButtonType);
		} else {
			placesOK = new Text("Réservation impossible");
			dialog.getDialogPane().getButtonTypes().addAll(editSimulation);
		}
		grid.add(placesOK, 0, 2);

		dialog.getDialogPane().setContent(grid);

		Optional<ButtonType> resDialog = dialog.showAndWait();
		if (resDialog.isPresent()) {
			System.out.println(resDialog.get());
			if(possible) {
				if (resDialog.get() == addButtonType) {
					PopupSearchClient.show(numDossier, nomClient, prenomClient);

				} else if (resDialog.get() == confirmButtonType) {
					if (PopupClient.show(numDossier, nomClient, prenomClient) == false)
						PopupError.bang();
				}
			}
			else if(resDialog.get() == editSimulation) {
				query = "SELECT * FROM ReserveVisite WHERE numDossier=?";
				pS = c.prepareStatement(query);
				pS.setInt(1, numDossier);
				res = pS.executeQuery();
				while (res.next()) {
					ReserveVisite visite = new ReserveVisite();
					visite.createFromSQL(res);
					visites.add(visite);
				}
				pS.close();
				
				GAccordionFX accordion = new GAccordionFX(new Simulation(numDossier, nomClient, prenomClient));
				Controller.container.setPane(accordion);
				
			}
		}
	}

}
