package kn.multinote.ui.services;

import java.util.Calendar;
import java.util.Timer;

import kn.multinote.ui.activity.R;
import kn.multinote.ui.icon.implement.IconHomeScreen;
import kn.multinote.ui.icon.implement.IconNoteCapture;
import kn.multinote.ui.icon.implement.IconNoteFinance;
import kn.multinote.ui.icon.implement.IconNoteRecordSound;
import kn.multinote.ui.object.IconNote;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.IBinder;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

public class PopupNote extends Service {

	private Timer timer;

	public static View mView;
	public static View mViewNext;
	public static WindowManager.LayoutParams mParams;
	public static WindowManager.LayoutParams mParamNexts;
	public static WindowManager mWindowManager;
	public static int widthTruct;
	public static int heightTruct;
	public static int mwidth, mheight;
	public static boolean flagViewNext;
	public int posX, posY;

	@Override
	public void onCreate() {
		super.onCreate();
		mwidth = 200 * widthTruct / 240;
		mheight = 200 * widthTruct / 240;
		init();
	}

	public void changeView(View view, WindowManager.LayoutParams params) {
		try {
			mWindowManager.addView(view, params);
		} catch (Exception ex) {
			mWindowManager.removeView(view);
			mWindowManager.addView(view, params);
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (timer != null) {
			timer.cancel();
		}
		try {
			((WindowManager) getSystemService(WINDOW_SERVICE))
					.removeView(mView);
			((WindowManager) getSystemService(WINDOW_SERVICE))
					.removeView(mViewNext);
		} catch (Exception ex) {

		}
		mView = null;
		mViewNext = null;
		Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();

	}

	int a = 0;

	public final static int COUNT_TIMER = 1000;

	@Override
	public int onStartCommand(Intent intent, int flags, int startid) {
		init();
		mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
		changeView(mView, mParams);
		// timer.scheduleAtFixedRate(new TimerTask() {
		// @Override
		// public void run() {
		// // Toast toast = Toast.makeText(getApplicationContext(),
		// // "Now Playing: " + a, Toast.LENGTH_LONG);
		// // toast.show();
		// try {
		// if (!flagViewNext) {
		// changeView(mView, mParams);
		// }
		//
		// } catch (Exception ex) {
		// // if (!flagViewNext) {
		// // mWindowManager.updateViewLayout(mView, mParams);
		// // }
		// }
		// a += 1;
		// }
		// }, COUNT_TIMER, 1000);
		return START_STICKY;
	}

	public void init() {
		if (mView == null) {
			mView = new IconNoteView(this);
			mViewNext = new NextView(this);
			mParams = new WindowManager.LayoutParams(
					40 * widthTruct / 240,
					40 * widthTruct / 240,
					0,
					0,
					WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY
							| WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
					WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
							| WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
							| WindowManager.LayoutParams.FLAG_SPLIT_TOUCH
							| WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
							| WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
					PixelFormat.TRANSLUCENT);

			posX = (widthTruct - mwidth) / 2;
			posY = (heightTruct - mheight) / 2;
			mParamNexts = new WindowManager.LayoutParams(
					mwidth,
					mheight,
					posX,
					posY,
					WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
					WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
							| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
							| WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
					PixelFormat.TRANSLUCENT);

			mParams.gravity = Gravity.TOP | Gravity.LEFT;
			// mParams.systemUiVisibility = 0;
			mParamNexts.gravity = Gravity.TOP | Gravity.LEFT;
			mParams.setTitle("Window test");

			// changeView(mView, mParams);
			flagViewNext = false;
			timer = new Timer();
		}
	}

	public class IconNoteView extends View {

		private Paint mPaint;
		private static final int MAX_CLICK_DURATION = 100;
		private long startClickTime;
		public byte type;
		public Thread thread;
		// private WindowManager.LayoutParams param;
		boolean flagStop = false;
		public Bitmap bitmap;

		public IconNoteView(Context context) {
			super(context);
			mPaint = new Paint();
			mPaint.setTextSize(50);
			mPaint.setARGB(200, 200, 200, 200);
			Drawable drawable = context.getResources().getDrawable(
					R.drawable.ic_launcher);

			// bitmap = ((BitmapDrawable) drawable).getBitmap();
			setBackground(drawable);
		}

		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			// canvas.drawBitmap(bitmap, 0, 0, null);
		}

		@Override
		protected void onAttachedToWindow() {
			super.onAttachedToWindow();
		}

		@Override
		protected void onDetachedFromWindow() {
			super.onDetachedFromWindow();
		}

		@Override
		protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}

