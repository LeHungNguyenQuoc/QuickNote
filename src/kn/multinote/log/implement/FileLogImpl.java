package kn.multinote.log.implement;

import kn.multinote.dto.iLog;
import kn.multinote.log.IBaseLog;

public class FileLogImpl implements IBaseLog {
	// private static String mCommand = "E";
	private FileLogWrite mLogDAO;

	public FileLogImpl() {
		mLogDAO = new FileLogWrite();
	}

	@Override
	public String command(String command) {
		return "logcat -v time ActivityManager:" + command + " MyApp:"
				+ command + " *:" + command;
	}

	@Override
	public void export(String log) {
		// Write log to file
		if (mLogDAO == null) {
			mLogDAO = new FileLogWrite();
		}
		iLog log1 = new iLog();
		log1.setContent(log);
		mLogDAO.save(log1);
	}

	@Override
	public void clearLog() {
		mLogDAO.deleteAll();
	}
}
