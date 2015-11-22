package com.jay.example.fragmentforhost;

import java.util.ArrayList;
import java.util.List;

import com.jay.example.db.DataSQLiteHelper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

public class HistoryLog extends Fragment {
	ListView historyList;
	List<List<String>> list;
	MainActivity mActivity;
	DataSQLiteHelper dh;
	String Test;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.history, container, false);
		mActivity = (MainActivity) this.getActivity();
		dh = mActivity.getDataSQLiteHelper();
		historyList = (ListView) view.findViewById(R.id.historyList);
		list = new ArrayList<List<String>>();
		SQLiteDatabase db = dh.getReadableDatabase();
		Cursor cs = db.rawQuery("select * from DATA", null);
		while (cs.moveToNext()) {
			String attrs = cs.getString(cs.getColumnIndex("attrs"));
			Test = attrs;
		}
		cs.close();
		db.close();
		historyList.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(mActivity, Test, Toast.LENGTH_SHORT).show();
			}
		});
		return view;
	}
}
