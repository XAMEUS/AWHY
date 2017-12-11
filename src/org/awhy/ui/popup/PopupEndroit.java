package org.awhy.ui.popup;

import java.util.Optional;

import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class PopupEndroit {
	public String ville;
	public String pays;

	public int show() {
		Dialog<ButtonType> dialog = new Dialog<>();
		dialog.setTitle("Recherche dans un emplacement");
		dialog.setHeaderText("Recherche dans un emplacement");

		ButtonType confirmButtonType = new ButtonType("Confimer", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 20, 10, 10));

		TextField ville = new TextField();
		TextField pays = new TextField();

		grid.add(new Label("Ville:"), 0, 0);
		grid.add(ville, 1, 0);
		grid.add(new Label("Pays:"), 0, 1);
		grid.add(pays, 1, 1);

		dialog.getDialogPane().setContent(grid);

		Optional<ButtonType> result = dialog.showAndWait();
		this.ville = "";
		this.pays = "";
		if (result.isPresent()) {
			try {
				this.ville = ville.getText().trim();
				this.pays = pays.getText().trim();
				if (!this.ville.isEmpty())
					this.ville = this.ville.substring(0, 1).toUpperCase()
							+ (this.ville.length() > 1 ? this.ville.substring(1).toLowerCase() : "");
				if (!this.pays.isEmpty())
					this.pays = this.pays.substring(0, 1).toUpperCase()
							+ (this.pays.length() > 1 ? this.pays.substring(1).toLowerCase() : "");
				return 1;
			} catch (NullPointerException | NumberFormatException e) {
				return -1;
			}
		}
		return 0;
	}

}
