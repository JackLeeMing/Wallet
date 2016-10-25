/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package work.android.smartbow.com.wallet.webserver;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;

/**
 * This file was created by hellomac on 2016/10/10.
 * name: Wallet.
 */

public class IndexHandler extends BaseHander {
  public IndexHandler(String webRoot) {
    super(webRoot);
  }

  @Override
  protected void handleLocale(HttpRequest request, HttpResponse response, HttpContext context) throws IOException, HttpException {

  }
}
