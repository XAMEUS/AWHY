package org.awhy.ui.popup;

import java.util.Optional;

import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class PopupNom {
	public String nom;
	public String prenom;
	public boolean show() {
		Dialog<ButtonType> dialog = new Dialog<>();
		dialog.setTitle("Nom Client");
		dialog.setHeaderText("Nom Client");

		ButtonType confirmButtonType = new ButtonType("Confimer", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 20, 10, 10));

		TextField nomClient = new TextField();
		TextField prenomClient = new TextField();

		grid.add(new Label("Nom:"), 0, 0);
		grid.add(nomClient, 1, 0);
		grid.add(new Label("Prénom:"), 0, 1);
		grid.add(prenomClient, 1, 1);

		dialog.getDialogPane().setContent(grid);

		Optional<ButtonType> result = dialog.showAndWait();

		if (result.isPresent()) {
			try {
				if(nomClient.getText().trim().isEmpty() || prenomClient.getText().trim().isEmpty())
					return false;
				this.nom = nomClient.getText();
				this.prenom = prenomClient.getText();
				return true;
			} catch (NullPointerException | NumberFormatException e) {
			}
		}
		return false;
	}

}