		float downX, downY;

		@Override
		public boolean onTouchEvent(MotionEvent event) {
			final int action = event.getAction();
			switch (action & MotionEvent.ACTION_MASK) {
			case MotionEvent.ACTION_DOWN:
				downX = event.getRawX() - mParams.x;
				downY = event.getRawY() - mParams.y;
				startClickTime = Calendar.getInstance().getTimeInMillis();
				break;
			case MotionEvent.ACTION_MOVE:
				mParams.x = Math.round(event.getRawX() - downX);
				mParams.y = Math.round(event.getRawY() - downY);
				mWindowManager.updateViewLayout(mView, mParams);
				break;
			case MotionEvent.ACTION_UP:
				long clickDuration = Calendar.getInstance().getTimeInMillis()
						- startClickTime;
				if (clickDuration < MAX_CLICK_DURATION) {
					changeView(mViewNext, mParamNexts);
					mWindowManager.removeView(mView);
					flagViewNext = true;
				} else {
					updatePosition();
				}
				break;
			case MotionEvent.ACTION_OUTSIDE:

				break;
			}
			return super.onTouchEvent(event);
		}

		public void updatePosition() {
			type = checklocation();
			if (thread == null) {
				thread = new Thread() {
					@Override
					public void run() {
						try {
							boolean flagStop = false;
							long timeStart = System.currentTimeMillis();
							while (!flagStop) {

								switch (type) {
								case 0:
									mParams.x = mParams.x - 1;
									if (mParams.x <= 0) {
										flagStop = true;
										mParams.x = 0;
									}
									break;
								case 1:
									mParams.x = mParams.x + 1;
									if (mParams.x >= widthTruct
											- mView.getWidth()) {
										flagStop = true;
										mParams.x = widthTruct
												- mView.getWidth();
									}
									break;
								case 2:
									mParams.y = mParams.y - 1;
									if (mParams.y <= 0) {
										flagStop = true;
										mParams.y = 0;
									}
									break;
								case 3:
									mParams.y = mParams.y + 1;
									if (mParams.y >= heightTruct
											- mView.getHeight()) {
										flagStop = true;
										mParams.y = heightTruct
												- mView.getHeight();
									}
									break;
								default:
									break;
								}
								mWindowManager.updateViewLayout(mView, mParams);
								// thread.stop();
							}
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
						}
					}
				};
			}
			if (thread.getState() == Thread.State.NEW) {
				thread.start();
				thread.run();
			} else {
				thread.run();
			}
		}

		public byte checklocation() {
			byte result = 0;
			int min = mParams.x;
			if (widthTruct - (mParams.x + mView.getWidth()) < min) {
				min = widthTruct - (mParams.x + mView.getWidth());
				result = 1;
			}
			if (mParams.y < min) {
				min = mParams.y;
				result = 2;
			}
			if (heightTruct - (mParams.y + mView.getHeight()) < min)
				result = 3;
			return result;
		}

		@Override
		public void onWindowVisibilityChanged(int v) {
			super.onWindowVisibilityChanged(v);
		}

