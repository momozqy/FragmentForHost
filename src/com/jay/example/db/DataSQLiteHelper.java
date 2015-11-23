
package com.jay.example.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DataSQLiteHelper extends SQLiteOpenHelper 
{
	
	private static final int VERSION = 1;
	private static String CREATE_DATA_TAB = "CREATE TABLE IF NOT EXISTS DATA(id INTEGER PRIMARY KEY autoincrement,type text,atrrs text,num int,time text)";
	public DataSQLiteHelper(Context context, String name,
			CursorFactory factory, int version) {
		// TODO Auto-generated constructor stub
		super(context, name, factory, version);
		
	}
	public DataSQLiteHelper(Context context,String name)
	{
		// TODO Auto-generated constructor stub
		this(context,name,VERSION);
	}
	public DataSQLiteHelper(Context context,String name,int version)
	{
		// TODO Auto-generated constructor stub
		this(context,name,null,version);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		// TODO Auto-generated method stub
		db.execSQL(CREATE_DATA_TAB);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		
	}

}
