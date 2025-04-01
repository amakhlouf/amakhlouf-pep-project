package Util.Interfaces;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface PreparedStatementSetter{
	public void set(PreparedStatement ps) throws SQLException;
}
