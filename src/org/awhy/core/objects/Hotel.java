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
		PreparedStatement preparedStatementInsert = c.prepareStatement(insert);
		preparedStatementInsert.setString(1, this.getDescriptif());
		preparedStatementInsert.setString(2, this.getVilleDepart());
		preparedStatementInsert.setString(3, this.getPaysDepart());
		preparedStatementInsert.setString(4, this.getVilleArrivee());
		preparedStatementInsert.setString(5, this.getPaysArrivee());
		preparedStatementInsert.setInt(6, this.getNbJoursTotal());
		preparedStatementInsert.setInt(7, this.getPrixCircuit());
		preparedStatementInsert.executeUpdate();
	}

	@Override
	public void updateSQL(Connection c) throws SQLException {
		String insert = "UPDATE " + dbName + " SET descriptif=?, villeDepart=?, paysDepart=?, villeArrivee=?, paysArrivee=?, nbJoursTotal=?, prixCircuit=? WHERE idCircuit=?";
		PreparedStatement preparedStatementInsert = c.prepareStatement(insert);
		preparedStatementInsert.setString(1, this.getDescriptif());
		preparedStatementInsert.setString(2, this.getVilleDepart());
		preparedStatementInsert.setString(3, this.getPaysDepart());
		preparedStatementInsert.setString(4, this.getVilleArrivee());
		preparedStatementInsert.setString(5, this.getPaysArrivee());
		preparedStatementInsert.setInt(6, this.getNbJoursTotal());
		preparedStatementInsert.setInt(7, this.getPrixCircuit());
		preparedStatementInsert.setString(8, this.getIdCircuit());
		preparedStatementInsert.executeUpdate();
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
}
