package org.awhy.core.objects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Etapes implements Object {

	public final SimpleStringProperty idCircuit;
  	public final SimpleIntegerProperty ordre;
	public final SimpleStringProperty nomLieu;
	public final SimpleStringProperty ville;
	public final SimpleStringProperty pays;
  	public final SimpleIntegerProperty nbJours;
  	public static String dbName = "Etapes";

	public Etapes() {
	    this.idCircuit = new SimpleStringProperty();
		this.ordre = new SimpleIntegerProperty();
	    this.nomLieu = new SimpleStringProperty();
	    this.ville = new SimpleStringProperty();
	    this.pays = new SimpleStringProperty();
	    this.nbJours = new SimpleIntegerProperty();
	}

	public Etapes(String idCircuit, int ordre, String nomLieu,
				   String ville, String pays, int nbJours){
	    this.idCircuit = new SimpleStringProperty(idCircuit);
	    this.ordre = new SimpleIntegerProperty(ordre);
	    this.nomLieu = new SimpleStringProperty(nomLieu);
	    this.ville = new SimpleStringProperty(ville);
	    this.pays = new SimpleStringProperty(pays);
	    this.nbJours = new SimpleIntegerProperty(nbJours);
	}

	@Override
	public void insertSQL(Connection c) throws SQLException {
		String insert = "INSERT INTO " + dbName + " VALUES " + "(?, ?, ?, ?, ?, ?)";
		PreparedStatement preparedStatementInsert = c.prepareStatement(insert);
		preparedStatementInsert.setString(1, this.getIdCircuit());
		preparedStatementInsert.setInt(2, this.getOrdre());
		preparedStatementInsert.setString(3, this.getNomLieu());
		preparedStatementInsert.setString(4, this.getVille());
		preparedStatementInsert.setString(5, this.getPays());
		preparedStatementInsert.setInt(6, this.getNbJours());
		preparedStatementInsert.executeUpdate();
	}

	@Override
	public void updateSQL(Connection c) throws SQLException {
		String insert = "UPDATE " + dbName + " SET nbJours=? WHERE idCircuit=?, ordre=?, nomLieu=?, ville=?, pays=?";
		PreparedStatement preparedStatementInsert = c.prepareStatement(insert);
		preparedStatementInsert.setInt(1, this.getNbJours());
		preparedStatementInsert.setString(2, this.getIdCircuit());
		preparedStatementInsert.setInt(3, this.getOrdre());
		preparedStatementInsert.setString(4, this.getNomLieu());
		preparedStatementInsert.setString(5, this.getVille());
		preparedStatementInsert.setString(6, this.getPays());
		preparedStatementInsert.executeUpdate();
	}

	@Override
	public Object createFromSQL(ResultSet res) throws SQLException {
		return new Etapes(res.getString(1), res.getInt(2), res.getString(3), res.getString(4), res.getString(5), res.getInt(6));
	}

  	public String getIdCircuit() {
		return idCircuit.get();
	}

	public Integer getOrdre() {
		return ordre.get();
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

  	public Integer getNbJours() {
		return nbJours.get();
	}
  	
  	public void setIdCircuit(String idCircuit) {
		this.idCircuit.set(idCircuit);
	}

	public void setOrdre(Integer ordre) {
		this.ordre.set(ordre);
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

  	public void setNbJours(Integer nbJours) {
		this.nbJours.set(nbJours);
	}
}
