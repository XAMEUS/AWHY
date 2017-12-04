package org.awhy.utils;
import java.io.IOException;
import java.sql.SQLException;

import org.awhy.core.Dialog;

public class Push {
	
	public static void main(String[] args) throws SQLException, IOException {
		Dialog bdd;
		bdd = new Dialog();
		bdd.connect();
		bdd.executeFile("/user/2/bouvipie/aa.sql", false);
	}

}
