package org.awhy.ui;

import java.io.File;
import java.sql.SQLException;

import org.awhy.core.Dialog;
import org.awhy.utils.Debugger;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;
import javafx.stage.Window;

public class Controller {

	static GConsoleFX console;
	public static Window mainWindow;
	public static SplitPane splitPane;
	public static Node root;
	public static Dialog dialog;

	public static void newFile() {
		System.out.println("TODO: newFile()");
	}

	public static void setDebug(boolean b) {
		Debugger.setStatus(b);
	}

	public static void showSQLConsole() throws SQLException {
		Stage s = new Stage();
		s.setTitle("AWHY - SQL Console");
		Parent root = new GConsoleFX();
		Scene scene = new Scene(root);
		s.setScene(scene);
		s.show();
	}
	
	public static void connect() {
		try {
			GConnectFX.connnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void executeSQLFile(File file) {
		System.out.println("TODO: executeSQLFile " + file.getAbsolutePath());
	}

}
