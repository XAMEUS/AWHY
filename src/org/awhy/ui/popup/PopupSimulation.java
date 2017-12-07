package org.awhy.ui.popup;
import javafx.scene.text.Text;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

import org.awhy.core.objects.Simulation;

import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

public class PopupSimulation {
	public static void show(int numDossier, Connection c) throws SQLException {

		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("Récapitulatif de la simulation");
		dialog.setHeaderText("Récapitulatif de la simulation numéro " + numDossier);
		
		ButtonType confirmButtonType = new ButtonType("Confirmer", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType);
		
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
		while(res.next()) {
			if(dateMin == null || (res.getDate(1) != null && dateMin.after(res.getDate(1))))
				dateMin = res.getDate(1);
			if(dateMax == null || (res.getDate(2) != null && res.getDate(2).after(dateMax)))
				dateMax = res.getDate(2);
		}
		pS.close();
		
		query = "SELECT min(dateDepartCircuit), max(nbJoursTotal + dateDepartCircuit) FROM ReserveCircuit R, Circuit C WHERE numDossier=? and R.idCircuit=C.idCircuit";
		pS = c.prepareStatement(query);
		pS.setInt(1, numDossier);
		res = pS.executeQuery();
		while(res.next()) {
			if(dateMin == null || (res.getDate(1) != null && dateMin.after(res.getDate(1))))
				dateMin = res.getDate(1);
			if(dateMax == null || (res.getDate(2) != null && res.getDate(2).after(dateMax)))
				dateMax = res.getDate(2);
		}
		pS.close();
		
		query = "SELECT min(dateVisite), max(dateVisite) FROM ReserveVisite R WHERE numDossier=?";
		pS = c.prepareStatement(query);
		pS.setInt(1, numDossier);
		res = pS.executeQuery();
		while(res.next()) {
			if(dateMin == null || (res.getDate(1) != null && dateMin.after(res.getDate(1))))
				dateMin = res.getDate(1);
			if(dateMax == null || (res.getDate(2) != null && res.getDate(2).after(dateMax)))
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
		while(res.next())
			if(res.getInt(1) > nbPersonnes) nbPersonnes = res.getInt(1);
		pS.close();

		query = "SELECT max(nbChambresReservees) FROM ReserveHotel WHERE numDossier=?";
		pS = c.prepareStatement(query);
		pS.setInt(1, numDossier);
		res = pS.executeQuery();
		while(res.next())
			if(res.getInt(1) > nbPersonnes) nbPersonnes = res.getInt(1); 
		pS.close();

		query = "SELECT max(nbPersonnesCircuit) FROM ReserveCircuit WHERE numDossier=?";
		pS = c.prepareStatement(query);
		pS.setInt(1, numDossier);
		res = pS.executeQuery();
		while(res.next())
			if(res.getInt(1) > nbPersonnes) nbPersonnes = res.getInt(1); 
		pS.close();

		Text nbPersonnesText = new Text(nbPersonnes + " personne.s");
		grid.add(nbPersonnesText, 0, 1);
		
		// Les réservations
		
		
		// Le pognon
		int cout = 0;
		query = "SELECT sum(prixCircuit) FROM ReserveCircuit R, Circuit C WHERE numDossier=? and R.idCircuit = C.idCircuit";
		pS = c.prepareStatement(query);
		pS.setInt(1, numDossier);
		res = pS.executeQuery();
		while(res.next())
			cout += res.getInt(1); 
		pS.close();

		query = "SELECT sum(prixChambre * nbChambresReservees + prixPetitDejeuner * nbPetitDejReserves) FROM ReserveHotel R, Hotel H WHERE numDossier=? and R.nomHotel = H.nomHotel";
		pS = c.prepareStatement(query);
		pS.setInt(1, numDossier);
		res = pS.executeQuery();
		while(res.next())
			cout += res.getInt(1); 
		pS.close();

		query = "SELECT sum(prix) FROM ReserveVisite R, LieuAVisiter L WHERE numDossier=? and R.nomLieu = L.nomLieu";
		pS = c.prepareStatement(query);
		pS.setInt(1, numDossier);
		res = pS.executeQuery();
		while(res.next())
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
		while(res.next()) {
			if(!possible) break;
			String check = "select (dc.nbPersonnes - sum(rcd.nbPersonnesCircuit)) as nbPlaces from DateCircuit dc, (select * from ReserveCircuit rc where exists (select * from Reservation r where r.numDossier = rc.numDossier) and rc.idCircuit = ? and rc.dateDepartCircuit = ?) rcd where dc.idCircuit = rcd.idCircuit and dc.dateDepartCircuit = rcd.dateDepartCircuit group by dc.idCircuit, dc.dateDepartCircuit, dc.nbPersonnes";
			PreparedStatement cPS = c.prepareStatement(check);
			cPS.setString(1, res.getString(2));
			cPS.setDate(2, res.getDate(3));
			ResultSet cRes = cPS.executeQuery();
			while(cRes.next())
				if(cRes.getInt(1) < res.getInt(1))
					possible = false;
			cPS.close();
		}
		pS.close();

		query = "SELECT nomHotel, ville, pays, nbChambresReservees, dateDepartHotel, dateArriveeHotel FROM ReserveHotel WHERE numDossier=?";
		pS = c.prepareStatement(query);
		pS.setInt(1, numDossier);
		res = pS.executeQuery();
		while(res.next()) {
			if(!possible) break;
			Date curr = new Date(res.getDate(5).getTime());
			while(res.getDate(6).after(curr)) {
				if(!possible) break;
				String check = "select (h.nbChambresTotal - sum(rhd.nbChambresReservees)) as nbPlaces from Hotel h, (select * from ReserveHotel rh where exists (select * from Reservation r where r.numDossier = rh.numDossier) and (dateDepartHotel <= ? and dateArriveeHotel > ?) and rh.nomHotel = ? and rh.ville = ? and rh.pays = ?) rhd where h.nomHotel = ? and h.ville = ? and h.pays = ? group by h.nomHotel, h.ville, h.pays, h.nbChambresTotal";
				PreparedStatement cPS = c.prepareStatement(check);
				cPS.setDate(1, curr);
				cPS.setDate(2, curr);
				cPS.setString(3, res.getString(1));
				cPS.setString(4, res.getString(2));
				cPS.setString(5, res.getString(3));
				cPS.setString(6, res.getString(1));
				cPS.setString(7, res.getString(2));
				cPS.setString(8, res.getString(3));
				ResultSet cRes = cPS.executeQuery();
				while(cRes.next())
					if(res.getInt(4) > cRes.getInt(1))
						possible = false;
				cPS.close();

			}
			curr = new Date(curr.getTime() + ((long) 1000) * 60 * 60 * 24);
		}
		pS.close();

		Text placesOK;
		if(possible)
			placesOK = new Text("Réservation possible");
		else
			placesOK = new Text("Réservation impossible");
		grid.add(placesOK, 0, 2);	


		dialog.getDialogPane().setContent(grid);

		dialog.showAndWait();
		
		//tp.object = new ReserveHotel(data.getNomHotel(), data.getVille(), data.getPays(), s.getNumDossier(), 
		//		Date.valueOf(depart.getValue()), Date.valueOf(arrivee.getValue()), Integer.valueOf(nbPersonnes.getText()), Integer.valueOf(nbPDej.getText()));
	}

}
