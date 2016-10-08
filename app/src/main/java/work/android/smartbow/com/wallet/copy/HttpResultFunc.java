/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package work.android.smartbow.com.wallet.copy;

import rx.functions.Func1;
import work.android.smartbow.com.wallet.bean.HttpResult;

/**
 * This file was created by hellomac on 2016/9/29.
 * name: Wallet.
 */

public class HttpResultFunc<T> implements Func1<HttpResult<T>,T> {

  @Override
  public T call(HttpResult<T> tHttpResult) {
    if (tHttpResult.getResultCode() != 0) {
      throw new ApiException(tHttpResult.getResultCode());
    }
    return tHttpResult.getSubjects();
  }
}
