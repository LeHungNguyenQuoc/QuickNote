package kn.multinote.ui.adapter;

import kn.multinote.data.viewmodel.adapter.RecordAdapteViewModel;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RecordsAdapter extends BaseAdapter {
	
	private Context context;
	private RecordAdapteViewModel viewModel;
	
	public RecordsAdapter(Context context,RecordAdapteViewModel viewModel) {
		this.context = context;
		this.viewModel = viewModel;
	}
	
	@Override
	public int getCount() {
		return viewModel.records.size();
	}

	@Override
	public Object getItem(int arg0) {
		return arg0;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView textView = new TextView(context);
		textView.setText(viewModel.records.get(position).name);
		
		return textView;
	}

}
