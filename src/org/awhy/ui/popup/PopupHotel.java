package org.awhy.ui.popup;

import java.sql.Date;
import java.util.Optional;

import org.awhy.core.objects.Hotel;
import org.awhy.core.objects.ReserveHotel;
import org.awhy.core.objects.Simulation;
import org.awhy.ui.pane.TPane;

import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class PopupHotel {
	public static boolean show(TPane tp, Hotel data, Simulation s) {

		Dialog<ButtonType> dialog = new Dialog<>();
		dialog.setTitle("Reserve Hotel");
		dialog.setHeaderText("Réserver " + data.getNomHotel());

		ButtonType confirmButtonType = new ButtonType("Confirmer", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 20, 10, 10));

		DatePicker depart = new DatePicker();
		DatePicker arrivee = new DatePicker();
		TextField nbPersonnes = new TextField();
		TextField nbPDej = new TextField();
		grid.add(new Label("Départ:"), 0, 0);
		grid.add(depart, 1, 0);
		grid.add(new Label("Arrivée:"), 0, 1);
		grid.add(arrivee, 1, 1);
		grid.add(new Label("Nombre de chambres:"), 0, 2);
		grid.add(nbPersonnes, 1, 2);
		grid.add(new Label("Nombre de petits déjeuners:"), 0, 3);
		grid.add(nbPDej, 1, 3);

		dialog.getDialogPane().setContent(grid);
		
		Optional<ButtonType> result = dialog.showAndWait();

		if (result.isPresent()) {
			// TODO : if Integer.valueOf(nbPersonnes.getText() == 0 -> pas de reservation
			tp.setText(data.getNomHotel());
			tp.objects.add(new ReserveHotel(data.getNomHotel(), data.getVille(), data.getPays(), s.getNumDossier(), 
					Date.valueOf(depart.getValue()), Date.valueOf(arrivee.getValue()), Integer.valueOf(nbPersonnes.getText()), Integer.valueOf(nbPDej.getText())));
		}
		return true;

	}

}
