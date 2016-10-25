/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package work.android.smartbow.com.wallet.db;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * This file was created by hellomac on 2016/10/21.
 * name: Wallet.
 */

public class NoteProviderMetaData {
  public static final String AUTHORITY = "com.lijiakui.provider.NoteProvider";
  public static final String DATABASE_NAME = "note.db";
  public static final int DATABASE_VERSION = 1;
  /*
  * 数据库中表相关的元数据
  * */
  public static final class NoteTableMetaData implements BaseColumns{
    public static final String TABLE_NAME = "notes";
    public static final Uri CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/notes");
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.lijiakui.note";
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.lijiakui.note";
    public static final String NOTE_TITLE = "title";
    public static final String NOTE_CONTENT ="content";
    public static final String CREATE_DATE = "create_date";
    public static final String DEFAULT_ORDERBY = "create_date DESC";
    public static final String SQL_CREATE_TABLE = "CREATE TABLE "+TABLE_NAME
        +" ("+_ID+" INTEGER PRIMARY KEY,"
        +NOTE_TITLE+" VARCHAR(50),"
        +NOTE_CONTENT+" TEXT,"
        +CREATE_DATE+" INTEGER"
        +");";
  }
}

