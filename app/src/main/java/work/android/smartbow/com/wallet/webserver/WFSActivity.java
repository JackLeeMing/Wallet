/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package work.android.smartbow.com.wallet.webserver;

import android.app.Activity;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import work.android.smartbow.com.wallet.R;
import work.android.smartbow.com.wallet.utils.CopyUtil;
import work.android.smartbow.com.wallet.utils.TLog;

public class WFSActivity extends Activity implements OnCheckedChangeListener {

	private ToggleButton toggleBtn;
	private TextView urlText;

	private Intent intent;

	WebView webView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_server);
		initViews();
		initFiles();

		intent = new Intent(this, WebService.class);

		webView = (WebView)findViewById(R.id.web_WFS_webView);
		webView.getSettings().setJavaScriptEnabled(true);

		webView.setWebViewClient(new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
				TLog.error("request: "+request.toString());
				return false;
			}

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				TLog.error("URL: "+url);
				return false;
			}
		});
	}

	private void initViews() {
		toggleBtn = (ToggleButton) findViewById(R.id.toggleBtn);
		toggleBtn.setOnCheckedChangeListener(this);
		urlText = (TextView) findViewById(R.id.urlText);
	}

	private void initFiles() {
		new CopyUtil(this).assetsCopy();
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked) {
			String ip = getIpAddress();
			if (ip == null) {
				Toast.makeText(this, R.string.msg_net_off, Toast.LENGTH_SHORT)
						.show();
				urlText.setText("");
			} else {
				startService(intent);
				String url = "http://" + ip + ":" + WebService.PORT + "/";
				urlText.setText(url);

				TLog.error(url);
				TLog.error(getLocalIpAddress());

			}
		} else {
			stopService(intent);
			urlText.setText("");
		}
	}

	/** 获取当前IP地址 */
	private String getLocalIpAddress() {
		try {
			// 遍历网络接口
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				// 遍历IP地址
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					// 非回传地址时返回
					if (!inetAddress.isLoopbackAddress()) {
						return inetAddress.getHostAddress();
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void onBackPressed() {
		if (intent != null) {
			stopService(intent);
		}
		super.onBackPressed();
	}


	public String getIpAddress(){
		WifiManager wifiManager = (WifiManager)getSystemService(WIFI_SERVICE);
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
     int ipAddress = wifiInfo.getIpAddress();
		int[] ipAddr = new int[4];
		ipAddr[0] = ipAddress & 0xFF;
		ipAddr[1] = (ipAddress>>8)& 0xFF;
		ipAddr[2] = (ipAddress >>16) & 0xFF;
		ipAddr[3] = (ipAddress >>24) & 0xFF;
		return new StringBuilder().append(ipAddr[0]).append(".")
				.append(ipAddr[1]).append(".").append(ipAddr[2]).append(".")
				.append(ipAddr[3]).toString();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	public void start(View view){
		String ip = getIpAddress();
		String url = "http://"+ip + ":" + WebService.PORT+"/sdcard/";

		webView.loadUrl(url);

	}

}