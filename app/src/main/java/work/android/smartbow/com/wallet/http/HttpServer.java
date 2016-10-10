/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package work.android.smartbow.com.wallet.http;

import android.content.Context;

public class HttpServer {
	private static final String TAG = "HttpServer";
	
	private static HttpServer mInstance = null;
	
	private Context mContext = null;
	
	private HttpServerThreadPool mHttpServerThreadPool = null;
	
	private HttpServerThreadManager mHttpServerThreadManager = null;
	
	private HttpListenerThread mHttpListenerThread = null;
	
	private HttpServer(){        

	}
	
	public static HttpServer getInstance(){
		if( mInstance == null )
			mInstance = new HttpServer();
		return mInstance;
	}
	
	public void init(Context context){
		mContext = context;
		
		Configuration.init( null/*mContext.getResources().openRawResource(R.raw.mime_types)*/ );
		
		mHttpServerThreadPool = new HttpServerThreadPool( Configuration.START_THREAD_NUM );
		mHttpServerThreadManager = new HttpServerThreadManager( mHttpServerThreadPool );
		
		mHttpListenerThread = new HttpListenerThread(mHttpServerThreadPool, Configuration.DEFAULT_HTTP_PORT);
	}
	
	public void startServer(){
        //start Http Listener Thread
		mHttpListenerThread.start();

		//start server thread manager
		mHttpServerThreadManager.start();
	}
	
	public void stopServer(){
		if( mHttpListenerThread != null )
			mHttpListenerThread.stopHttpListenerThread();
		
		if( mHttpServerThreadManager != null )
			mHttpServerThreadManager.stopHttpServerThreadManager();
	}
}
