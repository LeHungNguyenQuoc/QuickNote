package kn.multinote.ui.fragment.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

public abstract class MNBaseFragment extends Fragment{
	protected View mRootView;
	
	public View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(getFragmentLayoutResource(), container, false);
		mRootView = rootView;
		return rootView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		onStartFragment();
	} 
	
	/**
	 * @return resource layout for your fragment
	 */
	protected abstract int getFragmentLayoutResource();
	
	
	/**
	 * Setting for your fragment when enter state
	 */
	protected abstract void onStartFragment();
	
	
	/**
	 * Wrapper for findview quickly
	 * @param resId
	 * @return
	 */
	protected <T extends View> T findViewById(int resId) {
		return (T) mRootView.findViewById(resId);
	}
}
