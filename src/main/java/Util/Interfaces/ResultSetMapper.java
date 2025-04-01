package Util.Interfaces;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetMapper<T>{
	public T map(ResultSet rs) throws SQLException;
}
