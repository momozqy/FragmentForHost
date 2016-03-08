package com.jay.example.fragmentforhost;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

public class WelcomeActivity extends Activity {

	Handler handler = new Handler();
	Runnable run = new Runnable() {

		public void run() {
			Intent in = new Intent();
			in.setClass(WelcomeActivity.this, MainActivity.class);
			WelcomeActivity.this.startActivity(in);
			WelcomeActivity.this.finish();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_welcome);
		handler.postDelayed(run, 3000);
	}
}
