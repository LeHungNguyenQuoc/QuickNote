package kn.multinote.ui.fragment;

import android.annotation.SuppressLint;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import kn.multinote.data.viewmodel.activity.RecordListViewModel;
import kn.multinote.data.viewmodel.adapter.base.RecordViewModel;
import kn.multinote.ui.activity.R;
import kn.multinote.ui.adapter.RecordsAdapter;
import kn.multinote.ui.fragment.base.BasePagerFragment;
import kn.multinote.ui.fragment.base.MNBaseFragment;

@SuppressLint("ValidFragment")
public class RecordListFragment extends BasePagerFragment{

	private ListView lvRecords;
	private RecordsAdapter recordsAdapter;
	private RecordListViewModel viewModel;
	
	public RecordListFragment(ViewPager viewPager) {
		super(viewPager);
	}

	@Override
	protected int getFragmentLayoutResource() {
		return R.layout.frag_record_list;
	}

	@Override
	protected void onInitFragment() {
		
		prepareViewModel();
		initListRecord();
		
	}
	
	private void prepareViewModel() {
		viewModel = new RecordListViewModel();
	}
	
	private void initListRecord() {
		lvRecords = findViewById(R.id.lv_records);
		recordsAdapter = new RecordsAdapter(getActivity(), viewModel.adapter);
		
		for (int i = 0; i < 50; i++) {
			RecordViewModel recordViewModel = new RecordViewModel();
			recordViewModel.name = "    Record " + i;
			viewModel.adapter.records.add(recordViewModel);
		}
		
		lvRecords.setAdapter(recordsAdapter);
		
		lvRecords.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				switchToPage(2);
			}
		});
	}

}
