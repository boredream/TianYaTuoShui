package com.boredream.tianyatuoshui;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.boredream.tianyatuoshui.PinnedSectionListView.PinnedSectionListAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class BbsCommentAdapter extends BaseAdapter implements PinnedSectionListAdapter{
	
	private Context context;
	private ArrayList<BbsItem> comments;
	private ImageLoader imageLoader;
	
	public BbsCommentAdapter(Context context, ArrayList<BbsItem> comments) {
		this.context = context;
		this.comments = comments;
		this.imageLoader = ImageLoader.getInstance();
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public int getItemViewType(int position) {
		return getItem(position).getType();
	}

	@Override
	public int getCount() {
		return comments.size();
	}

	@Override
	public BbsItem getItem(int position) {
		return comments.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		int itemViewType = getItemViewType(position);
		return itemViewType == 1 ? 
				getHeaderView(position, convertView) : 
				getCommentView(position, convertView);
	}

	private View getHeaderView(int position, View convertView) {
		ViewHolderHeader holder;
		if(convertView == null) {
			holder = new ViewHolderHeader();
			convertView = View.inflate(context, R.layout.item_page_header, null);
			holder.tv_header = (TextView) convertView.findViewById(R.id.tv_header);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolderHeader) convertView.getTag();
		}
		
		// bind data
		BbsPageHeader comment = (BbsPageHeader) getItem(position);
		holder.tv_header.setText("第" + comment.getPageNumber() + "页");
		
		return convertView;
	}
	
	static class ViewHolderHeader {
		public TextView tv_header;
	}
	
	private View getCommentView(int position, View convertView) {
		ViewHolderComment holder;
		if(convertView == null) {
			holder = new ViewHolderComment();
			convertView = View.inflate(context, R.layout.item_comment, null);
			holder.ll_imgs = (LinearLayout) convertView.findViewById(R.id.ll_imgs);
			holder.tv_text = (TextView) convertView.findViewById(R.id.tv_title);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolderComment) convertView.getTag();
		}
		
		// bind data
		BbsComment comment = (BbsComment) getItem(position);
		
		ArrayList<BbsImg> bbsImgs = comment.getImgUrls();

		holder.ll_imgs.removeAllViews();
		for(BbsImg img : bbsImgs) {
			final ImageView iv = new ImageView(context);
			final int ivWidth = DisplayUtils.getScreenWidthPixels((Activity)context)
					- 2 * DisplayUtils.dp2px(context, 16);
			
			imageLoader.loadImage(img.getImgUrl(), new ImageLoadingListener() {
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
		
		holder.tv_text.setText(getContent(comment.getText().toString().trim()));
		return convertView;
	}
	
	private Spanned getContent(String htmlContent) {
		return Html.fromHtml(htmlContent);
	}

	static class ViewHolderComment {
		public LinearLayout ll_imgs;
		public TextView tv_text;
	}

	@Override
	public boolean isItemViewTypePinned(int viewType) {
		return viewType == 1;
	}
}
