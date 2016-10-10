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
import android.view.View;
import android.view.accessibility.AccessibilityEvent;

import work.android.smartbow.com.wallet.R;

/**
 * This file was created by hellomac on 2016/10/9.
 * name: Wallet.
 */

public class CompassView extends View  {

  private float bearing;

  public void setBearing(float _bearing) {
    bearing = _bearing;
    //sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_CLICKED);
  }

  public float getBearing() {
    return bearing;
  }


  private Paint markPaint;
  private Paint textPaint;
  private Paint circlePaint;
  private String northStr;
  private String westStr;
  private String eastStr;
  private String southStr;
  private int textHeight;

  public CompassView(Context context) {
    super(context);
    initCompassView();
  }

  public CompassView(Context context, AttributeSet attrs) {
    super(context, attrs);
    initCompassView();
  }

  public CompassView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initCompassView();
  }

  @TargetApi(21)
  public CompassView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    initCompassView();
  }

  private void initCompassView(){
     setFocusable(true);
    Context context = getContext();
    Resources resources;
    if (context != null){
      resources = context.getResources();
    }else{
      resources = Resources.getSystem();
    }

    circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    circlePaint.setColor(resources.getColor(R.color.compassView_bg_color));
    circlePaint.setStrokeWidth(1);
    circlePaint.setStyle(Paint.Style.FILL_AND_STROKE);

    northStr = resources.getString(R.string.compassView_cardinal_north);
    eastStr = resources.getString(R.string.compassView_cardinal_east);
    southStr = resources.getString(R.string.compassView_cardinal_south);
    westStr = resources.getString(R.string.compassView_cardinal_west);

    textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    textPaint.setColor(resources.getColor(R.color.compassView_text_color));

    textHeight = (int)textPaint.measureText("yY");

    markPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    markPaint.setColor(resources.getColor(R.color.compassView_marker_color));


  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    int measureWidth  = measure(widthMeasureSpec) ;
    int measureHeight = measure(heightMeasureSpec);
    int d = Math.min(measureHeight,measureWidth);
    setMeasuredDimension(d,d);
  }

  private int measure(int measureSpec){
    int result;
    int specMode = MeasureSpec.getMode(measureSpec);
    int size = MeasureSpec.getSize(measureSpec);
    if (specMode == MeasureSpec.UNSPECIFIED){
      result = 200;
    }else{
      result = size;
    }
    return result;
  }


  @Override
  protected void onDraw(Canvas canvas) {
    //super.onDraw(canvas);

    int mMeasureWidth = getMeasuredWidth();
    int mMeasureHeight = getMeasuredHeight();

    int px = mMeasureWidth/2;
    int py = mMeasureHeight/2;

    int radius = Math.min(px,py);

    canvas.drawCircle(px,py,radius,circlePaint);

    canvas.save();
    canvas.rotate(-bearing,px,py);

    int textWidth = (int)textPaint.measureText("W");
    int cardinalX = px - textWidth/2;
    int cardinalY = py - radius +textHeight;

    for (int i = 0; i < 24 ; i++) {
      canvas.drawLine(px,py-radius,px,py-radius+10,markPaint);
      canvas.save();
      canvas.translate(0,textHeight);

      if (i%6 == 0){
        String dirString= "";
        switch (i){
          case 0:
            dirString = northStr;
            int arrowY = 2*textHeight;
            canvas.drawLine(px,arrowY,px-5,3*textHeight,markPaint);
            canvas.drawLine(px,arrowY,px+5,3*textHeight,markPaint);
            break;

          case 6: dirString = eastStr; break;
          case 12: dirString = southStr;break;
          case 18:dirString = westStr;break;
        }

        canvas.drawText(dirString,cardinalX,cardinalY,textPaint);
      }else if(i%3 == 0){
          String angle = String.valueOf(i*15);
        float angleTextWidth = textPaint.measureText(angle);
        int angleTextX = (int)(px - angleTextWidth/2);
        int angleTextY =  py-radius+textHeight;

        canvas.drawText(angle,angleTextX,angleTextY,textPaint);
      }
      canvas.restore();
      canvas.rotate(15,px,py);

    }
    canvas.restore();
  }

  @Override
  public boolean dispatchPopulateAccessibilityEvent(final AccessibilityEvent event) {
    super.dispatchPopulateAccessibilityEvent(event);
    if (isShown()) {
      String bearingStr = String.valueOf(bearing);
      if (bearingStr.length() > AccessibilityEvent.MAX_TEXT_LENGTH)
        bearingStr = bearingStr.substring(0, AccessibilityEvent.MAX_TEXT_LENGTH);

      event.getText().add(bearingStr);
      return true;
    }
    else
      return false;
  }
}
