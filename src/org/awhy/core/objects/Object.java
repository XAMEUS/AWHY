package org.awhy.core.objects;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Object {
	
	public Object createFromSQL(ResultSet res) throws SQLException;

}
