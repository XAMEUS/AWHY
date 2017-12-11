package org.awhy.ui.popup;

import java.sql.Date;
import java.util.Optional;

import org.awhy.core.objects.LieuAVisiter;
import org.awhy.core.objects.ReserveVisite;
import org.awhy.core.objects.Simulation;
import org.awhy.ui.pane.VisitePane;
import org.awhy.utils.Debugger;

import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

public class PopupVisite {
	public static boolean show(VisitePane tp, LieuAVisiter data, Simulation s) {

		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("Reserve LAV");
		dialog.setHeaderText("Réserver " + data.getNomLieu() + data.getVille());

		ButtonType confirmButtonType = new ButtonType("Confirmer", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 20, 10, 10));
		
		DatePicker dateVisite = new DatePicker();
		TextField nbPersonnes = new TextField();
		grid.add(new Label("Date de la visite:"), 0, 0);
		grid.add(dateVisite, 0, 1);
		grid.add(new Label("Nombre de places voulues:"), 1, 0);
		grid.add(nbPersonnes, 1, 1);
		
		dialog.getDialogPane().setContent(grid);

		Optional<Pair<String, String>> result = dialog.showAndWait();
		if (result.isPresent()) {
			try {
				if(Integer.valueOf(nbPersonnes.getText()) <= 0)
					return false;
				tp.setText(data.getNomLieu() + data.getVille());
				tp.objects.add(new ReserveVisite(data.getNomLieu(), data.getVille(), data.getPays(), s.getNumDossier(), 
						Date.valueOf(dateVisite.getValue()), Integer.valueOf(nbPersonnes.getText())));
				return true;
			}
			catch (NumberFormatException e) {
				Debugger.println("PopupVisite: valeur incorrecte: " + e.toString());
				PopupError.bang();
				return false;
			}
		}
		return false;
	}
}
