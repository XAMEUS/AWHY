package org.awhy.ui;

import java.sql.SQLException;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class GContainerFX extends VBox {

	public GBarFX bottom;

	public GContainerFX() throws SQLException {
		ObservableList<Node> children = this.getChildren();
		children.add(new GMenuFX());
		GTableFX gt = new GTableFX();
		SplitPane container = new SplitPane();
		children.add(container);
		container.getItems().addAll(new GAccordionFX(), gt);
		// container.getChildren().add(box);
		// container.getChildren().add(gt);
		HBox.setHgrow(gt, Priority.ALWAYS);
		VBox.setVgrow(container, Priority.ALWAYS);

		this.bottom = new GBarFX();
		this.bottom.left.setFill(Color.GRAY);
		this.bottom.left.setText(" AWHY v0.1");
		children.add(this.bottom);
	}

}
