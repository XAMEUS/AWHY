
package org.awhy.core.objects;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Etapes implements Object {

	public final SimpleStringProperty idCircuit;
  	public final SimpleIntegerProperty ordre;
	public final SimpleStringProperty nomLieu;
	public final SimpleStringProperty ville;
	public final SimpleStringProperty pays;
  	public final SimpleIntegerProperty nbJours;

	public Etapes() {
	    this.idCircuit = new SimpleStringProperty();
		this.ordre = new SimpleIntegerProperty();
	    this.nomLieu = new SimpleStringProperty();
	    this.ville = new SimpleStringProperty();
	    this.pays = new SimpleStringProperty();
	    this.nbJours = new SimpleIntegerProperty();
	}

	public Etapes(String idCircuit, int ordre, String nomLieu,
				   String ville, String pays, int nbJours){
	    this.idCircuit = new SimpleStringProperty(idCircuit);
	    this.ordre = new SimpleIntegerProperty(ordre);
	    this.nomLieu = new SimpleStringProperty(nomLieu);
	    this.ville = new SimpleStringProperty(ville);
	    this.pays = new SimpleStringProperty(pays);
	    this.nbJours = new SimpleIntegerProperty(nbJours);
	}

	@Override
	public Object createFromSQL(ResultSet res) throws SQLException {
		return new Etapes(res.getString(1), res.getInt(2), res.getString(3), res.getString(4), res.getString(5), res.getInt(6));
	}

  	public String getIdCircuit() {
		return idCircuit.get();
	}

	public Integer getOrdre() {
		return ordre.get();
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

  	public Integer getNbJours() {
		return nbJours.get();
	}

	@Override
	public Object insertSQL(Connection c) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createFromSQL(Connection c) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
}
