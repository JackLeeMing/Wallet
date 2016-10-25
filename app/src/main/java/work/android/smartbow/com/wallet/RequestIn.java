/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package work.android.smartbow.com.wallet;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.POST;
import rx.Observable;
import work.android.smartbow.com.wallet.login.Info;

/**
 * This file was created by hellomac on 2016/10/18.
 * name: Wallet.
 */

public interface RequestIn {

  @POST("/m/login")
  Observable<Info> login(@Field("loginname") String name, @Field("pwd") String pwd);

  @POST("/m/login")
  Call<String> login2(@Field("loginname") String name,@Field("pwd") String pwd);
}
