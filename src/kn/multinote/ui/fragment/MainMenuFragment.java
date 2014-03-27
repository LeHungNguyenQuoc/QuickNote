package kn.multinote.ui.fragment;

import kn.multinote.ui.activity.R;
import kn.multinote.ui.adapter.MainCategoryAdapter;
import kn.multinote.ui.fragment.base.BasePagerFragment;
import kn.multinote.ui.fragment.base.MNBaseFragment;
import kn.multinote.utils.FontUtils;
import android.annotation.SuppressLint;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.foound.widget.AmazingListView;

@SuppressLint("ValidFragment")
public class MainMenuFragment extends BasePagerFragment {

	public MainMenuFragment(ViewPager viewPager) {
		super(viewPager);
	}


	private AmazingListView lvCategory;	
	
	@Override
	protected int getFragmentLayoutResource() {
		return R.layout.frag_main_menu;
	}

	@Override
	protected void onInitFragment() {
		initTitle();
		initCategoryMenu();
	}
	
	private void initTitle() {
		TextView tvTitle = findViewById(R.id.tv_title);
		FontUtils.getIntance().setFontMediumCond(tvTitle);
	}
	
	
	private void initCategoryMenu() {
		lvCategory = findViewById(R.id.lv_category);
		lvCategory.setAdapter(new MainCategoryAdapter(getActivity()));
		
		lvCategory.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				switchToPage(1);
			}
		});
	}

}
