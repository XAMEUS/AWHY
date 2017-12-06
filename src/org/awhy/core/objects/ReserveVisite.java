package org.awhy.core.objects;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ReserveVisite implements Object {

	public final SimpleStringProperty nomLieu;
	public final SimpleStringProperty ville;
	public final SimpleStringProperty pays;
	public final SimpleIntegerProperty numDossier;
	public final Date dateVisite;
	public final SimpleIntegerProperty nbPersonnesVisite;
	public static String dbName = "ReserveVisite";
	
	public ReserveVisite() {
		this.nomLieu = new SimpleStringProperty();
		this.ville = new SimpleStringProperty();
		this.pays = new SimpleStringProperty();
		this.numDossier = new SimpleIntegerProperty();
		this.dateVisite = new Date(0);
		this.nbPersonnesVisite = new SimpleIntegerProperty();
	}

	public ReserveVisite(String nomLieu, String ville, String pays, int numDossier, Date dateVisite, int nbPersonnesVisite) {
		this.nomLieu = new SimpleStringProperty(nomLieu);
		this.ville = new SimpleStringProperty(ville);
		this.pays = new SimpleStringProperty(pays);
		this.numDossier = new SimpleIntegerProperty(numDossier);
		this.dateVisite = dateVisite;
		this.nbPersonnesVisite = new SimpleIntegerProperty(nbPersonnesVisite);
	}
	
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

	@Override
	public Object createFromSQL(ResultSet res) throws SQLException {
		return new ReserveVisite(res.getString(1), res.getString(2), res.getString(3), res.getInt(4), res.getDate(5), res.getInt(6));
	}


	public String getNomLieu() {
		return nomLieu.get();
	}

	public String getVille() {
		return ville.get();
	}

	public String getPays() {
		return pays.get();
	}

	public Integer getNumDossier() {
		return numDossier.get();
	}

	public Date getDateVisite() {
		return dateVisite;
	}

	public Integer getNbPersonnesVisite() {
		return nbPersonnesVisite.get();
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
