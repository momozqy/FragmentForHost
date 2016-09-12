package com.jay.example.fragmentforhost;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.jay.example.adapter.MadeListViewAdapter;
import com.jay.example.db.DataSQLiteHelper;

public class MyMade extends Fragment {
	ListView historyList;
	List<List<String>> list;
	MainActivity mActivity;
	DataSQLiteHelper dh;
	FragmentManager fManager;
	FragmentTransaction ft;
	List<String> atrres;
	
	List<Integer> ids;
	private MadeListViewAdapter adapter;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.mymade, container, false);
		mActivity = (MainActivity) this.getActivity();
		dh = mActivity.getDataSQLiteHelper();
		historyList = (ListView) view.findViewById(R.id.historyList);
		fManager = mActivity.getSupportFragmentManager();
		list = new ArrayList<List<String>>();
		ids = new ArrayList<Integer>();
		
		SQLiteDatabase db = dh.getReadableDatabase();
		Cursor cs = db.rawQuery("select * from MAKE", null);
		atrres = new ArrayList<String>();
		while (cs.moveToNext()) {
			int id = cs.getInt(cs.getColumnIndex("id"));
			String attrs = cs.getString(cs.getColumnIndex("atrrs"));
			String date = cs.getString(cs.getColumnIndex("time"));
			atrres.add(attrs);
			String strs[] = attrs.split("#");
			List<String> liststr = new ArrayList<String>();
			for (int i = 0; i < strs.length; i++) {
				liststr.add(strs[i]);
			}
			ids.add(id);
			liststr.add(date);
			list.add(liststr);
		}
		adapter = new MadeListViewAdapter(this.getActivity(), list);
		historyList.setAdapter(adapter);
		historyList.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				ft = fManager.beginTransaction();
				int id = position + 1;
				int size = list.get(position).size();
				String[] args = list.get(position).toArray(new String[size]);
				if (mActivity.fg6 == null) {
					mActivity.fg6 = new CustomMade(args, ids.get(position));
					ft.add(R.id.content, mActivity.fg6);
				} else {
					ft.remove(mActivity.fg6);
					mActivity.fg6 = new CustomMade(args, ids.get(position));
					ft.add(R.id.content, mActivity.fg6);
				}

				ft.hide(mActivity.log);
				ft.show(mActivity.fg6);
				ft.commit();
			}
		});
		historyList.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				final int position = arg2;
				new AlertDialog.Builder(mActivity).setTitle("系统提示")// 设置对话框标题

						.setMessage("确认要删除该数据？！")// 设置显示的内容

						.setPositiveButton("确定", new DialogInterface.OnClickListener() {// 添加确定按钮

					public void onClick(DialogInterface dialog, int which) {// 确定按钮的响应事件
						SQLiteDatabase db = dh.getWritableDatabase();
						db.delete("MAKE", "atrrs=?", new String[] { atrres.get(position) });
						list.remove(position);
						adapter.notifyDataSetChanged();
					}

				}).setNegativeButton("返回", new DialogInterface.OnClickListener() {// 添加返回按钮

					public void onClick(DialogInterface dialog, int which) {// 响应事件

					}

				}).show();// 在按键响应事件中显示此对话框
				return false;
			}

		});
		cs.close();
		db.close();
		return view;
	}
}
