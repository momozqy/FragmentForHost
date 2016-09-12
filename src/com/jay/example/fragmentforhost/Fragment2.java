package com.jay.example.fragmentforhost;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

@SuppressLint("ValidFragment")
public class Fragment2 extends Fragment implements OnClickListener {
	LinearLayout ll_read;
	LinearLayout ll_attr;
	String[] args;
	MainActivity mActivity;
	int mWidth;
	List<EditText> names;
	List<EditText> contents;
	Button save;
	Button write;
	TextView right_text;
	String def = "defualt";
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case 1:
				Bundle bundle = msg.getData();
				args = (String[]) bundle.getStringArray("args");
				addView();
				break;
			}
		}
	};

	public Fragment2() {
	}

	public Fragment2(String[] args) {
		this.args = args;
	}

	public void setArgs(String args[]) {
		this.args = args;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fg2, container, false);
		mActivity = (MainActivity) this.getActivity();
		WindowManager windowManager = mActivity.getWindowManager();
		mWidth = windowManager.getDefaultDisplay().getWidth();
		ll_attr = (LinearLayout) view.findViewById(R.id.ll_attr);
		ll_read = (LinearLayout) view.findViewById(R.id.ll_read);
		write = (Button) view.findViewById(R.id.write);
		save = (Button) view.findViewById(R.id.change2);
		right_text = (TextView) view.findViewById(R.id.right_text);
		right_text.setText("添加");
		write.setOnClickListener(this);
		save.setOnClickListener(this);
		right_text.setOnClickListener(this);
		names = new ArrayList<EditText>();
		contents = new ArrayList<EditText>();
		ll_read.removeAllViews();
		if (args != null) {
			write.setVisibility(View.VISIBLE);
			save.setVisibility(View.VISIBLE);
			ll_attr.setVisibility(View.VISIBLE);
			addView();
		}
		else{
			write.setVisibility(View.INVISIBLE);
			save.setVisibility(View.INVISIBLE);
			ll_attr.setVisibility(View.INVISIBLE);
		}
		right_text.setVisibility(View.INVISIBLE);
		return view;
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		// mActivity = (MainActivity) activity;
		// mActivity.setHanlder(handler);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	public void addOneView(){
		LinearLayout data = new LinearLayout(mActivity);
		data.setOrientation(LinearLayout.HORIZONTAL);
		EditText name = new EditText(mActivity);
		EditText content = new EditText(mActivity);
		name.setWidth(mWidth / 3);
		content.setWidth(mWidth / 3 * 2);
		names.add(name);
		contents.add(content);
		data.addView(name);
		data.addView(content);
		ll_read.addView(data);
	}
	public void addView() {
		for (int i = 0; i < args.length; i++) {
			String strs[] = args[i].split(":");
			LinearLayout data = new LinearLayout(mActivity);
			data.setOrientation(LinearLayout.HORIZONTAL);
			EditText name = new EditText(mActivity);
			EditText content = new EditText(mActivity);
			if (strs.length == 2) {
				name.setWidth(mWidth / 3);
				name.setText(strs[0]);
				content.setWidth(mWidth / 3 * 2);
				content.setText(strs[1]);

			} else {
				name.setWidth(mWidth / 3);
				name.setText(strs[0]);
				content.setWidth(mWidth / 3 * 2);
				content.setText(" ");
			}
			name.setEnabled(false);
			content.setEnabled(false);
			names.add(name);
			contents.add(content);
			data.addView(name);
			data.addView(content);
			ll_read.addView(data);
		}
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.change2:
			if (save.getText().toString().equals("修改")) {
				for (int i = 0; i < names.size(); i++) {
					names.get(i).setEnabled(true);
					contents.get(i).setEnabled(true);
				}
				right_text.setVisibility(View.VISIBLE);
				save.setText("下一步");
			} else {
				for (int i = 0; i < names.size(); i++) {
					names.get(i).setEnabled(false);
					contents.get(i).setEnabled(false);
				}
				right_text.setVisibility(View.INVISIBLE);
				save.setText("修改");
			}
			break;
		case R.id.write:
			Intent in = new Intent();
			String content = getContent();
			in.setClass(mActivity, Write2Nfc.class);
			if (content.equals("")) {
				Toast.makeText(mActivity, "不能有空的", Toast.LENGTH_SHORT).show();
				return;
			}
			in.putExtra("content", content);
			in.putExtra("type", "读取时");
			in.putExtra("num", names.size());
			in.putExtra("time", mActivity.GetNowDate());
			mActivity.startActivityForResult(in,1);
			break;
		case R.id.right_text:
			addOneView();
			break;
		}
	}

	String getContent() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < names.size(); i++) {
			sb.append(names.get(i).getText().toString().trim() + ":" + contents.get(i).getText().toString().trim());
			if (i + 1 < names.size())
				sb.append("#");
		}
		return sb.toString();
	}
}
