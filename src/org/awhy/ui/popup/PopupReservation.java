package org.awhy.ui.popup;

import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class PopupReservation {
	public static void show() {
		Dialog<ButtonType> dialog = new Dialog<>();
		dialog.setTitle("Reservation");
		dialog.setHeaderText("Ajouter un paiement");

		ButtonType confirmButtonType = new ButtonType("Confimer", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType);
		
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 20, 10, 10));
		
//		int numDossier = 		
//		DatePicker datePaiement = new DatePicker();
//		TextField infoPaiement = new TextField();
//		int idClient = (int)
		
		
		//Checker si le client existe dans la base de donnée
		//Si non, popup de creation client puis creer la reservation
		//si oui, juste creer la reservation
	}

}
