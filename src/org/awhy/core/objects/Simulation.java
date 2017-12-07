package org.awhy.core.objects;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.awhy.core.Dialog;

import javafx.beans.property.SimpleIntegerProperty;


public class Simulation implements Object {

	public final SimpleIntegerProperty numDossier;
	public static String dbName = "Simulation";
	
	public void insertSQL(Connection c) throws SQLException {
		String insert = "INSERT INTO " + dbName + " VALUES " + "(?)";
		PreparedStatement preparedStatementInsert = c.prepareStatement(insert);
		preparedStatementInsert.setInt(1, this.getNumDossier());
		preparedStatementInsert.executeUpdate();
	}

	public Simulation() {
		this.numDossier = new SimpleIntegerProperty();
	}
	
	public Simulation(Dialog d) throws SQLException {
		ResultSet r = d.executeQuery("select numdossier.nextval from simulation");
		long id = 0;
		if (r.next()) {
			id = r.getLong(1);
			System.out.println("id : " + id);
		} else {
			System.out.println("empty : select numdossier.nextval from simulation");
		}
		this.numDossier = new SimpleIntegerProperty((int) id);
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

	//Attention à l'utilisation de setNumDossier !
	public void setNumDossier(Integer numDossier) {
		this.numDossier.set(numDossier);
	}
	
	@Override
	public void updateSQL(Connection c) throws SQLException {
	}
}
