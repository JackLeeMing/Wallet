/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package work.android.smartbow.com.wallet;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import rx.Subscriber;
import work.android.smartbow.com.wallet.bean.CMkk;
import work.android.smartbow.com.wallet.bean.MovieEntity;
import work.android.smartbow.com.wallet.db.DbHelper;
import work.android.smartbow.com.wallet.fragment.FragSm;
import work.android.smartbow.com.wallet.login.Info;
import work.android.smartbow.com.wallet.utils.TLog;

public class Main3Activity extends AppCompatActivity implements View.OnClickListener {


  CMkk cMkk1;
  CMkk cMkk2;

  FragSm fragSm;
  Context context;
  String data;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main3);

    context = getApplicationContext();
    fragSm = (FragSm) getSupportFragmentManager().findFragmentById(R.id.fragmentSam);
    findViewById(R.id.btn_retrofit1).setOnClickListener(this);
    findViewById(R.id.btn_retrofit2).setOnClickListener(this);
    findViewById(R.id.btn_retrofit3).setOnClickListener(this);
    findViewById(R.id.btn_retrofit4).setOnClickListener(this);

    if (savedInstanceState != null){
      data = savedInstanceState.getString(KEY1,"无");
      TLog.error("recover:"+data);

      if (fragSm != null && !TextUtils.isEmpty(data)){
        fragSm.setData(data);
      }
    }


    File file = getFilesDir();


    File  file1 = getExternalFilesDir(Environment.DIRECTORY_DCIM);//会自动创建
    if (file1 != null) {
      TLog.error(file1.getAbsolutePath());
    }

    File file2 = getExternalCacheDir();
    if (file2 != null) {
      TLog.error("file2:"+file2.getAbsolutePath());
    }

    File file3 = getCacheDir();
    TLog.error("file3:"+file3.getAbsolutePath());

    File file4 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
    TLog.error(file4.getAbsolutePath());

    cMkk1 = CMkk.getInstance();
    cMkk2 = CMkk.getInstance2();
  }

  //点击事件
  public void onClick(View view){
    SQLiteDatabase db = new DbHelper(context).getWritableDatabase();
    switch (view.getId()){
      case R.id.btn_retrofit1:
         get();
        ContentValues values = new ContentValues();
        values.put("key","LL");
        values.put("num",12.9);
        values.put("color",23);
        db.insert(DbHelper.DATA_TABLE_NAME,null,values);
        break;

      case R.id.btn_retrofit2:

        Cursor cursor = db.query(DbHelper.DATA_TABLE_NAME,new String[]{"key","num","color"},null,null,null,null,null);
        while (cursor.moveToNext()){
          String key = cursor.getString(cursor.getColumnIndex("key"));
          float num = cursor.getFloat(cursor.getColumnIndex("num"));
          int color = cursor.getInt(cursor.getColumnIndex("color"));
          TLog.error("key:"+key+" num:"+num+" color:"+color);
        }
        cursor.close();
        request();
        break;

      case R.id.btn_retrofit3:
        get2();
        break;
      case R.id.btn_retrofit4:
        db.close();
        request2();
        break;
    }


  }

  private static final String KEY1 = "INFO";
  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    TLog.error("save");
    outState.putString(KEY1,data);
  }

  public void get(){


    cMkk1.opt(0,2);
  }

  public void get2(){
    cMkk2.login2("waiwei","666666");
  }



  public void request2(){

    Subscriber<Info> sub = new Subscriber<Info>() {
      @Override
      public void onCompleted() {
        TLog.error("complete");
      }

      @Override
      public void onError(Throwable e) {
        TLog.error("Error:"+e.getMessage());
      }

      @Override
      public void onNext(Info user) {
        TLog.error("Ok");
        TLog.error(""+user.toString());
        data = user.toString();
        fragSm.setData(data);

        try {
          FileOutputStream fos = openFileOutput("tempfile.tmp",Context.MODE_PRIVATE);

        } catch (FileNotFoundException e) {
          e.printStackTrace();
        }
      }
    };

    cMkk2.login(sub,"waiwei","666666");
  }
  public void request(){


    Subscriber<MovieEntity> subscriber = new Subscriber<MovieEntity>() {
      @Override
      public void onCompleted() {
      TLog.error("completed");
      }

      @Override
      public void onError(Throwable e) {
        TLog.error("Error:"+e);
      }

      @Override
      public void onNext(MovieEntity movieEntity) {
           TLog.error(movieEntity.toString());
      }
    };
    cMkk1.getMoive(subscriber,0,2);

  }


  //添加Cookie
  public static class AddCookiesInterceptor implements Interceptor {

    private static final String PREF_COOKIES = "PREF_COOKIES";
    private Context context;
    public AddCookiesInterceptor(Context context) {
      this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
      Request.Builder builder = chain.request().newBuilder();
      HashSet<String> pres = (HashSet<String>) PreferenceManager.getDefaultSharedPreferences(context).getStringSet(PREF_COOKIES,new HashSet<String>());
      for (String cookie : pres){
        builder.addHeader("Cookie",cookie);
      }

      Request request = chain.request();
      TLog.error("url:"+request.url());

      return chain.proceed(builder.build());
    }
  }
  //接收Cookie
  public static class ReceiveCookiesInterceptor implements Interceptor
  {
    private static final String PREF_COOKIES = "PREF_COOKIES";
    private Context context;

    public ReceiveCookiesInterceptor(Context context) {
      this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
      Response response = chain.proceed(chain.request());
     String headers =  response.header("Set-Cookie");
      if (headers != null && !headers.isEmpty()){
        HashSet<String> cookies = (HashSet<String>)PreferenceManager.getDefaultSharedPreferences(context).getStringSet(PREF_COOKIES,new HashSet<String>());
        for (String header : response.headers("Set-Cookie")){
          cookies.add(header);
          TLog.error("cookie-header: "+header);
        }
        SharedPreferences.Editor memes = PreferenceManager.getDefaultSharedPreferences(context).edit();
        memes.putStringSet(PREF_COOKIES,cookies).apply();
        memes.commit();
      }
      return response;
    }

    private void subMovie() {

      Subscriber<MovieEntity> sub = new Subscriber<MovieEntity>() {

        @Override
        public void onStart() {
          super.onStart();
          TLog.error("start: "+Thread.currentThread());
        }

        @Override
        public void onCompleted() {
          TLog.error("completed");
        }

        @Override
        public void onError(Throwable e) {
          TLog.error("Error:"+e.getMessage());
        }

        @Override
        public void onNext(MovieEntity movieEntity) {
          if (movieEntity != null){
            TLog.error(movieEntity.toString());
          }
        }
      };

      CMkk.getInstance().getMoive(sub,0,10);

      if (!sub.isUnsubscribed()){
        sub.unsubscribe();
      }

      CMkk.getInstance().getMoive(sub,0,10);
    }

  }
}
