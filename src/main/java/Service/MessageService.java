package Service;

import Model.Message;
import DAO.MessageDAO;

import java.util.List;

public class MessageService {
	private MessageDAO dao;

	public MessageService() {
		dao = new MessageDAO();
	}

	public MessageService(MessageDAO dao) {
		this.dao = dao;
	}

	/**
	 * list
	 * @return
	 */
	public List<Message> list() {
		return dao.list();
	}

	/**
	 * listByAccountId
	 * @param accountId
	 * @return
	 */
	public List<Message> listByAccountId(int accountId) {
		return dao.listByAccountId(accountId);
	}

	/**
	 * getById
	 * @param id
	 * @return
	 */
	public Message getById(int id) {
		return dao.getById(id);
	}

	/**
	 * getById
	 * @param message
	 * @return
	 */
	public Message create(Message message) {
		if(!isValidText(message.getMessage_text()))
			return null;

		if(!existsPostedBy(message.getPosted_by()))
			return null;

		return dao.save(message);
	}

	/**
	 * update
	 * @param id
	 * @param message
	 * @return
	 */
	public Message update(int id, Message message) {
		String text = message.getMessage_text();

		if(!isValidText(text))
			return null;

		message = dao.getById(id);
		if(message == null)
			return null;

		message.setMessage_text(text);

		return dao.update(message);
	}

	/**
	 * delete
	 * @param id
	 * @return
	 */
	public Message delete(int id) {
		Message message = dao.getById(id);
		if(message == null)
			return null;

		return dao.delete(message);
	}

	/**
	 * isValidText
	 * @param text
	 * @return
	 */
	private boolean isValidText(String text) {
		if(text.length() < 1)
			return false;

		if(text.length() > 255)
			return false;

		return true;
	}

	/**
	 * existsPostedBy
	 * @param accountId
	 * @return
	 */
	private boolean existsPostedBy(int accountId) {
		AccountService accountService = new AccountService();
		return accountService.existsById(accountId);
	}
}
