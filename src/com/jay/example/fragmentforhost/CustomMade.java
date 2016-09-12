package com.jay.example.fragmentforhost;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
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

@SuppressLint("ValidFragment")
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
	private String title;
	private String str[];
	private int id = -1;
	DataSQLiteHelper dh;

	public CustomMade(String[] args, int id) {
		this.id = id;
		if (args.length > 0) {
			int index = 0;
			str = new String[2 * (args.length - 1)];
			for (int i = 0; i < args.length - 1; i++) {
				String tmp[] = args[i].split(":");
				str[index++] = tmp[0];
				str[index++] = tmp[1];
			}
		}
	}

	public CustomMade() {

	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
		if (str != null) {
			add.setVisibility(View.INVISIBLE);
			change.setVisibility(View.VISIBLE);
			save.setVisibility(View.VISIBLE);
			next.setText("写入");
			for (int i = 0; i < str.length; i += 2) {
				addView(str[i], str[i + 1]);
			}
		}
		return view;
	}

	public void onResume() {
		// TODO Auto-generated method stub
		count = 1;
		if (str == null)
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
				add.setVisibility(View.INVISIBLE);
			} else {
				Intent in = new Intent();
				String content = getContent();
				in.setClass(mActivity, Write2Nfc.class);
				if (content.equals("")) {
					Toast.makeText(mActivity, "不能有空的", Toast.LENGTH_SHORT).show();
					return;
				}
				in.putExtra("content", save());
				in.putExtra("type", "定制");
				in.putExtra("num", textList.size() / 2);
				in.putExtra("time", mActivity.GetNowDate());
				mActivity.startActivityForResult(in, 1);
			}
			break;
		case R.id.change1:
			add.setVisibility(View.VISIBLE);
			change(); 
			break;
		case R.id.save:
			final EditText ev = new EditText(mActivity);
			new AlertDialog.Builder(mActivity).setTitle("请输入").setIcon(android.R.drawable.ic_dialog_info).setView(ev)
					.setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							title = ev.getText().toString();
							save();
						}
					}).setNegativeButton("取消", null).show();

			break;
		}
		if (v.getTag() != null) {

		}
	}

	public String getContent() {
		StringBuilder sb = new StringBuilder();
		if (textList.size() == 0) {
			Toast.makeText(mActivity, "不能没有数据", Toast.LENGTH_SHORT).show();
			return "";
		}
		for (int i = 0; i < textList.size(); i++) {
			// 如果名字是空的 不存入
			if (textList.get(i).getText().toString().trim().equals("")
					|| textList.get(i + 1).getText().toString().trim().equals("")) {
				Toast.makeText(mActivity, "不能有空的", Toast.LENGTH_SHORT).show();
				return "";
			}
			sb.append(textList.get(i++).getText().toString().trim() + ":");
			if (i != textList.size() - 1)
				sb.append(textList.get(i).getText().toString().trim() + "#");
			else
				sb.append(textList.get(i).getText().toString().trim());
		}

		String content = sb.toString().trim();
		return content;
	}

	public String save() {
		StringBuilder sb = new StringBuilder();
		if (textList.size() == 0) {
			Toast.makeText(mActivity, "不能没有数据", Toast.LENGTH_SHORT).show();
			return "";
		}
		for (int i = 0; i < textList.size(); i++) {
			// 如果名字是空的 不存入
			if (textList.get(i).getText().toString().trim().equals("")
					|| textList.get(i + 1).getText().toString().trim().equals("")) {
				Toast.makeText(mActivity, "不能有空的", Toast.LENGTH_SHORT).show();
				return "";
			}
			sb.append(textList.get(i++).getText().toString().trim() + ":");
			if (i != textList.size() - 1)
				sb.append(textList.get(i).getText().toString().trim() + "#");
			else
				sb.append(textList.get(i).getText().toString().trim());
		}

		String content = sb.toString().trim();
		SQLiteDatabase db = dh.getWritableDatabase();
		ContentValues cv = new ContentValues();
		String date = mActivity.GetNowDate();
		cv.put("atrrs", content);
		cv.put("time", date);
		cv.put("title", title);
		// 更新
		if (str != null) {
			if (id != -1)
				db.update("MAKE", cv, "id=?", new String[] { id + "" });
			return content;
		}
		if (db.insert("MAKE", null, cv) == -1) {
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

	public void addView(String lab, String lab2) {
		LinearLayout data = new LinearLayout(mActivity);
		data.setOrientation(LinearLayout.HORIZONTAL);
		EditText name = new EditText(mActivity);
		name.setWidth(mWidth / 3);
		name.setTag(++count);
		textList.add(name);
		name.setText(lab);
		name.setEnabled(false);
		EditText content = new EditText(mActivity);
		content.setWidth(mWidth / 3 * 2);
		content.setTag(++count);
		textList.add(content);
		content.setText(lab2);
		content.setEnabled(false);
		data.addView(name);
		data.addView(content);
		datas.addView(data);
	}

}
