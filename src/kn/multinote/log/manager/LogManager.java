package kn.multinote.log.manager;

import java.io.IOException;
import java.lang.reflect.Constructor;

import kn.multinote.log.IBaseLog;
import kn.multinote.log.implement.FileLogImpl;
import kn.multinote.log.task.LogAsyncTask;

public class LogManager {
	
	private static LogManager instance;
	private String mCommand;
	private boolean mIsNew;
	
	public LogManager()
	{
		mCommand = "E";
		mIsNew = false;
		instance = this;
	}
	
	public static LogManager getInstance() {
		return instance;
	}
	
	public void configLog(byte tyeSave,String command,boolean isNew)
	{
		mCommand = command;
		mIsNew = isNew;
		switch(tyeSave)
		{
		case 0: //Save to File
			setup(FileLogImpl.class);
			break;
//		case 1: //Save to Database
//			setup(SqliteLogImpl.class);
//			break;
		default://Save to File
			setup(FileLogImpl.class);
			break;
		}
	}
	
	public void setup(Class<?> clazz) {
		try {
			Class<? extends IBaseLog> claz = Class.forName(clazz.getName())
					.asSubclass(IBaseLog.class);

			Constructor<?> constr = claz.getConstructor();

			IBaseLog baseLog = (IBaseLog) constr.newInstance();
			// Get command
			String command = baseLog.command(mCommand);
			
			// Clear logcat
			try {
				Runtime.getRuntime().exec("logcat -e");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(mIsNew)
				baseLog.clearLog();
			
			// Get log in runtime
			LogAsyncTask logTask = new LogAsyncTask(baseLog);

			logTask.execute(command);
			// Export log
			//baseLog.export(log);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
