package kn.multinote.data.viewmodel.adapter;

import java.util.ArrayList;

import kn.multinote.ui.fragment.base.MNBaseFragment;

public class PagerAdapterViewModel {
	public ArrayList<MNBaseFragment> fragments;
	
	public PagerAdapterViewModel() {
		this.fragments = new ArrayList<MNBaseFragment>();
	}
}
