package org.awhy.core.objects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class LieuAVisiter implements Object {

	public final SimpleStringProperty nomLieu;
	public final SimpleStringProperty ville;
	public final SimpleStringProperty pays;
	public final SimpleStringProperty adresseLieu;
	public final SimpleStringProperty descriptifLieu;
	public final SimpleIntegerProperty prix;

	public static String dbName = "LieuAVisiter";
	
	public LieuAVisiter() {
		this.nomLieu = new SimpleStringProperty();
		this.ville = new SimpleStringProperty();
		this.pays = new SimpleStringProperty();
		this.adresseLieu = new SimpleStringProperty();
		this.descriptifLieu = new SimpleStringProperty();
		this.prix = new SimpleIntegerProperty();
	}

	public LieuAVisiter(String nomLieu, String ville, String pays, String adresseLieu, String descriptifLieu, int prix) {
		this.nomLieu = new SimpleStringProperty(nomLieu);
		this.ville = new SimpleStringProperty(ville);
		this.pays = new SimpleStringProperty(pays);
		this.adresseLieu = new SimpleStringProperty(adresseLieu);
		this.descriptifLieu = new SimpleStringProperty(descriptifLieu);
		this.prix = new SimpleIntegerProperty(prix);
	}
	
	@Override
	public void insertSQL(Connection c) throws SQLException {
		String insert = "INSERT INTO " + dbName + " VALUES " + "(?, ?, ?, ?, ?, ?)";
		PreparedStatement preparedStatementInsert = c.prepareStatement(insert);
		preparedStatementInsert.setString(1, this.getNomLieu());
		preparedStatementInsert.setString(2, this.getVille());
		preparedStatementInsert.setString(3, this.getPays());
		preparedStatementInsert.setString(4, this.getAdresseLieu());
		preparedStatementInsert.setString(5, this.getDescriptifLieu());
		preparedStatementInsert.setInt(6, this.getPrix());
		preparedStatementInsert.executeUpdate();
		preparedStatementInsert.close();
	}

	@Override
	public void updateSQL(Connection c) throws SQLException {
		String insert = "UPDATE " + dbName + " SET adresseLieu=?, descriptifLieu=?, prix=? WHERE nomLieu=?, ville=?, pays=?";
		PreparedStatement preparedStatementInsert = c.prepareStatement(insert);
		preparedStatementInsert.setString(1, this.getAdresseLieu());
		preparedStatementInsert.setString(2, this.getDescriptifLieu());
		preparedStatementInsert.setInt(3, this.getPrix());
		preparedStatementInsert.setString(4, this.getNomLieu());
		preparedStatementInsert.setString(5, this.getVille());
		preparedStatementInsert.setString(6, this.getPays());
		preparedStatementInsert.executeUpdate();
		preparedStatementInsert.close();
	}

	@Override
	public Object createFromSQL(ResultSet res) throws SQLException {
		return new LieuAVisiter(res.getString(1), res.getString(2), res.getString(3), res.getString(4), res.getString(5), res.getInt(6));
	}
	
	public String getNomLieu() {
		return nomLieu.get();
	}

	public String getVille() {
		return ville.get();
	}

	public String getPays() {
		return pays.get();
	}

	public String getAdresseLieu() {
		return adresseLieu.get();
	}

	public String getDescriptifLieu() {
		return descriptifLieu.get();
	}

	public Integer getPrix() {
		return prix.get();
	}
	
	
	public void setNomLieu(String nomLieu) {
		this.nomLieu.set(nomLieu);
	}

	public void setVille(String ville) {
		this.ville.set(ville);
	}

	public void setPays(String pays) {
		this.pays.set(pays);
	}

	public void setAdresseLieu(String adresseLieu) {
		this.adresseLieu.set(adresseLieu);
	}

	public void setDescriptifLieu(String descriptifLieu) {
		this.descriptifLieu.set(descriptifLieu);
	}

	public void setPrix(Integer prix) {
		this.prix.set(prix);
	}

}
