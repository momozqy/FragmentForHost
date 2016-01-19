package com.jay.example.fragmentforhost;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.jay.example.db.DataSQLiteHelper;

public class Wild_animal extends Fragment {
	public static View content;
	EditText name;
	EditText ename;
	EditText latin;
	EditText dis;
	EditText feature;
	Button next;
	Button change;
	DataSQLiteHelper dh;
	MainActivity activity;
	private LinearLayout ll;
	private StringBuilder sb;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fg5, container, false);
		content = view;
		activity = (MainActivity) getActivity();
		name = (EditText) view.findViewById(R.id.namec);
		latin = (EditText) view.findViewById(R.id.latinamec);
		ename = (EditText) view.findViewById(R.id.enamec);
		dis = (EditText) view.findViewById(R.id.distributec);
		feature = (EditText) view.findViewById(R.id.featurec);
		change = (Button) view.findViewById(R.id.change);
		next = (Button) view.findViewById(R.id.next);
		dh = activity.getDataSQLiteHelper();
		sb = new StringBuilder();
		next.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (next.getText().toString().equals("下一步")) {
					next.setText("写入");
					change.setVisibility(View.VISIBLE);
					name.setEnabled(false);
					latin.setEnabled(false);
					dis.setEnabled(false);
					feature.setEnabled(false);
					ename.setEnabled(false);
				} else {
					Intent in = new Intent();
					in.setClass(activity, Write2Nfc.class);
					String str = name.getText().toString().trim();
					if (!str.equals("")) {
						sb.append("中文名:" + str + "#");
					}
					str = ename.getText().toString().trim();
					if (!str.equals(""))
						sb.append("英文名:" + str + "#");
					str = latin.getText().toString().trim();
					if (!str.equals(""))
						sb.append("拉丁名:" + str + "#");
					str = dis.getText().toString().trim();
					if (!str.equals("")) {
						sb.append("分     布:" + str + "#");
					}
					str = feature.getText().toString();
					if (!str.equals("")) {
						sb.append("习    性:" + str);
					}
					String content = sb.toString().trim();

					in.putExtra("content", content);
					in.putExtra("type", "动物");
					in.putExtra("num", 5);
					in.putExtra("time", GetNowDate());
					activity.startActivity(in);
				}
			}
		});
		change.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				change.setVisibility(View.INVISIBLE);
				next.setText("下一步");
				name.setEnabled(true);
				ename.setEnabled(true);
				latin.setEnabled(true);
				feature.setEnabled(true);
				dis.setEnabled(true);
			}
		});
		return view;
	}

	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
	}

	public String GetNowDate() {
		String temp_str = "";
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		temp_str = sdf.format(dt);
		return temp_str;
	}
}
