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

public class Wild_animal extends Fragment {
	public static View content;
	EditText name;
	EditText pinyin;
	EditText ename;
	EditText latin;
	EditText jie;
	EditText men;
	EditText gang;
	EditText mu;
	EditText ke;
	EditText shu;
	EditText zhong;
	EditText dis;
	EditText feature;
	EditText level;
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
		pinyin=(EditText) view.findViewById(R.id.pinyinc);
		ename = (EditText) view.findViewById(R.id.enamec);
		latin = (EditText) view.findViewById(R.id.latinamec);
		jie = (EditText) view.findViewById(R.id.jiec);
		men= (EditText) view.findViewById(R.id.menc);
		gang = (EditText) view.findViewById(R.id.gangc);
		mu = (EditText) view.findViewById(R.id.muc);
		ke = (EditText) view.findViewById(R.id.kec);
		shu = (EditText) view.findViewById(R.id.shuc);
		zhong = (EditText) view.findViewById(R.id.zhongc);
		dis = (EditText) view.findViewById(R.id.distributec);
		feature = (EditText) view.findViewById(R.id.featurec);
		level=(EditText) view.findViewById(R.id.levelc);
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
					pinyin.setEnabled(false);
					ename.setEnabled(false);
					latin.setEnabled(false);
					jie.setEnabled(false);
					men.setEnabled(false);
					gang.setEnabled(false);
					mu.setEnabled(false);
					ke.setEnabled(false);
					shu.setEnabled(false);
					zhong.setEnabled(false);				
					feature.setEnabled(false);
					dis.setEnabled(false);
					level.setEnabled(false);
				} else {
					Intent in = new Intent();
					in.setClass(activity, Write2Nfc.class);
					String str = name.getText().toString().trim();
					if (!str.equals("")) {
						sb.append("中文名:" + str + "#");
					}else
						sb.append("中文名:" + " " + "#");
					str = pinyin.getText().toString().trim();
					if (!str.equals(""))
						sb.append("拼   音:" + str + "#");
					else
						sb.append("拼     音:" + " " + "#");
					str = ename.getText().toString().trim();
					if (!str.equals(""))
						sb.append("英文名:" + str + "#");
					else
						sb.append("英文名:" + " " + "#");
					str = latin.getText().toString().trim();
					if (!str.equals(""))
						sb.append("拉丁名:" + str + "#");
					else
						sb.append("拉丁名:" + " " + "#");
					str = jie.getText().toString().trim();
					if (!str.equals(""))
						sb.append("界:" + str + "#");
					else
						sb.append("界:" + " " + "#");
					str = men.getText().toString().trim();
					if (!str.equals(""))
						sb.append("门:" + str + "#");
					else
						sb.append("门:" + " " + "#");
					str = gang.getText().toString().trim();
					if (!str.equals(""))
						sb.append("纲:" + str + "#");
					else
						sb.append("纲:" + " " + "#");
					str = mu.getText().toString().trim();
					if (!str.equals(""))
						sb.append("目:" + str + "#");
					else
						sb.append("目:" + " " + "#");
					str = ke.getText().toString().trim();
					if (!str.equals(""))
						sb.append("科:" + str + "#");
					else
						sb.append("科:" + " " + "#");
					str = shu.getText().toString().trim();
					if (!str.equals(""))
						sb.append("属:" + str + "#");
					else
						sb.append("属:" + " " + "#");
					str = zhong.getText().toString().trim();
					if (!str.equals(""))
						sb.append("种:" + str + "#");
					else
						sb.append("种:" + " " + "#");
					str = dis.getText().toString().trim();
					if (!str.equals("")) {
						sb.append("分     布:" + str + "#");
					}
					else
						sb.append("分     布:" + " " + "#");
					str = feature.getText().toString();
					if (!str.equals("")) {
						sb.append("习    性:" + str);
					}
					else
						sb.append("习    性:" + " " + "#");
					str = level.getText().toString().trim();
					if (!str.equals(""))
						sb.append("保护级别:" + str + "#");
					else
						sb.append("保护级别:" + " " + "#");
					String content = sb.toString().trim();
					in.putExtra("content", content);
					in.putExtra("type", "动物");
					in.putExtra("num",14);
					in.putExtra("time", GetNowDate());
					activity.startActivityForResult(in, 1);
				}
			}
		});
		change.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				change.setVisibility(View.INVISIBLE);
				next.setText("下一步");
				name.setEnabled(true);
				pinyin.setEnabled(true);
				ename.setEnabled(true);
				latin.setEnabled(true);
				jie.setEnabled(true);
				men.setEnabled(true);
				gang.setEnabled(true);
				mu.setEnabled(true);
				ke.setEnabled(true);
				shu.setEnabled(true);
				zhong.setEnabled(true);				
				feature.setEnabled(true);
				dis.setEnabled(true);
				level.setEnabled(true);
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
