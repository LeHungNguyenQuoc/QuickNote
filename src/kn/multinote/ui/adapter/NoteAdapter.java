package kn.multinote.ui.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import kn.multinote.defines.MessageConstant;
import kn.multinote.defines.TypeNote;
import kn.multinote.dto.NoteDto;
import kn.multinote.ui.activity.DisplayCaptureSoundActivity;
import kn.multinote.ui.activity.R;
import kn.multinote.ui.controller.NoteController;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NoteAdapter extends BaseAdapter implements OnClickListener {
	@SuppressWarnings("unused")
	private static final String TAG = NoteAdapter.class.getName();
	private LayoutInflater inflater;
	private Context context;
	private List<NoteDto> lNotes;
	private NoteController mController;

	public NoteAdapter(Context context, NoteController controller) {
		inflater = LayoutInflater.from(context);
		this.context = context;
		this.mController = controller;
		lNotes = new ArrayList<NoteDto>();
		getData();
	}

	public void getData() {
		List<NoteDto> listNotes = mController.getAllNote();
		if (listNotes != null) {
			lNotes = listNotes;
		} else if (lNotes == null) {
			lNotes = new ArrayList<NoteDto>();
		}
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return lNotes.size();
	}

	@Override
	public Object getItem(int position) {
		return lNotes.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		// if (convertView == null) {
		holder = new ViewHolder();
		convertView = inflater.inflate(R.layout.note_item, null);
		holder.tvNameNote = (TextView) convertView
				.findViewById(R.id.tvNameNote);
		holder.tvDate = (TextView) convertView.findViewById(R.id.tvDate);
		holder.ivType = (ImageView) convertView.findViewById(R.id.ivTypeNote);
		
		if (lNotes.get(position).getTypeNote() == TypeNote.NOTECAPTURE) {
			holder.ivType.setBackgroundResource(R.drawable.ic_capturep);
		}
		convertView.setTag(holder);
		holder.tvNameNote.setText(lNotes.get(position).getNameNote());
		SimpleDateFormat formatdate = new SimpleDateFormat("dd/MM/yy");
		holder.tvDate
				.setText(formatdate.format(lNotes.get(position).getDate()));
		convertView.setTag(lNotes.get(position));
		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				NoteDto msg = (NoteDto) v.getTag();
				mController.handleMessage(MessageConstant.MESSAGE_VIEW_NOTE,
						msg);
			}
		});
		return convertView;
	}

	private class ViewHolder {
		TextView tvNameNote;
		TextView tvDate;
		ImageView ivType;
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		
		default:
			break;
		}
	}

}
