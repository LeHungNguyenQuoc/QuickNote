package kn.multinote.ui.activity;

import java.io.FileOutputStream;

import kn.multinote.dto.NoteDto;
import kn.multinote.ui.note.implement.NoteCaptureSound;
import kn.multinote.ui.object.NoteBase;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayCaptureSoundActivity extends Activity implements
		OnClickListener {

	private static final int CAMERA_REQUEST = 1888;
	private ImageView imageView;
	private ImageButton playButton;
	private ImageButton stopButton;
	private TextView tvStatusPlay;
	private Bitmap photo;
	private NoteBase note;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_capture_sound);

		init();

	}

	public void init() {
		imageView = (ImageView) this.findViewById(R.id.showImg);
		playButton = (ImageButton) this.findViewById(R.id.ibtPlay);
		stopButton = (ImageButton) this.findViewById(R.id.ibtStop);
		tvStatusPlay = (TextView) this.findViewById(R.id.tvStatusPlay);

		playButton.setOnClickListener(this);
		stopButton.setOnClickListener(this);
		note = new NoteCaptureSound((NoteDto) getIntent().getSerializableExtra(
				"note"));
		BitmapFactory.Options option = new BitmapFactory.Options();
		option.inPreferredConfig = Bitmap.Config.ARGB_8888;
		imageView.setImageBitmap(BitmapFactory.decodeFile(note.getValue()));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ibtPhoto:
			Intent cameraIntent = new Intent(
					android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(cameraIntent, CAMERA_REQUEST);
			break;
		case R.id.ibtPlay:
			playSound(note.getParams());
			break;
		case R.id.ibtStop:
			stopSound();
			break;
		}
	}

	MediaPlayer myPlayer;

	private void playSound(String pathFile) {

		try {
			myPlayer = new MediaPlayer();
			myPlayer.setDataSource(pathFile);
			myPlayer.prepare();
			myPlayer.start();
		} catch (Exception ex) {

		}
		playButton.setVisibility(View.GONE);
		stopButton.setVisibility(View.VISIBLE);
		tvStatusPlay.setText("Play Point: playing sound");
		Toast.makeText(getApplicationContext(), "Playing sound...",
				Toast.LENGTH_SHORT).show();

	}

	private void stopSound() {

		try {
			myPlayer.stop();
		} catch (Exception ex) {

		}
		playButton.setVisibility(View.VISIBLE);
		stopButton.setVisibility(View.GONE);
		tvStatusPlay.setText("Play Point: Stop sound");
		Toast.makeText(getApplicationContext(), "Stop sound...",
				Toast.LENGTH_SHORT).show();

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