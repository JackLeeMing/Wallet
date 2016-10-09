/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package work.android.smartbow.com.wallet.widget;

/**
 * This file was created by hellomac on 2016/10/9.
 * name: Wallet.
 */

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SVSView extends SurfaceView implements SurfaceHolder.Callback {
  Activity father;

  private static final int MAX_TOUCHPOINTS = 10;
  private static final String START_TEXT = "请随便触摸屏幕进行测试";
  private Paint textPaint = new Paint();
  private Paint touchPaints[] = new Paint[MAX_TOUCHPOINTS];
  private int colors[] = new int[MAX_TOUCHPOINTS];
  private int width, height;

  // private float scale = 1.0f;

  public SVSView(Activity father) {
    super(father);
    // TODO Auto-generated constructor stub
    this.father = father;
    SurfaceHolder holder = getHolder();
    holder.addCallback(this);
    setFocusable(true); // 确保我们的View能获得输入焦点
    setFocusableInTouchMode(true); // 确保能接收到触屏事件
    init();
  }

  private void init() {
    // 初始化10个不同颜色的画笔
    textPaint.setColor(Color.WHITE);
    colors[0] = Color.BLUE;
    colors[1] = Color.RED;
    colors[2] = Color.GREEN;
    colors[3] = Color.YELLOW;
    colors[4] = Color.CYAN;
    colors[5] = Color.MAGENTA;
    colors[6] = Color.DKGRAY;
    colors[7] = Color.WHITE;
    colors[8] = Color.LTGRAY;
    colors[9] = Color.GRAY;
    for (int i = 0; i < MAX_TOUCHPOINTS; i++) {
      touchPaints[i] = new Paint();
      touchPaints[i].setColor(colors[i]);
    }
  }

  /*
   * 处理触屏事件
   */
  @Override
  public boolean onTouchEvent(MotionEvent event) {
    // 获得屏幕触点数量
    int pointerCount = event.getPointerCount();
    if (pointerCount > MAX_TOUCHPOINTS) {
      pointerCount = MAX_TOUCHPOINTS;
    }
    // 锁定Canvas,开始进行相应的界面处理
    Canvas c = getHolder().lockCanvas();
    if (c != null) {
      c.drawColor(Color.BLACK);
      if (event.getAction() == MotionEvent.ACTION_UP) {
        // 当手离开屏幕时，清屏
      } else {
        // 先在屏幕上画一个十字，然后画一个圆
        for (int i = 0; i < pointerCount; i++) {
          // 获取一个触点的坐标，然后开始绘制
          int id = event.getPointerId(i);
          int x = (int) event.getX(i);
          int y = (int) event.getY(i);
          drawCrosshairsAndText(x, y, touchPaints[id], i, id, c);
        }
        for (int i = 0; i < pointerCount; i++) {
          int id = event.getPointerId(i);
          int x = (int) event.getX(i);
          int y = (int) event.getY(i);
          drawCircle(x, y, touchPaints[id], c);
        }
      }
      // 画完后，unlock
      getHolder().unlockCanvasAndPost(c);
    }
    return true;
  }

  /**
   * 画十字及坐标信息
   */
  private void drawCrosshairsAndText(int x, int y, Paint paint, int ptr,
                                     int id, Canvas c) {
    c.drawLine(0, y, width, y, paint);
    c.drawLine(x, 0, x, height, paint);
    int textY =  (5 + 20 * ptr);
    c.drawText("x" + ptr + "=" + x, 10, textY, textPaint);
    c.drawText("y" + ptr + "=" + y, 70, textY, textPaint);
    c.drawText("id" + ptr + "=" + id, width - 55, textY, textPaint);
  }

  /**
   * 画圆
   */
  private void drawCircle(int x, int y, Paint paint, Canvas c) {
    c.drawCircle(x, y, 40, paint);
  }

  @Override
  public void surfaceChanged(SurfaceHolder holder, int format, int width,
                             int height) {
    // TODO Auto-generated method stub
    this.width = width;
    this.height = height;
    textPaint.setTextSize(14);
    Canvas c = getHolder().lockCanvas();
    if (c != null) {
      // 背景黑色
      c.drawColor(Color.BLACK);
      float tWidth = textPaint.measureText(START_TEXT);
      c.drawText(START_TEXT, width / 2 - tWidth / 2, height / 2,
          textPaint);
      getHolder().unlockCanvasAndPost(c);
    }
  }

  @Override
  public void surfaceCreated(SurfaceHolder holder) {
    // TODO Auto-generated method stub

  }

  @Override
  public void surfaceDestroyed(SurfaceHolder holder) {
    // TODO Auto-generated method stub

  }
}
