/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package work.android.smartbow.com.wallet.bean;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import work.android.smartbow.com.wallet.Main3Activity;
import work.android.smartbow.com.wallet.app.App;
import work.android.smartbow.com.wallet.login.Info;
import work.android.smartbow.com.wallet.utils.TLog;

/**
 * This file was created by hellomac on 2016/9/29.
 * name: Wallet.
 */

public class CMkk {


  //默认采用的Retrofit + RxJava的网络请求
  private CMkk() {
    OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
    httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
    httpClientBuilder.addInterceptor(new Main3Activity.AddCookiesInterceptor(App.getAppContext()));
    httpClientBuilder.addInterceptor(new Main3Activity.ReceiveCookiesInterceptor(App.getAppContext()));
    Retrofit retrofit = new Retrofit.Builder().client(httpClientBuilder.build())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build();

    movieService = retrofit.create(MovieService.class);


  }

  public CMkk(String tag) {
    OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
    httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
    httpClientBuilder.addInterceptor(new Main3Activity.AddCookiesInterceptor(App.getAppContext()));
    httpClientBuilder.addInterceptor(new Main3Activity.ReceiveCookiesInterceptor(App.getAppContext()));
    Retrofit retrofit = new Retrofit.Builder().client(httpClientBuilder.build())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL_LOGIN)
        .build();
    loginService = retrofit.create(LoginService.class);
  }


  //之采用Retrofit 的网络请求
  public void opt(int start,int count){
    if (movieService != null){
      Call<MovieEntity> call = movieService.getTopMovie2(start,count);
      call.enqueue(new Callback<MovieEntity>() {
        @Override
        public void onResponse(Call<MovieEntity> call, Response<MovieEntity> response) {
          if (response != null){
            TLog.error(response.body().toString());
          }
        }

        @Override
        public void onFailure(Call<MovieEntity> call, Throwable t) {
          TLog.error("fail");
        }
      });
    }
  }

  private static final String BASE_URL = "https://api.douban.com/v2/movie/";
  private static final String BASE_URL_LOGIN = "http://tcas.thunics.com/";
  private static final int DEFAULT_TIMEOUT = 5;

  private MovieService movieService;
  private LoginService loginService;

  private static class SingletonHolder{
    private static final CMkk INSTANCE = new CMkk();
    private static final CMkk INSTANCE2 = new CMkk("");
  }

  public static CMkk getInstance(){

    return CMkk.SingletonHolder.INSTANCE;
  }

  public static CMkk getInstance2(){

    return CMkk.SingletonHolder.INSTANCE2;
  }


  //外部调用的请求方法
  public void getMoive(Subscriber<MovieEntity> subscriber,int start,int count){
    Observable<MovieEntity> observable = movieService.getTopMovie(start,count);//获取被观察者的引用
    observable
        .subscribeOn(Schedulers.io())
        .unsubscribeOn(Schedulers.io())
        .doOnSubscribe(new Action0() {
          @Override
    public void call() {
      TLog.error("start prepare: "+Thread.currentThread());
    }
  })
      .subscribeOn(AndroidSchedulers.mainThread())
      .observeOn(AndroidSchedulers.mainThread())
        .subscribe(subscriber);
  }


  //login
  public void login(Subscriber<Info> subscriber,String name,String pwd){
    if (loginService != null){
      Observable<Info> observable =loginService.userLoginOb(name,pwd);
      observable.subscribeOn(Schedulers.io())
          .unsubscribeOn(Schedulers.io())
          .doOnSubscribe(new Action0() {
            @Override
            public void call() {
              TLog.error("Login");
            }
          })
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(subscriber);
    }
  }

  //login 2
  public void login2(String name,String pwd){
    if (loginService != null) {

      Call<Info> call = loginService.userLoginTxt(name,pwd);

      call.enqueue(new Callback<Info>() {
        @Override
        public void onResponse(Call<Info> call, retrofit2.Response<Info> response) {
          TLog.error("CallBack: " + response.body());
        }

        @Override
        public void onFailure(Call<Info> call, Throwable t) {
          TLog.error("CallBack fail:"+t);
        }
      });

    }
  }
}
