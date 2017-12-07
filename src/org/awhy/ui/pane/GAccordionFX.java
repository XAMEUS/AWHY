package org.awhy.ui.pane;

import java.sql.Date;
import java.sql.SQLException;

import org.awhy.core.objects.Hotel;
import org.awhy.core.objects.ReserveHotel;
import org.awhy.core.objects.Simulation;
import org.awhy.core.objects.Ville;
import org.awhy.ui.Controller;
import org.awhy.ui.tables.CircuitTable;
import org.awhy.ui.tables.HotelTable;
import org.awhy.ui.tables.LieuAVisiterTable;

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

	public final Simulation s;

	public GAccordionFX(Simulation s) {
		this.s = s;
		this.getChildren().add(new VBox());
		Accordion ac = new Accordion();
		StackPane pane = new StackPane();
		pane.setAlignment(Pos.TOP_CENTER);
		pane.getChildren().add(new Text("TODO"));

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
				ac.getPanes().add(tp);
				try {
					// TODO : filter ?
					Controller.container.setTableView(new HotelTable(Controller.executeQuery("select * from hotel")));
					TableView<Hotel> v = (TableView<Hotel>) (Controller.tableView);
					v.setRowFactory(tv -> {
						TableRow<Hotel> row = new TableRow<>();
						row.setOnMouseClicked(e -> {
							if (e.getClickCount() == 2 && (!row.isEmpty())) {
								Hotel rowData = row.getItem();
								tp.object = new ReserveHotel(rowData.getNomHotel(), rowData.getVille(), rowData.getPays(), s.getNumDossier(), new Date(0), new Date(1000), 10, 10);
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
				ac.getPanes().add(new HotelPane());
				try {
					Controller.container
							.setTableView(new LieuAVisiterTable(Controller.executeQuery("select * from lieuavisiter")));
					// TODO : on click, reservation.
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});

		Button b3 = new Button("Add Circuit");
		b3.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ac.getPanes().add(new HotelPane());
				try {
					Controller.container
							.setTableView(new CircuitTable(Controller.executeQuery("select * from circuit")));
					// TODO : on click, reservation.
					// more complex...
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});

		Button b4 = new Button("Confirm");
		b4.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					s.insertSQL(Controller.dialog.getConnection());
					for (TitledPane tp : ac.getPanes()) {
						((TPane) tp).object.insertSQL(Controller.dialog.getConnection());
					}
					Controller.dialog.getConnection().commit();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});

		this.bot = new ToolBar(b1, b2, b3, b4);

		this.getChildren().add(this.bot);

	}

}
