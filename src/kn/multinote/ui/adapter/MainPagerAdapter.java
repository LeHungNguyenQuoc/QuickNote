package kn.multinote.ui.adapter;

import kn.multinote.data.viewmodel.adapter.PagerAdapterViewModel;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MainPagerAdapter extends FragmentPagerAdapter{
	
	private PagerAdapterViewModel mViewModel;
	
	public MainPagerAdapter(FragmentManager fm, PagerAdapterViewModel viewModel) {
		super(fm);
		this.mViewModel = viewModel;
	}

	@Override
	public Fragment getItem(int index) {
		return mViewModel.fragments.get(index);
	}

	@Override
	public int getCount() {
		return mViewModel.fragments.size();
	}

}
