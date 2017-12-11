package org.awhy.ui;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.Optional;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.util.Pair;

public class GConnectFX {
	public static void connnect() throws SQLException {

		if (Controller.dialog == null)
			Controller.dialog = new org.awhy.core.Dialog();

		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("AWHY: Connection");
		dialog.setHeaderText("Se connecter à la base de données");

		ButtonType loginButtonType = new ButtonType("Connection", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(loginButtonType);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 20, 10, 10));

		TextField username = new TextField();
		username.setPromptText("Username");
		username.setPrefWidth(400);
		PasswordField password = new PasswordField();
		password.setPromptText("Password");
		TextField url = new TextField();
		url.setPromptText("Url");
		username.setText(Controller.dialog.user);
		password.setText(Controller.dialog.passwd);
		url.setText(Controller.dialog.url);

		grid.add(new Label("Utilisateur"), 0, 0);
		grid.add(username, 1, 0);
		grid.add(new Label("Mot de passe"), 0, 1);
		grid.add(password, 1, 1);
		grid.add(new Label("URL"), 0, 2);
		grid.add(url, 1, 2);

		dialog.getDialogPane().setContent(grid);

		Platform.runLater(() -> username.requestFocus());

		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == loginButtonType) {
				Controller.dialog.user = username.getText();
				Controller.dialog.passwd = password.getText();
				Controller.dialog.url = url.getText();
				try {
					Controller.dialog.connect();
				} catch (SQLException e) {
					e.printStackTrace();

					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Exception Dialog");
					alert.setHeaderText("SQLException");
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
			}
			return null;
		});

		Optional<Pair<String, String>> result = dialog.showAndWait();

		result.ifPresent(usernamePassword -> {
			System.out.println("Username=" + usernamePassword.getKey() + ", Password=" + usernamePassword.getValue());
		});
	}
}
