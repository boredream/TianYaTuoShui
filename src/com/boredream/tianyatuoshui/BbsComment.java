package com.boredream.tianyatuoshui;

import java.util.ArrayList;

public class BbsComment {
	// <div class="item item-ht  item-lz"
	// data-id="10" data-time="2008-08-30 17:26:00" data-replyid="5876579"><!--
	// 网友回复内容 -->
	// <div class="hd f-cf">
	// <p class="floor fc-gray">10楼</p>
	// <h4 class="author"><a
	// href="http://www.tianya.cn/m/home.jsp?uid=18836351">捣糨糊的洪七公</a>
	//
	// <span class="u-badge">楼主</span>
	//
	// </h4>
	// <p class="time fc-gray">2008-08-30 17:26</p>
	// </div>
	// <div class="bd">　　子曰：有朋自远方来，不亦乐乎。上酒<br><img
	// src="http://static.tianyaui.com/global/m/touch/images/loading.gif"
	// original="http://img12.tianya.cn/photo/2008/8/30/9744180_18836351.jpg"><br></div>
	// <div class="ft">
	//
	// </div>
	//
	// </div>
	private String text;
	private ArrayList<BbsImg> imgUrls;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public ArrayList<BbsImg> getImgUrls() {
		return imgUrls;
	}

	public void setImgUrls(ArrayList<BbsImg> imgUrls) {
		this.imgUrls = imgUrls;
	}

	@Override
	public String toString() {
		return "BbsComment [text=" + text + ", imgUrls=" + imgUrls + "]";
	}
	
}
