/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package work.android.smartbow.com.wallet.bean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * This file was created by hellomac on 2016/9/29.
 * name: Wallet.
 */

 interface MovieService {
  @GET("top250")
  Observable<MovieEntity> getTopMovie(@Query("start")int start,@Query("count")int count);

 @GET("top250")
 Call<MovieEntity> getTopMovie2(@Query("start") int start, @Query("count")int count);
}
