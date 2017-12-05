package org.awhy.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
			if (Debugger.isEnabled())
				System.out.println("close old connection(" + connection.getCatalog() + ") ");
			this.connection.close();
		}
		if (Debugger.isEnabled())
			System.out.print("DriverManager getConnection(" + url + ") ");
		this.connection = (DriverManager.getConnection(url, user, passwd));
		// this.connection.setAutoCommit(false);
		// this.connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
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
	
	public ResultSet executeFile(String path, boolean continueOnError) throws SQLException, IOException {
		if (Debugger.isEnabled())
			Debugger.print("executeFile: " + path + "\n");
        StringBuffer sqlFile = new StringBuffer();
        String tmpFile = new String();
        BufferedReader file = new BufferedReader(new FileReader(new File(path)));
        while(true) {
        	tmpFile = file.readLine();
        	if(tmpFile == null) break;
        	for(int i = 0; i < tmpFile.length() - 1; i++) {
        		if(tmpFile.charAt(i) == '-' && tmpFile.charAt(i+1) == '-') {
        			if(i > 0)
        				tmpFile = tmpFile.substring(0, i-1);
        			else
        				tmpFile = null;
    				break;
        		}
        	}
        	if(tmpFile != null)
        		sqlFile.append(tmpFile);
        }
        file.close();
        String[] queries = sqlFile.toString().split(";");
        for(String query : queries) {
        	try {
        		this.executeQuery(query);
        	}
        	catch (SQLException e) {
        		if(continueOnError)
        			Debugger.println(e + "\n");
        		else
        			throw new SQLException(e);
        	}
        		
        }
		if (Debugger.isEnabled())
			Debugger.println("File - OK\n");
		return res;
	}

	public Connection getConnection() {
		return connection;
	}

}
