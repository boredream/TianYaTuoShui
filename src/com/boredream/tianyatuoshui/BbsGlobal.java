package com.boredream.tianyatuoshui;

//<script type="text/javascript">
//var bbsGlobal = {
//	isEhomeItem : false,
//	isNewArticle : true,
//	blocktype : "副版",
//	itemType : "副版",
//	itemName : "饮食男女",
//	page : "1",
//	permission : true,
//	itemPermission : 1,
//	item : "96",
//	artId : 564625,
//	media : 1,
//	subType : "烹食谈情",
//	title : "[烹食谈情]会做饭的男人另类生活",
//	authorName : "捣糨糊的洪七公",
//	authorId : 18836351,
//	isSlide : true,
//	ToWeb : "true",
//	dashang : {
//		merId : "%E5%A4%A9%E6%B6%AF%E8%AE%BA%E5%9D%9B",
//		merNum : "96-564625",
//		getName : "%E6%8D%A3%E7%B3%A8%E7%B3%8A%E7%9A%84%E6%B4%AA%E4%B8%83%E5%85%AC",
//		time : "1434543297936",
//		ext1 : "564625",
//		ext2 : "96",
//		ext3 : "%5B%E7%83%B9%E9%A3%9F%E8%B0%88%E6%83%85%5D%E4%BC%9A%E5%81%9A%E9%A5%AD%E7%9A%84%E7%94%B7%E4%BA%BA%E5%8F%A6%E7%B1%BB%E7%94%9F%E6%B4%BB",
//		sign : "4a1291bb29ca4a181d0104b7709f2661"	
//	}
//}

public class BbsGlobal {
	private boolean isEhomeItem;
	private boolean isNewArticle;
	private String blocktype;
	private String itemType;
	private String itemName;
	private int page;
	private boolean permission;
	private int itemPermission;
	private String item;
	private int artId;
	private int media;
	private String subType;
	private String title;
	private String authorName;
	private int authorId;
	private boolean isSlide;
	private String ToWeb;
	private Dashang dashang;

	public boolean isEhomeItem() {
		return isEhomeItem;
	}

	public void setEhomeItem(boolean isEhomeItem) {
		this.isEhomeItem = isEhomeItem;
	}

	public boolean isNewArticle() {
		return isNewArticle;
	}

	public void setNewArticle(boolean isNewArticle) {
		this.isNewArticle = isNewArticle;
	}

	public String getBlocktype() {
		return blocktype;
	}

	public void setBlocktype(String blocktype) {
		this.blocktype = blocktype;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public boolean isPermission() {
		return permission;
	}

	public void setPermission(boolean permission) {
		this.permission = permission;
	}

	public int getItemPermission() {
		return itemPermission;
	}

	public void setItemPermission(int itemPermission) {
		this.itemPermission = itemPermission;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public int getArtId() {
		return artId;
	}

	public void setArtId(int artId) {
		this.artId = artId;
	}

	public int getMedia() {
		return media;
	}

	public void setMedia(int media) {
		this.media = media;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	public boolean isSlide() {
		return isSlide;
	}

	public void setSlide(boolean isSlide) {
		this.isSlide = isSlide;
	}

	public String getToWeb() {
		return ToWeb;
	}

	public void setToWeb(String toWeb) {
		ToWeb = toWeb;
	}

	public Dashang getDashang() {
		return dashang;
	}

	public void setDashang(Dashang dashang) {
		this.dashang = dashang;
	}

	/* sub class */
	public class Dashang {
		private String merId;
		private String merNum;
		private String getName;
		private String time;
		private String ext1;
		private String ext2;
		private String ext3;
		private String sign;

		public String getMerId() {
			return merId;
		}

		public void setMerId(String merId) {
			this.merId = merId;
		}

		public String getMerNum() {
			return merNum;
		}

		public void setMerNum(String merNum) {
			this.merNum = merNum;
		}

		public String getGetName() {
			return getName;
		}

		public void setGetName(String getName) {
			this.getName = getName;
		}

		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
		}

		public String getExt1() {
			return ext1;
		}

		public void setExt1(String ext1) {
			this.ext1 = ext1;
		}

		public String getExt2() {
			return ext2;
		}

		public void setExt2(String ext2) {
			this.ext2 = ext2;
		}

		public String getExt3() {
			return ext3;
		}

		public void setExt3(String ext3) {
			this.ext3 = ext3;
		}

		public String getSign() {
			return sign;
		}

		public void setSign(String sign) {
			this.sign = sign;
		}

	}
}
