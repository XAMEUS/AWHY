package org.awhy.core.objects;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.beans.property.SimpleStringProperty;

public class Ville implements Object {
	
    private final SimpleStringProperty nomVille;
	private final SimpleStringProperty pays;
	
	public Ville() {
		this.nomVille = new SimpleStringProperty();
		this.pays = new SimpleStringProperty();
	}
	
	public Ville(String nomVille, String pays) {
		this.nomVille = new SimpleStringProperty(nomVille);
		this.pays = new SimpleStringProperty(pays);
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
}
