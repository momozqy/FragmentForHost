package com.jay.example.fragmentforhost;

import java.io.UnsupportedEncodingException;

import android.R.color;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.nfc.tech.NfcF;
import android.nfc.tech.NfcV;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jay.example.db.DataSQLiteHelper;

public class MainActivity extends FragmentActivity implements OnClickListener {
	
	public DataSQLiteHelper dh ;
	public Fragment1 fg1;
	public Fragment2 fg2;
	public Fragment3 fg3;
	public Fragment4 fg4;
	public Wild_animal fg5;
	public Plant_resources plant;
	public Soil_microbe soil;
	public CustomMade fg6;
	public HistoryLog log;
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
	
    //定义要使用的颜色值
//	private int whirt = 0xf;
//	private int gray = 0x01010101;
//	private int blue = 0x00000000;
	
	private String gray="#cccccc";
	private String blue="#000000";
	
	//定义FragmentManager对象
	FragmentManager fManager;
	public DataSQLiteHelper getDataSQLiteHelper(){
		if(dh==null)
			dh = new DataSQLiteHelper(MainActivity.this,"zqydb");
			return dh;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		fManager = getSupportFragmentManager();
		initViews();
	}
    //完成组件的初始化
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

		nfcAdapter = NfcAdapter.getDefaultAdapter(this);
		if (!ifNFCUse()) {
			System.out.println("is not use");
		} // 将被调用的Intent，用于重复被Intent触发后将要执行的跳转 
		pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass())
				.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0); //
		System.out.println("pengdinng Intent");
		// * 设定要过滤的标签动作，这里只接收ACTION_NDEF_DISCOVERED类型
		ndef = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
		System.out.println("ndef");
		ndef.addCategory("*");
		mFilters = new IntentFilter[] { ndef };// 过滤器
		mTechLists = new String[][] { new String[] { NfcA.class.getName() },
				new String[] { NfcF.class.getName() },
				new String[] { NfcB.class.getName() },
				new String[] { NfcV.class.getName() } };
		// 允许扫描的标签类型

		if (isFirst) {
			if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent()
					.getAction())) {
				System.out.println(getIntent().getAction());
				if (readFromTag(getIntent())) { // ifo_NFC.setText(readResult);
					System.out.println("1.5...");
				} else { 
					Toast.makeText(this, readResult, Toast.LENGTH_SHORT).show();
				}
			}
			isFirst = false;
		}
		System.out.println("onCreate...");
		
		setChioceItem(0);

	}
    //重写onClick事件
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
	//定义一个选中一个item后的处理
	public void setChioceItem(int index) {
		//重置选项+隐藏所有Fragment
		FragmentTransaction transaction = fManager.beginTransaction();
		clearChioce();
		hideFragments(transaction);
		switch (index) {
		case 0:
			course_image.setImageResource(R.drawable.ic_tabbar_course_pressed);
			course_text.setTextColor(Color.parseColor(blue));
			course_layout.setBackgroundResource(R.drawable.ic_tabbar_bg_click);
			if (fg1 == null) {
				// 如果fg1为空，则创建一个并添加到界面上
				fg1 = new Fragment1();
				transaction.add(R.id.content, fg1);
			} else {
				 // 如果MessageFragment不为空，则直接将它显示出来
				transaction.show(fg1);
			}
			break;
		case 1:
			found_image.setImageResource(R.drawable.ic_tabbar_found_pressed);
			found_text.setTextColor(Color.parseColor(blue));
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
			settings_text.setTextColor(Color.parseColor(blue));
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
	//隐藏所有的Fragment,避免fragment混乱
	private void removeOthers(FragmentTransaction ft) {

		if (fg4 != null)
			ft.remove(fg4);
		//if (fg5 != null)
		//	ft.remove(fg5);
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
		if (fg6 != null) {
			transaction.hide(fg6);
		}
		if (log!= null) {
			transaction.hide(log);
		}
		if (plant!= null) {
			transaction.hide(plant);
		}
		if (soil!= null) {
			transaction.hide(soil);
		}
	}

	public void clearChioce() {
		course_image.setImageResource(R.drawable.ic_tabbar_course_normal);
		course_layout.setBackgroundColor(color.white);
		course_text.setTextColor(Color.parseColor(gray));
		found_image.setImageResource(R.drawable.ic_tabbar_found_normal);
		found_layout.setBackgroundColor(color.white);
		found_text.setTextColor(Color.parseColor(gray));
		settings_image.setImageResource(R.drawable.ic_tabbar_settings_normal);
		settings_layout.setBackgroundColor(color.white);
		settings_text.setTextColor(Color.parseColor(gray));
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
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		 前台分发系统,这里的作用在于第二次检测NFC标签时该应用有最高的捕获优先权.
				nfcAdapter.enableForegroundDispatch(this, pendingIntent, mFilters,
						mTechLists);

				System.out.println("onResume...");
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
//		nfcAdapter.disableForegroundDispatch(this);
		System.out.println("onPause...");
	}
	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		System.out.println("onNewIntent1...");
		System.out.println(intent.getAction());
		if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())
				|| NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())) {
			System.out.println("onNewIntent2...");
			if (readFromTag(intent)) {
				Toast.makeText(this, readResult, Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "标签为空", Toast.LENGTH_SHORT).show();
			}
		}

	}

}
