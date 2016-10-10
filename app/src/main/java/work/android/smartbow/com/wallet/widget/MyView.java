/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package work.android.smartbow.com.wallet.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;

import work.android.smartbow.com.wallet.R;

/**
 * This file was created by hellomac on 2016/10/9.
 * name: Wallet.
 */

public class MyView extends View {

  private int unit = TypedValue.COMPLEX_UNIT_SP;

  //AccessibilityEventSource

  public MyView(Context context) {
    super(context);
    init(context,null,0);
  }

  public MyView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context,attrs,0);

  }

  public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context,attrs,defStyleAttr);
  }

  @TargetApi(21)
  public MyView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init(context,attrs,defStyleAttr);
  }

  private void init(Context context,AttributeSet attrs,int defStyle){

    if (attrs != null){
      TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.MyView,defStyle,0);
       //attrs.getAttributeIntValue(R.styleable.MyView_unit_mode,TypedValue.COMPLEX_UNIT_DIP);
      unit = array.getInteger(R.styleable.MyView_unit_mode,TypedValue.COMPLEX_UNIT_DIP);
      array.recycle();
    }else{
      unit  = TypedValue.COMPLEX_UNIT_DIP;
    }

  }

  private void init2(Context context,AttributeSet attrs,int defStyle){

    if (attrs != null){
     unit =  attrs.getAttributeIntValue(R.styleable.MyView_unit_mode,TypedValue.COMPLEX_UNIT_DIP);
    }else{
      unit  = TypedValue.COMPLEX_UNIT_DIP;
    }

  }


  //空间测量
  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    int measureHeight = measureHeight(heightMeasureSpec);
    int measureWidth = measureWidth(widthMeasureSpec);

    setMeasuredDimension(measureWidth,measureHeight);
  }

  //测量控件的高度 根据控件内容来测定
  private int measureHeight(int heightMeasureSpec){
    int specMode = MeasureSpec.getMode(heightMeasureSpec);
    int size = MeasureSpec.getSize(heightMeasureSpec);


    switch (specMode){
      case MeasureSpec.EXACTLY:

        return size;
      case MeasureSpec.AT_MOST:
        return (int) getRawSize(unit,48);
      case MeasureSpec.UNSPECIFIED:
        return (int) getRawSize(unit,48);
    }

    return (int) getRawSize(unit,48);
  }



  //测量控件的宽度 根据控件内容来测定
  private int measureWidth(int widthMeasureSpec){
    int specMode = MeasureSpec.getMode(widthMeasureSpec);
    int size = MeasureSpec.getSize(widthMeasureSpec);


    switch (specMode){
      case MeasureSpec.EXACTLY:

        return size;
      case MeasureSpec.AT_MOST:
        return (int) getRawSize(unit,48);
      case MeasureSpec.UNSPECIFIED:
        return (int) getRawSize(unit,48);
    }

    return (int) getRawSize(unit,48);
  }


  //控件绘制 包含自定义绘制
  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    //

    canvas.drawColor(Color.YELLOW);

  }

  private float getRawSize(int unit, float size) {
    Context context = getContext();
    Resources resources;
    if (context == null) {
      resources = Resources.getSystem();//TODO
    } else {
      resources = context.getResources();
    }
    return TypedValue.applyDimension(unit, size, resources.getDisplayMetrics());
  }

  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {//如果得到处理 返回 true
    return super.onKeyDown(keyCode, event);
  }

  @Override
  public boolean onKeyUp(int keyCode, KeyEvent event) {//如果得到处理 返回true
    return super.onKeyUp(keyCode, event);
  }

  @Override
  public boolean onKeyLongPress(int keyCode, KeyEvent event) {
    return super.onKeyLongPress(keyCode, event);
  }

  @Override
  public boolean onKeyShortcut(int keyCode, KeyEvent event) {
    return super.onKeyShortcut(keyCode, event);
  }

  @Override
  public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
    return super.onKeyMultiple(keyCode, repeatCount, event);
  }

  @Override
  public void setOnKeyListener(OnKeyListener l) {
    super.setOnKeyListener(l);
  }

  @Override
  public boolean onKeyPreIme(int keyCode, KeyEvent event) {
    return super.onKeyPreIme(keyCode, event);
  }

  @Override
  public boolean onTrackballEvent(MotionEvent event) {
    return super.onTrackballEvent(event);
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    return super.onTouchEvent(event);
  }

  public void setUnit(int unit){
    this.unit  = unit;
    sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED);
  }


  //定制可访问事件属性
  @Override
  public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
    super.dispatchPopulateAccessibilityEvent(event);

    return super.dispatchPopulateAccessibilityEvent(event);
  }


}
