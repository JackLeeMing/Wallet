/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package work.android.smartbow.com.wallet.app;

import android.app.Application;

import work.android.smartbow.com.wallet.error.CustomActivityOnCrash;

/**
 * This file was created by hellomac on 2016/10/10.
 * name: Wallet.
 */

public class App extends Application {
  private static App appContext;

  @Override
  public void onCreate() {
    super.onCreate();
    appContext = this;

    CustomActivityOnCrash.install(appContext);
  }

  public static App getAppContext(){
     return appContext;
  }
}
