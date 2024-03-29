package org.awhy.ui.popup;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import org.awhy.utils.Debugger;

import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class PopupCircuit {
	public static boolean show(CircuitPane tp, Circuit data, Simulation s) {

		Dialog<ButtonType> dialog = new Dialog<>();
		dialog.setTitle("Reserve Circuit");
		dialog.setHeaderText("Réserver le circuit n°" + data.getIdCircuit());

		ButtonType confirmButtonType = new ButtonType("Voir les dates disponibles", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 20, 10, 10));
		dialog.getDialogPane().setContent(grid);

		try {
			EtapesTable et = new EtapesTable(
					Controller.executeQuery("select * from etapes where idCircuit = " + data.getIdCircuit()));
			grid.add(et, 0, 0);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		Optional<ButtonType> result = dialog.showAndWait();

		if (result.isPresent())
			return PopupCircuit.show2(tp, data, s);
		return false;
	}

	public static boolean show2(CircuitPane tp, Circuit data, Simulation s) {

		Dialog<ButtonType> dialog = new Dialog<>();
		dialog.setTitle("Reserve Circuit");
		dialog.setHeaderText(
				"Réserver le circuit n°" + data.getIdCircuit().trim() + " à une date et un nombre de personnes spécifiques");
		tp.setText("Réserver le circuit n°" + data.getIdCircuit().trim());

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
				DateCircuit dc = dct.getSelectionModel().getSelectedItem();
				Date dateDepart = dc.getDateDepartCircuit();
				tp.objects.add(new ReserveCircuit(data.getIdCircuit(), dateDepart, s.getNumDossier(),
						Integer.valueOf(nbPersonnes.getText())));

				ResultSet res = Controller.executeQuery(
						"select * from etapes where idCircuit = " + data.getIdCircuit() + " order by ordre");
				return PopupCircuit.show3(tp, data, s, res, Integer.valueOf(nbPersonnes.getText()), dateDepart);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NumberFormatException | NullPointerException e) {
			Debugger.println("PopupCircuit: valeur incorrecte: " + e.toString());
			PopupError.bang();
		}
		return false;
	}

	public static boolean show3(CircuitPane tp, Circuit data, Simulation s, ResultSet res, int nbPersonnes,
			Date dateDepart) {

		tp.setText(tp.getText() + " " + nbPersonnes);
		int jours = 0;
		System.out.println("depart: " + dateDepart);
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

				final HotelTable hotels = new HotelTable(Controller.executeQuery("select * from hotel where ville='"
						+ etape.getVille() + "' AND pays='" + etape.getPays() + "'"));
				grid.add(hotels, 0, 0);

				Optional<ButtonType> result = dialog.showAndWait();

				if (result.isPresent()) {
					for (int i = 0; i < etape.getNbJours(); i++) {
						ReserveVisite rv = new ReserveVisite(etape.getNomLieu(), etape.getVille(), etape.getPays(),
								s.getNumDossier(), new Date(dateDepart.getTime() + (jours + i) * 24 * 60 * 60 * 1000),
								nbPersonnes);
						tp.objects.add(rv);
						tp.add(new Text("Etape : " + rv.toString()));
					}
						
					System.out.println("jours: " + jours);
					Date depart = new Date(dateDepart.getTime() + jours * 24 * 60 * 60 * 1000);
					Date arrivee = new Date(dateDepart.getTime() + (jours + etape.getNbJours()) * 24 * 60 * 60 * 1000);
					PopupHotel.show(tp, hotels.getSelectionModel().getSelectedItem(), s, arrivee, depart, nbPersonnes);
					jours += etape.getNbJours();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} catch (NumberFormatException e) {
			Debugger.println("PopupCircuit: valeur incorrecte: " + e.toString());
			PopupError.bang();
			return false;
		}
		return true;
	}

}
