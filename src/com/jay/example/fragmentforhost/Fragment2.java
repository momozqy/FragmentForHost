package com.jay.example.fragmentforhost;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class Fragment2 extends Fragment {
	LinearLayout ll_read;
	String[] args;
	MainActivity mActivity;
	int mWidth;
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
		// addView();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fg2, container, false);
		mActivity = (MainActivity) this.getActivity();
		mActivity.setHanlder(handler);
		WindowManager windowManager = mActivity.getWindowManager();
		mWidth = windowManager.getDefaultDisplay().getWidth();
		ll_read = (LinearLayout) view.findViewById(R.id.ll_read);
		ll_read.removeAllViews();
		if (args != null) {
			addView();
		}
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

	public void addView() {
		for (int i = 0; i < args.length; i++) {
			String strs[] = args[i].split(":");
			LinearLayout data = new LinearLayout(mActivity);
			data.setOrientation(LinearLayout.HORIZONTAL);
			TextView name = new TextView(mActivity);
			name.setWidth(mWidth / 3);
			name.setText(strs[0]);
			EditText content = new EditText(mActivity);
			content.setWidth(mWidth / 3 * 2);
			content.setText(strs[1]);
			data.addView(name);
			data.addView(content);
			ll_read.addView(data);
		}
	}
}
