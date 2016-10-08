/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package work.android.smartbow.com.wallet.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import work.android.smartbow.com.wallet.R;
import work.android.smartbow.com.wallet.utils.TLog;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SampleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SampleFragment extends Fragment {
  int mStackLevel = 0;
  public static final int DIALOG_FRAGMENT = 1;
  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";

  // TODO: Rename and change types of parameters
  private String mParam1;
  private String mParam2;

  Context mContext;
  Button textView;


  public SampleFragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @param param1 Parameter 1.
   * @param param2 Parameter 2.
   * @return A new instance of fragment SampleFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static SampleFragment newInstance(String param1, String param2) {
    SampleFragment fragment = new SampleFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    args.putString(ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    mContext = context;
    TLog.error("Attach");
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mParam1 = getArguments().getString(ARG_PARAM1);
      mParam2 = getArguments().getString(ARG_PARAM2);
    }

    if (savedInstanceState != null){
      mStackLevel = savedInstanceState.getInt("level");
    }

    TLog.error("Create");
    final ListView listView = new ListView(mContext);




    final BaseAdapter adapter = new BaseAdapter() {

      private int state;
      @Override
      public int getCount() {
        return 0;
      }

      @Override
      public Object getItem(int position) {
        return null;
      }

      public void setScrollState(int state){
        this.state = state;
      }

      @Override
      public long getItemId(int position) {
        return 0;
      }

      @Override
      public View getView(int position, View convertView, ViewGroup parent) {

        return null;
      }
    };


    listView.setOnScrollListener(new AbsListView.OnScrollListener() {
      @Override
      public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState){
          case SCROLL_STATE_FLING:
            break;
          case SCROLL_STATE_TOUCH_SCROLL:
            break;
          case SCROLL_STATE_IDLE:
            break;
        }
      }

      @Override
      public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

      }
    });
    listView.setAdapter(adapter);



  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    TLog.error("createView");
    return inflater.inflate(R.layout.fragment_sample, container, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    textView = (Button) view.findViewById(R.id.textView);
    TLog.error("ViewCreated");

     if (savedInstanceState != null){
       String key = savedInstanceState.getString("key","----cre");
       textView.setText(key+" viewCreated");
     }

    textView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
          showDialog(DIALOG_FRAGMENT);
      }
    });
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    TLog.error("ActivityCreated");
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

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    TLog.error("save");
    if (outState == null){
      outState = new Bundle();
    }
      outState.putString("key","save");
    outState.putInt("level",mStackLevel);
  }

  @Override
  public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
    super.onViewStateRestored(savedInstanceState);
    TLog.error("restore");
    if (savedInstanceState != null){
      String key = savedInstanceState.getString("key","----re");
      textView.setText(key+" restore");
    }
  }

  void showDialog(int type){
    mStackLevel ++;

    FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
    Fragment prev = getChildFragmentManager().findFragmentByTag("dialog");
    if (prev != null){
      transaction.remove(prev);
    }
    transaction.addToBackStack(null);
    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
    transaction.setCustomAnimations(R.anim.slide_in_left,R.anim.slide_in_right);
    switch (type){
      case DIALOG_FRAGMENT:
        DialogFragment dialogFragment = MyDialogFragment.newInstance(123);
        dialogFragment.setTargetFragment(this,DIALOG_FRAGMENT);

        dialogFragment.show(transaction,"dialog");
        break;
    }
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    switch (requestCode){
      case DIALOG_FRAGMENT:
        if (resultCode == Activity.RESULT_OK){
             TLog.error("OK");
        }else if(resultCode == Activity.RESULT_CANCELED){
            TLog.error("cancel");
        }
        break;
    }
  }
}
