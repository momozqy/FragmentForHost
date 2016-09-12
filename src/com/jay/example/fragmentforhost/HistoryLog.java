package com.jay.example.fragmentforhost;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.jay.example.adapter.MadeListViewAdapter;
import com.jay.example.db.DataSQLiteHelper;

public class HistoryLog extends Fragment implements OnClickListener {
	ListView historyList;
	List<List<String>> list;
	MadeListViewAdapter adapter;
	MainActivity mActivity;
	DataSQLiteHelper dh;
	TextView clear;
	List<String[]> stres;
	FragmentManager fManager;
	FragmentTransaction ft;
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.history, container, false);
		mActivity = (MainActivity) this.getActivity();
		dh = mActivity.getDataSQLiteHelper();
		clear = (TextView) view.findViewById(R.id.clear);
		clear.setOnClickListener(this);
		historyList = (ListView) view.findViewById(R.id.historyList);
		list = new ArrayList<List<String>>();
		stres = new ArrayList<String[]>();
		fManager = mActivity.getSupportFragmentManager();
		SQLiteDatabase db = dh.getReadableDatabase();
		Cursor cs = db.rawQuery("select * from DATA", null);
		while (cs.moveToNext()) {
			String attrs = cs.getString(cs.getColumnIndex("atrrs"));
			String date = cs.getString(cs.getColumnIndex("time"));
			String strs[] = attrs.split("#");
			stres.add(strs);
			List<String> liststr = new ArrayList<String>();
			for (int i = 0; i < strs.length; i++) {
				liststr.add(strs[i]);
			}
			liststr.add(date);
			list.add(liststr);
		}
		adapter = new MadeListViewAdapter(this.getActivity(), list);
		historyList.setAdapter(adapter);
		historyList.setOnItemClickListener(new OnItemClickListener(){

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ft = fManager.beginTransaction();
				mActivity.clearChioce();
				mActivity.hideFragments(ft);
				mActivity.found_image.setImageResource(R.drawable.ic_tabbar_found_pressed);
				mActivity.found_text.setTextColor(Color.parseColor("#000000"));
				mActivity.found_layout.setBackgroundResource(R.drawable.ic_tabbar_bg_click);
				if(mActivity.fg2!=null)
					ft.remove(mActivity.fg2);
				mActivity.fg2 = new Fragment2(stres.get(position));
				ft.add(R.id.content,mActivity.fg2);
				ft.show(mActivity.fg2);
				ft.commit();
			}
			
		});
		cs.close();
		db.close();
		return view;
	}

	public void onClick(View v) {
		if (v.getId() == R.id.clear) {
			new AlertDialog.Builder(mActivity).setTitle("系统提示")// 设置对话框标题

					.setMessage("确认要清除所有数据？！")// 设置显示的内容

					.setPositiveButton("确定", new DialogInterface.OnClickListener() {// 添加确定按钮

						public void onClick(DialogInterface dialog, int which) {// 确定按钮的响应事件

							finish();

						}

					}).setNegativeButton("返回", new DialogInterface.OnClickListener() {// 添加返回按钮

						public void onClick(DialogInterface dialog, int which) {// 响应事件

							Log.i("alertdialog", " 请保存数据！");

						}

					}).show();// 在按键响应事件中显示此对话框
		}
	}

	public void finish() {
		SQLiteDatabase db = dh.getWritableDatabase();
		db.delete("DATA", null, null);
		historyList.setAdapter(null);
	}
}
