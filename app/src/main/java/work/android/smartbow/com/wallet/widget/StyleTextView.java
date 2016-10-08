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
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.widget.TextView;

import java.io.Serializable;

import work.android.smartbow.com.wallet.utils.TLog;

/**
 * This file was created by hellomac on 16/9/14.
 * name: Wallet.
 */
public class StyleTextView extends TextView {

  public StyleTextView(Context context) {
    super(context);
    initView();
  }

  public StyleTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
    initView();
  }

  public StyleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initView();
  }

  @TargetApi(21)
  public StyleTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    initView();
  }

  private void initView(){


  }


  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    if (first != null){

      Paint mPaint = getPaint();

      mPaint.setTextSize(first.size);
      float startX = mPaint.measureText(first.content);
      float space = space(mPaint);

      //画顶部基准线
      float lineY = mPaint.getFontMetrics().ascent - mPaint.getFontMetrics().top + mPaint
          .getFontMetrics().descent - mPaint.getFontMetrics().ascent - mPaint
          .getTextSize()+4;
      mPaint.setColor(Color.parseColor("#FFFFFF"));
      canvas.drawLine(0, lineY, getWidth(), lineY, mPaint);

      mPaint.setTextSize(second.size);
      //startX = getWidth()/2.0f +(startX+mPaint.measureText(second.content))/2.0f-mPaint.measureText(second.content);

      startX = getWidth()/2 + startX/2 - mPaint.measureText(second.content)*0.6f;


      //float baseLine = space + -mPaint.getFontMetrics().top-space(mPaint);
      //space  -mPaint.getFontMetrics().descent  + mPaint.getTextSize()
      float b = space + mPaint.getTextSize() - mPaint.getFontMetrics().descent+4;
      mPaint.setColor(second.color);
      canvas.drawText(second.content,startX,b,mPaint);
      TLog.error("width: " + getWidth() + "  height: " + getHeight() + " startY: " +
          startX + "  baseLine: " + b);

      mPaint.setColor(Color.parseColor("#FFFFFF"));
      canvas.drawLine(startX, 0, startX, getHeight(), mPaint);

    }

  }

  private float space(Paint mPaint){
    return mPaint.getFontMetrics().descent - mPaint.getFontMetrics().top - mPaint.getTextSize();
  }

  String content;
  TextStyle first;
  TextStyle second;
  public void setText(String content,TextStyle ...styles){

    this.content = content;
    SpannableString spannableString = new SpannableString(content);
    int i=0;
    for (TextStyle textStyle :styles){
      if (i==0){
        first = textStyle;
      }else if(i == 1){
        second = textStyle;
      }
      i++;
      int start= content.indexOf(textStyle.content);
      int end = start + textStyle.content.length();
      if (textStyle.size != -1){
        spannableString.setSpan(new AbsoluteSizeSpan(textStyle.size),start,end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
      }

      if (textStyle.color != -1){
        spannableString.setSpan(new ForegroundColorSpan(textStyle.color),start,end,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
      }

      if (textStyle.up){
        spannableString.setSpan(new ForegroundColorSpan(Color.TRANSPARENT),start,end,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
      }
    }

    setText(spannableString);

  }

  public static class TextStyle implements Serializable{
    String content;
    int size;
    int color;
    boolean up = false;

    public TextStyle() {}

    public TextStyle(String content, int size, int color, boolean up) {
      this.content = content;
      this.size = size;
      this.color = color;
      this.up = up;
    }
  }


}
