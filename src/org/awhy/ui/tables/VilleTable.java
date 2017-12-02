package org.awhy.ui.tables;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.awhy.core.objects.Ville;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class VilleTable extends Table<Ville> {

	public VilleTable(ResultSet res) throws SQLException {
		super(new Ville("", ""), res);

		TableColumn<Ville, String> nomVilleCol = new TableColumn<Ville, String>("Ville");
		nomVilleCol.setCellValueFactory(new PropertyValueFactory<Ville, String>("nomVille"));

		TableColumn<Ville, String> paysCol = new TableColumn<Ville, String>("Pays");
		paysCol.setCellValueFactory(new PropertyValueFactory<Ville, String>("pays"));

		this.setItems(this.data);
		this.getColumns().add(nomVilleCol);
		this.getColumns().add(paysCol);
	}

}
