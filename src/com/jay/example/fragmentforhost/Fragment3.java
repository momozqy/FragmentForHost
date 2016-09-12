package com.jay.example.fragmentforhost;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
	FragmentManager fm;
	FragmentTransaction ft;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fg3, container, false);
		list = (ListView) view.findViewById(R.id.mineOrder);
		mActivity = (MainActivity) this.getActivity();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1,
				new String[] { "已保存", "已写入" ,"我的定制"});
		biaot = (TextView) view.findViewById(R.id.biaot);
		fm = mActivity.getSupportFragmentManager();
		biaot.setText("NFC-RW");
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				ft = fm.beginTransaction();
				if (position == 0) {
					if (mActivity.log == null) {
						mActivity.log = new MyMade();
						ft.add(R.id.content, mActivity.log);
					} else {
						ft.remove(mActivity.log);
						mActivity.log = new MyMade();
						ft.add(R.id.content, mActivity.log);
					}
					ft.hide(mActivity.fg3);
					ft.show(mActivity.log);
					ft.commit();
				}
				if (position == 1) {
					if (mActivity.his == null) {
						mActivity.his = new HistoryLog();
						ft.add(R.id.content, mActivity.his);
					} else {
						ft.remove(mActivity.his);
						mActivity.his = new HistoryLog();
						ft.add(R.id.content, mActivity.his);
					}
					ft.hide(mActivity.fg3);
					ft.show(mActivity.his);
					ft.commit();
				}
				if(position == 2){
					if(mActivity.made != null){
						ft.remove(mActivity.made);
					}
					mActivity.made = new MyMadeClass();
					ft.add(R.id.content, mActivity.made);
					ft.hide(mActivity.fg3);
					ft.show(mActivity.made);
					ft.commit();
				}
			}
		});
		return view;
	}
}
