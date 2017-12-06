package org.awhy.core.objects;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ReserveHotel implements Object {

	public final SimpleStringProperty nomHotel;
	public final SimpleStringProperty ville;
	public final SimpleStringProperty pays;
	public final SimpleIntegerProperty numDossier;
	public final Date dateDepartHotel;
	public final Date dateArriveeHotel;
	public final SimpleIntegerProperty nbChambresReservees;
	public final SimpleIntegerProperty nbPetitDejReserves;

	public ReserveHotel() {
		this.nomHotel = new SimpleStringProperty();
		this.ville = new SimpleStringProperty();
		this.pays = new SimpleStringProperty();
		this.numDossier = new SimpleIntegerProperty();
		this.dateDepartHotel = new Date(0);
		this.dateArriveeHotel = new Date(0);
		this.nbChambresReservees = new SimpleIntegerProperty();
		this.nbPetitDejReserves = new SimpleIntegerProperty();
	}

	public ReserveHotel(String nomHotel, String ville, String pays, int numDossier,
                        Date dateDepartHotel, Date dateArriveeHotel, int nbChambresReservees, int nbPetitDejReserves) {
		this.nomHotel = new SimpleStringProperty(nomHotel);
		this.ville = new SimpleStringProperty(ville);
		this.pays = new SimpleStringProperty(pays);
		this.numDossier = new SimpleIntegerProperty(numDossier);
		this.dateDepartHotel = dateDepartHotel;
		this.dateArriveeHotel = dateArriveeHotel;
		this.nbChambresReservees = new SimpleIntegerProperty(nbChambresReservees);
    	this.nbPetitDejReserves = new SimpleIntegerProperty(nbPetitDejReserves);
	}

	@Override
	public Object createFromSQL(ResultSet res) throws SQLException {
		return new ReserveHotel(res.getString(1), res.getString(2), res.getString(3), res.getInt(4), res.getDate(5), res.getDate(5), res.getInt(6), res.getInt(6));
	}


	public String getNomHotel() {
		return nomHotel.get();
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

	public Date getDateDepartHotel() {
		return dateDepartHotel;
	}

	public Date getDateArriveeHotel() {
		return dateArriveeHotel;
	}

	public Integer getNbChambresReservees() {
		return nbChambresReservees.get();
	}

	public Integer getNbPetitDejReserves() {
		return nbPetitDejReserves.get();
	}

	@Override
	public void insertSQL(Connection c) throws SQLException {
		// TODO Auto-generated method stub
	}

	@Override
	public Object createFromSQL(Connection c) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
}
