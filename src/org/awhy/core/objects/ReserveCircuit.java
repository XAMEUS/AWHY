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
	public Date dateDepartCircuit;
	public final SimpleIntegerProperty numDossier;
	public final SimpleIntegerProperty nbPersonnesCircuit;
	public static String dbName = "ReserveCircuit";
	
	public ReserveCircuit() {
		this.idCircuit = new SimpleStringProperty();
		this.dateDepartCircuit = new Date(0);
		this.numDossier = new SimpleIntegerProperty();
		this.nbPersonnesCircuit = new SimpleIntegerProperty();
	}

	public ReserveCircuit(String idCircuit, Date dateDepartCircuit, int numDossier, int nbPersonnesCircuit) {
		this.idCircuit = new SimpleStringProperty(idCircuit);
		this.dateDepartCircuit = dateDepartCircuit;
		this.numDossier = new SimpleIntegerProperty(numDossier);
		this.nbPersonnesCircuit = new SimpleIntegerProperty(nbPersonnesCircuit);
	}

	@Override
	public void insertSQL(Connection c) throws SQLException {
		String insert = "INSERT INTO " + dbName + " VALUES " + "(?, ?, ?, ?)";
		c.commit();
		PreparedStatement preparedStatementInsert = c.prepareStatement(insert);
		preparedStatementInsert.setString(1, this.getIdCircuit());
		preparedStatementInsert.setDate(2, this.getDateDepartCircuit());
		preparedStatementInsert.setInt(3, this.getNumDossier());
		preparedStatementInsert.setInt(4, this.getNbPersonnesCircuit());
		preparedStatementInsert.executeUpdate();
		preparedStatementInsert.close();
		c.commit();
	}

	@Override
	public void updateSQL(Connection c) throws SQLException {
		String insert = "UPDATE " + dbName + " SET nbPersonnesCircuit=? WHERE idCircuit=?, datePaiement=?, numDossier=?";
		c.commit();
		PreparedStatement preparedStatementInsert = c.prepareStatement(insert);
		preparedStatementInsert.setInt(1, this.getNbPersonnesCircuit());
		preparedStatementInsert.setString(2, this.getIdCircuit());
		preparedStatementInsert.setDate(3, this.getDateDepartCircuit());
		preparedStatementInsert.setInt(4, this.getNumDossier());
		preparedStatementInsert.executeUpdate();
		preparedStatementInsert.close();
		c.commit();
	}

	@Override
	public Object createFromSQL(ResultSet res) throws SQLException {
		return new ReserveCircuit(res.getString(1), res.getDate(2), res.getInt(3), res.getInt(4));
	}


	public String getIdCircuit() {
		return idCircuit.get();
	}

	public Date getDateDepartCircuit() {
		return dateDepartCircuit;
	}

	public Integer getNumDossier() {
		return numDossier.get();
	}
	
	public Integer getNbPersonnesCircuit() {
		return nbPersonnesCircuit.get();
	}
	
	public void setIdCircuit(String idCircuit) {
		this.idCircuit.set(idCircuit);
	}

	public void setDepartCircuit(Date dateDepartCircuit) {
		this.dateDepartCircuit = dateDepartCircuit;		
	}

	//Attention à l'utilisation de setNumDossier !
	public void setNumDossier(Integer numDossier) {
		this.numDossier.set(numDossier);
	}
	
	public void setNbPersonnesCircuit(Integer nbPersonnesCircuit) {
		this.nbPersonnesCircuit.set(nbPersonnesCircuit);
	}

	@Override
	public void deleteSQL(Connection c) throws SQLException {
		// TODO Auto-generated method stub
		
	}

}
