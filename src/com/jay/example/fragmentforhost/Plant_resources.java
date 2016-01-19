package com.jay.example.fragmentforhost;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Intent;
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

public class Plant_resources extends Fragment {
	public static View content;
	EditText chname;
	EditText otname;
	EditText latin;
	EditText family;
	EditText name;
	EditText feature;
	Button next;
	Button change;
	DataSQLiteHelper dh;
	MainActivity activity;
	private LinearLayout ll;
	private StringBuilder sb;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.plant, container, false);
		content = view;
		activity = (MainActivity) getActivity();
		chname = (EditText) view.findViewById(R.id.chnamec);
		otname = (EditText) view.findViewById(R.id.otnamec);
		latin = (EditText) view.findViewById(R.id.latinamec);
		family = (EditText) view.findViewById(R.id.familyc);
		name = (EditText) view.findViewById(R.id.namec);
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
					chname.setEnabled(false);
					otname.setEnabled(false);
					latin.setEnabled(false);
					family.setEnabled(false);
					name.setEnabled(false);
					feature.setEnabled(false);

				} else {
					Intent in = new Intent();
					in.setClass(activity, Write2Nfc.class);
					String str = chname.getText().toString().trim();
					if (!str.equals("")) {
						sb.append("中文名:" + str + "#");
					}
					str = otname.getText().toString().trim();
					if (!str.equals(""))
						sb.append("别     名:" + str + "#");
					str = latin.getText().toString().trim();
					if (!str.equals(""))
						sb.append("拉丁名:" + str + "#");
					str = family.getText().toString().trim();
					if (!str.equals("")) {
						sb.append("科     名:" + str + "#");
					}
					str = name.getText().toString().trim();
					if (!str.equals("")) {
						sb.append("属     名:" + str + "#");
					}
					str = feature.getText().toString();
					if (!str.equals("")) {
						sb.append("习     性:" + str);
					}
					String content = sb.toString().trim();
					in.putExtra("type", "植物");
					in.putExtra("content", content);
					in.putExtra("num", 6);
					in.putExtra("time", GetNowDate());
					activity.startActivity(in);
				}
			}
		});
		change.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				change.setVisibility(View.INVISIBLE);
				next.setText("下一步");
				chname.setEnabled(true);
				otname.setEnabled(true);
				latin.setEnabled(true);
				family.setEnabled(true);
				name.setEnabled(true);
				feature.setEnabled(true);

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