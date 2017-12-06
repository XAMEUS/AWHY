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
		String insert = "INSERT INTO " + dbName	+ " VALUES " + "(idCircuit.nextval, ?, ?)";
		PreparedStatement preparedStatementInsert = c.prepareStatement(insert);
		preparedStatementInsert.setDate(1, this.getDateDepartCircuit());
		preparedStatementInsert.setInt(2, this.getNbPersonnes());
		preparedStatementInsert.executeUpdate();
	}

	public void updateSQL(Connection c) throws SQLException {
		String insert = "UPDATE " + dbName + " SET dateDepartCircuit=?, nbPersonnes=? WHERE idCircuit=?";
		PreparedStatement preparedStatementInsert = c.prepareStatement(insert);
		preparedStatementInsert.setDate(1, this.getDateDepartCircuit());
		preparedStatementInsert.setInt(2, this.getNbPersonnes());
		preparedStatementInsert.setString(3, this.getIdCircuit());
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
