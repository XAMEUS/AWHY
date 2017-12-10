
import java.util.Locale;

import org.awhy.ui.Controller;
import org.awhy.ui.GContainerFX;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Awhy extends Application {
	
	@Override
	public void start(Stage primaryStage) throws Exception {

		Controller.mainWindow = primaryStage;
		Controller.root = new GContainerFX();
		
		Scene scene = new Scene((Parent) Controller.root, 1600, 900);

		// setting color to the scene
		// scene.setFill(Color.BROWN);

		// Setting the title to Stage.
		primaryStage.setTitle("AWHY");

		// Adding the scene to Stage
		primaryStage.setScene(scene);

		// Displaying the contents of the stage
		primaryStage.show();
		
	}

	public static void main(String args[]) {
		// Locale.setDefault(Locale.ENGLISH);
		launch(args);
	}
}