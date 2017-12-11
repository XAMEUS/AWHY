package org.awhy.core.objects;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
  	
	public static String dbName = "Hotel";

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
	public void insertSQL(Connection c) throws SQLException {
		String insert = "INSERT INTO " + dbName + " VALUES " + "(?, ?, ?, ?, ?, ?, ?)";
		c.commit();
		PreparedStatement preparedStatementInsert = c.prepareStatement(insert);
		preparedStatementInsert.setString(1, this.getNomHotel());
		preparedStatementInsert.setString(2, this.getVille());
		preparedStatementInsert.setString(3, this.getPays());
		preparedStatementInsert.setString(4, this.getAdresseHotel());
		preparedStatementInsert.setInt(5, this.getNbChambresTotal());
		preparedStatementInsert.setInt(6, this.getPrixChambre());
		preparedStatementInsert.setInt(7, this.getPrixPetitDejeuner());
		preparedStatementInsert.executeUpdate();
		preparedStatementInsert.close();
		c.commit();
	}

	@Override
	public void updateSQL(Connection c) throws SQLException {
		String insert = "UPDATE " + dbName + " SET adresseHotel=?, nbChambresTotal=?, prixchambre=?, prixPetitDejeuner=? WHERE nomHotel=?, ville=?, pays=?";
		c.commit();
		PreparedStatement preparedStatementInsert = c.prepareStatement(insert);
		preparedStatementInsert.setString(1, this.getAdresseHotel());
		preparedStatementInsert.setInt(2, this.getNbChambresTotal());
		preparedStatementInsert.setInt(3, this.getPrixChambre());
		preparedStatementInsert.setInt(4, this.getPrixPetitDejeuner());
		preparedStatementInsert.setString(5, this.getNomHotel());
		preparedStatementInsert.setString(6, this.getVille());
		preparedStatementInsert.setString(7, this.getPays());
		preparedStatementInsert.executeUpdate();
		preparedStatementInsert.close();
		c.commit();

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
	
	
  	public void setNomHotel(String nomHotel) {
		this.nomHotel.set(nomHotel);
	}

	public void setVille(String ville) {
		this.ville.set(ville);
	}

	public void setPays(String pays) {
		this.pays.set(pays);
	}

	public void setAdresseHotel(String adresseHotel) {
		this.adresseHotel.set(adresseHotel);
	}

	public void setNbChambresTotal(Integer nbChambresTotal) {
		this.nbChambresTotal.set(nbChambresTotal);
	}

  	public void setPrixChambre(Integer prixChambre) {
		this.prixChambre.set(prixChambre);
	}

	public void setPrixPetitDejeuner(Integer prixPetitDejeuner) {
  		this.prixPetitDejeuner.set(prixPetitDejeuner);
	}

	@Override
	public void deleteSQL(Connection c) throws SQLException {
		// TODO Auto-generated method stub
		
	}
}
