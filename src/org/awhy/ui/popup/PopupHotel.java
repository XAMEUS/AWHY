package org.awhy.ui.popup;

import java.sql.Date;
import java.util.Optional;

import org.awhy.core.objects.Hotel;
import org.awhy.core.objects.ReserveHotel;
import org.awhy.core.objects.Simulation;
import org.awhy.ui.pane.TPane;
import org.awhy.utils.Debugger;

import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class PopupHotel {
	public static boolean show(TPane tp, Hotel data, Simulation s, Date dateArrivee, Date dateDepart, int n) {

		Dialog<ButtonType> dialog = new Dialog<>();
		dialog.setTitle("Reserve Hotel");
		dialog.setHeaderText("Réserver " + data.getNomHotel());

		ButtonType confirmButtonType = new ButtonType("Confirmer", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 20, 10, 10));

		DatePicker arrivee = new DatePicker();
		if (dateArrivee != null)
			arrivee.setValue(dateArrivee.toLocalDate());
		DatePicker depart = new DatePicker();
		if (dateDepart != null)
			depart.setValue(dateDepart.toLocalDate());
		TextField nbPersonnes = new TextField();
		nbPersonnes.setText(String.valueOf(n));
		TextField nbPDej = new TextField();
		grid.add(new Label("Arrivée à l'hôtel:"), 0, 0);
		grid.add(depart, 1, 0);
		grid.add(new Label("Départ de l'hôtel:"), 0, 1);
		grid.add(arrivee, 1, 1);
		grid.add(new Label("Nombre de chambres:"), 0, 2);
		grid.add(nbPersonnes, 1, 2);
		grid.add(new Label("Nombre de petits déjeuners:"), 0, 3);
		grid.add(nbPDej, 1, 3);

		dialog.getDialogPane().setContent(grid);

		Optional<ButtonType> result = dialog.showAndWait();
		if (result.isPresent()) {
			try {
				if (Integer.valueOf(nbPersonnes.getText()) <= 0 || Integer.valueOf(nbPDej.getText()) < 0
						|| Date.valueOf(depart.getValue()).after(Date.valueOf(arrivee.getValue())))
					return false;
				ReserveHotel rh = new ReserveHotel(data.getNomHotel(), data.getVille(), data.getPays(), s.getNumDossier(),
						Date.valueOf(depart.getValue()), Date.valueOf(arrivee.getValue()),
						Integer.valueOf(nbPersonnes.getText()), Integer.valueOf(nbPDej.getText()));
				tp.add(new Text(rh.toString()));
				tp.objects.add(rh);
				return true;

			} catch (NumberFormatException e) {
				Debugger.println("PopupHotel: valeur incorrecte: " + e.toString());
				PopupError.bang();
				return false;
			}
		}
		return false;
	}

}
