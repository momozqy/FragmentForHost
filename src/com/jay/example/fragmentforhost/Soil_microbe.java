package com.jay.example.fragmentforhost;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.ContentValues;
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

public class Soil_microbe extends Fragment {
	public static View content;
	EditText variety;
	EditText biomass;
	EditText microb_activity;
	EditText enzymatic_activity;
	Button next;
	Button change;
	DataSQLiteHelper dh;
	MainActivity activity;
	private LinearLayout ll;
	private StringBuilder sb;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.soil, container, false);
		content = view;
		activity = (MainActivity) getActivity();
		variety = (EditText) view.findViewById(R.id.varietyc);
		biomass = (EditText) view.findViewById(R.id.biomassc);
		microb_activity = (EditText) view.findViewById(R.id.microb_activityc);
		enzymatic_activity = (EditText) view
				.findViewById(R.id.enzymatic_activityc);
		change = (Button) view.findViewById(R.id.change);
		next = (Button) view.findViewById(R.id.next);
		dh = activity.getDataSQLiteHelper();
		sb = new StringBuilder();
		next.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (next.getText().toString().equals("下一步")) {
					next.setText("写入");
					change.setVisibility(View.VISIBLE);
					variety.setEnabled(false);
					biomass.setEnabled(false);
					microb_activity.setEnabled(false);
					enzymatic_activity.setEnabled(false);

				} else {
					Intent in = new Intent();
					in.setClass(activity, Write2Nfc.class);
					String str = variety.getText().toString().trim();
					if (!str.equals("")) {
						sb.append("群落组成和多样性:" + str + "#");
					}
					str = biomass.getText().toString().trim();
					if (!str.equals(""))
						sb.append("生物量:" + str + "#");
					str = microb_activity.getText().toString().trim();
					if (!str.equals(""))
						sb.append("微生物活性:" + str + "#");
					str = enzymatic_activity.getText().toString().trim();
					if (!str.equals("")) {
						sb.append("酶活性:" + str);
					}
					String content = sb.toString().trim();
					in.putExtra("content", content);
					String date = GetNowDate();
					SQLiteDatabase db = dh.getWritableDatabase();
					ContentValues cv = new ContentValues();
					// cv.put("type", "土壤微生物");
					// cv.put("atrrs",content);
					// cv.put("num", 4);
					// cv.put("time",GetNowDate());
					//
					// if(db.insert("DATA", null, cv)==-1){
					// Toast.makeText(activity, "插入失败",
					// Toast.LENGTH_SHORT).show();
					// }
					// else{
					// Toast.makeText(activity, "插入成功",
					// Toast.LENGTH_SHORT).show();
					// }
					activity.startActivity(in);
				}
			}
		});
		change.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				change.setVisibility(View.INVISIBLE);
				next.setText("下一步");
				variety.setEnabled(true);
				biomass.setEnabled(true);
				microb_activity.setEnabled(true);
				enzymatic_activity.setEnabled(true);
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
