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
	EditText pname;
	EditText ppinyin;
	EditText pename;
	EditText platin;
	EditText pjie;
	EditText pmen;
	EditText pgang;
	EditText pmu;
	EditText pke;
	EditText pshu;
	EditText pzhong;
	EditText pdis;
	EditText pfeature;
	EditText plevel;
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
		pname = (EditText) view.findViewById(R.id.pnamec);
		ppinyin = (EditText) view.findViewById(R.id.ppinyinc);
		pename = (EditText) view.findViewById(R.id.penamec);
		platin = (EditText) view.findViewById(R.id.platinamec);
		pjie = (EditText) view.findViewById(R.id.pjiec);
		pmen= (EditText) view.findViewById(R.id.pmenc);
		pgang = (EditText) view.findViewById(R.id.pgangc);
		pmu = (EditText) view.findViewById(R.id.pmuc);
		pke = (EditText) view.findViewById(R.id.pkec);
		pshu = (EditText) view.findViewById(R.id.pshuc);
		pzhong = (EditText) view.findViewById(R.id.pzhongc);
		pdis = (EditText) view.findViewById(R.id.pdistributec);
		pfeature = (EditText) view.findViewById(R.id.pfeaturec);
		plevel=(EditText) view.findViewById(R.id.plevelc);
		change = (Button) view.findViewById(R.id.change);
		next = (Button) view.findViewById(R.id.next);
		dh = activity.getDataSQLiteHelper();
		sb = new StringBuilder();
		next.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (next.getText().toString().equals("下一步")) {
					next.setText("写入");
					change.setVisibility(View.VISIBLE);
					pname.setEnabled(false);
					ppinyin.setEnabled(false);
					pename.setEnabled(false);
					platin.setEnabled(false);
					pjie.setEnabled(false);
					pmen.setEnabled(false);
					pgang.setEnabled(false);
					pmu.setEnabled(false);
					pke.setEnabled(false);
					pshu.setEnabled(false);
					pzhong.setEnabled(false);
					pdis.setEnabled(false);
					pfeature.setEnabled(false);
					plevel.setEnabled(false);

				} else {
					Intent in = new Intent();
					in.setClass(activity, Write2Nfc.class);
					String str =pname.getText().toString().trim();
					if (!str.equals("")) {
						sb.append("中文名:" + str + "#");
					}
					else
						sb.append("中文名:" + " " + "#");
					str = ppinyin.getText().toString().trim();
					if (!str.equals(""))
						sb.append("拼     音:" + str + "#");
					else
						sb.append("拼     音:" + " " + "#");
					str = pename.getText().toString().trim();
					if (!str.equals(""))
						sb.append("英文名:" + str + "#");
					else
						sb.append("英文名:" + " " + "#");
					str = platin.getText().toString().trim();
					if (!str.equals(""))
						sb.append("拉丁名:" + str + "#");
					else
						sb.append("拉丁名:" + " " + "#");
					str = pjie.getText().toString().trim();
					if (!str.equals(""))
						sb.append("界:" + str + "#");
					else
						sb.append("界:" + " " + "#");
					str = pmen.getText().toString().trim();
					if (!str.equals(""))
						sb.append("门:" + str + "#");
					else
						sb.append("门:" + " " + "#");
					str = pgang.getText().toString().trim();
					if (!str.equals(""))
						sb.append("纲:" + str + "#");
					else
						sb.append("纲:" + " " + "#");
					str = pmu.getText().toString().trim();
					if (!str.equals(""))
						sb.append("目:" + str + "#");
					else
						sb.append("目:" + " " + "#");
					str = pke.getText().toString().trim();
					if (!str.equals(""))
						sb.append("科:" + str + "#");
					else
						sb.append("科:" + " " + "#");
					str = pshu.getText().toString().trim();
					if (!str.equals(""))
						sb.append("属:" + str + "#");
					else
						sb.append("属:" + " " + "#");
					str = pzhong.getText().toString().trim();
					if (!str.equals(""))
						sb.append("种:" + str + "#");
					else
						sb.append("种:" + " " + "#");
					str = pdis.getText().toString().trim();
					if (!str.equals("")) {
						sb.append("分     布:" + str + "#");
						
					}else
						sb.append("分     布:" + " " + "#");
					str = pfeature.getText().toString();
					if (!str.equals("")) {
						sb.append("习    性:" + str);
					}else
						sb.append("习    性:" + " " + "#");
					str = plevel.getText().toString().trim();
					if (!str.equals(""))
						sb.append("保护级别:" + str + "#");
					else
						sb.append("保护级别:" + " " + "#");
					
					String content = sb.toString().trim();
					in.putExtra("type", "植物");
					in.putExtra("content", content);
					in.putExtra("num", 13);
					in.putExtra("time", GetNowDate());
					activity.startActivityForResult(in, 1);
				}
			}
		});
		change.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				change.setVisibility(View.INVISIBLE);
				next.setText("下一步");
				pname.setEnabled(true);
				ppinyin.setEnabled(true);
				pename.setEnabled(true);
				platin.setEnabled(true);
				pjie.setEnabled(true);
				pmen.setEnabled(true);
				pgang.setEnabled(true);
				pmu.setEnabled(true);
				pke.setEnabled(true);
				pshu.setEnabled(true);
				pzhong.setEnabled(true);	
				pdis.setEnabled(true);
				pfeature.setEnabled(true);
				plevel.setEnabled(true);
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