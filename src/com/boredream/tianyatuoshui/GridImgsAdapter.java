package com.boredream.tianyatuoshui;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.ImageLoader;


public class GridImgsAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<String> datas;
	private ImageLoader imageLoader;

	public GridImgsAdapter(Context context, ArrayList<String> datas) {
		this.context = context;
		this.datas = datas;
		imageLoader = ImageLoader.getInstance();
	}

	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public String getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView iv = new ImageView(context);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				dp2px(context, 100), dp2px(context, 100));
		iv.setLayoutParams(params);
		
		// set data
		String item = getItem(position);
		imageLoader.displayImage(item, iv);

		return iv;
	}
	
	private int dp2px(Context context, float dp) {  
	    final float scale = context.getResources().getDisplayMetrics().density;  
	    return (int) (dp * scale + 0.5f);  
	}  

}
