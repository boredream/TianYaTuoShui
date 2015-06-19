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

import com.android.volley.VolleyError;
import com.boredream.volley.BDListener;
import com.boredream.volley.BDVolleyHttp;

public class MainActivity extends Activity {
	
	private ListView lv_comments;
	
	private int page = 1;
	private String authorName;
	
	private ArrayList<BbsComment> comments = new ArrayList<BbsComment>();
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		String url = "http://bbs.tianya.cn/post-96-564625-"+page+".shtml";
		loadUrl(url);
		
		lv_comments = (ListView) findViewById(R.id.lv_comments);
		
	}

	private void loadUrl(String url) {
		BDVolleyHttp.getString(url, new BDListener<String>() {
			
			@Override
			public void onErrorResponse(VolleyError error) {
				
			}
			
			@Override
			public void onResponse(String response) {
				Document parse = Jsoup.parse(response);
				
				Elements allElements = parse.getAllElements();
				for(int i=0; i<allElements.size(); i++) {
					Element element = allElements.get(i);
					String nodeName = element.nodeName();
					String data = element.data();
//					if(nodeName.equals("script") && data.contains("bbsGlobal")) {
//						String json = getJson(data);
//						BbsGlobal bbsGlobal = new Gson().fromJson(json, BbsGlobal.class);
//						page = bbsGlobal.getPage();
//						authorName = bbsGlobal.getAuthorName();
//					}
					
					if(isAuthorComment(element)) {
						BbsComment comment = parseBbsComment(element);
						if(comment != null) {
							comments.add(comment);
						}
					}
				}
				
				System.out.println(comments);
				lv_comments.setAdapter(new CommentAdapter(MainActivity.this, comments));
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
				
				// img urls
				ArrayList<String> imgUrls = new ArrayList<String>();
				Elements allContentElement = element.getAllElements();
				for(int j=0; j<allContentElement.size(); j++) {
					Element contentElement = allContentElement.get(j);
					if(contentElement.nodeName().equals("img")) {
						String imgUrl = contentElement.attr("original");
						if(imgUrl != null) {
							imgUrls.add(imgUrl);
						}
					}
				}
				comment.setImgUrls(imgUrls);
				
				// text
				String text = element.toString()
						.replace("<div class=\"bd\">", "")
						.replace("</div>", "")
						.replace("<br>", "\n");
				comment.setText(text);
				
				break;
			}
		}
		
		return comment;
	}
	
	private boolean isUnAuthorComment(Element commentElement) {
//<div class="item item-ht " 
//			data-id="60" data-time="2008-08-31 18:39:00" data-replyid="5877745"><!-- 网友回复内容 -->
//	<div class="hd f-cf">
//     <p class="floor fc-gray">60楼</p>
//     <h4 class="author"><a href="http://www.tianya.cn/m/home.jsp?uid=16142319">ljleyz</a>
//     
//     </h4>
//     <p class="time fc-gray">2008-08-31 18:39</p>
// </div>			
//	<div class="bd">　　看着就想吃，不过不知道怎么做的，要能写出怎么做用什么料就好了<br></div>
//	<div class="ft">
//	
//	</div>
//	
//</div>
		String nodeName = commentElement.nodeName();
		return nodeName.equals("div") && 
				commentElement.attr("class").equals("item item-ht ");
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
		return nodeName.equals("div") && 
				commentElement.attr("class").equals("item item-ht  item-lz");
	}

}
