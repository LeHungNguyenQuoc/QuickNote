package kn.multinote.ui.fragment;

import android.annotation.SuppressLint;
import android.support.v4.view.ViewPager;
import kn.multinote.ui.activity.R;
import kn.multinote.ui.fragment.base.BasePagerFragment;

@SuppressLint("ValidFragment")
public class DetailRecordFragment extends BasePagerFragment {

	public DetailRecordFragment(ViewPager viewPager) {
		super(viewPager);
	}

	@Override
	protected int getFragmentLayoutResource() {
		return R.layout.frag_record_detail;
	}

	@Override
	protected void onInitFragment() {
		
	}

}
