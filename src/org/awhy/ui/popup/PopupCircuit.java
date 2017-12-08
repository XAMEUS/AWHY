package org.awhy.ui.popup;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Optional;

import org.awhy.core.objects.Circuit;
import org.awhy.core.objects.DateCircuit;
import org.awhy.core.objects.Etapes;
import org.awhy.core.objects.ReserveCircuit;
import org.awhy.core.objects.ReserveVisite;
import org.awhy.core.objects.Simulation;
import org.awhy.ui.Controller;
import org.awhy.ui.pane.CircuitPane;
import org.awhy.ui.tables.DateCircuitTable;
import org.awhy.ui.tables.EtapesTable;
import org.awhy.ui.tables.HotelTable;

import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class PopupCircuit {
	public static void show(CircuitPane tp, Circuit data, Simulation s) {

		Dialog<ButtonType> dialog = new Dialog<>();
		dialog.setTitle("Reserve Circuit");
		dialog.setHeaderText("Réserver le circuit n°" + data.getIdCircuit());

		ButtonType confirmButtonType = new ButtonType("Voir les dates disponibles", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 20, 10, 10));

		// TODO : nb personne dans reserve circuit
		// TextField nbPersonnes = new TextField();
		// grid.add(new Label("Nombre de personnes:"), 0, 0);
		// grid.add(nbPersonnes, 1, 0);

		dialog.getDialogPane().setContent(grid);

		// TODO: afficher les etape dans la popup
		try {
			EtapesTable et = new EtapesTable(
					Controller.executeQuery("select * from etapes where idCircuit = " + data.getIdCircuit()));
			grid.add(et, 0, 0);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		Optional<ButtonType> result = dialog.showAndWait();

		if (result.isPresent()) {
			PopupCircuit.show2(tp, data, s);
			// try {
			// Controller.container.setTableView(new DateCircuitTable(
			// Controller.executeQuery("select * from dateCircuit where
			// idCircuit = " + data.getIdCircuit())));
			// //TODO: reserver date, new popup ?
			// TableView<DateCircuit> v = (TableView<DateCircuit>)
			// (Controller.tableView);
			// v.setRowFactory(tv -> {
			// TableRow<DateCircuit> row = new TableRow<>();
			// row.setOnMouseClicked(event -> {
			// if (event.getClickCount() == 2 && (!row.isEmpty())) {
			// DateCircuit rowData = row.getItem();
			// // tp.setText ne fonctionne pas ?
			// tp.object = new ReserveCircuit(data.getIdCircuit(),
			// rowData.getDateDepartCircuit(),
			// s.getNumDossier(), Integer.valueOf(nbPersonnes.getText()));
			// System.out.println(rowData.getDateDepartCircuit());
			// // TODO : revenir aux circuits
			// }
			// });
			// return row;
			// });
			// } catch (SQLException e) {
			// e.printStackTrace();
			// }
		}

	}

	public static void show2(CircuitPane tp, Circuit data, Simulation s) {

		Dialog<ButtonType> dialog = new Dialog<>();
		dialog.setTitle("Reserve Circuit");
		dialog.setHeaderText(
				"Réserver le circuit n°" + data.getIdCircuit() + "à une date et un nombre de personnes spécifiques");

		ButtonType confirmButtonType = new ButtonType("Continuer", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 20, 10, 10));

		TextField nbPersonnes = new TextField();
		grid.add(new Label("Nombre de personnes:"), 0, 0);
		grid.add(nbPersonnes, 1, 0);

		dialog.getDialogPane().setContent(grid);

		try {
			final DateCircuitTable dct = new DateCircuitTable(
					Controller.executeQuery("select * from dateCircuit where idCircuit = " + data.getIdCircuit()));
			grid.add(dct, 0, 0);

			Optional<ButtonType> result = dialog.showAndWait();

			if (result.isPresent()) {
				// TODO: reserver date, new popup ?
				DateCircuit dc = dct.getSelectionModel().getSelectedItem();
				tp.objects.add(new ReserveCircuit(data.getIdCircuit(), dc.getDateDepartCircuit(), s.getNumDossier(),
						Integer.valueOf(nbPersonnes.getText())));

				ResultSet res = Controller
						.executeQuery("select * from etapes where idCircuit = " + data.getIdCircuit());
				PopupCircuit.show3(tp, data, s, res, Integer.valueOf(nbPersonnes.getText()));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void show3(CircuitPane tp, Circuit data, Simulation s, ResultSet res, int nbPersonnes) {

		try {
			ArrayList<Etapes> etapes = new ArrayList<>();
			while (res.next())
				etapes.add((Etapes) (new Etapes()).createFromSQL(res));
			for (Etapes etape : etapes) {
				
				Dialog<ButtonType> dialog = new Dialog<>();
				dialog.setTitle("Reserve Etape" + etape.getOrdre());
				dialog.setHeaderText("Réservez un hotel à " + etape.getVille());

				ButtonType confirmButtonType = new ButtonType("Continuer", ButtonData.OK_DONE);
				dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType);

				GridPane grid = new GridPane();
				grid.setHgap(10);
				grid.setVgap(10);
				grid.setPadding(new Insets(20, 20, 10, 10));

				dialog.getDialogPane().setContent(grid);

				final HotelTable hotels = new HotelTable(Controller.executeQuery(
						"select * from hotel where ville='" + etape.getVille() + "' AND pays='" + etape.getPays()+"'"));
				grid.add(hotels, 0, 0);

				Optional<ButtonType> result = dialog.showAndWait();

				if (result.isPresent()) {
					// TODO : la date ?
					tp.objects.add(new ReserveVisite(etape.getNomLieu(), etape.getVille(), etape.getPays(),
							s.getNumDossier(), new Date(0), nbPersonnes));
					PopupHotel.show(tp, hotels.getSelectionModel().getSelectedItem(), s);

				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
