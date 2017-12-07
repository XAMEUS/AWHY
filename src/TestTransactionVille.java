import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import org.awhy.core.Dialog;
import org.awhy.core.objects.Client;
import org.awhy.core.objects.Hotel;
import org.awhy.core.objects.Ville;

public class TestTransactionVille {
	public static void main(String[] args) throws SQLException {
		Dialog d = new Dialog();
		d.connect();

//test Création
//		Ville v = new Ville("Budapest", "Hongrie");
//		System.out.println(v.getNomVille());
		
//Test insertSQL
		Ville v = new Ville("Budapest", "Hongrie");		
		v.insertSQL(d.getConnection());		
		d.getConnection().commit();		

//test createfromSQL
//		ResultSet res = d.executeQuery("select * from Ville where nomVille='Budapest'");
//		Ville v = null;
//		if (res.next())
//			v = (Ville) new Ville().createFromSQL(res);
//		System.out.println(v.getNomVille());	
		

	}
}