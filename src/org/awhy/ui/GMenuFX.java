package org.awhy.ui;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import org.awhy.core.objects.Reservation;
import org.awhy.core.objects.Simulation;
import org.awhy.ui.popup.PopupError;
import org.awhy.ui.popup.PopupInfoReservation;
import org.awhy.ui.popup.PopupReservation;
import org.awhy.ui.popup.PopupSimulation;
import org.awhy.ui.tables.CircuitTable;
import org.awhy.ui.tables.ClientTable;
import org.awhy.ui.tables.DateCircuitTable;
import org.awhy.ui.tables.EtapesTable;
import org.awhy.ui.tables.HotelTable;
import org.awhy.ui.tables.LieuAVisiterTable;
import org.awhy.ui.tables.ReservationTable;
import org.awhy.ui.tables.ReserveCircuitTable;
import org.awhy.ui.tables.ReserveHotelTable;
import org.awhy.ui.tables.ReserveVisiteTable;
import org.awhy.ui.tables.SimulationTable;
import org.awhy.ui.tables.VilleTable;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;

public class GMenuFX extends MenuBar {
	public GMenuFX() {
		this.createFile();
		this.createView();
		this.createDatabase();
		this.createTools();
		this.createViewAgence();
	}

	private void createFile() {
		Menu file = new Menu("Simulation");
		MenuItem newFile = new MenuItem("Nouveau");
		newFile.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					Controller.newFile();
				} catch (SQLException e) {
					Controller.alert("ERROR", e);
				}
			}
		});
		file.getItems().add(newFile);

		// file.getItems().add(new SeparatorMenuItem());
		this.getMenus().add(file);
	}

	private void createView() {
		Menu view = new Menu("Vues");

		MenuItem villes = new MenuItem("Villes");
		villes.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Controller.container.setPane(null);
				try {
					Controller.container
					.setTableView(new VilleTable(Controller.executeQuery("select * from Ville")));
				} catch (SQLException e) {
					Controller.alert("SQLException", e);
				}
			}
		}) ;
		view.getItems().add(villes);

		MenuItem lav = new MenuItem("Lieux à visiter");
		lav.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Controller.container.setPane(null);
				try {
					Controller.container
					.setTableView(new LieuAVisiterTable(Controller.executeQuery("select * from LieuAvisiter")));
				} catch (SQLException e) {
					Controller.alert("SQLException", e);
				}
			}
		});
		view.getItems().add(lav);


		MenuItem hotels = new MenuItem("Hotels");
		hotels.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Controller.container.setPane(null);
				try {
					Controller.container
					.setTableView(new HotelTable(Controller.executeQuery("select * from Hotel")));
				} catch (SQLException e) {
					Controller.alert("SQLException", e);
				}
			}
		});
		view.getItems().add(hotels);

		MenuItem circuits = new MenuItem("Circuits");
		circuits.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Controller.container.setPane(null);
				try {
					Controller.container
					.setTableView(new CircuitTable(Controller.executeQuery("select * from Circuit")));
				} catch (SQLException e) {
					Controller.alert("SQLException", e);
				}
			}
		});
		view.getItems().add(circuits);

		MenuItem datecircuits = new MenuItem("Date des circuits");
		datecircuits.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					Controller.container
					.setTableView(new DateCircuitTable(Controller.executeQuery("select * from DateCircuit")));
				} catch (SQLException e) {
					Controller.alert("SQLException", e);
				}
			}
		});
		view.getItems().add(datecircuits);
		
		MenuItem etapes = new MenuItem("Etapes");
		etapes.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					Controller.container
					.setTableView(new EtapesTable(Controller.executeQuery("select * from Etapes")));
				} catch (SQLException e) {
					Controller.alert("SQLException", e);
				}
			}
		});
		view.getItems().add(etapes);

		this.getMenus().add(view);
	}

	private void createTools() {
		Menu tools = new Menu("Outils");
		CheckMenuItem debug = new CheckMenuItem("Débogage");
		debug.setSelected(true);
		debug.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Controller.setDebug(debug.isSelected());
			}
		});
		MenuItem sql = new MenuItem("Console SQL");
		sql.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					Controller.showSQLConsole();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		tools.getItems().add(debug);
		tools.getItems().add(sql);
		this.getMenus().add(tools);
	}

	private void createDatabase() {

		Menu file = new Menu("BDD");

		MenuItem connect = new MenuItem("Connection ");
		connect.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Controller.connect();
			}
		});
		file.getItems().add(connect);
		MenuItem script = new MenuItem("Script SQL");
		script.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Open SQL file");
				File file = fileChooser.showOpenDialog(Controller.mainWindow);
				try {
					Controller.executeSQLFile(file);
				} catch (SQLException e) {
					Controller.alert("SQLException", e);
				} catch (IOException e) {
				Controller.alert("IOException", e);
				} catch (NullPointerException e) {
					PopupError.bang();
				}
			}
		});
		file.getItems().add(script);
		this.getMenus().add(file);
	}
	
	private void createViewAgence(){
		Menu agence = new Menu("Agence");
		
		MenuItem clients = new MenuItem("Clients");
		clients.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Controller.container.setPane(null);
				try {
					Controller.container
							.setTableView(new ClientTable(Controller.executeQuery("select * from Client")));
				} catch (SQLException e) {
					Controller.alert("SQLException", e);
				}
			}
		});
		agence.getItems().add(clients);

		Menu simu = new Menu("Simulations");
		MenuItem simuAll = new MenuItem("Toutes");
		simuAll.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					Controller.container
							.setTableView(new SimulationTable(Controller.executeQuery("select * from Simulation")));
					TableView<Simulation> v = (TableView<Simulation>) (Controller.tableView);
					v.setRowFactory(tv -> {
						TableRow<Simulation> row = new TableRow<>();
						row.setOnMouseClicked(e -> {
							if (e.getClickCount() == 2 && (!row.isEmpty())) {
								Simulation rowData = row.getItem();
								try {
									PopupSimulation.show(rowData.getNumDossier(), rowData.getNomClient(), rowData.getPrenomClient(), Controller.dialog.getConnection());
								} catch (Exception e1) {
									e1.printStackTrace();
								}
							}
						});
						return row;
					});
				} catch (SQLException e) {
					Controller.alert("SQLException", e);
				}
			}
		});
		simu.getItems().add(simuAll);
		
		MenuItem simuNotOK = new MenuItem("Non payées");
		simuNotOK.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					Controller.container
							.setTableView(new SimulationTable(Controller.executeQuery
									("select * from Simulation where numDossier not in (select numDossier from Reservation )")));
					TableView<Simulation> v = (TableView<Simulation>) (Controller.tableView);
					v.setRowFactory(tv -> {
						TableRow<Simulation> row = new TableRow<>();
						row.setOnMouseClicked(e -> {
							if (e.getClickCount() == 2 && (!row.isEmpty())) {
								Simulation rowData = row.getItem();
								try {
									PopupSimulation.show(rowData.getNumDossier(), rowData.getNomClient(), rowData.getPrenomClient(), Controller.dialog.getConnection());
									Controller.container
									.setTableView(new SimulationTable(Controller.executeQuery
											("select * from Simulation where numDossier not in (select numDossier from Reservation )")));
								} catch (Exception e1) {
									e1.printStackTrace();
								}
							}
						});
						return row;
					});
				} catch (SQLException e) {
					Controller.alert("SQLException", e);
				}
			}
		});
		simu.getItems().add(simuNotOK);
		
		MenuItem lavS = new MenuItem("Lieux à visiter");
		lavS.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					Controller.container
							.setTableView(new ReserveVisiteTable(Controller.executeQuery("select * from ReserveVisite")));
				} catch (SQLException e) {
					Controller.alert("SQLException", e);
				}
			}
		});
		simu.getItems().add(lavS);
		
		MenuItem hotelsS = new MenuItem("Hotels");
		hotelsS.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					Controller.container
							.setTableView(new ReserveHotelTable(Controller.executeQuery("select * from ReserveHotel")));
				} catch (SQLException e) {
					Controller.alert("SQLException", e);
				}
			}
		});
		simu.getItems().add(hotelsS);
		
		MenuItem circuitS = new MenuItem("Circuits");
		circuitS.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					Controller.container
							.setTableView(new ReserveCircuitTable(Controller.executeQuery("select * from ReserveCircuit")));
				} catch (SQLException e) {
					Controller.alert("SQLException", e);
				}
			}
		});
		simu.getItems().add(circuitS);
		
		agence.getItems().add(simu);
		
		
		Menu dispo = new Menu("Réservations");
		MenuItem reservation = new MenuItem("Dossiers");
		reservation.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					Controller.container
					.setTableView(new ReservationTable(Controller.executeQuery("SELECT * from Reservation")));
					TableView<Reservation> v = (TableView<Reservation>) (Controller.tableView);
					v.setRowFactory(tv -> {
						TableRow<Reservation> row = new TableRow<>();
						row.setOnMouseClicked(e -> {
							if (e.getClickCount() == 2 && (!row.isEmpty())) {
								Reservation rowData = row.getItem();
								try {
									PopupInfoReservation.show(rowData.getNumDossier(), rowData.getIdClient(), Controller.dialog.getConnection());
								} catch (Exception e1) {
									e1.printStackTrace();
								}
							}
						});
						return row;
					});

				} catch (SQLException e) {
					Controller.alert("SQLException", e);
				}
			}
		});
		dispo.getItems().add(reservation);
		
		MenuItem lavR = new MenuItem("Lieux à visiter");
		lavR.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					Controller.container
							.setTableView(new ReserveVisiteTable(Controller.executeQuery("SELECT rv.* FROM ReserveVisite rv, Reservation r WHERE rv.numDossier = r.numDossier")));
				} catch (SQLException e) {
					Controller.alert("SQLException", e);
				}
			}
		});
		dispo.getItems().add(lavR);

		MenuItem hotelsR = new MenuItem("Hotels");
		hotelsR.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					Controller.container
							.setTableView(new ReserveHotelTable(Controller.executeQuery("SELECT rh.* FROM ReserveHotel rh, Reservation r WHERE rh.numDossier = r.numDossier")));
				} catch (SQLException e) {
					Controller.alert("SQLException", e);
				}
			}
		});
		dispo.getItems().add(hotelsR);

		MenuItem CircuitsR = new MenuItem("Circuits");
		CircuitsR.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					Controller.container
							.setTableView(new ReserveCircuitTable(Controller.executeQuery("SELECT rc.* FROM ReserveCircuit rc, Reservation r WHERE rc.numDossier = r.numDossier")));
				} catch (SQLException e) {
					Controller.alert("SQLException", e);
				}
			}
		});
		dispo.getItems().add(CircuitsR);
		
		agence.getItems().add(dispo);
		
		this.getMenus().add(agence);
	}
}
