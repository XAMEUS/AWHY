package org.awhy.core.objects;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface Object {

	public Object createFromSQL(ResultSet res) throws SQLException;

	public void insertSQL(Connection c) throws SQLException;

	public void updateSQL(Connection c) throws SQLException;

}
