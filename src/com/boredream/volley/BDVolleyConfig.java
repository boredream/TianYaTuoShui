package com.boredream.volley;

public class BDVolleyConfig {
	public static final String TAG = "BDVolley";
	
	/**
	 * url地址encode编码,默认为utf-8
	 */
	public static final String URL_ENCODE_CHARSET_NAME = "UTF-8";
	/**
	 * 响应数据编码,默认为utf-8
	 */
	public static final String RESPONSE_CHARSET_NAME = "UTF-8";
	
	/**
	 * scale=8即用app可用内存�?/8(推荐比例)作为图片缓存池�?缓存大小
	 */
	public static int IMAGE_CACHE_SCALE = 8;
	/**
	 * 默认加载图片
	 */
	public static int DEFAULT_IMAGE_RESID = -1/*R.drawable.ic_launcher*/;
	/**
	 * 加载失败时图�?
	 */
	public static int ERROR_IMAGE_RESID = -1/*R.drawable.ic_launcher*/;
	
}
