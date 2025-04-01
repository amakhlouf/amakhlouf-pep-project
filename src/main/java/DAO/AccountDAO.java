package DAO;

import Model.Account;
import Util.SQLUtil;
import Util.Interfaces.ResultSetMapper;

import java.util.List;

public class AccountDAO {
	private SQLUtil<Account> util = new SQLUtil<>();

	private ResultSetMapper<Account> defaultMapper = rs -> {
		return new Account(
			rs.getInt("account_id"),
			rs.getString("username"),
			rs.getString("password")
		);
	};

	/**
	 * list
	 * @return
	 */
	public List<Account> list() {
		String sql = "SELECT * FROM account";

		return util.executeQueryList(sql, defaultMapper, ps -> {});
	}

	/**
	 * getById
	 * @param id
	 * @return
	 */
	public Account getById(int id) {
		String sql = "SELECT * FROM account WHERE account_id = ?";

		return util.executeQueryGet(sql, defaultMapper, ps -> {
			ps.setInt(1, id);
		});
	}

	/**
	 * getByUserName
	 * @param username
	 * @return
	 */
	public Account getByUsername(String username) {
		String sql = "SELECT * FROM account WHERE username = ?";

		return util.executeQueryGet(sql, defaultMapper, ps -> {
			ps.setString(1, username);
		});
	}

	/**
	 * save
	 * @param account
	 * @return
	 */
	public Account save(Account account) {
		String sql = "INSERT INTO account (username, password) VALUES (?,?)";

		return util.executeUpdateGenKeys(sql, rs -> new Account(
			rs.getInt("account_id"),
			account.getUsername(),
			account.getPassword()
		), ps -> {
			ps.setString(1, account.getUsername());
			ps.setString(2, account.getPassword());
		});
	}
}
