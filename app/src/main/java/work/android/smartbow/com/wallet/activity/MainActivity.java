/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package work.android.smartbow.com.wallet.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import work.android.smartbow.com.wallet.R;
import work.android.smartbow.com.wallet.utils.ThreeDRotateAnimation;
import work.android.smartbow.com.wallet.widget.StyleTextView;

public class MainActivity extends AppCompatActivity {


   ImageView coinImage;
   ImageView walletImage;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    coinImage = (ImageView)findViewById(R.id.coin_iv);
    walletImage = (ImageView)findViewById(R.id.wallet_iv);

    StyleTextView textView1 = (StyleTextView) findViewById(R.id.textView1);
    assert textView1 != null;

    StyleTextView textView2 = (StyleTextView) findViewById(R.id.textView2);
    assert textView2 != null;

    StyleTextView textView3 = (StyleTextView) findViewById(R.id.textView3);
    assert textView3 != null;


    int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 25, getResources()
        .getDisplayMetrics());

    int color = Color.parseColor("#737272");
    int size2 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 25, getResources
        ().getDisplayMetrics());

    int color2 = Color.parseColor("#737272");
    int size3 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 60, getResources
        ().getDisplayMetrics());

    int color3 = Color.parseColor("#737272");
    textView1.setText("60%\n主胜", new StyleTextView.TextStyle("60", size3, color3, false), new
        StyleTextView.TextStyle("%", size, color, true), new StyleTextView.TextStyle
        ("主胜", size2, color2, false));

    textView2.setText("20%\n平局", new StyleTextView.TextStyle("20", size3, color3, false), new
        StyleTextView.TextStyle("%", size, color, true), new StyleTextView.TextStyle
        ("平局", size2, color2, false));

    textView3.setText("20%\n客胜", new StyleTextView.TextStyle("20", size3, color3, false), new
        StyleTextView.TextStyle("%", size, color, true), new StyleTextView.TextStyle
        ("客胜", size2, color2, false));


  }

  private void startCoin(){
    Animation animationTranslate = AnimationUtils.loadAnimation(this,R.anim.anim1);
    ThreeDRotateAnimation animation = new ThreeDRotateAnimation();
    animation.setRepeatCount(10);
    AnimationSet animationSet = new AnimationSet(true);
    animationSet.setDuration(800);
    animationSet.addAnimation(animation);
    animationSet.addAnimation(animationTranslate);

    coinImage.startAnimation(animationSet);

  }

  private void getTextViewData(){

  }

  public void start(View view){
    startCoin();
    setWallet();
    getTextViewData();
  }

  private void setWallet(){
    final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0,1);
    valueAnimator.setDuration(800);
    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator animator) {
        float fraction = animator.getAnimatedFraction();
        if (fraction >=0.75){
          valueAnimator.cancel();
          startWallet();
        }
      }
    });

    valueAnimator.start();
  }

  private void startWallet(){
    ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(walletImage,"scaleX",1f,1.1f,0.9f,1f);
    objectAnimator.setDuration(600);
    ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(walletImage,"scaleY",1,0.75f,1.25f,1);
    objectAnimator1.setDuration(600);
    AnimatorSet animatorSet = new AnimatorSet();
    animatorSet.setInterpolator(new LinearInterpolator());
    animatorSet.playTogether(objectAnimator,objectAnimator1);
    animatorSet.start();
  }
}
