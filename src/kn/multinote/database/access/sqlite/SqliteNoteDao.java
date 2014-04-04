package kn.multinote.database.access.sqlite;

import java.sql.SQLException;
import java.util.List;

import kn.multinote.database.access.INoteDAO;
import kn.multinote.dto.NoteDto;
import kn.supportrelax.database.transaction.TransactionCommandAck;

import com.j256.ormlite.dao.Dao;

public class SqliteNoteDao extends SqliteBaseDAO implements INoteDAO {
	Dao<NoteDto, Integer> dao;

	public SqliteNoteDao() {
		super();
		try {
			dao = dbHelper.getDao(NoteDto.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public TransactionCommandAck save(NoteDto entity) {
		TransactionCommandAck result = new TransactionCommandAck();
		try {
			if (entity.getId() == 0) {
				if (dao.create(entity) == 1) {
					result.isSuccess = true;
				} else {
					result.message = "Can't insert system setting";
				}
			} else {
				if (dao.update(entity) == 1) {
					result.isSuccess = true;
				} else {
					result.message = "Can't update system setting";
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.isSuccess = false;
		}
		return result;
	}

	@Override
	public TransactionCommandAck deleteById(Integer id) {
		return null;
	}

	@Override
	public TransactionCommandAck delete(NoteDto entity) {
		TransactionCommandAck result = new TransactionCommandAck();
		try {
			dao.delete(entity);
			result.isSuccess = true;
		} catch (SQLException e) {
			e.printStackTrace();
			result.isSuccess = false;
		}
		return result;
	}

	@Override
	public TransactionCommandAck getAll() {
		TransactionCommandAck result = new TransactionCommandAck();
		try {
			List<NoteDto> lFriend = dao.queryForAll();
			if (lFriend.size() > 0) {
				result.returnValue = lFriend;
				result.isSuccess = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public TransactionCommandAck getById(Integer id) {
		TransactionCommandAck result = new TransactionCommandAck();

		try {
			NoteDto type = dao.queryForId(id);

			if (type != null) {
				result.isSuccess = true;
				result.returnValue = type;
			} else {
				result.message = "Can't found message type";
			}

		} catch (Exception e) {
			result.message = e.getMessage();
		}

		return result;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}
}
