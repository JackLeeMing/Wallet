/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package work.android.smartbow.com.wallet.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import work.android.smartbow.com.wallet.R;

/**
 * This file was created by hellomac on 2016/10/8.
 * name: Wallet.
 */

public class MyDialogFragment extends DialogFragment {

  public static MyDialogFragment newInstance(int num){
    MyDialogFragment myDialogFragment = new MyDialogFragment();
    Bundle bundle = new Bundle();
    bundle.putInt("num",num);
    myDialogFragment.setArguments(bundle);
    return myDialogFragment;
  }

  Activity mContext;

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    mContext = (Activity) context;
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {

    return new AlertDialog.Builder(mContext)
        .setTitle(R.string.app_name)
        .setIcon(android.R.drawable.ic_dialog_map)
        .setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
             getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK,mContext.getIntent());
          }
        }).setNegativeButton(R.string.cancel_button, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {

            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_CANCELED,mContext.getIntent());
          }
        }).create();

  }
}
