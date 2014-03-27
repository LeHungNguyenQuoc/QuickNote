package kn.multinote.ui.fragment;

import android.widget.TextView;
import kn.multinote.ui.activity.R;
import kn.multinote.ui.fragment.base.MNBaseFragment;
import kn.multinote.utils.FontUtils;

public class MainMenuFragment extends MNBaseFragment {

	@Override
	protected int getFragmentLayoutResource() {
		return R.layout.frag_main_menu;
	}

	@Override
	protected void onStartFragment() {
		initTitle();
	}
	
	private void initTitle() {
		TextView tvTitle = findViewById(R.id.tv_title);
		FontUtils.getIntance().setFontMediumCond(tvTitle);
	}

}
