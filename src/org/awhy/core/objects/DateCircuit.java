package org.awhy.core.objects;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class DateCircuit implements Object {

	public final SimpleStringProperty idCircuit;
	public final Date dateDepartCircuit;
  	public final SimpleIntegerProperty nbPersonnes;

	public DateCircuit() {
	    this.idCircuit = new SimpleStringProperty();
		this.dateDepartCircuit = new Date(0);
		this.nbPersonnes = new SimpleIntegerProperty();
	}

	public DateCircuit(String idCircuit, Date dateDepartCircuit, int nbPersonnes) {
		this.idCircuit = new SimpleStringProperty(idCircuit);
		this.dateDepartCircuit = dateDepartCircuit;
		this.nbPersonnes = new SimpleIntegerProperty(nbPersonnes);
	}

	@Override
	public Object createFromSQL(ResultSet res) throws SQLException {
		return new DateCircuit(res.getString(1), res.getDate(2), res.getInt(3));
	}


	public String getIdCircuit() {
		return idCircuit.get();
	}

	public Date getDateDepartCircuit() {
		return dateDepartCircuit;
	}

	public Integer getNbPersonnes() {
		return nbPersonnes.get();
	}
}
