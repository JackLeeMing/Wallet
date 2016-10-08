/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package work.android.smartbow.com.wallet.copy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.List;
import java.util.Random;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import work.android.smartbow.com.wallet.R;
import work.android.smartbow.com.wallet.fragment.NewItemFragment;
import work.android.smartbow.com.wallet.fragment.SampleFragment;
import work.android.smartbow.com.wallet.utils.TLog;
import work.android.smartbow.com.wallet.bean.CMkk;
import work.android.smartbow.com.wallet.bean.MovieEntity;
import work.android.smartbow.com.wallet.rxjava.CatService;
import work.android.smartbow.com.wallet.rxjava.RatData;

public class CMakeActivity extends AppCompatActivity {


  static {
    System.loadLibrary("max-jni");
  }

  public static final String NULL_FRAGMENT = "null_ui";

  public static native int maxFromJNI(int a, int b);

  public native String  stringFromJNI();

  public static String getStringFromJava(){
    return "I am a String from Java.";
  }


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_cmake);
    TLog.error("Max: "+ maxFromJNI(10,16)+" "+stringFromJNI());
    Fragment fragment = SampleFragment.newInstance("","");
    getSupportFragmentManager().beginTransaction().add(R.id.content_layout,fragment,"tag1").commit();
//    getSupportFragmentManager().beginTransaction().remove(fragment).commit();
//    getSupportFragmentManager().beginTransaction().replace(R.id.content_layout,fragment,"tagd").commit();

    getSupportFragmentManager().beginTransaction().add(NewItemFragment.newInstace(),NULL_FRAGMENT).commit();

  }

  public void goNext(View view){

    NewItemFragment fragment = (NewItemFragment) getSupportFragmentManager().findFragmentByTag(NULL_FRAGMENT);

    if (fragment != null){

      if (fragment.isFlag()) {
        fragment.startWOrk();
      }
    }

  }

  public void goNext2(View view){

    NewItemFragment fragment = (NewItemFragment) getSupportFragmentManager().findFragmentByTag(NULL_FRAGMENT);

    if (fragment != null){
      fragment.setFlag(false);
    }
  }

  private void sub() {
    //subMovie();
    //被观察者 订阅 观察者。

    Subscriber<Cat> catsub = new Subscriber<Cat>() {
      @Override
      public void onCompleted() {

      }

      @Override
      public void onError(Throwable e) {

      }

      @Override
      public void onNext(Cat cat) {

        TLog.error(cat.toString());

      }
    };

    CatService service = new CatService();
    service.getCatFacts(20).map(new Func1<RatData, List<String>>() {

      @Override
      public List<String> call(RatData ratData) {

        return ratData.getData();
      }
    }).flatMap(new Func1<List<String>, Observable<String>>() {
      @Override
      public Observable<String> call(List<String> strings) {

        return Observable.from(strings);
      }
    }).distinct().filter(new Func1<String, Boolean>() {
      @Override
      public Boolean call(String s) {
        return s.length() <=3;
      }
    }).take(3).map(new Func1<String, Cat>() {

      @Override
      public Cat call(String s) {
        Cat cat = new Cat();
         cat.setIamge(new Random().nextInt());
        cat.setName(s);
        return cat;
      }
    })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(catsub);//toList()
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

  public class Cat{
    String name;
    int iamge;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public int getIamge() {
      return iamge;
    }

    public void setIamge(int iamge) {
      this.iamge = iamge;
    }

    @Override
    public String toString() {
      return "Cat{" +
          "name='" + name + '\'' +
          ", iamge=" + iamge +
          '}';
    }
  }


}
