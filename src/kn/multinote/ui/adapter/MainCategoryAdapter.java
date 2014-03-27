package kn.multinote.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foound.widget.AmazingAdapter;

public class MainCategoryAdapter extends AmazingAdapter{

	private Context mContext;
	
	public MainCategoryAdapter(Context context) {
		mContext = context;
	}
	
	@Override
	public int getCount() {
		return 30;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	protected void bindSectionHeader(View arg0, int arg1, boolean isHeader) {
		if (isHeader) {
			TextView textView = ((TextView)arg0);
			textView.setText("Group: "+ getSectionForPosition(arg1));
			textView.setBackgroundColor(Color.GRAY);
		}
	}

	@Override
	public void configurePinnedHeader(View arg0, int arg1, int arg2) {
		
	}

	@Override
	public View getAmazingView(int arg0, View arg1, ViewGroup arg2) {
		TextView textView = new TextView(mContext);
		textView.setText("  - Category:" + arg0 );
		return textView;
	}

	@Override
	public int getPositionForSection(int arg0) {
		return arg0 * 10;
	}

	@Override
	public int getSectionForPosition(int arg0) {
		return arg0 / 10;
	}

	@Override
	public Object[] getSections() {
		return null;
	}

	@Override
	protected void onNextPageRequested(int arg0) {
		// TODO Auto-generated method stub
		
	}

}
