package org.awhy.core.objects;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Circuit implements Object {

	public final SimpleStringProperty idCircuit;
	public final SimpleStringProperty descriptif;
	public final SimpleStringProperty villeDepart;
	public final SimpleStringProperty paysDepart;
	public final SimpleStringProperty villeArrivee;
	public final SimpleStringProperty paysArrivee;
  	public final SimpleIntegerProperty nbJoursTotal;
  	public final SimpleIntegerProperty prixCircuit;

	public Circuit() {
	    this.idCircuit = new SimpleStringProperty();
	    this.descriptif = new SimpleStringProperty();
	    this.villeDepart = new SimpleStringProperty();
	    this.paysDepart = new SimpleStringProperty();
	    this.villeArrivee = new SimpleStringProperty();
	    this.paysArrivee = new SimpleStringProperty();
	    this.nbJoursTotal = new SimpleIntegerProperty();
	    this.prixCircuit = new SimpleIntegerProperty();
	}

	public Circuit(String idCircuit, String descriptif, String villeDepart, String paysDepart,
                      String villeArrivee, String paysArrivee, int nbJoursTotal, int prixCircuit){
	    this.idCircuit = new SimpleStringProperty(idCircuit);
	    this.descriptif = new SimpleStringProperty(descriptif);
	    this.villeDepart = new SimpleStringProperty(villeDepart);
	    this.paysDepart = new SimpleStringProperty(paysDepart);
	    this.villeArrivee = new SimpleStringProperty(villeArrivee);
	    this.paysArrivee = new SimpleStringProperty(paysArrivee);
	    this.nbJoursTotal = new SimpleIntegerProperty(nbJoursTotal);
	    this.prixCircuit = new SimpleIntegerProperty(prixCircuit);
	}

	@Override
	public Object createFromSQL(ResultSet res) throws SQLException {
		return new Circuit(res.getString(1), res.getString(2), res.getString(3), res.getString(4), res.getString(5), res.getString(6), res.getInt(7), res.getInt(8));
	}

  	public String getIdCircuit() {
		return idCircuit.get();
	}

	public String getDescriptif() {
		return descriptif.get();
	}

	public String getVilleDepart() {
		return villeDepart.get();
	}

	public String getPaysDepart() {
		return paysDepart.get();
	}

	public String getVilleArrivee() {
		return villeArrivee.get();
	}

	public String getPaysArrivee() {
		return paysArrivee.get();
	}

	public Integer getNbJoursTotal() {
		return nbJoursTotal.get();
	}

  	public Integer getPrixCircuit() {
		return prixCircuit.get();
	}


  	public String setIdCircuit(String idCircuit) {
		this.idCircuit.set(idCircuit);
	}

	public String setDescriptif(String descriptif) {
		this.descriptif.set(descriptif);
	}

	public String setVilleDepart(String villeDepart) {
		this.villeDepart.set(villeDepart);
	}

	public String setPaysDepart(String paysDepart) {
		this.paysDepart.set(paysDepart);
	}

	public String setVilleArrivee(String villeArrivee) {
		this.villeArrivee.set(villeArrivee);
	}

	public String setPaysArrivee(String paysArrivee) {
		this.paysArrivee.set(paysArrivee);
	}

	public Integer setNbJoursTotal(Integer nbJoursTotal) {
		this.nbJoursTotal.set(nbJoursTotal);
	}

  	public Integer setPrixCircuit(Integer prixCircuit) {
		this.prixCircuit.set(prixCircuit);
	}
}
