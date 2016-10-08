/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package work.android.smartbow.com.wallet.bean;

import java.io.Serializable;

/**
 * This file was created by hellomac on 2016/9/28.
 * name: Wallet.
 */
 public class Course implements Serializable {
  private String name;
  private String time;
  private float code;

  public Course(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public float getCode() {
    return code;
  }

  public void setCode(float code) {
    this.code = code;
  }

  @Override
  public String toString() {
    return "Course{" +
        "name='" + name + '\'' +
        '}';
  }
}
