package com.boredream.tianyatuoshui;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.boredream.volley.BDListener;
import com.boredream.volley.BDVolleyHttp;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

public class MainActivity extends Activity {
	
	private TextView tv_title;
	private TextView tv_page;
	private PinnedPull2RefreshListView plv_comments;
	
	private int curPage = 1;
	private String authorName;
	private BbsCommentAdapter adapter;
	private ArrayList<BbsItem> bbsItems = new ArrayList<BbsItem>();
	private boolean isLoading;
	
	// 贴图帖子
//	String url = "http://bbs.tianya.cn/post-no04-2610476-"+page+".shtml";
	// 会做饭男人的生活
	String bbsUrl = "http://bbs.tianya.cn/post-96-564625-[page].shtml";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initView();
		loadUrl(1);
	}

	private void initView() {
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_page = (TextView) findViewById(R.id.tv_page);
		plv_comments = (PinnedPull2RefreshListView) findViewById(R.id.plv_comments);
		PinnedSectionListView lv = (PinnedSectionListView) plv_comments.getRefreshableView();
		lv.setShadowVisible(false);
		adapter = new BbsCommentAdapter(MainActivity.this, bbsItems);
		plv_comments.setAdapter(adapter);
		plv_comments.getRefreshableView().setFastScrollEnabled(true);
		
		plv_comments.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				loadUrl(1);
			}
		});
		
		plv_comments.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
				loadUrl(curPage + 1);
			}
		});
	}

	private void loadUrl(final int page) {
		if(isLoading == true) {
			return;
		}
		
		isLoading = true;
		
		String pageUrl = bbsUrl.replace("[page]", page+"");
		System.out.println("load url = " + pageUrl);
		BDVolleyHttp.getString(pageUrl, new BDListener<String>() {
			
			@Override
			public void onErrorResponse(VolleyError error) {
				isLoading = false;
				plv_comments.onRefreshComplete();
			}
			
			@Override
			public void onResponse(String response) {
				isLoading = false;
				plv_comments.onRefreshComplete();
				
				if(page == 1) {
					bbsItems.clear();
				}
				curPage = page;
				tv_page.setText("共加载"+curPage+"页");
				
				BbsPageHeader header = new BbsPageHeader();
				header.setType(1);
				header.setPageNumber(page);
				bbsItems.add(header);
				
				Document parse = Jsoup.parse(response);
				
				boolean hasAuthor = false;
				Elements allElements = parse.getAllElements();
				for(int i=0; i<allElements.size(); i++) {
					Element element = allElements.get(i);
					String nodeName = element.nodeName();
					String data = element.data();
					if(nodeName.equals("script") && data.contains("bbsGlobal")) {
						String json = getJson(data);
						BbsGlobal bbsGlobal = new Gson().fromJson(json, BbsGlobal.class);
						authorName = bbsGlobal.getAuthorName();
						
						tv_title.setText(bbsGlobal.getTitle());
					}
					
					if(isAuthorComment(element)) {
						BbsComment comment = parseBbsComment(element);
						if(comment != null && !bbsItems.contains(comment)) {
							bbsItems.add(comment);
						}
						
						hasAuthor = true;
					}
				}
				
				// 如果当前页没有楼主发言,自动加载下一页
				if(!hasAuthor) {
					loadUrl(curPage + 1);
				} else {
					adapter.notifyDataSetChanged();
				}
			}
		});
	}
	
	private String getJson(String source) {
//var bbsGlobal = {
//	 	isEhomeItem : false,
//	 	isNewArticle : true,
//	 	blocktype : "副版",
//	 	itemType : "副版",
//	 	itemName : "饮食男女",
//	 	page : "1",
//	 	permission : true,
//	 	itemPermission : 1,
//	 	item : "96",
//	 	artId : 564625,
//	 	media : 1,
//	 	subType : "烹食谈情",
//	 	title : "[烹食谈情]会做饭的男人另类生活",
//	 	authorName : "捣糨糊的洪七公",
//	 	authorId : 18836351,
//	 	isSlide : true,
//	 	ToWeb : "true",
//	 	dashang : {
//	 		merId : "%E5%A4%A9%E6%B6%AF%E8%AE%BA%E5%9D%9B",
//	 		merNum : "96-564625",
//	 		getName : "%E6%8D%A3%E7%B3%A8%E7%B3%8A%E7%9A%84%E6%B4%AA%E4%B8%83%E5%85%AC",
//	 		time : "1434543297936",
//	 		ext1 : "564625",
//	 		ext2 : "96",
//	 		ext3 : "%5B%E7%83%B9%E9%A3%9F%E8%B0%88%E6%83%85%5D%E4%BC%9A%E5%81%9A%E9%A5%AD%E7%9A%84%E7%94%B7%E4%BA%BA%E5%8F%A6%E7%B1%BB%E7%94%9F%E6%B4%BB",
//	 		sign : "4a1291bb29ca4a181d0104b7709f2661"	
//	 	}
//	 };
		String jsonStr = null;
		
		String regex = "var bbsGlobal = (\\{[\\s\\S]*\\});";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(source);
		if(matcher.find()) {
			jsonStr = matcher.group(1);;
		}
		
		return jsonStr;
	}
	
	public BbsComment parseBbsComment(Element commentElement) {
//		<div class="bd">　　子曰：有朋自远方来，不亦乐乎。上酒<br><img src="http://static.tianyaui.com/global/m/touch/images/loading.gif"  original="http://img12.tianya.cn/photo/2008/8/30/9744180_18836351.jpg"><br></div>
		BbsComment comment = null;
		
		Elements allElements = commentElement.getAllElements();
		for(int i=0; i<allElements.size(); i++) {
			Element element = allElements.get(i);
			if(element.nodeName().equals("div") 
					&& element.attr("class").equals("bd")) {
				comment = new BbsComment();
				
				// img
				int imgStart = -1;
				int imgEnd = 0;
				ArrayList<BbsImg> imgUrls = new ArrayList<BbsImg>();
				Elements allContentElement = element.getAllElements();
				for(int j=0; j<allContentElement.size(); j++) {
					Element contentElement = allContentElement.get(j);
					if(contentElement.nodeName().equals("img")) {
						if(imgStart == -1) {
							imgStart = element.toString().indexOf(contentElement.toString());
						}
						
						imgEnd = element.toString().indexOf(contentElement.toString())
								+ contentElement.toString().length();
						
						BbsImg img = new BbsImg();
						img.setImgElement(contentElement);
						String imgUrl = contentElement.attr("original");
						img.setImgUrl(imgUrl);
						imgUrls.add(img);
					}
				}
				comment.setImgUrls(imgUrls);
				
				// text
				String text = element.toString();
				// remove <image> + <br>
				if (imgStart != -1) {
					text = text.substring(0, imgStart) + text.substring(imgEnd);
				}
				comment.setText(text);
				
				break;
			}
		}
		
		return comment;
	}

	private boolean isAuthorComment(Element commentElement) {
//<div class="item item-ht  item-lz" 
//		data-id="10" data-time="2008-08-30 17:26:00" data-replyid="5876579"><!-- 网友回复内容 -->
//<div class="hd f-cf">
// <p class="floor fc-gray">10楼</p>
// <h4 class="author"><a href="http://www.tianya.cn/m/home.jsp?uid=18836351">捣糨糊的洪七公</a>
// 
// <span class="u-badge">楼主</span>
// 
// </h4>
// <p class="time fc-gray">2008-08-30 17:26</p>
//</div>			
//<div class="bd">　　子曰：有朋自远方来，不亦乐乎。上酒<br><img src="http://static.tianyaui.com/global/m/touch/images/loading.gif"  original="http://img12.tianya.cn/photo/2008/8/30/9744180_18836351.jpg"><br></div>
//<div class="ft">
//
//</div>
//
//</div>
		String nodeName = commentElement.nodeName();
		if(nodeName.equals("div")) {
			String attr = commentElement.attr("class");
			if(attr.contains("item-lz")) {
//				System.out.println(commentElement);
				return true;
			}
		}
		return false;
	}

}
