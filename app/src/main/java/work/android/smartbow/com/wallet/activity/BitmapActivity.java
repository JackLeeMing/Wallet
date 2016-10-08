/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package work.android.smartbow.com.wallet.activity;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import work.android.smartbow.com.wallet.R;

public class BitmapActivity extends AppCompatActivity {


  ImageView imageView;
  TextView textView;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_bitmap);

    imageView = (ImageView)findViewById(R.id.imageView);
    textView = (TextView)findViewById(R.id.textView);

    StringBuilder builder = new StringBuilder("bitmap size: ");
    Bitmap bitmap0 = BitmapFactory.decodeResource(getResources(),R.mipmap.pic1);
    Bitmap bitmap1= BitmapFactory.decodeResource(getResources(),R.mipmap.pic2);
    Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(),R.mipmap.pic3);
    Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(),R.mipmap.pic4);
    Bitmap bitmap4 = BitmapFactory.decodeResource(getResources(),R.mipmap.icon_scan);
    /*
ldpi (low) ~120dpi
mdpi (medium) ~160dpi
hdpi (high) ~240dpi
xhdpi (extra-high) ~320dpi
xxhdpi (extra-extra-high) ~480dpi
xxxhdpi (extra-extra-extra-high) ~640dpi
    *
    * */

    if (bitmap0 != null){
      builder.append("hdpi:").append(bitmap0.getByteCount()/1024).append("--").append(240/getResources().getDisplayMetrics().densityDpi).append("\n");
    }
    if (bitmap1 != null){
      builder.append("mdpi:").append(bitmap1.getByteCount()/1024).append("--").append(160/getResources().getDisplayMetrics().densityDpi).append("\n");
    }
    if (bitmap2 != null){
      builder.append("xhdpi:").append(bitmap2.getByteCount()/1024).append("--").append(320/getResources().getDisplayMetrics().densityDpi).append("\n");
    }
    if (bitmap3 != null){
      builder.append("xxhdpi:").append(bitmap3.getByteCount()/1024).append("--").append(480/getResources().getDisplayMetrics().densityDpi).append("\n");
    }

    if (bitmap4 != null){
      builder.append("xxxhdpi:").append(bitmap4.getByteCount()).append("--").append(640/getResources().getDisplayMetrics().densityDpi).append("\n");
    }

    builder.append("--").append(getResources().getDisplayMetrics().densityDpi).append("\n");
    builder.append("---density:").append(getResources().getDisplayMetrics().density);//1063X827  320  880992B  640 320

    //1063X827X4 = 3526404   880992  (1063X0.5)X(827X0.5)  inDensity = xxxhdpi(640)  targetDensity = getResources().getDisplayMetrics().densityDpi
    //scale = targetDensity/inDensity  = 320/640 = 0.5
    // 879101 != 880992  //  (1063/2 +0.5)*(827/2+0.5)X4 = 532X414=220248X4 = 880992
    //96X96X4/4 = 9216 9409 9216
    //大图小用 采样 小图大用矩阵

    textView.setText(builder.toString());
    imageView.setImageBitmap(bitmap0);

    Bitmap bitmap5 = BitmapFactory.decodeResource(getResources(),R.mipmap.pic5);

    Matrix matrix = new Matrix();
    matrix.preScale(0.5f,0.5f,0,0);

    imageView.setImageMatrix(matrix);
    imageView.setScaleType(ImageView.ScaleType.MATRIX);
    imageView.setImageBitmap(bitmap5);
    //处理滚动

    String s = getString(R.string.complexStr);
    String str = String.format(s,1);
    CharSequence sequence = Html.fromHtml(str);
    textView.setText(sequence);

    Resources resources = getResources();
    String stss = resources.getQuantityString(R.plurals.plurals,2,2);
    textView.setText(stss);

  }
}
