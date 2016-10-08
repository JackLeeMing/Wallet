/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package work.android.smartbow.com.wallet.copy;

/**
 * This file was created by hellomac on 2016/9/29.
 * name: Wallet.
 */

public class ApiException extends RuntimeException {

  private int resultCode;

  public ApiException(int resultCode) {
    this.resultCode = resultCode;
  }
}
