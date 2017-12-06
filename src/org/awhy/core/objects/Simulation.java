package org.awhy.core.objects;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.beans.property.SimpleIntegerProperty;


public class Simulation implements Object {

	public final SimpleIntegerProperty numDossier;
	public static String dbName = "Simulation";
	
	public void insertSQL(Connection c) throws SQLException {
		String insert = "INSERT INTO " + dbName + " VALUES " + "(?, ?, ?, ?, ?, ?)";
		PreparedStatement preparedStatementInsert = c.prepareStatement(insert);
		preparedStatementInsert.setString(1, this.getNomLieu());
		preparedStatementInsert.setString(2, this.getVille());
		preparedStatementInsert.setString(3, this.getPays());
		preparedStatementInsert.setInt(4, this.getNumDossier());
		preparedStatementInsert.setDate(5, this.getDateVisite());
		preparedStatementInsert.setInt(6, this.getNbPersonnesVisite());
		preparedStatementInsert.executeUpdate();
	}

	public void updateSQL(Connection c) throws SQLException {
		String insert = "UPDATE " + dbName + " SET nbPersonnesVisite=? WHERE nomLieu=?, ville=?, pays=?, numDossier=?, dateVisite=?"
		PreparedStatement preparedStatementInsert = c.prepareStatement(insert);
		preparedStatementInsert.setString(1, this.getNbPersonnesVisite());
		preparedStatementInsert.setString(2, this.getNomLieu());
		preparedStatementInsert.setString(3, this.getVille());
		preparedStatementInsert.setString(4, this.getPays());
		preparedStatementInsert.setString(5, this.getNumDossier());
		preparedStatementInsert.setString(6, this.getDateVisite());
		preparedStatementInsert.executeUpdate();
	}
	
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
	public void insertSQL(Connection c) throws SQLException {
		// TODO Auto-generated method stub
	}

	@Override
	public Object createFromSQL(Connection c) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
}
