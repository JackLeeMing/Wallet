/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package work.android.smartbow.com.wallet.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;


/**
 * Created by HongJay on 2016/8/30.c
 */
public class WaterWaveView2 extends View {

    //存放圆环的集合
    private ArrayList<Wave> mList;

    //界面刷新
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            invalidate();
            return false;
        }
    });

    public WaterWaveView2(Context context) {
        this(context, null);
    }

    public WaterWaveView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        mList = new ArrayList<>();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mList.size() > 0) {
            drawRound(canvas);
        }
    }

    /**
     * 绘制圆环
     *
     * @param canvas ca
     */
    private void drawRound(Canvas canvas) {

        //对集合中的圆环对象循环绘制
        for (Wave wave : mList) {

            canvas.drawCircle(wave.x, wave.y, wave.radius, wave.paint);
            wave.radius+=3;
            int alpha=wave.paint.getAlpha();
            if(alpha<80){
                alpha=0;
            }else{
                alpha-=3;
            }

            //设置画笔宽度和透明度
            wave.paint.setStrokeWidth(wave.radius/8);
            wave.paint.setAlpha(alpha);

            //延迟发送
            mHandler.sendEmptyMessageDelayed(1,100);

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                float x = event.getX();
                float y = event.getY();
                deleteItem();
                Wave wave = new Wave(x, y);
                mList.add(wave);

                //刷新界面
                invalidate();
                break;

            case MotionEvent.ACTION_MOVE:
                float x1 = event.getX();
                float y1 = event.getY();
                deleteItem();
                Wave wave1 = new Wave(x1, y1);
                mList.add(wave1);

                invalidate();
                break;
        }
        //处理事件
        return true;
    }
    //删除透明度已经为0的圆环
    private void deleteItem(){
        for (int i = 0; i <mList.size() ; i++) {
            if(mList.get(i).paint.getAlpha()==0){
                mList.remove(i);
            }
        }
    }
}
