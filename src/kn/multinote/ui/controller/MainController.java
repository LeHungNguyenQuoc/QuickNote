package kn.multinote.ui.controller;

import kn.multinote.app.MultiNoteApp;
import kn.multinote.database.access.ISystemSettingDAO;
import kn.multinote.defines.MessageConstant;
import kn.multinote.dto.SystemSettingDto;
import kn.multinote.factory.DAOFactory;
import kn.multinote.ui.services.QuickNoteService;
import kn.supportrelax.database.transaction.TransactionCommandAck;
import android.content.Intent;

public class MainController extends Controller {

	private static String TAG = MainController.class.getName();
	private static MainController instance;

	public MainController() {
		super();
		instance = this;
	}

	public static MainController getInstance() {
		if (instance == null) {
			instance = new MainController();
		}
		return instance;
	}

	int countsendSMS = 0;

	@Override
	public boolean handleMessage(int what, Object data) {
		switch (what) {
		case MessageConstant.MESSAGE_START_SERVICE:
			if (QuickNoteService.getInstance() == null) {
				startService();
			} else {
//				notifyOutboxHandlers(MessageConstant.MESSAGE_START_SHOWNOTE,
//						-1, -1, null);
			}
			break;
		}
		return false;
	}

	public void startService() {
		Intent intent = new Intent(MultiNoteApp.getContext(),
				QuickNoteService.class);
		//intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		MultiNoteApp.getContext().startService(intent);
		saveSystemSetting();
	}

	public void saveSystemSetting() {
		ISystemSettingDAO systemSettingDao = DAOFactory.getInstance()
				.getComponent(ISystemSettingDAO.class);
		TransactionCommandAck result = systemSettingDao.getAll();
		if (result != null) {
			if (!result.isSuccess) {
				SystemSettingDto mySystemSetting = new SystemSettingDto(
						QuickNoteService.widthTruct,
						QuickNoteService.heightTruct);
				systemSettingDao.save(mySystemSetting);
			}
		}
	}
}
