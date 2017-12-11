package org.awhy.ui.tables;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.awhy.core.objects.ReserveVisite;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class ReserveVisiteTable extends Table<ReserveVisite> {

	public ReserveVisiteTable(ResultSet res) throws SQLException {
		super(new ReserveVisite(), res);

		TableColumn<ReserveVisite, String> lieumCol = new TableColumn<ReserveVisite, String>("Lieu");
		lieumCol.setCellValueFactory(new PropertyValueFactory<ReserveVisite, String>("nomLieu"));

		TableColumn<ReserveVisite, String> nomVilleCol = new TableColumn<ReserveVisite, String>("Ville");
		nomVilleCol.setCellValueFactory(new PropertyValueFactory<ReserveVisite, String>("ville"));

		TableColumn<ReserveVisite, String> paysCol = new TableColumn<ReserveVisite, String>("Pays");
		paysCol.setCellValueFactory(new PropertyValueFactory<ReserveVisite, String>("pays"));

		TableColumn<ReserveVisite, Integer> dossierCol = new TableColumn<ReserveVisite, Integer>("Dossier");
		dossierCol.setCellValueFactory(new PropertyValueFactory<ReserveVisite, Integer>("numDossier"));

		TableColumn<ReserveVisite, Date> dateCol = new TableColumn<ReserveVisite, Date>("Date");
		dateCol.setCellValueFactory(new PropertyValueFactory<ReserveVisite, Date>("dateVisite"));

		TableColumn<ReserveVisite, Integer> nbPersonnesCol = new TableColumn<ReserveVisite, Integer>("Quantité");
		nbPersonnesCol.setCellValueFactory(new PropertyValueFactory<ReserveVisite, Integer>("nbPersonnesVisite"));

		this.setItems(this.data);
		this.getColumns().add(lieumCol);
		this.getColumns().add(nomVilleCol);
		this.getColumns().add(paysCol);
		this.getColumns().add(dossierCol);
		this.getColumns().add(dateCol);
		this.getColumns().add(nbPersonnesCol);

	}

}
