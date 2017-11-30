package org.awhy.ui;

import java.sql.SQLException;

import org.awhy.core.Dialog;
import org.awhy.utils.Debugger;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Controller {

	static GConsoleFX console;
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
		if (Controller.dialog == null) {
			Controller.dialog = new Dialog();
			Controller.dialog.connect();
		}
		Stage s = new Stage();
		s.setTitle("AWHY - SQL Console");
		Parent root = new GConsoleFX();
		s.getIcons().add(new Image("img/palm-tree512.png"));
		Scene scene = new Scene(root);
		s.setScene(scene);
		s.show();
	}
	
	public static void connect() {
		GConnectFX.connnect();
	}

}
