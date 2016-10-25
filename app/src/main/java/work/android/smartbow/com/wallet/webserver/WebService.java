/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package work.android.smartbow.com.wallet.webserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class WebService extends Service {

	public static final int PORT = 7766;
	public static final String WEBROOT = "/";

	private WebServer webServer;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		webServer = new WebServer(PORT, WEBROOT);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		webServer.setDaemon(true);//守护线程
		webServer.start();
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		webServer.close();
		super.onDestroy();
	}

}
