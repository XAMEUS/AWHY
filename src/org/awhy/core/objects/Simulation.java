package org.awhy.core.objects;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.beans.property.SimpleIntegerProperty;


public class Simulation implements Object {

	public final SimpleIntegerProperty numDossier;

	public Simulation() {
		this.numDossier = new SimpleIntegerProperty();
	}

	public Simulation(int numDossier) {
		this.numDossier = new SimpleIntegerProperty(numDossier);
	}

	@Override
	public Object createFromSQL(ResultSet res) throws SQLException {
		return new Simulation(res.getInt(1));
	}

	public Integer getNumDossier() {
		return numDossier.get();
	}

	@Override
	public Object insertSQL(Connection c) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createFromSQL(Connection c) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
}
