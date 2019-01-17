package indi.cyken.atx.bean;

public class Word {
	private Location location;			//字的位置
	private String words;				//字的内容
	
	
	
	public Location getLocation() {
		return location;
	}



	public void setLocation(Location location) {
		this.location = location;
	}



	public String getWords() {
		return words;
	}



	public void setWords(String words) {
		this.words = words;
	}



	public class Location{
		private String width;
		private String top;
		private String height;
		private String left;
		public String getWidth() {
			return width;
		}
		public void setWidth(String width) {
			this.width = width;
		}
		public String getTop() {
			return top;
		}
		public void setTop(String top) {
			this.top = top;
		}
		public String getHeight() {
			return height;
		}
		public void setHeight(String height) {
			this.height = height;
		}
		public String getLeft() {
			return left;
		}
		public void setLeft(String left) {
			this.left = left;
		}
	}

}
