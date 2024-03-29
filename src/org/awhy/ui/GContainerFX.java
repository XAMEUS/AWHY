package org.awhy.ui;

import java.sql.SQLException;

import org.awhy.core.objects.Object;
import org.awhy.ui.pane.GAccordionFX;
import org.awhy.ui.tables.Table;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class GContainerFX extends VBox {

	public GBarFX bottom;
	private SplitPane container;
	private GAccordionFX paneLeft;

	public GContainerFX() throws SQLException {
		this.paneLeft = null;
		Controller.container = this;
		ObservableList<Node> children = this.getChildren();
		children.add(new GMenuFX());
		container = new SplitPane();
		children.add(container);
		Controller.connect();

		container.setDividerPositions(0.3);
		// container.getChildren().add(box);
		// container.getChildren().add(gt);
		// HBox.setHgrow(gt, Priority.ALWAYS);
		VBox.setVgrow(container, Priority.ALWAYS);

		this.bottom = new GBarFX();
		this.bottom.left.setFill(Color.GRAY);
		this.bottom.left.setText(" AWHY v0.1");
		children.add(this.bottom);
	}

	public void setTableView(Table<? extends Object> tableView) throws SQLException {
		this.container.getItems().remove(Controller.tableView);
		Controller.tableView = tableView;
		if (tableView != null)
			this.container.getItems().add(tableView);
	}

	public void setPane(GAccordionFX pane) {
		if (this.paneLeft != null) {
			this.container.getItems().remove(this.paneLeft);
			this.paneLeft = null;
		}
		if (pane != null) {
			this.container.getItems().addAll(pane);
			this.paneLeft = pane;
		}
	}
}
