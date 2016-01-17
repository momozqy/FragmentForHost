package com.jay.example.fragmentforhost;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jay.example.db.DataSQLiteHelper;

public class CustomMade extends Fragment implements OnClickListener {
	private TextView add;
	private LinearLayout datas;
	private MainActivity mActivity;
	private Button next;
	private int mWidth;
	private int count = 2;
	List<EditText> textList = new ArrayList<EditText>();
	private Button change;
	private Button save;
	DataSQLiteHelper dh;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.custommade, container, false);
		mActivity = (MainActivity) this.getActivity();
		textList.clear();
		WindowManager wm = mActivity.getWindowManager();
		mWidth = wm.getDefaultDisplay().getWidth();
		datas = (LinearLayout) view.findViewById(R.id.datass);
		add = (TextView) view.findViewById(R.id.add);
		next = (Button) view.findViewById(R.id.next1);
		change = (Button) view.findViewById(R.id.change1);
		save = (Button) view.findViewById(R.id.save);
		dh = mActivity.getDataSQLiteHelper();
		next.setOnClickListener(this);
		add.setOnClickListener(this);
		change.setOnClickListener(this);
		save.setOnClickListener(this);
		return view;
	}

	public void onResume() {
		// TODO Auto-generated method stub
		count = 1;
		datas.removeAllViews();
		super.onResume();
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.add:
			addView();
			break;
		case R.id.next1:
			if (next.getText().toString().equals("下一步")) {
				next.setText("写入");
				for (int i = 0; i < textList.size(); i++) {
					textList.get(i).setEnabled(false);
				}
				change.setVisibility(View.VISIBLE);
				save.setVisibility(View.VISIBLE);
			} else {
				Intent in = new Intent();
				in.setClass(mActivity, Write2Nfc.class);
				in.putExtra("content", save());
				mActivity.startActivity(in);
			}
			break;
		case R.id.change1:
			change();
			break;
		case R.id.save:
			save();
			break;
		}
		if (v.getTag() != null) {

		}
	}

	public String save() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < textList.size(); i++) {
			sb.append(textList.get(i++).getText().toString() + ":");
			if (i != textList.size() - 1)
				sb.append(textList.get(i).getText().toString() + "#");
			else
				sb.append(textList.get(i).getText().toString());
		}
		String content = sb.toString().trim();
		String date = GetNowDate();
		SQLiteDatabase db = dh.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("type", "定制");
		cv.put("atrrs", content);
		cv.put("num", textList.size() / 2);
		cv.put("time", GetNowDate());
		if (db.insert("DATA", null, cv) == -1) {
			Toast.makeText(mActivity, "保存失败", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(mActivity, "保存成功", Toast.LENGTH_SHORT).show();
		}
		return content;
	}

	public void change() {
		change.setVisibility(View.INVISIBLE);
		save.setVisibility(View.INVISIBLE);
		for (int i = 0; i < textList.size(); i++) {
			textList.get(i).setEnabled(true);
		}
		next.setText("下一步");
	}

	public void addView() {
		LinearLayout data = new LinearLayout(mActivity);
		data.setOrientation(LinearLayout.HORIZONTAL);
		EditText name = new EditText(mActivity);
		name.setWidth(mWidth / 3);
		name.setTag(++count);
		textList.add(name);
		EditText content = new EditText(mActivity);
		content.setWidth(mWidth / 3 * 2);
		content.setTag(++count);
		textList.add(content);
		data.addView(name);
		data.addView(content);
		datas.addView(data);
	}

	public String GetNowDate() {
		String temp_str = "";
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		temp_str = sdf.format(dt);
		return temp_str;
	}
}
