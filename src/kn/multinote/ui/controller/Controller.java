package kn.multinote.ui.controller;

import java.util.ArrayList;
import java.util.List;

import android.os.Handler;
import android.os.Message;

public abstract class Controller {
	@SuppressWarnings("unused")
	private static final String TAG = Controller.class.getSimpleName();
	private final List<Handler> outboxHandlers = new ArrayList<Handler>();

	public Controller() {

	}

	public void dispose() {
	}

	abstract public boolean handleMessage(int what, Object data);

	public boolean handleMessage(int what) {
		return handleMessage(what, null);
	}

	public final void addOutboxHandler(Handler handler) {
		outboxHandlers.add(handler);
	}

	public final void removeOutboxHandler(Handler handler) {
		outboxHandlers.remove(handler);
	}

	public final void notifyOutboxHandlers(int what, int arg1, int arg2,
			Object obj) {
		if (!outboxHandlers.isEmpty()) {
			for (Handler handler : outboxHandlers) {
				Message msg = Message.obtain(handler, what, arg1, arg2, obj);
				msg.sendToTarget();
			}
		}
	}
}
