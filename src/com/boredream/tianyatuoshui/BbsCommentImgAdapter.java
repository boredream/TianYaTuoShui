package com.boredream.tianyatuoshui;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class BbsCommentImgAdapter extends BaseAdapter {
	
	private Context context;
	private ArrayList<BbsImg> imgs;
	private ImageLoader imageLoader;
	
	public BbsCommentImgAdapter(Context context, ArrayList<BbsImg> imgs) {
		this.context = context;
		this.imgs = imgs;
		this.imageLoader = ImageLoader.getInstance();
	}

	@Override
	public int getCount() {
		return imgs.size();
	}

	@Override
	public BbsImg getItem(int position) {
		return imgs.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, final ViewGroup parent) {
		final ViewHolder holder;
		if(convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.item_comment_img, null);
			holder.iv_comment = (ImageView) convertView.findViewById(R.id.iv_comment);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		// bind data
		BbsImg item = getItem(position);
		
		imageLoader.loadImage(item.getImgUrl(), new ImageLoadingListener() {
			@Override
			public void onLoadingStarted(String imageUri, View view) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				float scale = (float) loadedImage.getHeight() / loadedImage.getWidth();
				int ivHeight = (int) (parent.getWidth() * scale);
				LayoutParams params = new LayoutParams(parent.getWidth(), ivHeight);
				holder.iv_comment.setLayoutParams(params);
				holder.iv_comment.setImageBitmap(loadedImage);
				notifyDataSetChanged();
			}
			
			@Override
			public void onLoadingCancelled(String imageUri, View view) {
				// TODO Auto-generated method stub
			}
		});
		
		return convertView;
	}
	
	static class ViewHolder {
		public ImageView iv_comment;
	}
	
}
