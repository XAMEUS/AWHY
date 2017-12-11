package org.awhy.ui.pane;

import java.util.ArrayList;

import org.awhy.core.objects.Object;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class TPane extends TitledPane {
	public ArrayList<Object> objects = new ArrayList<>();
	private GridPane content;
	private int i = 0;
	public boolean valid = true;
	
	public TPane(Accordion ac) {
		content = new GridPane();
		Button button = new Button("Enlever");
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ac.getPanes().remove(TPane.this);
			}
		});
		this.setContent(new VBox(content, button));
	}
	
	public void add(Node e) {
		this.content.add(e, 0, i++);
	}
	
	public void setInvalid() {
		this.setText(this.getText() + " [INVALIDE - SUPPRIME]");
		this.valid = false;
		this.setContent(null);
	}
}
