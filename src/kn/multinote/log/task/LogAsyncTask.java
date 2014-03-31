package kn.multinote.log.task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import kn.multinote.log.IBaseLog;
import android.os.AsyncTask;

public class LogAsyncTask extends AsyncTask<String, String, String> {

	private IBaseLog mIBaseLog;

	public LogAsyncTask(IBaseLog baseLog) {
		super();
		mIBaseLog = baseLog;
	}

	@Override
	protected String doInBackground(String... params) {
		String log = "";

		try {
			Process proccess = Runtime.getRuntime().exec(params[0]);
			InputStream inputStream = proccess.getInputStream();

			InputStreamReader inputReader = new InputStreamReader(inputStream);
			BufferedReader bufferedReader = new BufferedReader(inputReader);

			while (bufferedReader.read() != -1) {
				log = bufferedReader.readLine();
				publishProgress(log);
			}
			// mIBaseLog.export(log);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return log;
	}

	@Override
	protected void onProgressUpdate(String... progress) {
		if (progress[0] != null)
			mIBaseLog.export(progress[0]);
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}
}
