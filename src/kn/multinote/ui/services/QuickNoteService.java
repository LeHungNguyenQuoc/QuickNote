package kn.multinote.ui.services;

import java.util.Calendar;
import java.util.List;

import kn.multinote.database.access.ISystemSettingDAO;
import kn.multinote.dto.SystemSettingDto;
import kn.multinote.factory.DAOFactory;
import kn.multinote.ui.activity.R;
import kn.multinote.ui.icon.implement.IconHomeScreen;
import kn.multinote.ui.icon.implement.IconNoteCapture;
import kn.multinote.ui.icon.implement.IconNoteFinance;
import kn.multinote.ui.icon.implement.IconNoteRecordSound;
import kn.multinote.ui.object.IconNote;
import kn.supportrelax.database.transaction.TransactionCommandAck;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;

public class QuickNoteService extends Service {

	private View mView;
	WindowManager.LayoutParams params;
	public View mViewNext;
	public WindowManager.LayoutParams paramNexts;
	WindowManager wm;
	public static int widthTruct;
	public static int heightTruct;
	public static int mwidth, mheight;
	public int posX, posY;
	public boolean flagViewNext;
	private static QuickNoteService instance;
	public SystemSettingDto mySystemSetting;

	public void onCreate() {
		super.onCreate();
		instance = this;
		showIcon();
	}

	public static QuickNoteService getInstance() {
		return instance;
	}

	private void showIcon() {
		if (mView == null) {
			ISystemSettingDAO systemSettingDao = DAOFactory.getInstance()
					.getComponent(ISystemSettingDAO.class);
			TransactionCommandAck result = systemSettingDao.getAll();
			if (result != null) {
				if (result.isSuccess) {
					List<SystemSettingDto> lSystem = (List<SystemSettingDto>) result.returnValue;
					mySystemSetting = lSystem.get(0);
					widthTruct = mySystemSetting.getWidthTruct();
					heightTruct = mySystemSetting.getHeightTruct();
				}
			}
			mView = new View(getApplicationContext());
			mwidth = 200 * widthTruct / 240;
			mheight = 200 * widthTruct / 240;
			posX = (widthTruct - mwidth) / 2;
			posY = (heightTruct - mheight) / 2;
			mViewNext = new NextView(getApplicationContext());
			mView.setBackgroundResource(R.drawable.ic_launcher);

			mView.setOnTouchListener(new OnTouchListener() {
				float downX, downY;
				private static final int MAX_CLICK_DURATION = 100;
				private long startClickTime;

				@Override
				public boolean onTouch(View v, MotionEvent event) {

					final int action = event.getAction();
					switch (action & MotionEvent.ACTION_MASK) {
					case MotionEvent.ACTION_DOWN:
						downX = event.getRawX() - params.x;
						downY = event.getRawY() - params.y;
						startClickTime = Calendar.getInstance()
								.getTimeInMillis();
						break;
					case MotionEvent.ACTION_MOVE:
						params.x = Math.round(event.getRawX() - downX);
						params.y = Math.round(event.getRawY() - downY);
						wm.updateViewLayout(mView, params);
						break;
					case MotionEvent.ACTION_UP:
						long clickDuration = Calendar.getInstance()
								.getTimeInMillis() - startClickTime;
						if (clickDuration < MAX_CLICK_DURATION) {
							changeView(mViewNext, paramNexts);
							wm.removeView(mView);
							flagViewNext = true;
						} else {
							updatePosition();
						}
						break;
					case MotionEvent.ACTION_OUTSIDE:

						break;
					}
					return true;
				}
			});
			params = new WindowManager.LayoutParams(
					WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
					WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
							| WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
							| WindowManager.LayoutParams.FLAG_SPLIT_TOUCH
							| WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
							| WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
					PixelFormat.TRANSLUCENT);

			paramNexts = new WindowManager.LayoutParams(

					WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
					WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
							| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
							| WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
					PixelFormat.TRANSLUCENT);
			params.gravity = Gravity.LEFT | Gravity.TOP;
			params.setTitle("Load Average");
			params.width = 35 * widthTruct / 240;
			params.height = 35 * widthTruct / 240;

			paramNexts.width = mwidth;
			paramNexts.height = mheight;
			paramNexts.x = posX;
			paramNexts.y = posY;
			paramNexts.gravity = Gravity.LEFT | Gravity.TOP;
			paramNexts.setTitle("View Average");

			wm = (WindowManager) getSystemService(WINDOW_SERVICE);
			wm.addView(mView, params);
			flagViewNext = false;
		}
	}

	byte type;
	public Thread thread;

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
								params.x = params.x - 1;
								if (params.x <= 0) {
									flagStop = true;
									params.x = 0;
								}
								break;
							case 1:
								params.x = params.x + 1;
								if (params.x >= widthTruct - mView.getWidth()) {
									flagStop = true;
									params.x = widthTruct - mView.getWidth();
								}
								break;
							case 2:
								params.y = params.y - 1;
								if (params.y <= 0) {
									flagStop = true;
									params.y = 0;
								}
								break;
							case 3:
								params.y = params.y + 1;
								if (params.y >= heightTruct - mView.getHeight()) {
									flagStop = true;
									params.y = heightTruct - mView.getHeight();
								}
								break;
							default:
								break;
							}
							wm.updateViewLayout(mView, params);
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
		int min = params.x;
		if (widthTruct - (params.x + mView.getWidth()) < min) {
			min = widthTruct - (params.x + mView.getWidth());
			result = 1;
		}
		if (params.y < min) {
			min = params.y;
			result = 2;
		}
		if (heightTruct - (params.y + mView.getHeight()) < min)
			result = 3;
		return result;
	}

	public void changeView(View view, WindowManager.LayoutParams params) {
		try {
			wm.addView(view, params);
		} catch (Exception ex) {
			wm.removeView(view);
			wm.addView(view, params);
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.e("debug", "PopupNote ========== on destroy");
		try {
			((WindowManager) getSystemService(WINDOW_SERVICE))
					.removeView(mView);
			((WindowManager) getSystemService(WINDOW_SERVICE))
					.removeView(mViewNext);
		} catch (Exception ex) {

		}
		mView = null;
		mViewNext = null;
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		showIcon();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		showIcon();

		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	public class NextView extends View {
		private boolean flagTouch;
		private IconNote iconSound;
		private IconNote iconHomescreen;
		private IconNote iconCapture;
		private IconNote iconFinance;

		private Bitmap bitmap;

		public NextView(Context context) {
			super(context);
			setBackgroundResource(R.drawable.move_touch);
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
					changeView(mView, params);
					wm.removeView(mViewNext);
				} else {
					if (iconHomescreen.getFocus()) {
						iconHomescreen.runable();
						iconHomescreen.setFocus(false);
						changeView(mView, params);
						wm.removeView(mViewNext);
					} else {
						if (iconCapture.getFocus()) {
							iconCapture.runable();
							iconCapture.setFocus(false);
							changeView(mView, params);
							wm.removeView(mViewNext);
						} else if (iconFinance.getFocus()) {
							iconFinance.runable();
							iconFinance.setFocus(false);
							changeView(mView, params);
							wm.removeView(mViewNext);
						}
					}
				}
				flagTouch = false;
				break;
			case MotionEvent.ACTION_OUTSIDE:
				wm.addView(mView, params);
				wm.removeView(mViewNext);
				flagViewNext = false;
				break;
			}
			return super.onTouchEvent(event);
		}

	}

}
