package com.boredream.tianyatuoshui;

import android.app.Application;
import android.graphics.Bitmap;

import com.boredream.volley.BDVolley;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class BaseApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();

		BDVolley.init(this);

		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.cacheOnDisc()
				.cacheInMemory()
				.bitmapConfig(Bitmap.Config.RGB_565)
				.displayer(new RoundedBitmapDisplayer(4)).build();
		// 初始化图片处理
		ImageLoaderConfiguration config = new ImageLoaderConfiguration
				.Builder(this)
						.threadPriority(Thread.NORM_PRIORITY - 2)
						.discCacheFileNameGenerator(new Md5FileNameGenerator())
						.tasksProcessingOrder(QueueProcessingType.LIFO)
						.imageDownloader(new StealLinkImageDownloader(this))
						.defaultDisplayImageOptions(defaultOptions)
						.build();
		ImageLoader.getInstance().init(config);
	}
}
