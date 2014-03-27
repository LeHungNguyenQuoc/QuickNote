package kn.multinote.data.viewmodel.adapter.base;

import java.util.Date;

import kn.multinote.data.viewmodel.MNBaseViewModel;


public class RecordViewModel extends MNBaseViewModel{
	public enum RECORD_TYPE{
		Sound, Text, Picture,Other,
	}
	public String name;
	public String description;
	public RECORD_TYPE type;
	public boolean isRead;
	
	public Date startDate;
	public Date updateDate;
}
