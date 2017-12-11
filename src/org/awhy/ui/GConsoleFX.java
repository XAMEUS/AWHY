package org.awhy.ui;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.LinkedList;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

public class GConsoleFX extends StackPane {

	private GTableFX table;
	private LinkedList<String> history = new LinkedList<>();
	private int c = 0;

	public GConsoleFX() throws SQLException {

		this.setMinWidth(1200);
		this.setMinHeight(700);
		this.table = new GTableFX();
		this.history.add("");

		this.getChildren().add(table);
		StackPane.setMargin(table, new Insets(0, 0, 30, 0));
		TextField input = new TextField();
		input.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				GConsoleFX.this.history.set(GConsoleFX.this.history.size() - 1, input.getText());
				c = GConsoleFX.this.history.size();
				GConsoleFX.this.history.add("");

				try {
					GConsoleFX.this.getChildren().remove(GConsoleFX.this.table);
					table.getColumns().clear();
					table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
					table.buildData(Controller.dialog.executeQuery(input.getText()));
					GConsoleFX.this.getChildren().add(0, table);
				} catch (SQLException e) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Exception Dialog");
					alert.setHeaderText("SQLException");
					alert.setContentText(e.getMessage());

					// Create expandable Exception.
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

					// Set expandable Exception into the dialog pane.
					alert.getDialogPane().setExpandableContent(expContent);

					alert.showAndWait();
				}
				input.clear();
			}
		});
		input.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.UP) {
					if (c > 0) {
						if (c == GConsoleFX.this.history.size() - 1)
							GConsoleFX.this.history.set(c, input.getText());
						c--;
						input.setText(GConsoleFX.this.history.get(c));
					}
				}
				if (event.getCode() == KeyCode.DOWN) {
					if (c < GConsoleFX.this.history.size() - 1) {
						c++;
						input.setText(GConsoleFX.this.history.get(c));
					}
				}
			}
		});
		input.setFont(Font.font("Monospaced"));
		this.getChildren().add(input);
		Platform.runLater(() -> {
			if (!input.isFocused()) {
				input.requestFocus();
			}
		});

		StackPane.setAlignment(input, Pos.BOTTOM_CENTER);

		if (Controller.dialog == null)
			Controller.connect();
	}

}
