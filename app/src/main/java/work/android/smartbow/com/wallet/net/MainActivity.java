/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package work.android.smartbow.com.wallet.net;

import android.app.Activity;
import android.os.Bundle;

import work.android.smartbow.com.wallet.R;
import work.android.smartbow.com.wallet.http.HttpServer;
import work.android.smartbow.com.wallet.utils.TLog;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		NanoHTTPD nanoHttpd = new MyNanoHTTPD("127.0.0.1",23456);//
		//http://127.0.0.1:23456
		try{
			nanoHttpd.start();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		TLog.error(nanoHttpd.isAlive()+"");

		initHttp();
		
	}

	private void initHttp(){
		HttpServer server = HttpServer.getInstance();
		server.init(this);
		HttpServer.getInstance().startServer();



	}

	@Override
	public void onStop(){
		super.onStop();
		HttpServer.getInstance().stopServer();
	}

}


class MyNanoHTTPD extends NanoHTTPD{

	public MyNanoHTTPD(int port) {
		super(port);
	}
	
	public MyNanoHTTPD(String hostName,int port){
		super(hostName,port);
	}
	
	 public Response serve(IHTTPSession session) { 
		 Method method = session.getMethod();
		 TLog.error("Method:"+method.toString());
		 if(NanoHTTPD.Method.GET.equals(method)){
			 String queryParams = session.getQueryParameterString();
			 TLog.error("params:"+queryParams);
		 }else if(NanoHTTPD.Method.POST.equals(method)){
		 }
		 return super.serve(session);
	 }
	
	
}
