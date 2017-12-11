package org.awhy.ui.popup;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Optional;

import org.awhy.core.objects.Reservation;
import org.awhy.ui.Controller;

import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class PopupReservation {
	public static void show(int numDossier, int idClient) {

		Dialog<ButtonType> dialog = new Dialog<>();
		
		dialog.setTitle("Reservation");
		dialog.setHeaderText("Ajouter un paiement");

		ButtonType confirmButtonType = new ButtonType("Confimer", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 20, 10, 10));

		TextField informationDePaiement = new TextField();
		DatePicker dateDuPaiement = new DatePicker();	
		
		
		grid.add(new Label("Numero de dossier : " + Integer.toString(numDossier)), 0, 0);
		grid.add(new Label("	Numéro client : " + Integer.toString(idClient)), 1, 0);
		grid.add(new Label(" "), 0, 1);
		grid.add(new Label("Date du paiement :"), 0, 2);
		grid.add(dateDuPaiement, 1, 2);
		grid.add(new Label("Information de paiement :"), 0, 3);
		grid.add(informationDePaiement, 1, 3);
		
		dialog.getDialogPane().setContent(grid);
		Optional<ButtonType> result = dialog.showAndWait();

		if (result.isPresent()) {

			Reservation r;
			try {
				r = new Reservation(numDossier, Date.valueOf(dateDuPaiement.getValue()), informationDePaiement.getText(), idClient);
				r.insertSQL(Controller.dialog.getConnection());
				Controller.dialog.getConnection().commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			catch (NumberFormatException | NullPointerException e) {
				PopupError.bang();
			}
		}
	}

}
