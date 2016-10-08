/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package work.android.smartbow.com.wallet.copy;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import work.android.smartbow.com.wallet.R;
import work.android.smartbow.com.wallet.activity.Main2Activity;
import work.android.smartbow.com.wallet.utils.TLog;

public class LearnActivity extends AppCompatActivity {

   TextView textView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_learn);
    textView = (TextView)findViewById(R.id.textView);
    TLog.error("Create 1 args");

    textView.setText(stringFromJNI());


  }

  /* A native method that is implemented by the
   * 'hello-jni' native library, which is packaged
   * with this application.
   */
  public native String  stringFromJNI();

  /* This is another native method declaration that is *not*
   * implemented by 'hello-jni'. This is simply to show that
   * you can declare as many native methods in your Java code
   * as you want, their implementation is searched in the
   * currently loaded native libraries only the first time
   * you call them.
   *
   * Trying to call this function will result in a
   * java.lang.UnsatisfiedLinkError exception !
   */

  /* this is used to load the 'hello-jni' library on application
   * startup. The library has already been unpacked into
   * /data/data/com.example.hellojni/lib/libhello-jni.so at
   * installation time by the package manager.
   */
  static {
    System.loadLibrary("hello-jni");
  }



  @Override
  public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
    super.onCreate(savedInstanceState, persistentState);
    TLog.error("Create 2 args");
  }

  @Override
  protected void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    TLog.error("onRestore");

    int requestCode = 1;
    Intent intent = new Intent( LearnActivity.this, Main2Activity.class);
    intent.putExtra("requestCode",requestCode);

    startActivityForResult(intent,requestCode);
  }

  @Override
  protected void onStart() {
    super.onStart();
    TLog.error("onStart");

    Intent intent = getIntent();
    if (intent != null) {
      int requestCode = intent.getIntExtra("requestCode", 0);
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
    TLog.error("onResume");
  }

  @Override
  public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
    super.onSaveInstanceState(outState, outPersistentState);
    TLog.error("onSave 2 args");
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    TLog.error("onSave 1 args");
  }

  @Override
  protected void onPause() {
    super.onPause();
    TLog.error("onPause");
  }

  @Override
  protected void onStop() {
    super.onStop();
    TLog.error("onStop");
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    TLog.error("onDestroy");
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);

    TLog.error("onConfigChange");
    if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
      Log.e("TAG","LAND");
    }

    if (newConfig.keyboardHidden == Configuration.KEYBOARDHIDDEN_NO){
      TLog.error("key board hidden no");
    }

  }
}
