package org.awhy.core.objects;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.awhy.core.Dialog;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


public class Simulation implements Object {

	public final SimpleIntegerProperty numDossier;
	public SimpleStringProperty nomClient;
	public SimpleStringProperty prenomClient;
	public static String dbName = "Simulation";
	
	public void insertSQL(Connection c) throws SQLException {
		String insert = "INSERT INTO " + dbName + " VALUES " + "(?)";
		c.commit();
		PreparedStatement preparedStatementInsert = c.prepareStatement(insert);
		preparedStatementInsert.setInt(1, this.getNumDossier());
		preparedStatementInsert.setString(2, this.getNomClient());
		preparedStatementInsert.setString(3, this.getPrenomClient());
		preparedStatementInsert.executeUpdate();
		preparedStatementInsert.close();
		c.commit();
	}

	public Simulation() {
		this.numDossier = new SimpleIntegerProperty();
		this.nomClient = new SimpleStringProperty();
		this.prenomClient = new SimpleStringProperty();
	}
	
	public Simulation(Dialog d) throws SQLException {
		ResultSet r = d.executeQuery("select numdossier.nextval from simulation");
		long id = 0;
		if (r.next()) {
			id = r.getLong(1);
			System.out.println("id : " + id);
		} else {
			System.out.println("empty : select numdossier.nextval from simulation");
		}
		d.getConnection().commit();
		String insert = "select * from Simulation where numDossier= (?)";
		PreparedStatement preparedStatementInsert = d.getConnection().prepareStatement(insert);
		preparedStatementInsert.setLong(1, id);
		ResultSet nR = preparedStatementInsert.executeQuery();
		while(nR.next()) {
			this.nomClient = new SimpleStringProperty(nR.getString(2));
			this.prenomClient = new SimpleStringProperty(nR.getString(3));
		}
		preparedStatementInsert.close();

		this.numDossier = new SimpleIntegerProperty((int) id);
	}

	public Simulation(int numDossier, String nomClient, String prenomClient) {
		this.numDossier = new SimpleIntegerProperty(numDossier);
		this.nomClient = new SimpleStringProperty(nomClient);
		this.prenomClient = new SimpleStringProperty(prenomClient);
	}

	@Override
	public Object createFromSQL(ResultSet res) throws SQLException {
		return new Simulation(res.getInt(1), res.getString(2), res.getString(3));
	}


	public Integer getNumDossier() {
		return numDossier.get();
	}
	
	//Attention à l'utilisation de setNumDossier !
	public void setNumDossier(Integer numDossier) {
		this.numDossier.set(numDossier);
	}

	
	//Attention à l'utilisation de setNumDossier !
	public void setNomClient(String nomClient) {
		this.nomClient.set(nomClient);
	}
	
	public String getNomClient() {
		return nomClient.get();
	}

	//Attention à l'utilisation de setNumDossier !
	public void setPrenomClient(String prenomClient) {
		this.nomClient.set(prenomClient);
	}
	
	public String getPrenomClient() {
		return nomClient.get();
	}

	@Override
	public void updateSQL(Connection c) throws SQLException {
	}
}
