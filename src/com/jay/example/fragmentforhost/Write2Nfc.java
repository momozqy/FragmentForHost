package com.jay.example.fragmentforhost;

import java.io.IOException;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.nfc.tech.NfcF;
import android.nfc.tech.NfcV;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.Toast;

import com.jay.example.db.DataSQLiteHelper;

public class Write2Nfc extends Activity {
	String text;
	ImageView img;
	private IntentFilter[] mWriteTagFilters;
	private NfcAdapter nfcAdapter;
	PendingIntent pendingIntent;
	String[][] mTechLists;
	private Boolean ifWrite;
	DataSQLiteHelper dh;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_write_nfc);

		Intent in = getIntent();
		img = (ImageView) findViewById(R.id.tips);
		text = in.getStringExtra("content");
		if (dh == null)
			dh = new DataSQLiteHelper(Write2Nfc.this, "zqydb");
		init();

	}

	private void init() {
		// TODO Auto-generated method stub
		ifWrite = true;
		displayControl(true);
		nfcAdapter = NfcAdapter.getDefaultAdapter(this);
		pendingIntent = PendingIntent.getActivity(this, 0,
				new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
		IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
		ndef.addCategory("*/*");
		mWriteTagFilters = new IntentFilter[] { ndef };
		mTechLists = new String[][] { new String[] { NfcA.class.getName() }, new String[] { NfcF.class.getName() },
				new String[] { NfcB.class.getName() }, new String[] { NfcV.class.getName() } };
	}

	public void displayControl(Boolean ifWriting) {
		if (ifWriting) {
			img.setBackgroundResource(R.drawable.near);
			return;
		}
		img.setBackgroundResource(R.drawable.success);
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {

			public void run() {
				Intent in = new Intent();
				in.putExtra("result", "success");
				Write2Nfc.this.setResult(RESULT_OK,in);
				Write2Nfc.this.finish();
			}
		}, 300);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		System.out.println("1.5....");
		if (text == null) {
			Toast.makeText(getApplicationContext(), "数据不能为空!", Toast.LENGTH_SHORT).show();
			System.out.println("2....");
			return;
		}
		if (ifWrite == true) {
			System.out.println("2.5....");
			if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())
					|| NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())) {
				Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
				Ndef ndef = Ndef.get(tag);
				try {
					// 数据的写入过程一定要有连接操作
					ndef.connect();
					// 构建数据包，也就是你要写入标签的数据
					NdefRecord ndefRecord = new NdefRecord(NdefRecord.TNF_MIME_MEDIA, "text/plain".getBytes(),
							new byte[] {}, text.getBytes());
					NdefRecord[] records = { ndefRecord };
					NdefMessage ndefMessage = new NdefMessage(records);
					ndef.writeNdefMessage(ndefMessage);
					System.out.println("3....");

					SQLiteDatabase db = dh.getWritableDatabase();
					ContentValues cv = new ContentValues();
					String type = String.valueOf(getIntent().getStringExtra("type"));
					cv.put("type", type);
					cv.put("atrrs", text);
					cv.put("num", getIntent().getIntExtra("num", 6));
					cv.put("time", String.valueOf(getIntent().getStringExtra("time")));
					db.insert("DATA", null, cv);
					if(type.equals("定制")){
						db.delete("MAKE","atrrs=?",new String []{text});
					}
					displayControl(false);
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (FormatException e) {

				}
			}
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		nfcAdapter.enableForegroundDispatch(this, pendingIntent, mWriteTagFilters, mTechLists);
	}
}
