package DAO;

import Model.Message;
import Util.SQLUtil;
import Util.Interfaces.ResultSetMapper;

import java.util.List;

public class MessageDAO {
	private SQLUtil<Message> util = new SQLUtil<>();

	private ResultSetMapper<Message> defaultMapper = rs -> new Message(
		rs.getInt("message_id"),
		rs.getInt("posted_by"),
		rs.getString("message_text"),
		rs.getLong("time_posted_epoch")
	);

	/**
	 * list
	 * @return
	 */
	public List<Message> list(){
		String sql = "SELECT * FROM message";

		return util.executeQueryList(sql, defaultMapper, ps -> {});
	}

	/**
	 * listByAccountId
	 * @param accountId
	 * @return
	 */
	public List<Message> listByAccountId(int accountId){
		String sql = "SELECT * FROM message WHERE posted_by = ?";

		return util.executeQueryList(sql, defaultMapper, ps -> {
			ps.setInt(1, accountId);
		});
	}

	/**
	 * getById
	 * @param id
	 * @return
	 */
	public Message getById(int id) {
		String sql = "SELECT * FROM message WHERE message_id = ?";

		return util.executeQueryGet(sql, defaultMapper, ps -> {
			ps.setInt(1, id);
		});
	}

	/**
	 * save
	 * @param message
	 * @return
	 */
	public Message save(Message message) {
		String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?,?,?)";

		return util.executeUpdateGenKeys(sql, rs -> new Message(
			rs.getInt("message_id"),
			message.getPosted_by(),
			message.getMessage_text(),
			message.getTime_posted_epoch()
		), ps -> {
			ps.setInt(1, message.posted_by);
			ps.setString(2, message.message_text);
			ps.setLong(3, message.time_posted_epoch);
		});
	}

	/**
	 * update
	 * @param message
	 * @return
	 */
	public Message update(Message message) {
		String sql = "UPDATE message SET message_text = ? WHERE message_id = ?";

		int rows = util.executeUpdate(sql, ps -> {
			ps.setString(1, message.getMessage_text());
			ps.setInt(2, message.getMessage_id());
		});

		if(rows > 0)
			return message;

		return null;
	}

	/**
	 * delete
	 * @param message
	 * @return
	 */
	public Message delete(Message message) {
		String sql = "DELETE FROM message WHERE message_id = ?";

		int rows = util.executeUpdate(sql, ps -> {
			ps.setInt(1, message.getMessage_id());
		});

		if(rows > 0)
			return message;

		return null;
	}
}
