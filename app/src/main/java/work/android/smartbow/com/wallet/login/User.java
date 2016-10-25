/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package work.android.smartbow.com.wallet.login;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * This file was created by hellomac on 2016/10/18.
 * name: Wallet.
 */

public class User implements Serializable {
  private String nickname;

  @SerializedName("_id")
  private String id;

  private String email;
  private String username;

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Override
  public String toString() {
    return "User{" +
        "nickname='" + nickname + '\'' +
        ", id='" + id + '\'' +
        ", email='" + email + '\'' +
        ", username='" + username + '\'' +
        '}';
  }
}
