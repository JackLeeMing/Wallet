/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package work.android.smartbow.com.wallet.http;

import java.net.Socket;
import java.util.ArrayList;

public class HttpServerThreadPool {
	private static final String TAG = "HttpServerThreadPool";
	
    private ArrayList<Socket> mClientConnectionPool;
    private final int mStartThreadNum;
    private int mBusyThreadNum = 0;
    private ThreadGroup mServerThreadGroup = new ThreadGroup("http-servers");
    
    private byte[] mLock = new byte[0];
    private byte[] mLock2 = new byte[0];

    /** Constructs a ServerPool with <b>10</b> initial threads.*/
    public HttpServerThreadPool() {
        this(10);
    }

    /** Constructs a ServerPool with the specified number of initial threads.*/
    public HttpServerThreadPool(int startThreads) {
        this.mStartThreadNum = startThreads;
        /* Set the size of the taskPool to one third the initial number
         * of threads. This should be tuned.*/
        mClientConnectionPool = new ArrayList<Socket>((int)(startThreads / 3));
        // Fill the pool with threads
        for (int i = 0; i < startThreads; ++i) {
            addThread();
        }
    }
    
    public boolean isEmpty(){
    	boolean empty = false;
    	
    	synchronized( mLock ){
    		empty = mClientConnectionPool.isEmpty();
    	}
    	
    	return empty;
    }
    
    public Socket removeFirstClientConnection(){
    	Socket client = null;
    	
    	synchronized( mLock ){
    		if( !mClientConnectionPool.isEmpty() )
    			client = mClientConnectionPool.remove(0);
    	}
    	
    	return client;
    }

    /** Creates a new <code>ServerThread</code> and adds it to the pool.
      * This method should be used whenever a <code>ServerThread</code>
      * needs to be created -- constructing by hand is discouraged.
      */
    public void addThread() {
        HttpServerThread t = new HttpServerThread(mServerThreadGroup, this);
        t.start();
    }

    /** Removes a <code>ServerThread</code> from the pool. The thread will
      * stop serving connections and be immediately killed. This is
      * synchronized so that we never try to kill the same thread twice --
      * although that should never occur currently.
      */
    public synchronized void removeThread() {
    	HttpServerThread[] threadList = new HttpServerThread[getAllThreadNum()];
        mServerThreadGroup.enumerate(threadList);
        //threadList[0].interrupt();
        threadList[0].stopHttpServerThread();
    }
    
    public void stopAllThread(){
    	HttpServerThread[] threadList = new HttpServerThread[getAllThreadNum()];
        mServerThreadGroup.enumerate( threadList );
        for( HttpServerThread thread : threadList ){
            //threadList[0].interrupt();
        	thread.stopHttpServerThread();
        }
    }

    /** Dispatchs an incoming connection to a <code>ServerThread</code>.
      * This method is internally synchronized, so it should be safe to
      * call this concurrently from multiple threads.
      */
    public void dispatch(Socket clientConnection) {
    	if( clientConnection == null )
    		return;
    	
    	synchronized( mLock ){
        	mClientConnectionPool.add( clientConnection );
        }
    	
    	synchronized( this ){
    	    /* Wake up one of the threads that is waiting for a connection.
             * We can use notify() because all threads are identical.
             * 
             */
    	    this.notify();
    	}
    	
    	incrementBusyThreadNum();
    }

    /** Returns the number of threads initially started.*/
    public int getStartThreadNum() {
        return mStartThreadNum;
    }

    /** Returns the total number of threads.*/
    public int getAllThreadNum() {
        return mServerThreadGroup.activeCount();
    }

    /** Returns the number of threads currently not servicing a client.*/
    public int getIdleThreadNum() {
        return (getAllThreadNum() - getBusyThreadNum());
    }

    /** Returns the number of threads currently servicing a client.*/
    public int getBusyThreadNum() {
    	int num = 0;
    	
    	synchronized( mLock2 ){
            num = mBusyThreadNum;
    	}
    	
    	return num;
    }

    /** Notifies the pool that a thread is busy.*/
    public synchronized void incrementBusyThreadNum() {
    	synchronized( mLock2 ){
            ++mBusyThreadNum;
    	}
    }

    /** Notifies the pool that a thread is no longer busy.*/
    public synchronized void decrementBusyThreadNum() {
    	synchronized( mLock2 ){
            --mBusyThreadNum;
    	}
    }

}
