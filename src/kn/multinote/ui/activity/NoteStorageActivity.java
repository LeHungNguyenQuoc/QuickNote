package kn.multinote.ui.activity;

import kn.multinote.data.viewmodel.activity.NoteStorageViewModel;
import kn.multinote.ui.adapter.MainPagerAdapter;
import kn.multinote.ui.fragment.DetailRecordFragment;
import kn.multinote.ui.fragment.MainMenuFragment;
import kn.multinote.ui.fragment.RecordListFragment;
import kn.multinote.ui.services.PopupNote;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

public class NoteStorageActivity extends FragmentActivity {

	private NoteStorageViewModel mViewModel;

	private ViewPager mMainPager;
	private MainPagerAdapter mMainPagerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note_storage);

		PopupNote.widthTruct = getWindowManager().getDefaultDisplay()
				.getWidth();
		PopupNote.heightTruct = getWindowManager().getDefaultDisplay()
				.getHeight();
		// sendReceiver();
		prepareViewModel();
		initMainPager();
		
	}

	private void prepareViewModel() {
		mViewModel = new NoteStorageViewModel();
	}

	public void sendReceiver() {
		String uniqueActionString = "com.androidbook.intents.testbc";
		Intent broadcastIntent = new Intent(uniqueActionString);
		broadcastIntent.putExtra("message", "Hello world");
		this.sendBroadcast(broadcastIntent);
	}

	private void initMainPager() {
		mMainPager = (ViewPager) findViewById(R.id.pager);
		mMainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(),
				mViewModel.pagerAdapter);

		MainMenuFragment mainMenuFragment = new MainMenuFragment(mMainPager);
		RecordListFragment recordListFragment = new RecordListFragment(
				mMainPager);
		DetailRecordFragment detailRecordFragment = new DetailRecordFragment(
				mMainPager);

		mViewModel.pagerAdapter.fragments.add(mainMenuFragment);
		mViewModel.pagerAdapter.fragments.add(recordListFragment);
		mViewModel.pagerAdapter.fragments.add(detailRecordFragment);

		mMainPager.setAdapter(mMainPagerAdapter);
	}
}
