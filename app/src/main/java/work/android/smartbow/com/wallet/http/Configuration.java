/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package work.android.smartbow.com.wallet.http;

import java.io.InputStream;

public class Configuration {
	private static final String TAG = "Configuration";
	
	public static final int DEFAULT_HTTP_PORT = 9905;
	
	public static final int START_THREAD_NUM = 10;
	public static final int MAX_THREAD_COUNT = 30;
	public static final int MIN_IDLE_THREAD_NUM = 5;
	public static final int MAX_IDLE_THREAD_NUM = 10;
	
	public static MimeType mMimeType = null;
	
	public Configuration() {
		
	}
	
	public static void init(InputStream mimeTypesInputStream){
		if( mimeTypesInputStream != null )
			mMimeType = new MimeType( mimeTypesInputStream );
	}
}
