package com.boredream.tianyatuoshui;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.net.Uri;

import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

public class StealLinkImageDownloader extends BaseImageDownloader {
	
	private static final int MAX_REDIRECT_COUNT = 5;

	public StealLinkImageDownloader(Context context, int connectTimeout, int readTimeout) {
		super(context, connectTimeout, readTimeout);
	}

	public StealLinkImageDownloader(Context context) {
		super(context);
	}

	@Override
	protected InputStream getStreamFromNetwork(String imageUri, Object extra) throws IOException {
		// connect
		String encodedUrl = Uri.encode(imageUri, ALLOWED_URI_CHARS);
		HttpURLConnection conn = (HttpURLConnection) new URL(encodedUrl).openConnection();
		conn.setConnectTimeout(connectTimeout);
		conn.setReadTimeout(readTimeout);
		// steal link !!!
		conn.setRequestProperty("Referer", imageUri);  
		conn.connect();

		// redirect
		int redirectCount = 0;
		while (conn.getResponseCode() / 100 == 3 && redirectCount < MAX_REDIRECT_COUNT) {
			conn = connectTo(conn.getHeaderField("Location"));
			redirectCount++;
		}

		return new BufferedInputStream(conn.getInputStream(), BUFFER_SIZE);
	}

	private HttpURLConnection connectTo(String url) throws IOException {
		String encodedUrl = Uri.encode(url, ALLOWED_URI_CHARS);
		HttpURLConnection conn = (HttpURLConnection) new URL(encodedUrl).openConnection();
		conn.setConnectTimeout(connectTimeout);
		conn.setReadTimeout(readTimeout);
		conn.connect();
		return conn;
	}
	
}
