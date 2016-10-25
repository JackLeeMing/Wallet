/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package work.android.smartbow.com.wallet.login;

import java.io.Serializable;

/**
 * This file was created by hellomac on 2016/10/18.
 * name: Wallet.
 */

public class Info implements Serializable {
  private boolean status;
  ////{"status":true,"data":{"username":"qiaodong","_id":"5796d1a96ab73af4d35e4820","nickname":"qiaodong","email":"qiaodong@163.com"}}
  private User data;

  public boolean isStatus() {
    return status;
  }

  public void setStatus(boolean status) {
    this.status = status;
  }

  public User getData() {
    return data;
  }

  public void setData(User data) {
    this.data = data;
  }

  @Override
  public String toString() {
    return "Info{" +
        "status=" + status +
        ", data=" + data +
        '}';
  }
}
