package org.awhy.ui;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import org.awhy.core.Dialog;
import org.awhy.ui.tables.ClientTable;
import org.awhy.ui.tables.LieuAVisiterTable;
import org.awhy.ui.tables.ReserveLieuTable;
import org.awhy.ui.tables.VilleTable;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;

public class GMenuFX extends MenuBar {
	public GMenuFX() {
		this.createFile();
		this.createView();
		this.createProfile();
		this.createDatabase();
		this.createTools();
	}

	private void createFile() {
		Menu file = new Menu(" File");
		MenuItem newFile = new MenuItem("New       ");
		newFile.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Controller.newFile();
			}
		});
		file.getItems().add(newFile);
		// file.getItems().add(new SeparatorMenuItem());
		this.getMenus().add(file);
	}

	private void createView() {
		Menu view = new Menu("View");

		MenuItem villes = new MenuItem("Villes");
		villes.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					Controller.container.setTableView(new VilleTable(Controller.executeQuery("select * from ville")));
				} catch (SQLException e) {
					Controller.alert("SQLException", e);
				}
			}
		});
		view.getItems().add(villes);

		MenuItem lav = new MenuItem("Lieux à visiter");
		lav.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					Controller.container
							.setTableView(new LieuAVisiterTable(Controller.executeQuery("select * from lieuavisiter")));
				} catch (SQLException e) {
					Controller.alert("SQLException", e);
				}
			}
		});
		view.getItems().add(lav);

		MenuItem clients = new MenuItem("Clients");
		clients.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					Controller.container
							.setTableView(new ClientTable(Controller.executeQuery("select * from client")));
				} catch (SQLException e) {
					Controller.alert("SQLException", e);
				}
			}
		});
		view.getItems().add(clients);
		
		Menu reservations = new Menu("Reservations");
		MenuItem lavR = new MenuItem("Lieux à visiter");
		lavR.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					Controller.container
							.setTableView(new ReserveLieuTable(Controller.executeQuery("select * from reservevisite")));
				} catch (SQLException e) {
					Controller.alert("SQLException", e);
				}
			}
		});
		reservations.getItems().add(lavR);
		view.getItems().add(reservations);
		
		
		this.getMenus().add(view);
	}

	private void createProfile() {
		Menu file = new Menu("Profile");
		MenuItem customer = new MenuItem("Customer");
		MenuItem admin = new MenuItem("Admin");
		file.getItems().add(customer);
		// file.getItems().add(new SeparatorMenuItem());
		this.getMenus().add(file);
	}

	private void createTools() {
		Menu tools = new Menu("Tools");
		CheckMenuItem debug = new CheckMenuItem("Debug Mode");
		debug.setSelected(true);
		debug.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Controller.setDebug(debug.isSelected());
			}
		});
		MenuItem sql = new MenuItem("SQL Console");
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

		Menu file = new Menu("Database");

		MenuItem connect = new MenuItem("Connect ");
		connect.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Controller.connect();
			}
		});
		file.getItems().add(connect);
		MenuItem script = new MenuItem("SQL Script");
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
				}
			}
		});
		file.getItems().add(script);
		this.getMenus().add(file);
	}
}
