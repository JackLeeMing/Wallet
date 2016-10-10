/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package work.android.smartbow.com.wallet.http;

import android.util.Log;

public class HttpServerThreadManager extends Thread {
	private static final String TAG = "HttpServerThreadManager";
	
    /** By default, wake up once per 1 seconds.*/
    private final static int DEFAULT_SLEEP_TIME = 1000;

    private final HttpServerThreadPool mHttpServerThreadPool;
    private final int mSleepTime;
    
    private byte[] mLock = new byte[0];
    private boolean mForceExit = false;

    /** Constructs the manager, using the specified values.*/
    HttpServerThreadManager(HttpServerThreadPool threadPool, int sleepTime) {
        this.mHttpServerThreadPool = threadPool;
        this.mSleepTime = sleepTime;
    }

    /** Constructs the manager, using the specified pool and the
      * default sleep time.
      */
    HttpServerThreadManager(HttpServerThreadPool threadPool) {
        this(threadPool, DEFAULT_SLEEP_TIME);
    }
    
    public void stopHttpServerThreadManager(){
    	 synchronized( mLock ){
    		 mForceExit = true;
    	 }
    }

    /** Begin the infinite loop of sleeping and monitoring threads.*/
    public void run() {
    	
    	mForceExit = false;
    	
        while (true) {
            try {
                Thread.sleep(mSleepTime);
            } catch (InterruptedException e) {
                /* A thread called interrupt() on us, so we wake up early and
                 * begin execution as normal. Currently, no code interrupts
                 * ThreadManager, but this may occur in the future.*/
            }
            
            synchronized( mLock ){
            	if( !mForceExit ){
            		int idleThreads = mHttpServerThreadPool.getIdleThreadNum();
                    
                    if (idleThreads < Configuration.MIN_IDLE_THREAD_NUM ) {
                        // Spawn additional threads, as necessary
                        spawnThreads(Configuration.MIN_IDLE_THREAD_NUM - idleThreads);
                    } else if (idleThreads > Configuration.MAX_IDLE_THREAD_NUM) {
                        // Kill additional threads, as necessary
                        killThreads(idleThreads - Configuration.MAX_IDLE_THREAD_NUM);
                    }
            	}else{ //exit the Thread 
            		Log.i(TAG, "Thread is exited!");
            		mHttpServerThreadPool.stopAllThread();
            		return;
            	}
            		
            }
        }
    }

    /** Spawns the specified number of <code>ServerThread</code>s
      * and adds them to the <code>ServerPool</code.
      */
    private void spawnThreads(int num) {
        for (int i = 0; i < num; ++i) {
        	mHttpServerThreadPool.addThread();
        }
    }

    /** Kill the specified number of <code>ServerThread</code>s and
      * removes them from the <code>ServerPool</code>.
      */
    private void killThreads(int num) {

        for (int i = 0; i < num; ++i) {
        	mHttpServerThreadPool.removeThread();
        }
    }

}
