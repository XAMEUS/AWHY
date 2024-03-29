package org.awhy.ui;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.awhy.core.Dialog;
import org.awhy.core.objects.Object;
import org.awhy.core.objects.Simulation;
import org.awhy.ui.pane.GAccordionFX;
import org.awhy.ui.tables.Table;
import org.awhy.utils.Debugger;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.stage.Window;

public class Controller {

	static GConsoleFX console;
	public static Window mainWindow;
	public static SplitPane splitPane;
	public static Node root;
	public static Dialog dialog;
	public static GContainerFX container;
	public static Table<? extends Object> tableView = null;
	public static GAccordionFX g;

	public static void newFile() throws SQLException {
		Controller.container.setPane(new GAccordionFX(new Simulation(Controller.dialog)));
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

	public static void alert(String title, Exception e) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Exception Dialog");
		alert.setHeaderText(title);
		alert.setContentText(e.getMessage());

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		String exceptionText = sw.toString();

		Label label = new Label("The exception stacktrace was:");

		TextArea textArea = new TextArea(exceptionText);
		textArea.setEditable(false);
		textArea.setWrapText(true);

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(label, 0, 0);
		expContent.add(textArea, 0, 1);

		alert.getDialogPane().setExpandableContent(expContent);

		alert.showAndWait();

	}

	public static void connect() {
		try {
			GConnectFX.connnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static ResultSet executeQuery(String sql) throws SQLException {
		if (Controller.dialog == null)
			Controller.connect();
		return Controller.dialog.executeQuery(sql);
	}

	public static void executeSQLFile(File file) throws SQLException, IOException {
		if (Controller.dialog == null)
			Controller.connect();
		Controller.dialog.executeFile(file.getPath(), true);
	}

}
