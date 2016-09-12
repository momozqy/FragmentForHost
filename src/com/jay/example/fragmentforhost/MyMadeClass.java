package com.jay.example.fragmentforhost;


import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import com.jay.example.db.DataSQLiteHelper;

public class MyMadeClass extends Fragment {
	ListView list;
	DataSQLiteHelper dh;
	FragmentManager fManager;
	FragmentTransaction ft;
	List<String> titles;
	MainActivity mActivity;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.mymadeclass, container, false);
		list = (ListView) view.findViewById(R.id.mineClasses);
		titles = new ArrayList<String>();
		mActivity = (MainActivity) this.getActivity();
		dh = mActivity.getDataSQLiteHelper();
		fManager = mActivity.getSupportFragmentManager();
		SQLiteDatabase db = dh.getReadableDatabase();
		Cursor cs = db.rawQuery("select DISTINCT title from MAKE", null);
		while (cs.moveToNext()) {
			String title = cs.getString(cs.getColumnIndex("title"));
			titles.add(title);
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1,
				titles.toArray(new String[titles.size()]));
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ft = fManager.beginTransaction();
				if(mActivity.detail!=null){
					ft.remove(mActivity.detail);
				}
				mActivity.detail = new MymadeDetail(titles.get(position));
				ft.add(R.id.content, mActivity.detail);
				ft.hide(mActivity.made);
				ft.show(mActivity.detail);
				ft.commit();
			}
		});
		return view;
	}
}
