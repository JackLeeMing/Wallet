/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package work.android.smartbow.com.wallet.net;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;

import work.android.smartbow.com.wallet.utils.TLog;

/**
 * This file was created by hellomac on 2016/10/10.
 * name: Wallet.
 */

public class MyServer {
  private static final int PORT = 8080;
  public static void createAServer() throws IOException {
    ServerSocket serverSocket = new ServerSocket();
    Socket socket = serverSocket.accept();
    Reader reader = new InputStreamReader(socket.getInputStream());
    int len;
    char chars[] = new char[64];
    StringBuilder sb = new StringBuilder();
    while ((len=reader.read(chars)) !=-1){
      sb.append(new String(chars,0,len));
    }
    TLog.error("from client: "+sb);
    reader.close();
    socket.close();
    serverSocket.close();
  }

}
