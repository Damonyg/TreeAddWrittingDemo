package com.example.treeaddwritedemo.httpconnect;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.example.treeaddwritedemo.global.UrlContent;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

public class NetRequestTask implements Runnable {

	private static final int BITMAP_READY = 0;
	private boolean cancelled;
	private OnCompleteHandler onCompleteHandler;

	private String mUrl;
	private Map<String, String> mParams;// 请求参数
	private int METHOD = 0;// 0-GET 1-POST 2-PUT
	private static final int LOADING_THREADS = 4;
	private static ExecutorService threadPool = Executors
			.newFixedThreadPool(LOADING_THREADS);

	private NetRequestTask(final String mUrl, Map<String, String> mParams2,
			int method) {
		this.mUrl = mUrl;
		this.mParams = mParams2;
		this.METHOD = method;
		threadPool.execute(this);
	}

	public static NetRequestTask get(String mUrl, Map<String, String> mParams) {
		return new NetRequestTask(mUrl, mParams, 0);
	}

	public static NetRequestTask post(String mUrl, Map<String, String> mParams2) {
		return new NetRequestTask(mUrl, mParams2, 1);
	}
	
	public static NetRequestTask put(String mUrl, Map<String, String> mParams2) {
		return new NetRequestTask(mUrl, mParams2, 2);
	}

	@Override
	public void run() {
		if (!TextUtils.isEmpty(mUrl)) {
			try {
				if (METHOD == 0) {
					HttpRequest request = HttpRequest.get(mUrl).headerKey(UrlContent.HTTP_HEADER_KEY);
					request.acceptGzipEncoding().uncompress(true);// 设置gzip
					if (request.ok()) {
						
						complete(request.body());
					}
				} else  if(METHOD == 1){
					HttpRequest request = HttpRequest.post(mUrl).headerKey(UrlContent.HTTP_HEADER_KEY);
					request.acceptGzipEncoding().uncompress(true);// 设置gzip
					request.form(mParams);
					if (request.ok()) {
						complete(request.body());
					}
				}else  if(METHOD == 2){
					HttpRequest request = HttpRequest.put(mUrl).headerKey(UrlContent.HTTP_HEADER_KEY);
					request.acceptGzipEncoding().uncompress(true);// 设置gzip
					request.form(mParams);
					if (request.ok()) {
						complete(request.body());
					}
				}
			} catch (Exception e) {
				complete("errNet");
				e.printStackTrace();
			}
		}
	}

	public void setOnCompleteHandler(OnCompleteHandler handler) {
		onCompleteHandler = handler;
	}

	public void cancel() {
		cancelled = true;
	}

	public void complete(final String mUrl) {
		if (onCompleteHandler != null && !cancelled) {
			Message message = onCompleteHandler.obtainMessage(BITMAP_READY,
					mUrl);
			onCompleteHandler.sendMessage(message);
		}
	}

	public static abstract class OnCompleteHandler extends Handler {

		@Override
		public void handleMessage(final Message message) {
			String mJson = (String) message.obj;
			onComplete(mJson);
		}

		public abstract void onComplete(final String mJson);

	}

	public abstract static class OnCompleteListener {
		public abstract void onComplete();
	}

}