import java.sql.SQLException;

import org.awhy.core.Dialog;

public class TestTransactionHotel {
	public static void main(String[] args) throws SQLException {
		Dialog d = new Dialog();
		d.connect();

		// test Création
		// Hotel h = new Hotel("GBH", "Budapest", "Hongrie", "1 rue ", 100, 200,
		// 20);
		// System.out.println(h.getNomHotel());

		// Test insertSQL
		// Hotel h = new Hotel("GBH", "Budapest", "Hongrie", "1 rue ", 100, 200,
		// 20);
		// h.insertSQL(d.getConnection());
		// d.getConnection().commit();

		// test createfromSQL
		// ResultSet res = d.executeQuery("select * from Hotel where
		// nomHotel='GBH'");
		// Hotel h = null;
		// if (res.next())
		// h = (Hotel) new Hotel().createFromSQL(res);
		// System.out.println(h.getNomHotel());

		// Test updateSQL
		// Hotel h = new Hotel("GrandBudapestHotel", "Budapest", "Hongrie", "1
		// rue ", 100, 200, 20);
		// h.updateSQL(d.getConnection());
		// d.getConnection().commit();

		// ResultSet res = d.executeQuery("select * from Hotel where
		// nomHotel='GBH', ville='Budapest', pays='Hongrie'");
		// Hotel h = null;
		// if (res.next())
		// h = (Client) new Hotel().createFromSQL(res);
		// System.out.println(h.getNomHotel());
		//
		// c.insertSQL(d.getConnection());
		// c.updateSQL(d.getConnection());
		// String sqlIdentifier = "select idClient.NEXTVAL from Client";
		// PreparedStatement pst =
		// d.getConnection().prepareStatement(sqlIdentifier);
		// ResultSet rs = pst.executeQuery();
		// if (rs.next())
		// System.out.println("id :" + rs.getLong(1));
		// Scanner sc = new Scanner(System.in);
		// System.out.print("Press [ENTER] to continue...");
		// sc.nextLine();
		// d.getConnection().commit();
	}
}
