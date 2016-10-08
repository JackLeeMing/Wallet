/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package work.android.smartbow.com.wallet.bean;

import java.io.Serializable;
import java.util.List;

/**
 * This file was created by hellomac on 2016/9/28.
 * name: Wallet.
 */

public class Student implements Serializable {
  private String name;
  private String numberID;
  private List<Course> courses;

  public Student(String name, String numberID) {
    this.name = name;
    this.numberID = numberID;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getNumberID() {
    return numberID;
  }

  public void setNumberID(String numberID) {
    this.numberID = numberID;
  }

  public List<Course> getCourses() {
    return courses;
  }

  public void setCourses(List<Course> courses) {
    this.courses = courses;
  }

  @Override
  public String toString() {
    return "Student{" +
        "name='" + name + '\'' +
        ", numberID='" + numberID + '\'' +
        ", courses=" + courses +
        '}';
  }
}
