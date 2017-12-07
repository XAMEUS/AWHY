package org.awhy.ui.popup;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Optional;

import org.awhy.core.objects.Circuit;
import org.awhy.core.objects.DateCircuit;
import org.awhy.core.objects.ReserveCircuit;
import org.awhy.core.objects.ReserveHotel;
import org.awhy.core.objects.Simulation;
import org.awhy.ui.Controller;
import org.awhy.ui.pane.CircuitPane;
import org.awhy.ui.tables.DateCircuitTable;
import org.awhy.ui.tables.EtapesTable;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

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

		TextField nbPersonnes = new TextField();
		grid.add(new Label("Nombre de personnes:"), 0, 0);
		grid.add(nbPersonnes, 1, 0);

		dialog.getDialogPane().setContent(grid);

		try {
			Controller.container.setTableView(new EtapesTable(
					Controller.executeQuery("select * from etapes where idCircuit = " + data.getIdCircuit())));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		Optional<ButtonType> result = dialog.showAndWait();

		if (result.isPresent()) {
			
			try {
				Controller.container.setTableView(new DateCircuitTable(
						Controller.executeQuery("select * from dateCircuit where idCircuit = " + data.getIdCircuit())));
				//TODO: reserver date, new popup ?
				TableView<DateCircuit> v = (TableView<DateCircuit>) (Controller.tableView);
				v.setRowFactory(tv -> {
					TableRow<DateCircuit> row = new TableRow<>();
					row.setOnMouseClicked(event -> {
						if (event.getClickCount() == 2 && (!row.isEmpty())) {
							DateCircuit rowData = row.getItem();
							// tp.setText ne fonctionne pas ?
							tp.object = new ReserveCircuit(data.getIdCircuit(), rowData.getDateDepartCircuit(), 
									s.getNumDossier(), Integer.valueOf(nbPersonnes.getText()));
							System.out.println(rowData.getDateDepartCircuit());
							// TODO : revenir aux circuits
						}
					});
					return row;
				});
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

}
