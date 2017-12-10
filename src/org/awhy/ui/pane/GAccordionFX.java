package org.awhy.ui.pane;

import java.sql.Date;
import java.sql.SQLException;

import org.awhy.core.objects.Circuit;
import org.awhy.core.objects.Hotel;
import org.awhy.core.objects.LieuAVisiter;
import org.awhy.core.objects.Object;
import org.awhy.core.objects.ReserveHotel;
import org.awhy.core.objects.Simulation;
import org.awhy.core.objects.Ville;
import org.awhy.ui.Controller;
import org.awhy.ui.popup.PopupCircuit;
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
	private Button confirmer;

	public final Simulation s;

	public GAccordionFX(Simulation s) {
		this.s = s;
		this.getChildren().add(new VBox());
		Accordion ac = new Accordion();
		StackPane pane = new StackPane();
		pane.setAlignment(Pos.TOP_CENTER);
		pane.getChildren().add(new Text("TODO"));
		this.confirmer = new Button("Confirmer");
		final Button confirmer = this.confirmer;

		ScrollPane sp = new ScrollPane(new StackPane(ac));
		sp.setFitToHeight(true);
		sp.setFitToWidth(true);

		this.top = new ToolBar(new Text("Sim: " + s.getNumDossier()));
		this.getChildren().add(this.top);
		this.getChildren().add(sp);

		Button b1 = new Button("Add Hotel");
		b1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				HotelPane tp = new HotelPane();
				try {
					// TODO : filter ?
					Controller.container.setTableView(new HotelTable(Controller.executeQuery("select * from hotel")));
					TableView<Hotel> v = (TableView<Hotel>) (Controller.tableView);
					v.setRowFactory(tv -> {
						TableRow<Hotel> row = new TableRow<>();
						row.setOnMouseClicked(e -> {
							if (e.getClickCount() == 2 && (!row.isEmpty())) {
								Hotel rowData = row.getItem();
								if(PopupHotel.show(tp, rowData, s, null, null, 0)) {
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

		Button b2 = new Button("Add LAV");
		b2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				VisitePane tp = new VisitePane();
				try {
					Controller.container.setTableView(new LieuAVisiterTable(Controller.executeQuery("select * from lieuavisiter")));
					TableView<LieuAVisiter> v = (TableView<LieuAVisiter>) (Controller.tableView);
					v.setRowFactory(tv -> {
						TableRow<LieuAVisiter> row = new TableRow<>();
						row.setOnMouseClicked(e -> {
							if (e.getClickCount() == 2 && (!row.isEmpty())) {
								LieuAVisiter rowData = row.getItem();
								if(PopupVisite.show(tp, rowData, s)) {
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

		Button b3 = new Button("Add Circuit");
		b3.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				CircuitPane tp = new CircuitPane();
				try {
					Controller.container
							.setTableView(new CircuitTable(Controller.executeQuery("select * from circuit")));
					// TODO : on click, reservation.
					TableView<Circuit> v = (TableView<Circuit>) (Controller.tableView);
					v.setRowFactory(tv -> {
						TableRow<Circuit> row = new TableRow<>();
						row.setOnMouseClicked(e -> {
							if (e.getClickCount() == 2 && (!row.isEmpty())) {
								Circuit rowData = row.getItem();
								if(PopupCircuit.show(tp, rowData, s)) {
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

				switch(nom.show()) {
				case(-1):
					PopupError.bang();
				case(0):
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
							Controller.container
							.setTableView(new SimulationTable(Controller.executeQuery("select * from Simulation")));
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
