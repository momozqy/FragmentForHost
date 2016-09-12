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

public class Soil_microbe extends Fragment {
	public static View content;
	EditText variety;
	EditText biomass;
	EditText sdis;
	EditText sfeature;
	Button next;
	Button change;
	DataSQLiteHelper dh;
	MainActivity activity;
	private LinearLayout ll;
	private StringBuilder sb;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.soil, container, false);
		content = view;
		activity = (MainActivity) getActivity();
		variety = (EditText) view.findViewById(R.id.varietyc);
		biomass = (EditText) view.findViewById(R.id.biomassc);
		sdis = (EditText) view.findViewById(R.id.sdistributec);
		sfeature= (EditText) view.findViewById(R.id.sfeaturec);
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
					sdis.setEnabled(false);
					sfeature.setEnabled(false);

				} else {
					Intent in = new Intent();
					in.setClass(activity, Write2Nfc.class);
					String str = variety.getText().toString().trim();
					if (!str.equals("")) {
						sb.append("种类:" + str + "#");
					}
					else
						sb.append("种类:" + " " + "#");
					str = biomass.getText().toString().trim();
					if (!str.equals(""))
						sb.append("生物量:" + str + "#");
					else
						sb.append("生物量:" + " " + "#");
					str =sdis.getText().toString().trim();
					if (!str.equals(""))
						sb.append("分布:" + str + "#");
					else
						sb.append("分布:" + " " + "#");
					str = sfeature.getText().toString().trim();
					if (!str.equals("")) {
						sb.append("活动规律:" + str);
					}
					else
						sb.append("活动规律:" + " " + "#");
					String content = sb.toString().trim();
					in.putExtra("content", content);
					in.putExtra("type", "土壤微生物");
					in.putExtra("num", 4);
					in.putExtra("time", GetNowDate());
					activity.startActivityForResult(in, 1);
				}
			}
		});
		change.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				change.setVisibility(View.INVISIBLE);
				next.setText("下一步");
				variety.setEnabled(true);
				biomass.setEnabled(true);
				sdis.setEnabled(true);
				sfeature.setEnabled(true);
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
