package org.awhy.core.objects;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

	public static String dbName = "Circuit";

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

	public Circuit(String idCircuit, String descriptif, String villeDepart, String paysDepart, String villeArrivee,
			String paysArrivee, int nbJoursTotal, int prixCircuit) {
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
	public void insertSQL(Connection c) throws SQLException {
		String insert = "INSERT INTO " + dbName + " VALUES " + "(idCircuit.nextval, ?, ?, ?, ?, ?, ?, ?)";
		c.commit();
		PreparedStatement preparedStatementInsert = c.prepareStatement(insert);
		preparedStatementInsert.setString(1, this.getDescriptif());
		preparedStatementInsert.setString(2, this.getVilleDepart());
		preparedStatementInsert.setString(3, this.getPaysDepart());
		preparedStatementInsert.setString(4, this.getVilleArrivee());
		preparedStatementInsert.setString(5, this.getPaysArrivee());
		preparedStatementInsert.setInt(6, this.getNbJoursTotal());
		preparedStatementInsert.setInt(7, this.getPrixCircuit());
		preparedStatementInsert.executeUpdate();
		preparedStatementInsert.close();
		c.commit();
	}

	@Override
	public void updateSQL(Connection c) throws SQLException {
		String insert = "UPDATE " + dbName
				+ " SET descriptif=?, villeDepart=?, paysDepart=?, villeArrivee=?, paysArrivee=?, nbJoursTotal=?, prixCircuit=? WHERE idCircuit=?";
		c.commit();
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
		preparedStatementInsert.close();
		c.commit();
	}

	@Override
	public Object createFromSQL(ResultSet res) throws SQLException {
		return new Circuit(res.getString(1), res.getString(2), res.getString(3), res.getString(4), res.getString(5),
				res.getString(6), res.getInt(7), res.getInt(8));
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

	public void setIdCircuit(String idCircuit) {
		this.idCircuit.set(idCircuit);
	}

	public void setDescriptif(String descriptif) {
		this.descriptif.set(descriptif);
	}

	public void setVilleDepart(String villeDepart) {
		this.villeDepart.set(villeDepart);
	}

	public void setPaysDepart(String paysDepart) {
		this.paysDepart.set(paysDepart);
	}

	public void setVilleArrivee(String villeArrivee) {
		this.villeArrivee.set(villeArrivee);
	}

	public void setPaysArrivee(String paysArrivee) {
		this.paysArrivee.set(paysArrivee);
	}

	public void setNbJoursTotal(Integer nbJoursTotal) {
		this.nbJoursTotal.set(nbJoursTotal);
	}

	public void setPrixCircuit(Integer prixCircuit) {
		this.prixCircuit.set(prixCircuit);
	}
}
