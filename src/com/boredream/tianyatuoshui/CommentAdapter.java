package com.boredream.tianyatuoshui;

import java.util.ArrayList;

import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

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
			holder.iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
			holder.gv_imgs = (GridView) convertView.findViewById(R.id.gv_imgs);
			holder.tv_text = (TextView) convertView.findViewById(R.id.tv_text);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		BbsComment comment = getItem(position);
		ArrayList<String> imgUrls = comment.getImgUrls();
		if(imgUrls.size() == 1) {
			holder.iv_img.setVisibility(View.VISIBLE);
			holder.gv_imgs.setVisibility(View.GONE);
			
			imageLoader.displayImage(imgUrls.get(0), holder.iv_img);
		} else if(imgUrls.size() > 1) {
			holder.iv_img.setVisibility(View.GONE);
			holder.gv_imgs.setVisibility(View.VISIBLE);
			
			GridImgsAdapter gvAdapter = new GridImgsAdapter(context, imgUrls);
			holder.gv_imgs.setAdapter(gvAdapter);
		} else {
			holder.iv_img.setVisibility(View.GONE);
			holder.gv_imgs.setVisibility(View.GONE);
		}
		
		holder.tv_text.setText(comment.getText());
		
		return convertView;
	}
	
	static class ViewHolder {
		public ImageView iv_img;
		public GridView gv_imgs;
		public TextView tv_text;
	}

}
