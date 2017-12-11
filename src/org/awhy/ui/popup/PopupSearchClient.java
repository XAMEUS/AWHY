package org.awhy.ui.popup;

import java.sql.SQLException;
import java.util.Optional;

import org.awhy.core.objects.Client;
import org.awhy.ui.Controller;
import org.awhy.ui.tables.ClientTable;

import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableRow;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class PopupSearchClient {
	public static void show(int numDossier, String nom, String prenom) {

		Dialog<ButtonType> dialog = new Dialog<>();
		dialog.setTitle("Client existant");
		dialog.setHeaderText("Chercher un client");

		ButtonType confirmButtonType = new ButtonType("Rechercher", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 20, 10, 10));

		TextField nomClient = new TextField();
		nomClient.setText(nom);
		TextField prenomClient = new TextField();
		prenomClient.setText(prenom);

		grid.add(new Label("Nom:"), 0, 0);
		grid.add(nomClient, 1, 0);
		grid.add(new Label("Prénom:"), 0, 1);
		grid.add(prenomClient, 1, 1);

		dialog.getDialogPane().setContent(grid);

		Optional<ButtonType> result = dialog.showAndWait();

		if (result.isPresent()) {
			String query = "select * from Client";
			String condNom = new String();
			String condPrenom = new String();
			ClientTable ct;

			if (nomClient.getText() != null && !nomClient.getText().trim().isEmpty())
				condNom += "nomClient like '%" + nomClient.getText().trim() + "%'";
			else if (nomClient.getText() != null && !prenomClient.getText().trim().isEmpty())
				condPrenom += "prenomClient like '%" + prenomClient.getText().trim() + "%'";

			if (!condNom.isEmpty() && !condPrenom.isEmpty())
				query += " where " + condNom + " and " + condPrenom;
			else if (!condNom.isEmpty())
				query += " where " + condNom;
			else if (!condPrenom.isEmpty())
				query += " where " + condPrenom;

			try {
				ct = new ClientTable(Controller.executeQuery(query));
				PopupSearchClient.show2(numDossier, ct, nom, prenom);
			} catch (SQLException e) {

				e.printStackTrace();
				try {
					ct = new ClientTable(Controller.executeQuery("select * from Client"));
					PopupSearchClient.show2(numDossier, ct, nom, prenom);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	private static void show2(int numDossier, ClientTable ct, String nom, String prenom) {

		Dialog<ButtonType> dialog = new Dialog<>();
		dialog.setTitle("Client existant");
		dialog.setHeaderText("Sélectionner le client");

		ButtonType confirmButtonType = new ButtonType("Rechercher à nouveau", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 20, 10, 10));

		grid.add(ct, 0, 0);

		ct.setRowFactory(tv -> {
			TableRow<Client> row = new TableRow<>();
			row.setOnMouseClicked(e -> {
				if (e.getClickCount() == 2 && (!row.isEmpty())) {
					Client rowData = row.getItem();
					PopupReservation.show(numDossier, rowData.getIdClient());
					dialog.close();
				}
			});
			return row;
		});

		dialog.getDialogPane().setContent(grid);

		Optional<ButtonType> result = dialog.showAndWait();
		
		if (result.isPresent()) {
			PopupSearchClient.show(numDossier, nom, prenom);
		}

	}
}
