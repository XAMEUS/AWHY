package org.awhy.core.objects;

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

}
