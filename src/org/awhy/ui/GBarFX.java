package org.awhy.ui;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

public class GBarFX extends HBox {
	public Text left;
	public Text center;
	public Text right;

	public GBarFX() {

		this.setMinHeight(25);
		this.setAlignment(Pos.CENTER);

		this.left = new Text();
		this.center = new Text();
		this.right = new Text();

		Region region1 = new Region();
		HBox.setHgrow(region1, Priority.ALWAYS);
		Region region2 = new Region();
		HBox.setHgrow(region2, Priority.ALWAYS);

		this.getChildren().addAll(this.left, region1, this.center, region2, this.right);
	}

}
