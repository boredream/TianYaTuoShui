package com.boredream.tianyatuoshui;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

public class CommentAdapter extends BaseAdapter {
	
	private Context context;
	private ArrayList<BbsComment> comments;
	private ImageLoader imageLoader;
	
	public CommentAdapter(Context context, ArrayList<BbsComment> comments) {
		this.context = context;
		this.comments = comments;
		this.imageLoader = ImageLoader.getInstance();
	}

	@Override
	public int getCount() {
		return comments.size();
	}

	@Override
	public BbsComment getItem(int position) {
		return comments.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.item_comment, null);
			holder.ll_imgs = (LinearLayout) convertView.findViewById(R.id.ll_imgs);
			holder.tv_text = (TextView) convertView.findViewById(R.id.tv_text);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		// bind data
		BbsComment comment = getItem(position);
		
		Html.fromHtml(comment.getText(), new ImageGetter() {
			
			@Override
			public Drawable getDrawable(String source) {
				return null;
			}
		}, null);
		
		
		ArrayList<String> imgUrls = comment.getImgUrls();

		holder.ll_imgs.removeAllViews();
		for(String imgUrl : imgUrls) {
			final ImageView iv = new ImageView(context);
			final int ivWidth = DisplayUtils.getScreenWidthPixels((Activity)context)
					- 2 * DisplayUtils.dp2px(context, 16);
			
			imageLoader.loadImage(imgUrl, new ImageLoadingListener() {
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
					int ivHeight = (int) (ivWidth * scale);
					LayoutParams params = new LayoutParams(ivWidth, ivHeight);
					params.setMargins(0, DisplayUtils.dp2px(context, 8), 0, 0);
					iv.setLayoutParams(params);
					iv.setImageBitmap(loadedImage);
				}
				
				@Override
				public void onLoadingCancelled(String imageUri, View view) {
					// TODO Auto-generated method stub
				}
			});
			
			holder.ll_imgs.addView(iv);
		}
		
		holder.tv_text.setText(Html.fromHtml(comment.getText().trim()));
		
		return convertView;
	}
	
	static class ViewHolder {
		public LinearLayout ll_imgs;
		public TextView tv_text;
	}

	private void setContent(BbsComment comment) {
		
	}
}
