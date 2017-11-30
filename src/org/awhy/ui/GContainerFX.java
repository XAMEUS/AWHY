package org.awhy.ui;

import java.sql.SQLException;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class GContainerFX extends VBox {
	
	public GBarFX bottom;
	
	public GContainerFX() throws SQLException {
		ObservableList<Node> children = this.getChildren();
		children.add(new GMenuFX());
		GTableFX gt = new GTableFX();
		children.add(gt);
		VBox.setVgrow(gt, Priority.ALWAYS);

		this.bottom = new GBarFX();
		this.bottom.left.setFill(Color.GRAY);
		this.bottom.left.setText(" AWHY v0.1");
		children.add(this.bottom);
	}
	
}
