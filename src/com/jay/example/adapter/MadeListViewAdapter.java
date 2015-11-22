package com.jay.example.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ActionBar.LayoutParams;
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

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datas.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public final class Data {
		public LinearLayout datas;
		public TextView date;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Data item = null;
		if (convertView == null) {
			item = new Data();
			convertView = inflater.inflate(R.layout.list_item, null);
			item.datas = (LinearLayout) convertView.findViewById(R.id.datas);
			item.date = (TextView) convertView.findViewById(R.id.date);
			convertView.setTag(item);
		} else {
			item = (Data) convertView.getTag();
		}
		List<String> list = new ArrayList<String>();
		list = datas.get(position);
		int i;
		for (i = 0; i < list.size() - 1; i++) {
			TextView view = new TextView(mContext);
			view.setTextSize(15);
			view.setWidth(LayoutParams.WRAP_CONTENT);
			view.setHeight(LayoutParams.WRAP_CONTENT);
			view.setText(list.get(i) + list.get(i++));
			item.datas.addView(view);
		}
		item.date.setText(list.get(i));
		return convertView;
	}

}
