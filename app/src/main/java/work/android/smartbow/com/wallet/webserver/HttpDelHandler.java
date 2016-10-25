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
import org.apache.http.HttpStatus;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HttpContext;

import java.io.File;
import java.io.IOException;

import work.android.smartbow.com.wallet.utils.TLog;

public class HttpDelHandler extends BaseHander {


	public HttpDelHandler(final String webRoot) {
		super(webRoot);
	}

	@Override
	public void handleLocale(HttpRequest request, HttpResponse response,HttpContext context) throws HttpException, IOException {


		target = target.substring(0, target.length() - WebServer.SUFFIX_DEL.length());

		final File file = new File(this.webRoot, target);
		deleteFile(file);
		// 回复客户端处理消息
		response.setStatusCode(HttpStatus.SC_OK);
		TLog.error("File: "+file.getAbsolutePath());

		StringEntity entity = new StringEntity(file.exists() ? "删除失败" : "删除成功","UTF-8");
		response.setEntity(entity);
	}

	/** 递归删除File */
	private void deleteFile(File file) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			if (null != files) {
				for (File f : files) {
					deleteFile(f);
				}
			}
			if (!HttpFileHandler.hasWfsDir(file)) {
				 boolean flag = file.delete();
				if (flag){
					TLog.error("delete");
				}
			}
		} else {
			if (!HttpFileHandler.hasWfsDir(file)) {
				boolean flag = file.delete();
				if (flag){
					TLog.error("delete");
				}
			}
		}
	}

}
