package kn.multinote.ui.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import kn.multinote.database.access.INoteDAO;
import kn.multinote.database.access.ISystemSettingDAO;
import kn.multinote.defines.TypeNote;
import kn.multinote.dto.NoteDto;
import kn.multinote.factory.DAOFactory;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CaptureSoundActivity extends Activity implements OnClickListener {

	private static final int CAMERA_REQUEST = 1888;
	private ImageView imageView;
	private ImageButton photoButton;
	private ImageButton recordButton;
	private ImageButton stoprecordButton;
	private ImageButton saveCaptureRecord;
	private TextView tvStatusRecord;
	private Bitmap photo;

	private static final String AUDIO_RECORDER_FILE_EXT_3GP = ".3gp";
	private static final String AUDIO_RECORDER_FILE_EXT_MP4 = ".mp4";
	private static final String AUDIO_RECORDER_FOLDER = "AudioRecorder";
	private static final String CAPTURE_RECORDER_FOLDER = "CaptureRecorder";
	private MediaRecorder recorder = null;
	private int currentFormat = 0;
	private int output_formats[] = { MediaRecorder.OutputFormat.MPEG_4,
			MediaRecorder.OutputFormat.THREE_GPP };
	private String file_exts[] = { AUDIO_RECORDER_FILE_EXT_MP4,
			AUDIO_RECORDER_FILE_EXT_3GP };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_capture_sound);
		this.imageView = (ImageView) this.findViewById(R.id.showImg);
		photoButton = (ImageButton) this.findViewById(R.id.ibtPhoto);
		recordButton = (ImageButton) this.findViewById(R.id.ibtRecord);
		stoprecordButton = (ImageButton) this.findViewById(R.id.ibtStop);
		saveCaptureRecord = (ImageButton) this.findViewById(R.id.ibtSave);
		tvStatusRecord = (TextView) this.findViewById(R.id.tvStatusRecord);

		photoButton.setOnClickListener(this);
		recordButton.setOnClickListener(this);
		stoprecordButton.setOnClickListener(this);
		saveCaptureRecord.setOnClickListener(this);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
			// photo = (Bitmap) data.getExtras().get("data");
			BitmapFactory.Options option = new BitmapFactory.Options();
			option.inPreferredConfig = Bitmap.Config.ARGB_8888;
			photo = BitmapFactory.decodeFile(output.getPath());
			imageView.setImageBitmap(photo);
		}
	}

	private File output = null;
	private String pathNameCapture = null;
	private String fileName = null;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ibtPhoto:
			Intent cameraIntent = new Intent(
					android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			// pathNameCapture = getNameCurrent("IMG");
			// output = new File(pathNameCapture);
			fileName = getNameCurrent("IMG");
			output = getFile(fileName);
			cameraIntent
					.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(output));
			startActivityForResult(cameraIntent, CAMERA_REQUEST);
			break;
		case R.id.ibtRecord:
			startRecording();
			break;
		case R.id.ibtStop:
			stopRecording();
			break;
		case R.id.ibtSave:
			SaveCaptureRecord();
			break;
		}
	}

	private String getNameCurrent(String name) {
		final Calendar c = Calendar.getInstance();
		return name + "_" + c.get(Calendar.DAY_OF_MONTH)
				+ (c.get(Calendar.MONTH) + 1) + c.get(Calendar.YEAR) + "_"
				+ c.get(Calendar.HOUR_OF_DAY) + c.get(Calendar.MINUTE);
	}

	private String getFilename() {
		String filepath = Environment.getExternalStorageDirectory().getPath();
		File file = new File(filepath, AUDIO_RECORDER_FOLDER);
		if (!file.exists()) {
			file.mkdirs();
		}

		return (file.getAbsolutePath() + "/" + getNameCurrent("AUD") + file_exts[currentFormat]);
	}

	private String getFilename(String namefile) {
		String filepath = Environment.getExternalStorageDirectory().getPath();
		File file = new File(filepath, CAPTURE_RECORDER_FOLDER);
		if (!file.exists()) {
			file.mkdirs();
		}
		return (file.getAbsolutePath() + "/" + namefile + ".jpg");
	}

	private File getFile(String namefile) {
		String filepath = Environment.getExternalStorageDirectory().getPath();
		File file = new File(filepath, CAPTURE_RECORDER_FOLDER);
		if (!file.exists()) {
			file.mkdirs();
		}
		return new File(file, namefile + ".jpg");
	}

	private String pathfile;

	private void startRecording() {
		recorder = new MediaRecorder();
		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		recorder.setOutputFormat(output_formats[currentFormat]);
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		recorder.setOutputFile(pathfile = getFilename());
		recorder.setOnErrorListener(errorListener);
		recorder.setOnInfoListener(infoListener);
		recordButton.setVisibility(View.GONE);
		saveCaptureRecord.setVisibility(View.GONE);
		stoprecordButton.setVisibility(View.VISIBLE);
		tvStatusRecord.setText("Recording Point: Recording");

		Toast.makeText(getApplicationContext(), "Start recording...",
				Toast.LENGTH_SHORT).show();
		try {
			recorder.prepare();
			recorder.start();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	MediaPlayer myPlayer;

	private void stopRecording() {
		if (null != recorder) {
			recorder.stop();
			recorder.reset();
			recorder.release();
			recordButton.setVisibility(View.VISIBLE);
			saveCaptureRecord.setVisibility(View.VISIBLE);
			stoprecordButton.setVisibility(View.GONE);
			tvStatusRecord.setText("Recording Point: Stop recording");
			Toast.makeText(getApplicationContext(), "Stop recording...",
					Toast.LENGTH_SHORT).show();
			recorder = null;
		}
	}

	private MediaRecorder.OnErrorListener errorListener = new MediaRecorder.OnErrorListener() {
		@Override
		public void onError(MediaRecorder mr, int what, int extra) {
			Toast.makeText(getApplicationContext(),
					"Error: " + what + ", " + extra, Toast.LENGTH_SHORT).show();
		}
	};

	private MediaRecorder.OnInfoListener infoListener = new MediaRecorder.OnInfoListener() {
		@Override
		public void onInfo(MediaRecorder mr, int what, int extra) {
			// Toast.makeText(getApplicationContext(),
			// "Warning: " + what + ", " + extra, Toast.LENGTH_SHORT)
			// .show();
		}
	};

	public void SaveCaptureRecord() {
		saveCaptureSound(fileName, false);
		Toast.makeText(getApplicationContext(), "Save capture sound",
				Toast.LENGTH_SHORT).show();
		finish();
	}

	@SuppressWarnings("deprecation")
	public void saveCaptureSound(String filename, boolean exit) {
		// String pathName = getFilename(filename);
		// savePicture(pathName, photo);
		final Calendar c = Calendar.getInstance();
		NoteDto noteDto = new NoteDto();
		noteDto.setNameNote(filename);
		noteDto.setDate(new Date(c.get(Calendar.YEAR) - 1900, c
				.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), c
				.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE)));
		noteDto.setTypeNote(TypeNote.NOTECAPTURE);
		noteDto.setParams(pathfile);
		noteDto.setValue(output.getPath());
		INoteDAO noteDao = DAOFactory.getInstance()
				.getComponent(INoteDAO.class);
		noteDao.save(noteDto);
		if (exit)
			finish();
	}

	public void savePicture(String filename, Bitmap bmp) {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(filename);
			bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (Throwable ignore) {
			}
		}
	}

}