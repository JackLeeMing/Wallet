/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package work.android.smartbow.com.wallet.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * This file was created by hellomac on 2016/10/20.
 * name: Wallet.
 */

public class DbHelper extends SQLiteOpenHelper {

  private static final String DATABASE_NAME = "NoteDataBase.db";
  public static final String DATA_TABLE_NAME = "Note1";
  private static final int DB_VERSION = 1;
  private static final String DB_CREATE = "create table "+DATA_TABLE_NAME+"(_id integer primary key autoincrement, key text not null,num float,color integer);";


  private DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
    super(context, name, factory, version);
  }

  public DbHelper(Context context){
    this(context,DATABASE_NAME,null,DB_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(DB_CREATE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("drop table if exists "+DATA_TABLE_NAME);
    onCreate(db);
  }

}
