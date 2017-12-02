package org.awhy.ui;

import java.sql.SQLException;

import org.awhy.core.Dialog;
import org.awhy.core.objects.Object;
import org.awhy.ui.tables.Table;
import org.awhy.ui.tables.VilleTable;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class GContainerFX extends VBox {

	public GBarFX bottom;
	private SplitPane container;

	public GContainerFX() throws SQLException {
		Controller.container = this;
		ObservableList<Node> children = this.getChildren();
		children.add(new GMenuFX());
		container = new SplitPane();
		children.add(container);
//		Controller.connect();
//		this.setTableView(new VilleTable(Controller.dialog.executeQuery("select * from ville")));
		System.out.println("ok");
		container.getItems().addAll(new GAccordionFX());
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
		this.container.getItems().add(tableView);
	}

}
