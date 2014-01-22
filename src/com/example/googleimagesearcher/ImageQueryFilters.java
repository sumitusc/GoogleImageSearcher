package com.example.googleimagesearcher;

public class ImageQueryFilters {

	private String searchExpression;
	private String version = "1.0";
	private String siteSearchFilter;
	private String imageColor;
	private String imageSize;
	private String imageType;
	private int resultsPerPage = 8;
	private int start = 0;
	
	public ImageQueryFilters() {
		super();
	}

	public ImageQueryFilters(String searchExpression) {
		super();
		this.searchExpression = searchExpression;
	}

	public ImageQueryFilters(String searchExpression, String version,
			String siteSearchFilter, String imageColor, String imageSize,
			String imageType, int resultsPerPage, int start) {
		super();
		this.searchExpression = searchExpression;
		this.version = version;
		this.siteSearchFilter = siteSearchFilter;
		this.imageColor = imageColor;
		this.imageSize = imageSize;
		this.imageType = imageType;
		this.resultsPerPage = resultsPerPage;
		this.start = start;
	}
	
	public String getSearchExpression() {
		return searchExpression;
	}
	public void setSearchExpression(String searchExpression) {
		this.searchExpression = searchExpression;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getSiteSearchFilter() {
		return siteSearchFilter;
	}
	public void setSiteSearchFilter(String siteSearchFilter) {
		this.siteSearchFilter = siteSearchFilter;
	}
	public String getImageColor() {
		return imageColor;
	}
	public void setImageColor(String imageColor) {
		this.imageColor = imageColor;
	}
	public String getImageSize() {
		return imageSize;
	}
	public void setImageSize(String imageSize) {
		this.imageSize = imageSize;
	}
	public String getImageType() {
		return imageType;
	}
	public void setImageType(String imageType) {
		this.imageType = imageType;
	}
	public int getResultsPerPage() {
		return resultsPerPage;
	}
	public void setResultsPerPage(int resultsPerPage) {
		this.resultsPerPage = resultsPerPage;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	
	
	
}
