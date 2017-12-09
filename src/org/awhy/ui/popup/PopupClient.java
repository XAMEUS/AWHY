package org.awhy.ui.popup;

import java.sql.SQLException;
import java.time.Year;
import java.util.Optional;

import org.awhy.core.objects.Client;
import org.awhy.ui.Controller;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class PopupClient {
	public static boolean show(int numDossier, String nom, String prenom) {

		Dialog<ButtonType> dialog = new Dialog<>();
		dialog.setTitle("Nouveau client");
		dialog.setHeaderText("Ajouter un client");

		ButtonType confirmButtonType = new ButtonType("Confimer", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 20, 10, 10));

		TextField nomClient = new TextField();
		nomClient.setText(nom);
		TextField prenomClient = new TextField();
		prenomClient.setText(prenom);
		ChoiceBox<String> typeClient = new ChoiceBox<>(
				FXCollections.observableArrayList("individuel", "societe", "groupe"));
		TextField adresseClient = new TextField();
		TextField emailClient = new TextField();
		TextField telClient = new TextField();
		int anneeEnregistrement = (int) Year.now().getValue();

		grid.add(new Label("Nom:"), 0, 0);
		grid.add(nomClient, 1, 0);
		grid.add(new Label("Prénom:"), 0, 1);
		grid.add(prenomClient, 1, 1);
		grid.add(new Label("Type de client:"), 0, 2);
		grid.add(typeClient, 1, 2);
		grid.add(new Label("Adresse:"), 0, 3);
		grid.add(adresseClient, 1, 3);
		grid.add(new Label("Email:"), 0, 4);
		grid.add(emailClient, 1, 4);
		grid.add(new Label("Téléphone:"), 0, 5);
		grid.add(telClient, 1, 5);

		dialog.getDialogPane().setContent(grid);

		Optional<ButtonType> result = dialog.showAndWait();

		if (result.isPresent()) {

			Client c;
			try {
				if(nomClient.getText().trim().isEmpty() || prenomClient.getText().trim().isEmpty() || !emailClient.getText().matches("[A-Za-z].*?@[A-Za-z].*\\.[A-Za-z].*"))
					return false;
				Integer.parseInt(telClient.getText());
				c = new Client(Controller.dialog, nomClient.getText(), prenomClient.getText(), typeClient.getValue(),
						adresseClient.getText(), emailClient.getText(), telClient.getText(), anneeEnregistrement);
				c.insertSQL(Controller.dialog.getConnection());
				PopupReservation.show(numDossier, c.getIdClient());
				return true;
			} catch (NullPointerException | SQLException | NumberFormatException e) {
				return false;
			}
		}
		return true;
	}

}
