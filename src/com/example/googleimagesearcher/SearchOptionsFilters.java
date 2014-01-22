package com.example.googleimagesearcher;

import java.io.Serializable;

public class SearchOptionsFilters implements Serializable{

	private static final long serialVersionUID = -378746775988369704L;
	private String imageSize;
	private String colorFilter;
	private String imageType;
	private String siteFilter;
	
	
	public SearchOptionsFilters(String imageSize, 
			String colorFilter, 
			String imageType,
			String siteFilter) {
		super();
		this.imageSize = imageSize;
		this.colorFilter = colorFilter;
		this.imageType = imageType;
		this.siteFilter = siteFilter;
	}
	
	public String getImageSize() {
		return imageSize;
	}
	public void setImageSize(String imageSize) {
		this.imageSize = imageSize;
	}
	public String getColorFilter() {
		return colorFilter;
	}
	public void setColorFilter(String colorFilter) {
		this.colorFilter = colorFilter;
	}
	public String getImageType() {
		return imageType;
	}
	public void setImageType(String imageType) {
		this.imageType = imageType;
	}
	public String getSiteFilter() {
		return siteFilter;
	}
	public void setSiteFilter(String siteFilter) {
		this.siteFilter = siteFilter;
	}
	
	
	
}
