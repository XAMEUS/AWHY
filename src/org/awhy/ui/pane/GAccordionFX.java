package org.awhy.ui.pane;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.awhy.core.objects.Circuit;
import org.awhy.core.objects.Hotel;
import org.awhy.core.objects.LieuAVisiter;
import org.awhy.core.objects.Object;
import org.awhy.core.objects.Simulation;
import org.awhy.ui.Controller;
import org.awhy.ui.popup.PopupCircuit;
import org.awhy.ui.popup.PopupEndroit;
import org.awhy.ui.popup.PopupError;
import org.awhy.ui.popup.PopupHotel;
import org.awhy.ui.popup.PopupNom;
import org.awhy.ui.popup.PopupVisite;
import org.awhy.ui.tables.CircuitTable;
import org.awhy.ui.tables.HotelTable;
import org.awhy.ui.tables.LieuAVisiterTable;
import org.awhy.ui.tables.SimulationTable;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class GAccordionFX extends VBox {

	private ToolBar top;
	private ToolBar bot;
	public Button confirmer;
	
	public Accordion ac;

	public final Simulation s;

	public GAccordionFX(Simulation s) {
		this.s = s;
		this.getChildren().add(new VBox());
		ac = new Accordion();
		StackPane pane = new StackPane();
		pane.setAlignment(Pos.TOP_CENTER);
		pane.getChildren().add(new Text("TODO"));
		this.confirmer = new Button("Confirmer");
		final Button confirmer = this.confirmer;

		ScrollPane sp = new ScrollPane(new StackPane(ac));
		sp.setFitToHeight(true);
		sp.setFitToWidth(true);

		this.top = new ToolBar(new Text("Simulation n°: " + s.getNumDossier()));
		this.getChildren().add(this.top);
		this.getChildren().add(sp);

		Button b1 = new Button("Ajouter Hotel");
		b1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				HotelPane tp = new HotelPane(ac);
				try {
					PopupEndroit endroit = new PopupEndroit();
					endroit.show();
					String query = "select * from hotel";
					if (!endroit.ville.isEmpty() || !endroit.pays.isEmpty())
						query += " where ";
					if (!endroit.ville.isEmpty())
						query += "ville LIKE ?";
					if (!endroit.ville.isEmpty() && !endroit.pays.isEmpty())
						query += " and ";
					if (!endroit.pays.isEmpty())
						query += "pays LIKE ?";
					PreparedStatement pS = Controller.dialog.getConnection().prepareStatement(query);
					if (!endroit.ville.isEmpty())
						pS.setString(1, "%" + endroit.ville + "%");
					if (!endroit.pays.isEmpty())
						pS.setString((endroit.ville.isEmpty()) ? 1 : 2, "%" + endroit.pays + "%");
					ResultSet res = pS.executeQuery();
					Controller.container.setTableView(new HotelTable(res));
					pS.close();
					TableView<Hotel> v = (TableView<Hotel>) (Controller.tableView);
					v.setRowFactory(tv -> {
						TableRow<Hotel> row = new TableRow<>();
						row.setOnMouseClicked(e -> {
							if (e.getClickCount() == 2 && (!row.isEmpty())) {
								Hotel rowData = row.getItem();
								if (PopupHotel.show(tp, rowData, s, null, null, 0)) {
									ac.getPanes().add(tp);
									confirmer.setVisible(true);
								} else
									PopupError.bang();
							}
						});
						return row;
					});
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});

		Button b2 = new Button("Ajouter LAV");
		b2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				VisitePane tp = new VisitePane(ac);
				try {
					PopupEndroit endroit = new PopupEndroit();
					endroit.show();
					String query = "select * from lieuavisiter";
					if (!endroit.ville.isEmpty() || !endroit.pays.isEmpty())
						query += " where ";
					if (!endroit.ville.isEmpty())
						query += "ville LIKE ?";
					if (!endroit.ville.isEmpty() && !endroit.pays.isEmpty())
						query += " and ";
					if (!endroit.pays.isEmpty())
						query += "pays LIKE ?";
					PreparedStatement pS = Controller.dialog.getConnection().prepareStatement(query);
					if (!endroit.ville.isEmpty())
						pS.setString(1, "%" + endroit.ville + "%");
					if (!endroit.pays.isEmpty())
						pS.setString((endroit.ville.isEmpty()) ? 1 : 2, "%" + endroit.pays + "%");
					ResultSet res = pS.executeQuery();
					Controller.container.setTableView(new LieuAVisiterTable(res));
					pS.close();
					TableView<LieuAVisiter> v = (TableView<LieuAVisiter>) (Controller.tableView);
					v.setRowFactory(tv -> {
						TableRow<LieuAVisiter> row = new TableRow<>();
						row.setOnMouseClicked(e -> {
							if (e.getClickCount() == 2 && (!row.isEmpty())) {
								LieuAVisiter rowData = row.getItem();
								if (PopupVisite.show(tp, rowData, s)) {
									ac.getPanes().add(tp);
									confirmer.setVisible(true);
								}
							}
						});
						return row;
					});
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});

		Button b3 = new Button("Ajouter Circuit");
		b3.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				CircuitPane tp = new CircuitPane(ac);
				try {
					PopupEndroit endroit = new PopupEndroit();
					endroit.show();
					String query = "select * from circuit";
					if (!endroit.ville.isEmpty() || !endroit.pays.isEmpty())
						query += " where ";
					if (!endroit.ville.isEmpty())
						query += "villeDepart LIKE ?";
					if (!endroit.ville.isEmpty() && !endroit.pays.isEmpty())
						query += " and ";
					if (!endroit.pays.isEmpty())
						query += "paysDepart LIKE ?";
					PreparedStatement pS = Controller.dialog.getConnection().prepareStatement(query);
					if (!endroit.ville.isEmpty())
						pS.setString(1, "%" + endroit.ville + "%");
					if (!endroit.pays.isEmpty())
						pS.setString((endroit.ville.isEmpty()) ? 1 : 2, "%" + endroit.pays + "%");
					ResultSet res = pS.executeQuery();
					Controller.container.setTableView(new CircuitTable(res));
					pS.close();
					TableView<Circuit> v = (TableView<Circuit>) (Controller.tableView);
					v.setRowFactory(tv -> {
						TableRow<Circuit> row = new TableRow<>();
						row.setOnMouseClicked(e -> {
							if (e.getClickCount() == 2 && (!row.isEmpty())) {
								Circuit rowData = row.getItem();
								if (PopupCircuit.show(tp, rowData, s)) {
									ac.getPanes().add(tp);
									confirmer.setVisible(true);
								}
							}
						});
						return row;
					});
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});

		confirmer.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				PopupNom nom = new PopupNom();

				switch (nom.show()) {
				case (-1):
					PopupError.bang();
				case (0):
					return;
				}
				try {
					s.setNomClient(nom.nom);
					s.setPrenomClient(nom.prenom);
					s.insertSQL(Controller.dialog.getConnection());
					for (TitledPane tp : ac.getPanes()) {
						for (Object o : ((TPane) tp).objects) {
							System.out.println(o);
							o.insertSQL(Controller.dialog.getConnection());
							Controller.container.setPane(null);
							Controller.container.setTableView(
									new SimulationTable(Controller.executeQuery("select * from Simulation")));
						}
					}
					Controller.dialog.getConnection().commit();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		confirmer.setVisible(false);

		this.bot = new ToolBar(b1, b2, b3, this.confirmer);
		this.getChildren().add(this.bot);

	}

}
