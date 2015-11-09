package com.jay.example.fragmentforhost;

class GridItem {
	// private String title;
	private int imageId;

	// private String description;

	public GridItem() {
		super();
	}

	public GridItem(int imageId) {
		super();
		// this.title = title;
		this.imageId = imageId;
		// this.description = time;
	}

	/*
	 * public String getTime( ) { return description; }
	 * 
	 * public String getTitle() { return title; }
	 */

	public int getImageId() {
		return imageId;
	}
}
