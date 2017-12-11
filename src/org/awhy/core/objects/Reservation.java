package org.awhy.core.objects;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Reservation implements Object {

	public final SimpleIntegerProperty numDossier;
	public Date datePaiement;
	public final SimpleStringProperty infoPaiement;
	public final SimpleIntegerProperty idClient;
	public static String dbName = "Reservation";

	public Reservation() {
		this.numDossier = new SimpleIntegerProperty();
		this.datePaiement = new Date(0);
		this.infoPaiement = new SimpleStringProperty();
		this.idClient = new SimpleIntegerProperty();
	}

	public Reservation(int numDossier, Date date, String infoPaiement, int idClient) {
		this.numDossier = new SimpleIntegerProperty(numDossier);
		this.datePaiement = date;
		this.infoPaiement = new SimpleStringProperty(infoPaiement);
		this.idClient = new SimpleIntegerProperty(idClient);
	}

	@Override
	public void insertSQL(Connection c) throws SQLException {
		String insert = "INSERT INTO " + dbName + " VALUES " + "(?, ?, ?, ?)";
		c.commit();
		PreparedStatement preparedStatementInsert = c.prepareStatement(insert);
		preparedStatementInsert.setInt(1, this.getNumDossier());
		preparedStatementInsert.setDate(2, this.getDatePaiement());
		preparedStatementInsert.setString(3, this.getInfoPaiement());
		preparedStatementInsert.setInt(4, this.getIdClient());
		preparedStatementInsert.executeUpdate();
		preparedStatementInsert.close();
		c.commit();
	}

	@Override
	public void updateSQL(Connection c) throws SQLException {
		String insert = "UPDATE " + dbName + " SET numDossier=?, datePaiement=?, infoPaiement WHERE idClient=?";
		c.commit();
		PreparedStatement preparedStatementInsert = c.prepareStatement(insert);
		preparedStatementInsert.setInt(1, this.getNumDossier());
		preparedStatementInsert.setDate(2, this.getDatePaiement());
		preparedStatementInsert.setString(3, this.getInfoPaiement());
		preparedStatementInsert.setInt(4, this.getIdClient());
		preparedStatementInsert.executeUpdate();
		preparedStatementInsert.close();
		c.commit();
	}

	@Override
	public Object createFromSQL(ResultSet res) throws SQLException {
		return new Reservation(res.getInt(1), res.getDate(2), res.getString(3), res.getInt(4));
	}

	public Integer getNumDossier() {
		return numDossier.get();
	}

	public Date getDatePaiement() {
		return datePaiement;
	}

	public String getInfoPaiement() {
		return infoPaiement.get();
	}

	public Integer getIdClient() {
		return idClient.get();
	}

	// Attention à l'utilisation de setNumDossier !
	public void setNumDossier(Integer numDossier) {
		this.numDossier.set(numDossier);
	}

	public void setDatePaiement(Date datePaiement) {
		this.datePaiement = datePaiement;
	}

	public void setInfoPaiement(String infoPaiement) {
		this.infoPaiement.set(infoPaiement);
	}

	public void setIdClient(Integer idClient) {
		this.idClient.set(idClient);
	}

	@Override
	public void deleteSQL(Connection c) throws SQLException {
		// TODO Auto-generated method stub
	}
}
