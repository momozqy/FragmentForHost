package com.jay.example.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jay.example.fragmentforhost.R;

public class MadeListViewAdapter extends BaseAdapter {
	List<List<String>> datas;
	private LayoutInflater inflater;
	private Context mContext;

	public MadeListViewAdapter(Context mContext, List<List<String>> datas) {
		this.mContext = mContext;
		this.datas = datas;
		inflater = LayoutInflater.from(mContext);
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return datas.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return datas.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public final class Data {
		public LinearLayout datas;
		public TextView date;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Data item = null;
		if (convertView == null) {
			item = new Data();
			convertView = inflater.inflate(R.layout.list_item, null);
			item.datas = (LinearLayout) convertView
					.findViewById(R.id.list_datas);
			item.date = (TextView) convertView.findViewById(R.id.date);
			convertView.setTag(item);
		} else {
			item = (Data) convertView.getTag();
		}
		List<String> list = new ArrayList<String>();
		list = datas.get(position);
		int i;
		item.datas.removeAllViews();
		for (i = 0; i < list.size() - 1; i++) {
			TextView view = new TextView(mContext);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,
					android.widget.LinearLayout.LayoutParams.WRAP_CONTENT);
			view.setLayoutParams(lp);
			view.setTextSize(15);
			view.setText(list.get(i));
			item.datas.addView(view);
		}
		item.date.setText(list.get(i));
		return convertView;
	}

}
