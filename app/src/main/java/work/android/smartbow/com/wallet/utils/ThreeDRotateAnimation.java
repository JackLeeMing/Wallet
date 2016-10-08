/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package work.android.smartbow.com.wallet.utils;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

/**
 * This file was created by hellomac on 16/9/13.
 * name: Wallet.
 */
public class ThreeDRotateAnimation extends Animation {
  int centerX,centerY;
  Camera camera = new Camera();

  @Override
  public void initialize(int width, int height, int parentWidth, int parentHeight) {
    super.initialize(width, height, parentWidth, parentHeight);

    centerX = width/2;
    centerY = height/2;
    setDuration(500);
    setInterpolator(new LinearInterpolator());
    Log.e("TAG","wi:"+width+" hei:"+height+" pwi:"+parentWidth+" phe:"+parentHeight);
  }

  @Override
  protected void applyTransformation(float interpolatedTime, Transformation t) {
    super.applyTransformation(interpolatedTime, t);
    final Matrix matrix = t.getMatrix();
    camera.save();
    camera.rotateY(360*interpolatedTime);
    camera.getMatrix(matrix);
    matrix.setScale(2*interpolatedTime,2*interpolatedTime);
    matrix.preTranslate(-centerX,-centerX);
    matrix.postTranslate(centerX,centerY);
    camera.restore();
  }
}
