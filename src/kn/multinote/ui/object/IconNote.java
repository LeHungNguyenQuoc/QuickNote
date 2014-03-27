package kn.multinote.ui.object;

import kn.multinote.app.MultiNoteApp;
import kn.multinote.defines.TypeIconNote;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public abstract class IconNote {
	private Bitmap bmIcon;
	private Bitmap bmIconFocur;
	private boolean flagFocus;
	private byte typeIcon;
	private int posX, posY;
	public static int widthIcon, heightIcon;

	public IconNote(int indexIcon, int indexIconfocus) {
		Drawable drawable = MultiNoteApp.getContext().getResources()
				.getDrawable(indexIcon);
		bmIcon = ((BitmapDrawable) drawable).getBitmap();
		Drawable drawableFocus = MultiNoteApp.getContext().getResources()
				.getDrawable(indexIconfocus);
		bmIconFocur = ((BitmapDrawable) drawableFocus).getBitmap();
	}

	public IconNote(Bitmap icon, Bitmap iconfocus) {
		this.bmIcon = icon;
		this.bmIconFocur = iconfocus;
	}

	public boolean getFocus() {
		return flagFocus;
	}

	public void setFocus(boolean value) {
		this.flagFocus = value;
	}

	public byte getTypeicon() {
		return typeIcon;
	}

	public void setTypeicon(byte type) {
		this.typeIcon = type;
	}

	public void setPos(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public void paint(Canvas g) {
		if (flagFocus) {
			g.drawBitmap(bmIconFocur, new Rect(0, 0, bmIconFocur.getWidth(),
					bmIconFocur.getHeight()), new Rect(posX, posY, posX
					+ widthIcon, posY + heightIcon), null);
		} else {
			g.drawBitmap(bmIcon,
					new Rect(0, 0, bmIcon.getWidth(), bmIcon.getHeight()),
					new Rect(posX, posY, posX + widthIcon, posY + heightIcon),
					null);
		}
	}

	public boolean checkArea(int x, int y) {
		flagFocus = (posX <= x && posY <= y && x <= posX + widthIcon && y <= posY
				+ heightIcon);
		return flagFocus;
	}

	public void runable() {
		switch (typeIcon) {
		case TypeIconNote.NOTERECORDSOUND:

			break;
		case TypeIconNote.HOMESCREEN:
			Intent startMain = new Intent(Intent.ACTION_MAIN);
			startMain.addCategory(Intent.CATEGORY_HOME);
			startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			MultiNoteApp.getContext().startActivity(startMain);
			break;

		default:
			break;
		}
	}

}
