/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package work.android.smartbow.com.wallet.db;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.io.FileNotFoundException;

/**
 * This file was created by hellomac on 2016/10/20.
 * name: AppContentProvider.
 */

public class MyContentProvider extends ContentProvider {

  private static final Uri URI= Uri.parse("content://com.android.smartbow.work.wallet/elements");

  private DbHelper helper;
  private static final int ALLROWS = 1;
  private static final int SINGLE_ROWS = 2;
  private static final UriMatcher uriMatcher;
  private Context context;

  static {
    uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    uriMatcher.addURI("com.android.smartbow.work.wallet","elements",ALLROWS);
    uriMatcher.addURI("com.android.smartbow.work.wallet","elements/#",SINGLE_ROWS);
  }
  @Override
  public boolean onCreate() {
    context = getContext();
    helper = new DbHelper(context.getApplicationContext());

    return true;
  }

  @Nullable
  @Override
  public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
    SQLiteDatabase database;
    try {
      database = helper.getWritableDatabase();
    }catch (SQLiteException e){
      database = helper.getReadableDatabase();
    }
    SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

    switch (uriMatcher.match(uri)){
      case SINGLE_ROWS:
        String rowId = uri.getPathSegments().get(1);
        builder.appendWhere("_id ="+rowId);
        break;
      case ALLROWS:

        break;
    }
    builder.setTables(DbHelper.DATA_TABLE_NAME);
    Cursor cursor = builder.query(database,projection,selection,selectionArgs,null,null,null,null);
    return cursor;
  }

  @Nullable
  @Override
  public String getType(@NonNull Uri uri) {
    switch (uriMatcher.match(uri)){
      case ALLROWS:
        return "vnd.android.cursor.dir/vnd.smartBow.elemental";
      case SINGLE_ROWS:
        return "vnd.android.cursor.item/vnd.smartBow.elemental";

      default:
        throw new RuntimeException("Unsupported uri!");
    }

  }

  @Nullable
  @Override
  public Uri insert(@NonNull Uri uri, ContentValues values) {
    // Open a read / write database to support the transaction.
    SQLiteDatabase db = helper.getWritableDatabase();

    // To add empty rows to your database by passing in an empty
    // Content Values object you must use the null column hack
    // parameter to specify the name of the column that can be
    // set to null.
    String nullColumnHack = null;

    // Insert the values into the table
    long id = db.insert(DbHelper.DATA_TABLE_NAME,
        nullColumnHack, values);

    // Construct and return the URI of the newly inserted row.
    if (id > -1) {
      // Construct and return the URI of the newly inserted row.
      Uri insertedId = ContentUris.withAppendedId(URI, id);

      // Notify any observers of the change in the data set.
      getContext().getContentResolver().notifyChange(insertedId, null);

      return insertedId;
    }
    else
      return null;
  }

  @Override
  public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
    SQLiteDatabase db = helper.getWritableDatabase();
    switch (uriMatcher.match(uri)){
      case ALLROWS:
        break;
      case SINGLE_ROWS:
        String id = uri.getPathSegments().get(1);
        selection = "_id = "+id+(!TextUtils.isEmpty(selection) ? "and ("+selection+")":"");
        break;
    }

    if (selection == null){
      selection = "1";
    }
    int deleteCount = db.delete(DbHelper.DATA_TABLE_NAME,selection,selectionArgs);
    if (context != null) {
      ContentResolver resolver = context.getContentResolver();
      if (resolver != null) {
        resolver.notifyChange(uri, null);
      }
    }

    return deleteCount;
  }

  @Override
  public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {

    // Open a read / write database to support the transaction.
    SQLiteDatabase db = helper.getWritableDatabase();

    // If this is a row URI, limit the deletion to the specified row.
    switch (uriMatcher.match(uri)) {
      case SINGLE_ROWS :
        String rowID = uri.getPathSegments().get(1);
        selection =  "_id=" + rowID
            + (!TextUtils.isEmpty(selection) ?
            " AND (" + selection + ')' : "");
      default: break;
    }

    // Perform the update.
    int updateCount = db.update(DbHelper.DATA_TABLE_NAME,
        values, selection, selectionArgs);

    // Notify any observers of the change in the data set.
     context.getContentResolver().notifyChange(uri, null);

    return updateCount;
  }

  @Nullable
  @Override
  public ParcelFileDescriptor openFile(Uri uri, String mode) throws FileNotFoundException {
    return super.openFile(uri, mode);
  }

  @Nullable
  @Override
  public AssetFileDescriptor openAssetFile(Uri uri, String mode) throws FileNotFoundException {
    return super.openAssetFile(uri, mode);
  }

}
