package org.awhy.ui.pane;

import javafx.geometry.Pos;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class GAccordionFX extends VBox {

	public GAccordionFX() {
		this.getChildren().add(new VBox());
		Accordion ac = new Accordion();
		StackPane pane = new StackPane();
		pane.setAlignment(Pos.TOP_CENTER);
		pane.getChildren().add(new Text("TODO"));
		TitledPane tp = new TitledPane("title", pane);
		tp.expandedProperty().set(true);
		ac.getPanes().add(tp);
		ac.getPanes().add(new TitledPane("Reservation", new Text("aze")));

		ScrollPane sp = new ScrollPane(new StackPane(ac));
		sp.setFitToHeight(true);
		sp.setFitToWidth(true);
		this.getChildren().add(new ToolBar(new Text("File: 7893 - idC: 42")));
		this.getChildren().add(sp);
		ToolBar tb = new ToolBar(new Button("add"));

		VBox.setVgrow(sp, Priority.ALWAYS);

		this.getChildren().add(tb);

	}
	
}
