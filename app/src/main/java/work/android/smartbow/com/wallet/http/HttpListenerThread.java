/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package work.android.smartbow.com.wallet.http;

import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpListenerThread extends Thread {
	private static final String TAG = "HttpListenerThread";
	
    private final HttpServerThreadPool mHttpServerThreadPool;
    private final int mPort;
    private ServerSocket mServerSocket = null;
    
    private byte[] mLock = new byte[0];
    private boolean mForceExit = false;

    /** Constructs a new thread with the specified values.*/
    public HttpListenerThread(HttpServerThreadPool serverPool, int port) {
        this.mHttpServerThreadPool = serverPool;
        this.mPort = port;
    }
    
    public void stopHttpListenerThread(){
    	synchronized( mLock ){
  		     mForceExit = true;
  	    }
    	
    	if( mServerSocket != null && !mServerSocket.isClosed() ){
        	try {
				mServerSocket.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
   }

    /** Begins an infinite loop, accepting and dispatching incoming
      * connections.
      */
    public void run() {
    	mForceExit = false;
    	
        bindToPort();
            
        while( true ) {
        	
        	synchronized( mLock ){
            	if( mForceExit ){
            		 //exit the Thread 
            		Log.i(TAG, "Thread is exited!");            		
            		return;
            	}
        	}
            	
            if( mServerSocket != null && !mServerSocket.isClosed() ){
                listen();
    	    }
        }
    }

    /** Connects Tornado to a local system port. If an error is encountered,
      * Tornado aborts.
      */
    private void bindToPort() {
        try {
        	mServerSocket = new ServerSocket(mPort);
        	mServerSocket.setReuseAddress(true);
        } catch (IOException e) {
            // treat all I/O errors as fatal
            throw new RuntimeException(e.getMessage());
        }
    }

    /** Waits for, accepts and dispatches a single incoming connection.*/
    private void listen() {
        try {
            Socket clientConnection = mServerSocket.accept();
            
            Log.i(TAG,"listen() new socket connection");
            mHttpServerThreadPool.dispatch( clientConnection );
        } catch (IOException e) {
            e.printStackTrace();
            
            if( mServerSocket != null ) {
            	try {
					mServerSocket.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        }
    }

}
