/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package work.android.smartbow.com.wallet.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import work.android.smartbow.com.wallet.utils.TLog;

/**
 * This file was created by hellomac on 2016/10/8.
 * name: Wallet.
 */

public class NewItemFragment extends Fragment {
  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    TLog.error("Attach");
  }

  private  boolean flag = true;

  public boolean isFlag() {
    return flag;
  }

  public void setFlag(boolean flag) {
    this.flag = flag;
  }

  public static NewItemFragment newInstace(){
    return new NewItemFragment();
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    TLog.error("create");
  }

  @Override
  public void onStart() {
    super.onStart();
    TLog.error("start");
  }

  @Override
  public void onResume() {
    super.onResume();
    TLog.error("resume");
  }

  @Override
  public void onPause() {
    super.onPause();
    TLog.error("pause");
  }

  @Override
  public void onStop() {
    super.onStop();
    TLog.error("stop");
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    TLog.error("destroyView");
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    TLog.error("destroy");
  }

  @Override
  public void onDetach() {
    super.onDetach();
    TLog.error("detach");
  }

  public void startWOrk(){

    ExecutorService executors = Executors.newCachedThreadPool();

    executors.submit(new Runnable() {
      @Override
      public void run() {
        while (flag){
          TLog.error("啊哈哈哈哈!");
        }
      }
    });

    executors.shutdown();
  }

}
