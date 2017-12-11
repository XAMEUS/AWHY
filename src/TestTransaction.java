import java.sql.SQLException;
import java.util.Scanner;

import org.awhy.core.Dialog;
import org.awhy.core.objects.Client;

public class TestTransaction {
	public static void main(String[] args) throws SQLException {
		Dialog d = new Dialog();
		d.connect();
		Client c = new Client(d, "Pierrounet <3", "Pierre", "individuel", "1 rue X", "m@g", "06", 2017);
//		ResultSet res = d.executeQuery("select idclient.nextval from client");
//		Client c = null;
//		if (res.next())
//			System.out.println(res.getLong(1));
//			c = (Client) new Client().createFromSQL(res);
		System.out.println(c.getIdClient());
//		System.out.println(c.getNomClient());
//		c.setNomClient("<3");
//		c.updateSQL(d.getConnection());
		c.insertSQL(d.getConnection());
//		String sqlIdentifier = "select idClient.NEXTVAL from Client";
//		PreparedStatement pst = d.getConnection().prepareStatement(sqlIdentifier);
//		ResultSet rs = pst.executeQuery();
//		if (rs.next())
//			System.out.println("id :" + rs.getLong(1));
		Scanner sc = new Scanner(System.in);
		System.out.print("Press [ENTER] to continue...");
		sc.nextLine();
		d.getConnection().commit();
	}
}
