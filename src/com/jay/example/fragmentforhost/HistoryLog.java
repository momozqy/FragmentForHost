package com.jay.example.fragmentforhost;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.jay.example.adapter.MadeListViewAdapter;
import com.jay.example.db.DataSQLiteHelper;

public class HistoryLog extends Fragment {
	ListView historyList;
	List<List<String>> list;
	MainActivity mActivity;
	DataSQLiteHelper dh;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.history, container, false);
		mActivity = (MainActivity) this.getActivity();
		dh = mActivity.getDataSQLiteHelper();
		historyList = (ListView) view.findViewById(R.id.historyList);
		list = new ArrayList<List<String>>();
		SQLiteDatabase db = dh.getReadableDatabase();
		Cursor cs = db.rawQuery("select * from DATA", null);
		while (cs.moveToNext()) {
			String attrs = cs.getString(cs.getColumnIndex("atrrs"));
			String date = cs.getString(cs.getColumnIndex("time"));
			String strs[] = attrs.split("#");
			List<String> liststr = new ArrayList<String>();
			for (int i = 0; i < strs.length; i++) {
				liststr.add(strs[i]);
			}
			liststr.add(date);
			list.add(liststr);
		}
		MadeListViewAdapter adapter = new MadeListViewAdapter(
				this.getActivity(), list);
		historyList.setAdapter(adapter);
		historyList.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				int id = position + 1;

			}
		});
		cs.close();
		db.close();
		return view;
	}
}
