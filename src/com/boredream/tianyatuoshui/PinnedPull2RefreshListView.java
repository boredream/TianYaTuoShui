package com.boredream.tianyatuoshui;

import android.content.Context;
import android.util.AttributeSet;

import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class PinnedPull2RefreshListView extends PullToRefreshListView {

	public PinnedPull2RefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public PinnedPull2RefreshListView(Context context, com.handmark.pulltorefresh.library.PullToRefreshBase.Mode mode, com.handmark.pulltorefresh.library.PullToRefreshBase.AnimationStyle style) {
		super(context, mode, style);
		// TODO Auto-generated constructor stub
	}

	public PinnedPull2RefreshListView(Context context, com.handmark.pulltorefresh.library.PullToRefreshBase.Mode mode) {
		super(context, mode);
		// TODO Auto-generated constructor stub
	}

	public PinnedPull2RefreshListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected PinnedSectionListView createListView(Context context, AttributeSet attrs) {
		return new PinnedSectionListView(context, attrs);
	}
	
}
