import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import org.awhy.core.Dialog;
import org.awhy.core.objects.Client;

public class TestTransaction {
	public static void main(String[] args) throws SQLException {
		Dialog d = new Dialog();
		d.connect();
		Client c = new Client(1, "G", "Maxime", "individuel", "1 rue X", "m@g", "06", 2017);
		// c.insertSQL(d.getConnection());
		String sqlIdentifier = "select idClient.NEXTVAL from Client";
		PreparedStatement pst = d.getConnection().prepareStatement(sqlIdentifier);
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			System.out.println("id :" + rs.getLong(1));
		Scanner sc = new Scanner(System.in);
		System.out.print("Press [ENTER] to continue...");
		sc.nextLine();
		d.getConnection().commit();
	}
}
