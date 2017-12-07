package org.awhy.core.objects;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Hotel implements Object {

	public final SimpleStringProperty nomHotel;
	public final SimpleStringProperty ville;
	public final SimpleStringProperty pays;
	public final SimpleStringProperty adresseHotel;
  	public final SimpleIntegerProperty nbChambresTotal;
  	public final SimpleIntegerProperty prixChambre;
  	public final SimpleIntegerProperty prixPetitDejeuner;

	public Hotel() {
	    this.nomHotel = new SimpleStringProperty();
	    this.ville = new SimpleStringProperty();
	    this.pays = new SimpleStringProperty();
	    this.adresseHotel = new SimpleStringProperty();
	    this.nbChambresTotal = new SimpleIntegerProperty();
	    this.prixChambre = new SimpleIntegerProperty();
		this.prixPetitDejeuner = new SimpleIntegerProperty();
	}

	public Hotel(String nomHotel, String ville, String pays, String adresseHotel,
                 int nbChambresTotal, int prixChambre, int prixPetitDejeuner){
	    this.nomHotel = new SimpleStringProperty(nomHotel);
	    this.ville = new SimpleStringProperty(ville);
	    this.pays = new SimpleStringProperty(pays);
	    this.adresseHotel = new SimpleStringProperty(adresseHotel);
	    this.nbChambresTotal = new SimpleIntegerProperty(nbChambresTotal);
	    this.prixPetitDejeuner = new SimpleIntegerProperty(prixPetitDejeuner);
	    this.prixChambre = new SimpleIntegerProperty(prixChambre);
	}

	@Override
	public Object createFromSQL(ResultSet res) throws SQLException {
		return new Hotel(res.getString(1), res.getString(2), res.getString(3), res.getString(4), res.getInt(5), res.getInt(6), res.getInt(7));
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

	public String getAdresseHotel() {
		return adresseHotel.get();
	}

	public Integer getNbChambresTotal() {
		return nbChambresTotal.get();
	}

  	public Integer getPrixChambre() {
		return prixChambre.get();
	}

	public Integer getPrixPetitDejeuner() {
  		return prixPetitDejeuner.get();
	}

	@Override
	public void insertSQL(Connection c) throws SQLException {
		// TODO Auto-generated method stub
	}

	@Override
	public void updateSQL(Connection c) throws SQLException {
		// TODO Auto-generated method stub
	}
}
