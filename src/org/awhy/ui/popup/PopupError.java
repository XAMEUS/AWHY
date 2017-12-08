package org.awhy.ui.popup;

import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.util.Pair;

public class PopupError {
	public static void bang() {

		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("Erreur!");
		dialog.setHeaderText("Erreur de saisie utilisateur!");

		ButtonType confirmButtonType = new ButtonType("Fermer", ButtonData.CANCEL_CLOSE);
		dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType);
		dialog.showAndWait();
	}
}