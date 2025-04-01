package Util;

import Util.Interfaces.ResultSetMapper;
import Util.Interfaces.PreparedStatementSetter;

import java.util.*;
import java.sql.*;

public class SQLUtil <T> {
	/**
	 * executeQueryGet - getBy...
	 * @param sql
	 * @param mapper
	 * @param setter
	 * @return
	 */
	public T executeQueryGet(String sql, ResultSetMapper<T> mapper, PreparedStatementSetter setter) {
		Connection conn = ConnectionUtil.getConnection();

		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			setter.set(ps);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				return mapper.map(rs);
			}
		} catch (SQLException e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}

		return null;
	}

	/**
	 * executeQueryList - list
	 * @param sql
	 * @param mapper
	 * @param setter
	 * @return
	 */
	public List<T> executeQueryList(String sql, ResultSetMapper<T> mapper, PreparedStatementSetter setter) {
		Connection conn = ConnectionUtil.getConnection();
		List<T> list = new ArrayList<>();

		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			setter.set(ps);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				list.add(mapper.map(rs));
			}
		} catch (SQLException e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}

		return list;
	}

	/**
	 * executeUpdate - update & delete
	 * @param sql
	 * @param setter
	 * @return
	 */
	public int executeUpdate(String sql, PreparedStatementSetter setter) {
		Connection conn = ConnectionUtil.getConnection();

		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			setter.set(ps);
			return ps.executeUpdate();
		} catch (SQLException e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}

		return 0;
	}

	/**
	 * executeUpdateGenKeys - insert
	 * @param sql
	 * @param mapper
	 * @param setter
	 * @return
	 */
	public T executeUpdateGenKeys(String sql, ResultSetMapper<T> mapper, PreparedStatementSetter setter) {
		Connection conn = ConnectionUtil.getConnection();

		try {
			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			setter.set(ps);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if(rs.next()) {
				return mapper.map(rs);
			}
		} catch (SQLException e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}

		return null;
	}
}
