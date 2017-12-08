package org.awhy.core.objects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.beans.property.SimpleStringProperty;

public class Ville implements Object {
	
    private final SimpleStringProperty nomVille;
	private final SimpleStringProperty pays;
	public static String dbName = "Ville";
	
	public Ville() {
		this.nomVille = new SimpleStringProperty();
		this.pays = new SimpleStringProperty();
	}
	
	public Ville(String nomVille, String pays) {
		this.nomVille = new SimpleStringProperty(nomVille);
		this.pays = new SimpleStringProperty(pays);
	}
	
	public void insertSQL(Connection c) throws SQLException {
		String insert = "INSERT INTO " + dbName + " VALUES " + "(?, ?)";
		c.commit();
		PreparedStatement preparedStatementInsert = c.prepareStatement(insert);
		preparedStatementInsert.setString(1, this.getNomVille());
		preparedStatementInsert.setString(2, this.getPays());
		preparedStatementInsert.executeUpdate();
		preparedStatementInsert.close();
		c.commit();
	}
	
    public String getNomVille() {
		return nomVille.get();
	}
    
	public String getPays() {
		return pays.get();
	}
	
	public void setNomVille(String nomVille) {
		this.nomVille.set(nomVille);
	}
	
	public void setPays(String pays) {
		this.pays.set(pays);
	}

	@Override
	public Object createFromSQL(ResultSet res) throws SQLException {
		return new Ville(res.getString(1), res.getString(2));
	}
	
	@Override
	public void updateSQL(Connection c) throws SQLException {
	}
}
