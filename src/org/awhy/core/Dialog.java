package org.awhy.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.awhy.utils.Debugger;

public class Dialog {

	private Connection connection;
	public ResultSet res;
	private Statement stmt;

	public String url = "jdbc:oracle:thin:@ensioracle1.imag.fr:" + "1521:ensioracle1";
	public String user = "gourgoum";
	public String passwd = "gourgoum";

	public Dialog() throws SQLException {
		if (Debugger.isEnabled())
			System.out.print("RegisterDriver... oracle.jdbc.driver.OracleDriver() ");
		DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		if (Debugger.isEnabled())
			System.out.println("- OK");
	}

	public void connect() throws SQLException {
		if (this.connection != null && !this.connection.isClosed()) {
			this.connection.close();
			if (Debugger.isEnabled())
				System.out.print("close old connection(" + connection.getCatalog() + ") ");
		}
		if (Debugger.isEnabled())
			System.out.print("DriverManager getConnection(" + url + ") ");
		this.connection = (DriverManager.getConnection(url, user, passwd));
		if (Debugger.isEnabled())
			System.out.println("- OK");
		this.stmt = this.connection.createStatement();
	}

	public ResultSet executeQuery(String sql) throws SQLException {
		if (Debugger.isEnabled())
			Debugger.print("executeQuery: " + sql);
		ResultSet res = stmt.executeQuery(sql);
		if (Debugger.isEnabled())
			Debugger.println(" - OK");
		return res;
	}

	public Connection getConnection() {
		return connection;
	}

}
