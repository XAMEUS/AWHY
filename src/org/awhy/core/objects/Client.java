package org.awhy.core.objects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.awhy.core.Dialog;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Client implements Object {

	public final SimpleIntegerProperty idClient;
	public final SimpleStringProperty nomClient;
	public final SimpleStringProperty prenomClient;
	public final SimpleStringProperty typeClient;
	public final SimpleStringProperty adresseClient;
	public final SimpleStringProperty emailClient;
	public final SimpleStringProperty telClient;
	public final SimpleIntegerProperty anneeEnregistrement;

	public static String dbName = "Client";

	public Client() {
		this.idClient = new SimpleIntegerProperty();
		this.nomClient = new SimpleStringProperty();
		this.prenomClient = new SimpleStringProperty();
		this.typeClient = new SimpleStringProperty();
		this.adresseClient = new SimpleStringProperty();
		this.emailClient = new SimpleStringProperty();
		this.telClient = new SimpleStringProperty();
		this.anneeEnregistrement = new SimpleIntegerProperty();
	}

	public Client(int idClient, String nomClient, String prenomClient, String typeClient, String adresseClient, String emailClient, String telClient, int anneeEnregistrement) {
		this.idClient = new SimpleIntegerProperty(idClient);
		this.nomClient = new SimpleStringProperty(nomClient);
		this.prenomClient = new SimpleStringProperty(prenomClient);
		this.typeClient = new SimpleStringProperty(typeClient);
		this.adresseClient = new SimpleStringProperty(adresseClient);
		this.emailClient = new SimpleStringProperty(emailClient);
		this.telClient = new SimpleStringProperty(telClient);
		this.anneeEnregistrement = new SimpleIntegerProperty(anneeEnregistrement);
	}
	
	public Client(Dialog d, String nomClient, String prenomClient, String typeClient, String adresseClient, String emailClient, String telClient, int anneeEnregistrement) throws SQLException {
		ResultSet r = d.executeQuery("select idclient.nextval from client");
		long id = 0;
		if (r.next())
			id = r.getLong(1);
		this.idClient = new SimpleIntegerProperty((int) id);
		this.nomClient = new SimpleStringProperty(nomClient);
		this.prenomClient = new SimpleStringProperty(prenomClient);
		this.typeClient = new SimpleStringProperty(typeClient);
		this.adresseClient = new SimpleStringProperty(adresseClient);
		this.emailClient = new SimpleStringProperty(emailClient);
		this.telClient = new SimpleStringProperty(telClient);
		this.anneeEnregistrement = new SimpleIntegerProperty(anneeEnregistrement);
	}

	@Override
	public Object createFromSQL(ResultSet res) throws SQLException {
		return new Client(res.getInt(1), res.getString(2), res.getString(3), res.getString(4), res.getString(5), res.getString(6), res.getString(7), res.getInt(8));
	}

	@Override
	public void insertSQL(Connection c) throws SQLException {
		String insert = "INSERT INTO " + dbName + " VALUES " + "(?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement preparedStatementInsert = c.prepareStatement(insert);
		preparedStatementInsert.setInt(1, this.getIdClient());
		preparedStatementInsert.setString(2, this.getNomClient());
		preparedStatementInsert.setString(3, this.getPrenomClient());
		preparedStatementInsert.setString(4, this.getTypeClient());
		preparedStatementInsert.setString(5, this.getAdresseClient());
		preparedStatementInsert.setString(6, this.getEmailClient());
		preparedStatementInsert.setString(7, this.getTelClient());
		preparedStatementInsert.setInt(8, this.getAnneeEnregistrement());
		preparedStatementInsert.executeUpdate();
	}

	@Override
	public void updateSQL(Connection c) throws SQLException {
		String insert = "UPDATE " + dbName + " SET nomClient=?, prenomClient=?, typeClient=?, adresseClient=?, emailClient=?, telClient=?, anneeEnregistrement=? WHERE idClient=?";
		PreparedStatement preparedStatementInsert = c.prepareStatement(insert);
		preparedStatementInsert.setString(1, this.getNomClient());
		preparedStatementInsert.setString(2, this.getPrenomClient());
		preparedStatementInsert.setString(3, this.getTypeClient());
		preparedStatementInsert.setString(4, this.getAdresseClient());
		preparedStatementInsert.setString(5, this.getEmailClient());
		preparedStatementInsert.setString(6, this.getTelClient());
		preparedStatementInsert.setInt(7, this.getAnneeEnregistrement());
		preparedStatementInsert.setInt(8, this.getIdClient());
		preparedStatementInsert.executeUpdate();
	}

	public Integer getIdClient() {
		return idClient.get();
	}

	public String getNomClient() {
		return nomClient.get();
	}

	public String getPrenomClient() {
		return prenomClient.get();
	}

	public String getTypeClient() {
		return typeClient.get();
	}

	public String getAdresseClient() {
		return adresseClient.get();
	}

	public String getEmailClient() {
		return emailClient.get();
	}

	public String getTelClient() {
		return telClient.get();
	}

	public Integer getAnneeEnregistrement() {
		return anneeEnregistrement.get();
	}


	public void setIdClient(Integer idClient) {
		this.idClient.set(idClient);
	}

	public void setNomClient(String name) {
		this.nomClient.set(name);
	}

	public void setPrenomClient(String prenomClient) {
		this.prenomClient.set(prenomClient);
	}

	public void setTypeClient(String typeClient) {
		this.typeClient.set(typeClient);
	}

	public void setAdresseClient(String adresseClient) {
		this.adresseClient.set(adresseClient);
	}

	public void setEmailClient(String emailClient) {
		this.emailClient.set(emailClient);
	}

	public void setTelClient(String telClient) {
		this.telClient.set(telClient);
	}

	public void setAnneeEnregistrement(Integer anneeEnregistrement) {
		this.anneeEnregistrement.set(anneeEnregistrement);
	}

}
