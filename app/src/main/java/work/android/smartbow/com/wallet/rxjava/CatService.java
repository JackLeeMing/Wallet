/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package work.android.smartbow.com.wallet.rxjava;

import java.util.Arrays;
import java.util.List;

import rx.Observable;

/**
 * This file was created by hellomac on 2016/10/5.
 * name: Wallet.
 */

public class CatService {

  private static final String strSource = "sss;1ss;3dwdw;dsdsds;dede;dede;de;dededde;dededde;dededd;dededed;ddd;ddd;dddd;ddddd;dsscs;scscsc;cdcec;ceedaswd;edwdwd;dedwdsds";
  private static final int code = 200;
  public Observable<RatData> getCatFacts(int count){
       String datas[] = strSource.trim().split(";");
      List<String> strs = Arrays.asList(datas);

    RatData ratData = new RatData();
    ratData.setCode(code);
    if (strs.size() >= count){
      ratData.setData(strs.subList(0,count));
    }else{
      ratData.setData(strs);
    }
    return Observable.just(ratData);
  }
}
