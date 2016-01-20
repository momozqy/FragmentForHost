package com.jay.example.fragmentforhost;

import com.jay.example.db.DataSQLiteHelper;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NonCustomMade extends Fragment implements OnClickListener {

	EditText input;
	MainActivity mActivity;
	private TextView add;
	private Button next;
	private Button change;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.noncoustom, container, false);
		mActivity = (MainActivity) this.getActivity();
		input = (EditText) view.findViewById(R.id.input);
		next = (Button) view.findViewById(R.id.next);
		change = (Button) view.findViewById(R.id.change);
		next.setOnClickListener(this);
		change.setOnClickListener(this);
		return view;
	}

	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.add:
			break;
		case R.id.next:
			if (next.getText().toString().equals("下一步")) {
				next.setText("写入");
				change.setVisibility(View.VISIBLE);
			} else {
				Intent in = new Intent();
				in.setClass(mActivity, Write2Nfc.class);
				in.putExtra("content", input.getText().toString().trim());
				in.putExtra("num", 1);
				in.putExtra("type", "非定制");
				in.putExtra("time", mActivity.GetNowDate());
				mActivity.startActivity(in);
			}
			break;
		case R.id.change:
			change.setVisibility(View.INVISIBLE);
			next.setText("下一步");
			break;
		}
	}
}
