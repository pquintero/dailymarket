package ar.com.dailyMarket.model;

public class Image extends Attach{
	
	Thumbnail thumbnail;

	public Thumbnail getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(Thumbnail thumbnail) {
		this.thumbnail = thumbnail;
	}		
}
