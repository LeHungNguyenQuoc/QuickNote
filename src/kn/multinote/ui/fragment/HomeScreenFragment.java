package kn.multinote.ui.fragment;

import android.annotation.SuppressLint;
import android.support.v4.view.ViewPager;
import kn.multinote.ui.activity.R;
import kn.multinote.ui.fragment.base.BasePagerFragment;
import kn.multinote.ui.fragment.base.MNBaseFragment;

@SuppressLint("ValidFragment")
public class HomeScreenFragment extends BasePagerFragment{

	public HomeScreenFragment(ViewPager viewPager) {
		super(viewPager);
	}

	@Override
	protected int getFragmentLayoutResource() {
		return R.layout.frag_home_screen;
	}

	@Override
	protected void onInitFragment() {
		
	}

}
