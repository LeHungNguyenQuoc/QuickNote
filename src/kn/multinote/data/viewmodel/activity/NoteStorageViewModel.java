package kn.multinote.data.viewmodel.activity;

import kn.multinote.data.viewmodel.adapter.PagerAdapterViewModel;


public class NoteStorageViewModel {
	public PagerAdapterViewModel pagerAdapter;
	
	public NoteStorageViewModel() {
		pagerAdapter = new PagerAdapterViewModel();
	}

}
