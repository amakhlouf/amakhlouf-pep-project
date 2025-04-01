package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {
	private AccountDAO dao;

	public AccountService(){
		dao = new AccountDAO();
	}

	public AccountService(AccountDAO dao){
		this.dao = dao;
	}

	/**
	 * register
	 * @param account
	 * @return
	 */
	public Account register(Account account){
		if(!isValidUsername(account.getUsername()))
			return null;
		if(!isValidPassword(account.getPassword()))
			return null;
		if(existsByUsername(account.getUsername()))
			return null;

		return dao.save(account);
	}

	/**
	 * login
	 * @param account
	 * @return
	 */
	public Account login(Account account){
		Account check = dao.getByUsername(account.username);
		if(check == null)
			return null;
		if(check.password.equals(account.password))
			return check;

		return null;
	}

	/**
	 * existsById
	 * @param id
	 * @return
	 */
	public boolean existsById(int id){
		if(dao.getById(id) != null)
			return true;
		else
			return false;
	}

	/**
	 * existsByUsername
	 * @param username
	 * @return
	 */
	private boolean existsByUsername(String username) {
		return dao.getByUsername(username) != null;
	}

	/**
	 * isValidUsername
	 * @param username
	 * @return
	 */
	private boolean isValidUsername(String username) {
		return username.length() >= 1;
	}

	/**
	 * isValidPassword
	 * @param password
	 * @return
	 */
	private boolean isValidPassword(String password) {
		return password.length() >= 4;
	}
}
