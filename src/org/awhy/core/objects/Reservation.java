package org.awhy.core.objects;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Reservation implements Object {

	public final SimpleIntegerProperty numDossier;
	public final Date datePaiement;
	public final SimpleStringProperty infoPaiement;
	public final SimpleIntegerProperty idClient;

	public Reservation() {
		this.numDossier = new SimpleIntegerProperty();
		this.datePaiement = new Date(0);
		this.infoPaiement = new SimpleStringProperty();
		this.idClient = new SimpleIntegerProperty();
	}

	public Reservation(int numDossier, Date datePaiement, String infoPaiement, int idClient) {
		this.numDossier = new SimpleIntegerProperty(numDossier);
		this.datePaiement = datePaiement;
		this.infoPaiement = new SimpleStringProperty(infoPaiement);
		this.idClient = new SimpleIntegerProperty(idClient);
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

	
	public void setNumDossier(Integer numDossier) {
		this.numDossier.set(numDossier);
	}

//Comment je fais le set d'une date? Suppression du final?	
//	public void setDatePaiement(Date datePaiement) {
//		this.datePaiement.setTime(datePaiement);
//		this.datePaiement.set(datePaiement);
//		this.datePaiement = datePaiement;
//	}

	public void setInfoPaiement(String infoPaiement) {
		this.infoPaiement.set(infoPaiement);
	}

	public void setIdClient(Integer idClient) {
		this.idClient.set(idClient);
	}

	@Override
	public void insertSQL(Connection c) throws SQLException {
		// TODO Auto-generated method stub
	}

	@Override
	public Object createFromSQL(Connection c) throws SQLException {
		// TODO Auto-generated method stub
		return(null);
	}
}
