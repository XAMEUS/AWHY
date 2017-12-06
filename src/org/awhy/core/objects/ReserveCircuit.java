package org.awhy.core.objects;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ReserveCircuit implements Object {

	public final SimpleStringProperty idCircuit;
	public final Date datePaiement;
	public final SimpleIntegerProperty numDossier;
	public final SimpleIntegerProperty idClient;
	public static String dbName = "ReserveCircuit";
	
	public ReserveCircuit() {
		this.idCircuit = new SimpleStringProperty();
		this.datePaiement = new Date(0);
		this.numDossier = new SimpleIntegerProperty();
		this.idClient = new SimpleIntegerProperty();
	}

	public ReserveCircuit(String idCircuit, Date datePaiement, int numDossier,int idClient) {
		this.idCircuit = new SimpleStringProperty(idCircuit);
		this.datePaiement = datePaiement;
		this.numDossier = new SimpleIntegerProperty(numDossier);
		this.idClient = new SimpleIntegerProperty(idClient);
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
		return new ReserveCircuit(res.getString(1), res.getDate(2), res.getInt(3), res.getInt(4));
	}


	public String getIdCircuit() {
		return idCircuit.get();
	}

	public Date getDatePaiement() {
		return datePaiement;
	}

	public Integer getNumDossier() {
		return numDossier.get();
	}

	public Integer getIdClient() {
		return idClient.get();
	}
	
	
	public void setIdCircuit(String idCircuit) {
		this.idCircuit.set(idCircuit);
	}

//Comment je fais le set d'une date? Suppression du final?	
//	public void setDatePaiement(Date datePaiement) {
//		this.datePaiement = datePaiement;		
//		this.datePaiement.setDate(datePaiement);
//		this.datePaiement.set(datePaiement);
//	}

	public void setNumDossier(Integer numDossier) {
		this.numDossier.set(numDossier);
	}

	public void setIdClient(Integer idClient) {
		this.idClient.set(idClient);
	}

	@Override
	public Object createFromSQL(Connection c) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
}
