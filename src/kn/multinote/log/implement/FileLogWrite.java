package kn.multinote.log.implement;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import kn.multinote.dto.iLog;
import kn.supportrelax.database.transaction.TransactionCommandAck;
import android.os.Environment;

public class FileLogWrite {

	private final static String m_sfileLog = "log.txt";
	private static InputStream m_fInput;
	private static DataOutputStream m_fOutput;
	private static File file;
	private static String strtmp;
	private static int countID = 1;

	public FileLogWrite() {
		strtmp = "";
		init();
	}

	public void init() {
		try {
			// LogApp.getContext().getAssets().open(m_sfileLog);
			File root = new File(Environment.getExternalStorageDirectory(),
					"Notes");
			if (!root.exists()) {
				root.mkdirs();
			}

			file = new File(root, m_sfileLog);

			if (!file.exists())
				file.createNewFile();
			else {
				try {
					m_fInput = new FileInputStream(file);
					BufferedReader myReader = new BufferedReader(
							new InputStreamReader(m_fInput));
					String aDataRow = "";
					while ((aDataRow = myReader.readLine()) != null) {
						strtmp += aDataRow + "\n";
						countID++;
					}
				} catch (Exception ex) {

				}
			}
			m_fOutput = new DataOutputStream(new FileOutputStream(file));
			m_fOutput.writeBytes(strtmp);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public TransactionCommandAck save(iLog log) {
		TransactionCommandAck result = new TransactionCommandAck();
		try {
			if (m_fOutput == null) {
				init();
			}
			strtmp += (countID++) + " : " + log.getContent() + "\n";
			m_fOutput.writeBytes(countID + " : " + log.getContent() + "\n");
			m_fOutput.flush();
			result.isSuccess = true;
		} catch (Exception ex) {
			try {
				m_fInput.close();
				m_fOutput.close();
				m_fInput = null;
				m_fOutput = null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			result.isSuccess = false;

		}
		return result;
	}


	public TransactionCommandAck getAll() {
		TransactionCommandAck result = new TransactionCommandAck();
		if (strtmp == "")
			init();
		List<iLog> lLog = new ArrayList<iLog>();
		String[] tmp = strtmp.split("\n");
		for (int i = 0; i < tmp.length; i++) {
			String[] tmp1 = tmp[i].split(" : ");
			if (tmp1.length > 1) {
				iLog lg = new iLog(Integer.parseInt(tmp1[0]), tmp1[1], 0);
				lLog.add(lg);
			}

		}
		if (lLog.size() > 0) {
			result.returnValue = lLog;
			result.isSuccess = true;
		} else
			result.isSuccess = false;

		return result;
	}


	public TransactionCommandAck deleteById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}


	public TransactionCommandAck delete(iLog entity) {
		TransactionCommandAck result = new TransactionCommandAck();
		if (entity == null) {
			strtmp = "";
			countID = 1;
		} else {
			strtmp = strtmp.replace(
					entity.getId() + " : " + entity.getContent() + "\n", "");
		}
		try {
			m_fOutput = new DataOutputStream(new FileOutputStream(file));
			m_fOutput.writeBytes(strtmp);
			m_fOutput.flush();
			result.isSuccess = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.isSuccess = false;
		}
		return result;
	}


	public TransactionCommandAck getById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}


	public void close() {
		// TODO Auto-generated method stub

	}


	public TransactionCommandAck deleteAll() {
		TransactionCommandAck result = new TransactionCommandAck();
		strtmp = "";
		countID = 1;
		try {
			m_fOutput = new DataOutputStream(new FileOutputStream(file));
			m_fOutput.writeBytes(strtmp);
			m_fOutput.flush();
			result.isSuccess = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.isSuccess = false;
		}
		return result;
	}

}
