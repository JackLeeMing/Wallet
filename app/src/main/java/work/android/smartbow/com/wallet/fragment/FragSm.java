/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package work.android.smartbow.com.wallet.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import work.android.smartbow.com.wallet.R;
import work.android.smartbow.com.wallet.utils.TLog;

/**
 * This file was created by hellomac on 2016/10/20.
 * name: Wallet.
 */

public class FragSm extends Fragment {


  public FragSm() {
  }

  TextView textView;
  public static FragSm getInstance(){
    return new FragSm();
  }

  InputStream in;
  InputStreamReader inr;
  BufferedReader reader;
  public void setData(String data){
       textView.setText(data);
     in =  getResources().openRawResource(R.raw.index);
    inr = new InputStreamReader(in);
    reader = new BufferedReader(inr);
    try {
      String str;
      while ( (str = reader.readLine()) != null){
        TLog.error(str);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }finally {
      try {
        if (in != null){
          in.close();
        }

        if (inr != null){
          inr.close();
        }
        if (reader != null){
          reader.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

    return inflater.inflate(R.layout.frament_1,container,false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    textView = (TextView)view.findViewById(R.id.textViewF);
  }

  @Override
  public void onStart() {
    super.onStart();
    TLog.error("fragment start.");
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    TLog.error("fragment onDestroy");
  }

  @Override
  public void onDetach() {
    super.onDetach();
    TLog.error("fragment detach");
  }
}
