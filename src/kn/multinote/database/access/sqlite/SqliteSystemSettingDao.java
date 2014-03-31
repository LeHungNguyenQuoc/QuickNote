package kn.multinote.database.access.sqlite;

import java.sql.SQLException;
import java.util.List;

import kn.multinote.database.access.ISystemSettingDAO;
import kn.multinote.dto.SystemSettingDto;
import kn.supportrelax.database.transaction.TransactionCommandAck;

import com.j256.ormlite.dao.Dao;

public class SqliteSystemSettingDao extends SqliteBaseDAO implements
		ISystemSettingDAO {
	Dao<SystemSettingDto, Integer> dao;

	public SqliteSystemSettingDao() {
		super();
		try {
			dao = dbHelper.getDao(SystemSettingDto.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public TransactionCommandAck save(SystemSettingDto entity) {
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
	public TransactionCommandAck delete(SystemSettingDto entity) {
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
			List<SystemSettingDto> lFriend = dao.queryForAll();
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
			SystemSettingDto type = dao.queryForId(id);

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
