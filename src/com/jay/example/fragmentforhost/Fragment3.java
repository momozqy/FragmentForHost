package com.jay.example.fragmentforhost;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Fragment3 extends Fragment {
	ListView list;
	TextView biaot;
	MainActivity mActivity;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fg3, container, false);
		list = (ListView) view.findViewById(R.id.mineOrder);
		mActivity = (MainActivity) this.getActivity();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1,
				new String[] { "我的定制", "历史记录" });
		biaot = (TextView) view.findViewById(R.id.biaot);
		biaot.setText("NFC-RW");
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				if (position == 1) {
					mActivity.getFragmentManager();
				}
			}
		});
		return view;
	}
}
