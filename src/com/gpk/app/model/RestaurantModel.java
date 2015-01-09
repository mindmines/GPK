package com.gpk.app.model;

public class RestaurantModel {
	
	private String Restaurant_name;
	private Integer Distance;
	private String Image;
	private String ObjectId;
	
	
	public String getObjectId() {
		return ObjectId;
	}
	public void setObjectId(String objectId) {
		ObjectId = objectId;
	}
	public String getRestaurant_name() {
		return Restaurant_name;
	}
	public void setRestaurant_name(String restaurant_name) {
		Restaurant_name = restaurant_name;
	}
	public Integer getDistance() {
		return Distance;
	}
	public void setDistance(Integer distance) {
		Distance = distance;
	}
	public String getImage() {
		return Image;
	}
	public void setImage(String image) {
		Image = image;
	}
	
	
}
