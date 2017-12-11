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
	public Date dateVisite;
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

	public ReserveVisite(String nomLieu, String ville, String pays, int numDossier, Date dateVisite,
			int nbPersonnesVisite) {
		this.nomLieu = new SimpleStringProperty(nomLieu);
		this.ville = new SimpleStringProperty(ville);
		this.pays = new SimpleStringProperty(pays);
		this.numDossier = new SimpleIntegerProperty(numDossier);
		this.dateVisite = dateVisite;
		this.nbPersonnesVisite = new SimpleIntegerProperty(nbPersonnesVisite);
	}

	@Override
	public void insertSQL(Connection c) throws SQLException {
		String insert = "INSERT INTO " + dbName + " VALUES " + "(?, ?, ?, ?, ?, ?)";
		c.commit();
		PreparedStatement preparedStatementInsert = c.prepareStatement(insert);
		preparedStatementInsert.setString(1, this.getNomLieu());
		preparedStatementInsert.setString(2, this.getVille());
		preparedStatementInsert.setString(3, this.getPays());
		preparedStatementInsert.setInt(4, this.getNumDossier());
		preparedStatementInsert.setDate(5, this.getDateVisite());
		preparedStatementInsert.setInt(6, this.getNbPersonnesVisite());
		preparedStatementInsert.executeUpdate();
		preparedStatementInsert.close();
		c.commit();
	}

	@Override
	public void updateSQL(Connection c) throws SQLException {
		String insert = "UPDATE " + dbName
				+ " SET nbPersonnesVisite=? WHERE nomLieu=?, ville=?, pays=?, numDossier=?, dateVisite=?";
		c.commit();
		PreparedStatement preparedStatementInsert = c.prepareStatement(insert);
		preparedStatementInsert.setInt(1, this.getNbPersonnesVisite());
		preparedStatementInsert.setString(2, this.getNomLieu());
		preparedStatementInsert.setString(3, this.getVille());
		preparedStatementInsert.setString(4, this.getPays());
		preparedStatementInsert.setInt(5, this.getNumDossier());
		preparedStatementInsert.setDate(6, this.getDateVisite());
		preparedStatementInsert.executeUpdate();
		preparedStatementInsert.close();
		c.commit();
	}

	@Override
	public Object createFromSQL(ResultSet res) throws SQLException {
		return new ReserveVisite(res.getString(1), res.getString(2), res.getString(3), res.getInt(4), res.getDate(5),
				res.getInt(6));
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

	public void setNomLieu(String nomLieu) {
		this.nomLieu.set(nomLieu);
	}

	public void setVille(String ville) {
		this.ville.set(ville);
	}

	public void setPays(String pays) {
		this.pays.set(pays);
	}

	public void setNumDossier(Integer numDossier) {
		this.numDossier.set(numDossier);
	}

	public void setDateVisite(Date dateVisite) {
		this.dateVisite = dateVisite;
	}

	public void setNbPersonnesVisite(Integer nbPersonnesVisite) {
		this.nbPersonnesVisite.set(nbPersonnesVisite);
	}

	@Override
	public void deleteSQL(Connection c) throws SQLException {
		String insert = "DELETE from " + dbName
				+ " WHERE nomLieu=? and ville=? and pays=? and numDossier=? and dateVisite=?";
		c.commit();
		PreparedStatement preparedStatementInsert = c.prepareStatement(insert);
		preparedStatementInsert.setString(1, this.getNomLieu());
		preparedStatementInsert.setString(2, this.getVille());
		preparedStatementInsert.setString(3, this.getPays());
		preparedStatementInsert.setInt(4, this.getNumDossier());
		preparedStatementInsert.setDate(5, this.getDateVisite());
		preparedStatementInsert.executeUpdate();
		preparedStatementInsert.close();
		c.commit();
	}
}
