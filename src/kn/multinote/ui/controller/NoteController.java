package kn.multinote.ui.controller;

import java.util.ArrayList;
import java.util.List;

import kn.multinote.database.access.INoteDAO;
import kn.multinote.defines.MessageConstant;
import kn.multinote.dto.NoteDto;
import kn.multinote.factory.DAOFactory;
import kn.supportrelax.database.transaction.TransactionCommandAck;
import android.util.Log;

public class NoteController extends Controller {

	private static String TAG = NoteController.class.getName();
	private static NoteController instance;
	private INoteDAO noteDao;
	private List<NoteDto> lNote;
	private List<NoteDto> lNoteRemove;

	public NoteController() {
		super();
		instance = this;
		noteDao = DAOFactory.getInstance().getComponent(INoteDAO.class);
		lNoteRemove = new ArrayList<NoteDto>();
	}

	public static NoteController getInstance() {
		if (instance == null) {
			instance = new NoteController();
		}
		return instance;
	}

	int countsendSMS = 0;

	@Override
	public boolean handleMessage(int what, Object data) {
		switch (what) {
		case MessageConstant.MESSAGE_VIEW_NOTE:
			notifyOutboxHandlers(MessageConstant.MESSAGE_VIEW_NOTE, -1,
					 -1,data);
			break;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public List<NoteDto> getAllNote() {
		TransactionCommandAck result = noteDao.getAll();
		if (result.isSuccess == true) {
			lNote = ((List<NoteDto>) result.returnValue);
			if (lNote.size() > 0) {
				// notifyOutboxHandlers(
				// MessageConstant.MESSAGE_REQUEST_START_SERVERTIME, -1,
				// -1, null);
			} else {
				// notifyOutboxHandlers(
				// MessageConstant.MESSAGE_REQUEST_STOP_SERVERTIME, -1,
				// -1, null);
			}
			return lNote;
		}
		return null;
	}

	public void deleteNote(NoteDto message) {
		TransactionCommandAck result = noteDao.delete(message);
		if (result.isSuccess == true) {
			Log.i("HUUU", "Xóa thành công");
		}
	}

	public void saveMessage(NoteDto message) {
		TransactionCommandAck result = noteDao.save(message);
		if (result.isSuccess == true) {
			// notifyOutboxHandlers(MessageConstant.MESSAGE_RESPONSE_SAVE, -1,
			// -1,
			// ResultResponse.OK);
		}
	}
}