		@Override
		public void onWindowSystemUiVisibilityChanged(int visible) {
			super.onWindowSystemUiVisibilityChanged(visible);
		}
	}

	public class NextView extends View {
		private boolean flagTouch;
		private IconNote iconSound;
		private IconNote iconHomescreen;
		private IconNote iconCapture;
		private IconNote iconFinance;

		// private Bitmap bitmap;

		public NextView(Context context) {
			super(context);
			Drawable drawable = context.getResources().getDrawable(
					R.drawable.move_touch);
			// bitmap = ((BitmapDrawable) drawable).getBitmap();
			setBackground(drawable);
			IconNote.widthIcon = 41 * widthTruct / 240;
			IconNote.heightIcon = 41 * widthTruct / 240;
			iconSound = new IconNoteRecordSound(R.drawable.ic_sound,
					R.drawable.ic_soundfocus);
			iconSound.setPos((mwidth - IconNote.widthIcon) / 2, 20);
			iconHomescreen = new IconHomeScreen(R.drawable.ic_home,
					R.drawable.ic_homefocus);
			iconHomescreen.setPos((mwidth - IconNote.widthIcon) / 2, mheight
					- 20 - IconNote.heightIcon);
			iconCapture = new IconNoteCapture(R.drawable.ic_capture,
					R.drawable.ic_capture_focus);
			iconCapture.setPos(20, (mheight - IconNote.heightIcon) / 2);
			iconFinance = new IconNoteFinance(R.drawable.ic_money,
					R.drawable.ic_money_focus);
			iconFinance.setPos(mwidth - 20 - IconNote.widthIcon,
					(mheight - IconNote.heightIcon) / 2);
		}

		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			// canvas.drawBitmap(bitmap, 0, 0, null);
			iconSound.paint(canvas);
			iconHomescreen.paint(canvas);
			iconCapture.paint(canvas);
			iconFinance.paint(canvas);
		}

		@Override
		protected void onAttachedToWindow() {
			super.onAttachedToWindow();
		}

		@Override
		protected void onDetachedFromWindow() {
			super.onDetachedFromWindow();
		}

		@Override
		protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}

		@Override
		public boolean onTouchEvent(MotionEvent event) {
			// Toast.makeText(getContext(), "onTouchEvent", Toast.LENGTH_LONG)
			// .show();
			final int action = event.getAction();
			switch (action & MotionEvent.ACTION_MASK) {
			case MotionEvent.ACTION_DOWN:
				iconSound.checkArea((int) event.getX(), (int) event.getY());
				iconHomescreen
						.checkArea((int) event.getX(), (int) event.getY());
				iconCapture.checkArea((int) event.getX(), (int) event.getY());
				iconFinance.checkArea((int) event.getX(), (int) event.getY());
				this.invalidate();
				flagTouch = true;
				break;
			case MotionEvent.ACTION_MOVE:
				iconSound.checkArea((int) event.getX(), (int) event.getY());
				iconHomescreen
						.checkArea((int) event.getX(), (int) event.getY());
				iconCapture.checkArea((int) event.getX(), (int) event.getY());
				iconFinance.checkArea((int) event.getX(), (int) event.getY());
				this.invalidate();
				break;
			case MotionEvent.ACTION_UP:
				if (iconSound.getFocus()) {
					iconSound.runable();
					iconSound.setFocus(false);
					changeView(mView, mParams);
					mWindowManager.removeView(mViewNext);
				} else {
					if (iconHomescreen.getFocus()) {
						iconHomescreen.runable();
						iconHomescreen.setFocus(false);
						changeView(mView, mParams);
						mWindowManager.removeView(mViewNext);
					} else {
						if (iconCapture.getFocus()) {
							iconCapture.runable();
							iconCapture.setFocus(false);
							changeView(mView, mParams);
							mWindowManager.removeView(mViewNext);
						} else if (iconFinance.getFocus()) {
							iconFinance.runable();
							iconFinance.setFocus(false);
							changeView(mView, mParams);
							mWindowManager.removeView(mViewNext);
						}
					}
				}
				flagTouch = false;
				break;
			case MotionEvent.ACTION_OUTSIDE:
				changeView(mView, mParams);
				mWindowManager.removeView(mViewNext);
				flagViewNext = false;
				break;
			}
			return super.onTouchEvent(event);
		}

	}
}
