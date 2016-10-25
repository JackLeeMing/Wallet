/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package work.android.smartbow.com.wallet.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.io.FileNotFoundException;
import java.util.HashMap;

/**
 * This file was created by hellomac on 2016/10/21.
 * name: Wallet.
 */

public class NoteContentProvider extends ContentProvider {

  private static final UriMatcher uriMatcher;
  private static final int COLLECTION_INDICATOR = 1;
  private static final int SINGLE_INDICATOR = 2;
  static {
    uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    uriMatcher.addURI(NoteProviderMetaData.AUTHORITY,"notes",COLLECTION_INDICATOR);
    uriMatcher.addURI(NoteProviderMetaData.AUTHORITY,"notes/#",SINGLE_INDICATOR);
  }

  private Context context;

  //查询的投影映射 key 是抽象字段的名称，value对应数据库中的字段名称
  private static HashMap<String,String> noteProjectionMap;
  static {
    noteProjectionMap = new HashMap<>();
    noteProjectionMap.put(NoteProviderMetaData.NoteTableMetaData._ID, NoteProviderMetaData.NoteTableMetaData._ID);
    noteProjectionMap.put(NoteProviderMetaData.NoteTableMetaData.NOTE_CONTENT, NoteProviderMetaData.NoteTableMetaData.NOTE_CONTENT);
    noteProjectionMap.put(NoteProviderMetaData.NoteTableMetaData.NOTE_TITLE, NoteProviderMetaData.NoteTableMetaData.NOTE_TITLE);
    noteProjectionMap.put(NoteProviderMetaData.NoteTableMetaData.CREATE_DATE, NoteProviderMetaData.NoteTableMetaData.CREATE_DATE);
  }

  NoteHelper helper;
  @Override
  public boolean onCreate() {
    context = getContext();
    helper = new NoteHelper(context);
    return false;
  }

  @Nullable
  @Override
  public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
    SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
    switch (uriMatcher.match(uri)){
      case COLLECTION_INDICATOR:
        //设置查询表
        queryBuilder.setTables(NoteProviderMetaData.NoteTableMetaData.TABLE_NAME);
        //设置投影映射
        queryBuilder.setProjectionMap(noteProjectionMap);
        break;
      case SINGLE_INDICATOR:
        queryBuilder.setTables(NoteProviderMetaData.NoteTableMetaData.TABLE_NAME);
        queryBuilder.setProjectionMap(noteProjectionMap);
        queryBuilder.appendWhere(NoteProviderMetaData.NoteTableMetaData._ID+"="+uri.getPathSegments().get(1));
        break;
      default:
        throw new RuntimeException("Unknown URI "+uri);
    }

    String orderBy;
    if (TextUtils.isEmpty(sortOrder)){
      orderBy = NoteProviderMetaData.NoteTableMetaData.DEFAULT_ORDERBY;
    }else{
      orderBy = sortOrder;
    }
    SQLiteDatabase db = helper.getReadableDatabase();
    Cursor cursor = queryBuilder.query(db,projection,selection,selectionArgs,null,null,orderBy);
    return cursor;
  }

  @Nullable
  @Override
  public String getType(Uri uri) {
    switch (uriMatcher.match(uri)){
      case COLLECTION_INDICATOR:
        return NoteProviderMetaData.NoteTableMetaData.CONTENT_TYPE;
      case SINGLE_INDICATOR:
        return NoteProviderMetaData.NoteTableMetaData.CONTENT_ITEM_TYPE;
      default:
        throw new IllegalArgumentException("Unknown URI "+uri);
    }
  }

  @Nullable
  @Override
  public Uri insert(Uri uri, ContentValues values) {
    if (uriMatcher.match(uri) != COLLECTION_INDICATOR){
      throw new IllegalArgumentException("Unknown URI "+uri);
    }
    SQLiteDatabase db = helper.getWritableDatabase();
    long rowID = db.insert(NoteProviderMetaData.NoteTableMetaData.TABLE_NAME,null,values);
    if(rowID >0){
      Uri retUri = ContentUris.withAppendedId(NoteProviderMetaData.NoteTableMetaData.CONTENT_URI,rowID);
      return  retUri;
    }
    return null;
  }

  @Override
  public int delete(Uri uri, String selection, String[] selectionArgs) {
    SQLiteDatabase db = helper.getWritableDatabase();
    int count = -1;
    switch (uriMatcher.match(uri)){
      case COLLECTION_INDICATOR:
        count = db.delete(NoteProviderMetaData.NoteTableMetaData.TABLE_NAME,selection,selectionArgs);
        break;
      case SINGLE_INDICATOR:
        String rowID = uri.getPathSegments().get(1);
        count = db.delete(NoteProviderMetaData.NoteTableMetaData.TABLE_NAME, NoteProviderMetaData.NoteTableMetaData._ID+"=?",new String[]{rowID});
        break;
      default:
        throw new RuntimeException("Unknown URI:"+uri);
    }
    context.getContentResolver().notifyChange(uri,null);
    return count;
  }

  @Override
  public int update(@Nullable Uri uri, ContentValues values, String selection, String[] selectionArgs) {
    if (uri == null){
      return 0;
    }
    SQLiteDatabase db = helper.getWritableDatabase();
    int count = -1;
    switch (uriMatcher.match(uri)){
      case COLLECTION_INDICATOR:
        count = db.update(NoteProviderMetaData.NoteTableMetaData.TABLE_NAME,values,selection,selectionArgs);
        break;
      case SINGLE_INDICATOR:
        String rowID = uri.getPathSegments().get(1);
        count = db.update(NoteProviderMetaData.NoteTableMetaData.TABLE_NAME,values, NoteProviderMetaData.NoteTableMetaData._ID+"=?",new String[]{rowID});
        break;
        default:
          throw new RuntimeException("Unknown URI: "+uri);
    }
    context.getContentResolver().notifyChange(uri,null);
    return count;
  }

  @Nullable
  @Override
  public AssetFileDescriptor openAssetFile(Uri uri, String mode) throws FileNotFoundException {
    return super.openAssetFile(uri, mode);
  }

}
