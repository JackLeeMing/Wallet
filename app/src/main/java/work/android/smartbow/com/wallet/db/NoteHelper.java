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
 * This file was created by hellomac on 2016/10/21.
 * name: Wallet.
 */

public class NoteHelper extends SQLiteOpenHelper {


  public NoteHelper(Context context) {
    super(context, NoteProviderMetaData.DATABASE_NAME,null, NoteProviderMetaData.DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(NoteProviderMetaData.NoteTableMetaData.SQL_CREATE_TABLE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
   db.execSQL("DROP TABLE IF "+NoteProviderMetaData.NoteTableMetaData.TABLE_NAME);
    onCreate(db);
  }
}
