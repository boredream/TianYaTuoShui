package com.boredream.tianyatuoshui;

import android.app.Application;
import android.graphics.Bitmap;

import com.boredream.volley.BDVolley;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class BaseApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();

		BDVolley.init(this);

		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.cacheOnDisk(true)
				.cacheInMemory(true)
				.showImageOnLoading(R.drawable.loading)
				.showImageForEmptyUri(R.drawable.loading)
				.showImageOnFail(R.drawable.loading)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.build();
		// 初始化图片处理
		ImageLoaderConfiguration config = new ImageLoaderConfiguration
				.Builder(this)
						.threadPriority(Thread.NORM_PRIORITY - 2)
						.diskCacheFileNameGenerator(new Md5FileNameGenerator())
						.tasksProcessingOrder(QueueProcessingType.LIFO)
						.imageDownloader(new StealLinkImageDownloader(this))
						.defaultDisplayImageOptions(defaultOptions)
						.build();
		ImageLoader.getInstance().init(config);
	}
}
