package org.awhy.ui.pane;

import java.util.ArrayList;

import org.awhy.core.objects.Object;

import javafx.scene.Node;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;

public class TPane extends TitledPane {
	public ArrayList<Object> objects = new ArrayList<>();
	private GridPane content;
	private int i = 0;
	public boolean valid = true;
	
	public TPane() {
		content = new GridPane();
		this.setContent(this.content);
	}
	
	public void add(Node e) {
		this.content.add(e, 0, i++);
	}
	
	public void setInvalid() {
		this.setText(this.getText() + " [INVALIDE]");
		this.valid = false;
	}
}
