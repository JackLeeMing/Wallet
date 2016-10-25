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
import org.apache.http.protocol.HttpRequestHandler;

import java.io.IOException;
import java.net.URLDecoder;

import work.android.smartbow.com.wallet.utils.TLog;

/**
 * This file was created by hellomac on 2016/10/10.
 * name: Wallet.
 */

public abstract class BaseHander implements HttpRequestHandler {


  protected String webRoot;
  protected String target;

  public BaseHander(final String webRoot) {
    this.webRoot = webRoot;
  }
  @Override
  public void handle(HttpRequest request, HttpResponse response, HttpContext context) throws HttpException, IOException {
    //String target = request.getRequestLine().getUri();
    target = URLDecoder.decode(request.getRequestLine().getUri(),"UTF-8");
    TLog.error("target: "+target);
    TLog.error("webRoot: "+webRoot);

    handleLocale(request,response,context);
  }

  protected abstract void handleLocale(HttpRequest request, HttpResponse response, HttpContext context) throws IOException,HttpException;
}
