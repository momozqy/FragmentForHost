package com.jay.example.fragmentforhost;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Write2Nfc extends Activity {
	String text;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_write_nfc);
		Intent in = getIntent();
		text = in.getStringExtra("content");
	}

}
