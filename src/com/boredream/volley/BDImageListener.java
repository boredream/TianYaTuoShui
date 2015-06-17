package com.boredream.volley;

import android.graphics.Bitmap;

import com.android.volley.Response.ErrorListener;

public interface BDImageListener extends ErrorListener {
	/**
	 * 加载图片完成
	 * 
	 * @param bitmap	为null时代表图片获取失�?
	 */
	public void onComplete(Bitmap bitmap);
}