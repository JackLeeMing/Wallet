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
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

import work.android.smartbow.com.wallet.R;

/**
 * This file was created by hellomac on 16/9/14.
 * name: Wallet.
 */
public class TTView extends TextView {


  private Paint marginPaint;
  private Paint linePaint;
  private int paperColor;
  private float margin;
  public TTView(Context context) {
    super(context);
    init();

  }

  public TTView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public TTView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  @TargetApi(21)
  public TTView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init();
  }

  private void init(){
    Resources resources = getResources();
     marginPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    marginPaint.setColor(resources.getColor(R.color.notepad_margin));
    linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    linePaint.setColor(resources.getColor(R.color.notepad_lines));

    linePaint.setStrokeWidth(2);

    paperColor = resources.getColor(R.color.notepad_paper);
    margin = resources.getDimension(R.dimen.notepad_margin);
    setPadding((int) margin+8,0,0,0);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    canvas.drawColor(paperColor);
    canvas.drawLine(0,0,0,getMeasuredHeight(),linePaint);
    canvas.drawLine(0,getMeasuredHeight(),getMeasuredWidth(),getMeasuredHeight(),linePaint);

    canvas.drawLine(margin,0,margin,getMeasuredHeight(),marginPaint);
    canvas.save();
    //canvas.translate(margin,0);

    super.onDraw(canvas);

    canvas.restore();
  }
}
