package kn.multinote.ui.fragment.base;

import android.support.v4.view.ViewPager;

public abstract class BasePagerFragment extends MNBaseFragment{
	
	private ViewPager mViewPager;
	public BasePagerFragment(ViewPager viewPager) {
		mViewPager = viewPager;
	}
	
	
	public void switchToPage(int pageIndex) {
		mViewPager.setCurrentItem(pageIndex, true);
	}
}
