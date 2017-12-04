package org.awhy.core.objects;

import java.sql.ResultSet;
import java.sql.SQLException;

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

	@Override
	public Object createFromSQL(ResultSet res) throws SQLException {
		return new Client(res.getInt(1), res.getString(2), res.getString(3), res.getString(4), res.getString(5), res.getString(6), res.getString(7), res.getInt(8));
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

}
