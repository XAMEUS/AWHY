package org.awhy.ui;

import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class GMenuFX extends MenuBar {
	public GMenuFX() {
		this.createFile();
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
		Menu file = new Menu("Databse");
		MenuItem newFile = new MenuItem("Connect ");
		newFile.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Controller.connect();
			}
		});
		file.getItems().add(newFile);
		this.getMenus().add(file);
	}
}
