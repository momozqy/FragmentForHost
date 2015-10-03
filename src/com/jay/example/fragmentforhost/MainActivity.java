package com.jay.example.fragmentforhost;

import java.io.UnsupportedEncodingException;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements OnClickListener {

	public Fragment1 fg1;
	public Fragment2 fg2;
	public Fragment3 fg3;
	public Fragment4 fg4;
	public Fragment5 fg5;
	public NfcAdapter nfcAdapter;
	private FrameLayout flayout;
	public String readResult = "";
	private RelativeLayout course_layout;
	private RelativeLayout found_layout;
	private RelativeLayout settings_layout;
	private ImageView course_image;
	private ImageView found_image;
	private ImageView settings_image;
	private TextView course_text;
	private TextView settings_text;
	private TextView found_text;
	private boolean isFirst = true;
	private IntentFilter ndef;
	private PendingIntent pendingIntent;
	private IntentFilter[] mFilters;
	private String[][] mTechLists;

	private int whirt = 0xFFFFFFFF;
	private int gray = 0xFF7597B3;
	private int blue = 0xFF0AB2FB;
	FragmentManager fManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		fManager = getSupportFragmentManager();
		initViews();
	}

	public void initViews() {
		course_image = (ImageView) findViewById(R.id.course_image);
		found_image = (ImageView) findViewById(R.id.found_image);
		settings_image = (ImageView) findViewById(R.id.setting_image);
		course_text = (TextView) findViewById(R.id.course_text);
		found_text = (TextView) findViewById(R.id.found_text);
		settings_text = (TextView) findViewById(R.id.setting_text);
		course_layout = (RelativeLayout) findViewById(R.id.course_layout);
		found_layout = (RelativeLayout) findViewById(R.id.found_layout);
		settings_layout = (RelativeLayout) findViewById(R.id.setting_layout);
		course_layout.setOnClickListener(this);
		found_layout.setOnClickListener(this);
		settings_layout.setOnClickListener(this);
		/*
		 * nfcAdapter = NfcAdapter.getDefaultAdapter(this); if (!ifNFCUse()) {
		 * return; } // 将被调用的Intent，用于重复被Intent触发后将要执行的跳转 pendingIntent =
		 * PendingIntent.getActivity(this, 0, new Intent(this,
		 * getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0); //
		 * 设定要过滤的标签动作，这里只接收ACTION_NDEF_DISCOVERED类型 ndef = new
		 * IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
		 * ndef.addCategory("*"); mFilters = new IntentFilter[] { ndef };// 过滤器
		 * mTechLists = new String[][] { new String[] { NfcA.class.getName() },
		 * new String[] { NfcF.class.getName() }, new String[] {
		 * NfcB.class.getName() }, new String[] { NfcV.class.getName() } };//
		 * 允许扫描的标签类型
		 * 
		 * if (isFirst) { if
		 * (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent() .getAction()))
		 * { System.out.println(getIntent().getAction()); if
		 * (readFromTag(getIntent())) { // ifo_NFC.setText(readResult);
		 * System.out.println("1.5..."); } else { // ifo_NFC.setText("标签数据为空");
		 * } } isFirst = false; } System.out.println("onCreate...");
		 */
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.course_layout:
			setChioceItem(0);
			break;
		case R.id.found_layout:
			setChioceItem(1);
			break;
		case R.id.setting_layout:
			setChioceItem(2);
			break;
		default:
			break;
		}

	}

	public void setChioceItem(int index) {
		FragmentTransaction transaction = fManager.beginTransaction();
		clearChioce();
		hideFragments(transaction);
		switch (index) {
		case 0:
			course_image.setImageResource(R.drawable.ic_tabbar_course_pressed);
			course_text.setTextColor(blue);
			course_layout.setBackgroundResource(R.drawable.ic_tabbar_bg_click);
			if (fg1 == null) {
				fg1 = new Fragment1();
				transaction.add(R.id.content, fg1);
			} else {
				transaction.show(fg1);
			}
			break;

		case 1:
			found_image.setImageResource(R.drawable.ic_tabbar_found_pressed);
			found_text.setTextColor(blue);
			found_layout.setBackgroundResource(R.drawable.ic_tabbar_bg_click);
			if (fg2 == null) {
				fg2 = new Fragment2();
				transaction.add(R.id.content, fg2);
			} else {
				transaction.show(fg2);
			}
			break;

		case 2:
			settings_image
					.setImageResource(R.drawable.ic_tabbar_settings_pressed);
			settings_text.setTextColor(blue);
			settings_layout
					.setBackgroundResource(R.drawable.ic_tabbar_bg_click);
			if (fg3 == null) {
				fg3 = new Fragment3();
				transaction.add(R.id.content, fg3);
			} else {
				transaction.show(fg3);
			}
			break;
		}
		removeOthers(transaction);
		transaction.commit();
	}

	private void removeOthers(FragmentTransaction ft) {

		if (fg4 != null)
			ft.remove(fg4);
		if (fg5 != null)
			ft.remove(fg5);
	}

	private void hideFragments(FragmentTransaction transaction) {
		if (fg1 != null) {
			transaction.hide(fg1);
		}
		if (fg2 != null) {
			transaction.hide(fg2);
		}
		if (fg3 != null) {
			transaction.hide(fg3);
		}

		if (fg4 != null) {
			transaction.hide(fg4);
		}
		if (fg5 != null) {
			transaction.hide(fg5);
		}
	}

	public void clearChioce() {
		course_image.setImageResource(R.drawable.ic_tabbar_course_normal);
		course_layout.setBackgroundColor(whirt);
		course_text.setTextColor(gray);
		found_image.setImageResource(R.drawable.ic_tabbar_found_normal);
		found_layout.setBackgroundColor(whirt);
		found_text.setTextColor(gray);
		settings_image.setImageResource(R.drawable.ic_tabbar_settings_normal);
		settings_layout.setBackgroundColor(whirt);
		settings_text.setTextColor(gray);
	}

	/**
	 * 检测工作,判断设备的NFC支持情况
	 * 
	 * @return
	 */
	private Boolean ifNFCUse() {

		if (nfcAdapter == null) {
			// ifo_NFC.setText("设备不支持NFC！");
			finish();
			return false;
		}
		if (nfcAdapter != null && !nfcAdapter.isEnabled()) {
			// ifo_NFC.setText("请在系统设置中先启用NFC功能！");
			finish();
			return false;
		}
		return true;
	}

	/**
	 * 读取NFC标签数据的操作
	 * 
	 * @param intent
	 * @return
	 */
	private boolean readFromTag(Intent intent) {
		Parcelable[] rawArray = intent
				.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
		if (rawArray != null) {
			NdefMessage mNdefMsg = (NdefMessage) rawArray[0];
			NdefRecord mNdefRecord = mNdefMsg.getRecords()[0];
			try {
				if (mNdefRecord != null) {
					readResult = new String(mNdefRecord.getPayload(), "UTF-8");
					return true;
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return false;
		}
		return false;
	}
}
