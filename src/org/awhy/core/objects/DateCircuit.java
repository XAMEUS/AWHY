package org.awhy.core.objects;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class DateCircuit implements Object {

	public final SimpleStringProperty idCircuit;
	public final Date dateDepartCircuit;
  	public final SimpleIntegerProperty nbPersonnes;

	public static String dbName = "DateCircuit";  	
  	
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

	public void insertSQL(Connection c) throws SQLException {
		String insert = "INSERT INTO " + dbName	+ " VALUES " + "(?, ?, ?)";
		PreparedStatement preparedStatementInsert = c.prepareStatement(insert);
		preparedStatementInsert.setString(1, this.getIdCircuit());
		preparedStatementInsert.setDate(2, this.getDateDepartCircuit());
		preparedStatementInsert.setInt(3, this.getNbPersonnes());
		preparedStatementInsert.executeUpdate();
	}

	public void updateSQL(Connection c) throws SQLException {
		String insert = "UPDATE " + dbName + " SET nbPersonnes=? WHERE idCircuit=?, dateDepartCircuit=?";
		PreparedStatement preparedStatementInsert = c.prepareStatement(insert);
		preparedStatementInsert.setInt(1, this.getNbPersonnes());
		preparedStatementInsert.setString(2, this.getIdCircuit());
		preparedStatementInsert.setDate(3, this.getDateDepartCircuit());
		preparedStatementInsert.executeUpdate();
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

	@Override
	public Object createFromSQL(Connection c) throws SQLException {
		// TODO Auto-generated method stub
		return(null);
	}
}
