package kn.multinote.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FontUtils {
	private static FontUtils instance;
	public static FontUtils getIntance() {
		return instance;
	}
	
	public static void init(Context context) {
		instance = new FontUtils(context);
	}
	private Typeface helveticaNeueRoman;
	private Typeface helveticaNeueMediumCond;
	private Typeface helveticaNeueBoldCond;
	
	public FontUtils(Context context) {
		helveticaNeueRoman = Typeface.createFromAsset(context.getAssets(), "font/HelveticaNeueLTStd-Roman.otf");
		helveticaNeueMediumCond = Typeface.createFromAsset(context.getAssets(), "font/HelveticaNeueLTStd-MdCn.otf");
		helveticaNeueBoldCond = Typeface.createFromAsset(context.getAssets(), "font/HelveticaNeueLTStd-BdCn.otf");
	}
	
	
	private void setFont(View view, Typeface font) {
		if (view instanceof TextView) {
			((TextView) view).setTypeface(font);
		} else if (view instanceof Button) {
			((Button) view).setTypeface(font);
		} else if (view instanceof EditText) {
			 ((EditText) view).setTypeface(font);
		}
	}
	
	public void setFontRoman(View view) {
		setFont(view, helveticaNeueRoman);
	}
	
	public void setFontMediumCond(View view) {
		setFont(view, helveticaNeueMediumCond);
	}
	
	public void setFontBoldCond(View view) {
		setFont(view, helveticaNeueBoldCond);
	}
}
