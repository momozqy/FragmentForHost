package com.jay.example.fragmentforhost;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import android.R.color;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.nfc.tech.NfcF;
import android.nfc.tech.NfcV;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
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

	public static DataSQLiteHelper dh;
	// 第一个界面 主要功能展示界面
	public Fragment1 fg1;
	// 第二个界面 读出数据 展示界面
	public Fragment2 fg2;
	// 第三个界面 我的定制 和 历史记录
	public Fragment3 fg3;
	// 写入测试界面
	public Fragment4 fg4;
	// 野生动物 写入界面
	public Wild_animal fg5;
	// 植物写入界面
	public Plant_resources plant;
	// 土壤生物 写入界面
	public Soil_microbe soil;
	// 定制 写入界面
	public CustomMade fg6;
	// 历史记录界面
	public MyMade log;
	//
	public NonCustomMade non;
	//我的定制
	public MyMadeClass made; 
	//我的定制细节
	public MymadeDetail detail;
	
	public HistoryLog his;
	public NfcAdapter nfcAdapter;
	private FrameLayout flayout;
	public String readResult = "";
	private RelativeLayout course_layout;
	public RelativeLayout found_layout;
	private RelativeLayout settings_layout;
	private ImageView course_image;
	public ImageView found_image;
	private ImageView settings_image;
	private TextView course_text;
	private TextView settings_text;
	public TextView found_text;
	private boolean isFirst = true;
	private IntentFilter ndef;
	private PendingIntent pendingIntent;
	private IntentFilter[] mFilters;
	private String[][] mTechLists;

	// 定义要使用的颜色值
	// private int whirt = 0xf;
	// private int gray = 0x01010101;
	// private int blue = 0x00000000;

	private String gray = "#cccccc";
	private String blue = "#000000";

	// 定义FragmentManager对象
	FragmentManager fManager;

	public DataSQLiteHelper getDataSQLiteHelper() {
		if (dh == null)
			dh = new DataSQLiteHelper(MainActivity.this, "zqydb");
		return dh;
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		fManager = getSupportFragmentManager();
		initViews();
	}

	// 完成组件的初始化
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
		pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
				getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0); //
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
					setChioceItem(0);
					popReadFragment();
					setChioceItem(1);
				}
			}
			isFirst = false;
		}
		System.out.println("onCreate...");

		setChioceItem(0);

	}

	private void popReadFragment() {
		FragmentTransaction transaction = fManager.beginTransaction();
		clearChioce();
		hideFragments(transaction);
		found_image.setImageResource(R.drawable.ic_tabbar_found_pressed);
		found_text.setTextColor(Color.parseColor(blue));
		found_layout.setBackgroundResource(R.drawable.ic_tabbar_bg_click);
		if (fg2 != null)
			transaction.remove(fg2);
		fg2 = new Fragment2(readResult.split("#"));
		transaction.add(R.id.content, fg2);
		transaction.show(fg2);
		transaction.commit();
	}

	// 重写onClick事件

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

	// 定义一个选中一个item后的处理
	public void setChioceItem(int index) {
		// 重置选项+隐藏所有Fragment
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
			if (fg2 != null) 
				// 如果fg1为空，则创建一个并添加到界面上
				transaction.remove(fg2);
				// 如果MessageFragment不为空，则直接将它显示出来
				fg2 = new Fragment2();
				transaction.add(R.id.content, fg2);
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

	// 隐藏所有的Fragment,避免fragment混乱
	private void removeOthers(FragmentTransaction ft) {

		if (fg4 != null)
			ft.remove(fg4);
		// if (fg5 != null)
		// ft.remove(fg5);
	}

	void hideFragments(FragmentTransaction transaction) {
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
		if (log != null) {
			transaction.hide(log);
		}
		if (plant != null) {
			transaction.hide(plant);
		}
		if (soil != null) {
			transaction.hide(soil);
		}
		if (non != null)
			transaction.hide(non);
		if (his != null) {
			transaction.hide(his);
		}
		if(made != null){
			transaction.hide(made);
		}
		if(detail!=null){
			transaction.hide(detail);
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

	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// 前台分发系统,这里的作用在于第二次检测NFC标签时该应用有最高的捕获优先权.
		nfcAdapter.enableForegroundDispatch(this, pendingIntent, mFilters,
				mTechLists);

		System.out.println("onResume...");
	}

	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		// nfcAdapter.disableForegroundDispatch(this);
		System.out.println("onPause...");
	}

	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		System.out.println("onNewIntent1...");
		System.out.println(intent.getAction());
		if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())
				|| NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())) {
			System.out.println("onNewIntent2...");
			if (readFromTag(intent)) {
				setChioceItem(0);
				popReadFragment();
				/*
				 * if (mHandler != null) { Message msg =
				 * mHandler.obtainMessage(); msg.what = 1;
				 * msg.getData().putStringArray("args", readResult.split("#"));
				 * mHandler.sendMessage(msg); } setChioceItem(1);
				 */
			} else {
				Toast.makeText(this, "标签为空", Toast.LENGTH_SHORT).show();
			}
		}

	}

	public static String GetNowDate() {
		String temp_str = "";
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		temp_str = sdf.format(dt);
		return temp_str;
	}

	@Override
	protected void onDestroy() {
		if (dh != null)
			dh.close();
		super.onDestroy();
	}

	private static final int FILE_SELECT_CODE = 88;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == FILE_SELECT_CODE)// 自定义的一个static final int常量
		{
			if (resultCode == RESULT_OK) {
				
				Toast.makeText(this, "22442", 3000).show();
				// 得到文件的Uri
				Uri uri = data.getData();
				// ContentResolver resolver = getContentResolver();
				// ContentResolver对象的getType方法可返回形如content://的Uri的类型
				// 如果是一张图片，返回结果为image/jpeg或image/png等
				// String fileType = resolver.getType(uri);
				String string = uri.toString();
				if (!TextUtils.isEmpty(string) && string.endsWith("xls")) {
					File f = getFileByUri(uri);

					test(f);

					// do anything you want
				} else {
					Toast.makeText(MainActivity.this, "请选择Excel文件",
							Toast.LENGTH_SHORT).show();
				}
			}
		}
		if (requestCode == 1){
			if (resultCode == RESULT_OK) {
				FragmentTransaction transaction = fManager.beginTransaction();
				clearChioce();
				hideFragments(transaction);
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
				removeOthers(transaction);
				transaction.commitAllowingStateLoss();
			}
		}
	}

	private void test(File f) {
		// AssetManager am = this.getAssets();
		// InputStream is = null;
		try {

			// is = am.open("data.xls");
			InputStream is = new FileInputStream(f);
			// is=f.get
			Workbook wb = Workbook.getWorkbook(is);
			Sheet sheet = wb.getSheet(0);
			int row = sheet.getRows();
			int Column = sheet.getColumns();
			HashMap<String, String> hm;
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < row; ++i) {
				Cell cell = sheet.getCell(0, i);
				if (TextUtils.isEmpty(cell.getContents())) {
					break;
				}
				for (int j = 0; j < Column; j++) {
					Cell cellarea = sheet.getCell(j, i);
					if (TextUtils.isEmpty(cellarea.getContents())) {
						break;
					}
					sb.append(cellarea.getContents()+"#");
				}
				sb.append("@");
			}

			String[] split = sb.toString().split("@");
			Toast.makeText(this, sb.toString(), 3000).show();
			for (String str : split) {
//				if (str == null)
//					return ;
//				Toast.makeText(this, str, 3000).show();
				String date = GetNowDate();
				getDataSQLiteHelper();
				SQLiteDatabase db = dh.getWritableDatabase();
				ContentValues cv = new ContentValues();
				cv.put("atrrs", str);
				cv.put("time", date);
				if (db.insert("MAKE", null, cv) == -1) {
					Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
				}
			}
			
//			Toast.makeText(MainActivity.this, sb.toString(), Toast.LENGTH_LONG)
//					.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 通过Uri返回File文件 注意：通过相机的是类似content://media/external/images/media/97596
	 * 通过相册选择的：file:///storage/sdcard0/DCIM/Camera/IMG_20150423_161955.jpg
	 * 通过查询获取实际的地址
	 * 
	 * @param uri
	 * @return
	 */
	public File getFileByUri(Uri uri) {
		String path = null;
		if ("file".equals(uri.getScheme())) {
			path = uri.getEncodedPath();
			if (path != null) {
				path = Uri.decode(path);
				ContentResolver cr = this.getContentResolver();
				StringBuffer buff = new StringBuffer();
				buff.append("(").append(Images.ImageColumns.DATA).append("=").append("'" + path + "'").append(")");
				Cursor cur = cr.query(Images.Media.EXTERNAL_CONTENT_URI,
						new String[] { Images.ImageColumns._ID, Images.ImageColumns.DATA }, buff.toString(), null,
						null);
				int index = 0;
				int dataIdx = 0;
				for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
					index = cur.getColumnIndex(Images.ImageColumns._ID);
					index = cur.getInt(index);
					dataIdx = cur.getColumnIndex(Images.ImageColumns.DATA);
					path = cur.getString(dataIdx);
				}
				cur.close();
				if (index == 0) {
				} else {
					Uri u = Uri.parse("content://media/external/images/media/" + index);
					System.out.println("temp uri is :" + u);
				}
			}
			if (path != null) {
				return new File(path);
			}
		} else if ("content".equals(uri.getScheme())) {
			// 4.2.2以后
			String[] proj = { MediaStore.Images.Media.DATA };
			Cursor cursor = this.getContentResolver().query(uri, proj, null, null, null);
			if (cursor.moveToFirst()) {
				int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
				path = cursor.getString(columnIndex);
			}
			cursor.close();

			return new File(path);
		} else {
			Log.i("TAG", "Uri Scheme:" + uri.getScheme());
		}
		return null;
	}
}
