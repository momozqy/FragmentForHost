package com.jay.example.fragmentforhost;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Fragment4 extends Fragment {

	TextView label;
	EditText input;
	Button change;
	Button next;
	MainActivity activity;
	FragmentManager fManager;
	FragmentTransaction ft;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fg4, container, false);
		label = (TextView) view.findViewById(R.id.lab);
		input = (EditText) view.findViewById(R.id.input);
		change = (Button) view.findViewById(R.id.change);
		activity = (MainActivity) Fragment4.this.getActivity();
		next = (Button) view.findViewById(R.id.next);
		fManager = activity.getSupportFragmentManager();
		next.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (input.getText().toString().trim().equals("")) {
					Toast.makeText(activity, "请输入文本！", Toast.LENGTH_SHORT);
				} else {
					if (next.getText().toString().equals("下一步")) {
						next.setText("写入");
						change.setVisibility(View.VISIBLE);
						label.setHint("文本数据内容:");
						input.setEnabled(false);
					} else {
						// 跳转
						/*
						 * ft = fManager.beginTransaction(); if (activity.fg5 ==
						 * null) { activity.fg5 = new Fragment5(); }
						 * ft.add(R.id.content, activity.fg5); if (activity.fg4
						 * != null) ft.remove(activity.fg4);
						 * ft.show(activity.fg5); ft.commit();
						 */
						Intent in = new Intent();
						in.setClass(activity, Write2Nfc.class);
						in.putExtra("content", input.getText().toString()
								.trim());
						activity.startActivity(in);
					}
				}
			}
		});
		change.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				change.setVisibility(View.INVISIBLE);
				input.setEnabled(true);
				next.setText("下一步");
				label.setText("请输入文本数据:");
			}
		});
		return view;
	}

	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
}
