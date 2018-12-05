package com.pricer.parser;
public class FoodVO {
	String name;
	String price;
	String description;
	int calories;
	protected String getName() {
		return name;
	}
	protected void setName(String name) {
		this.name = name;
	}
	protected String getPrice() {
		return price;
	}
	protected void setPrice(String price) {
		this.price = price;
	}
	protected String getDescription() {
		return description;
	}
	protected void setDescription(String description) {
		this.description = description;
	}
	protected int getCalories() {
		return calories;
	}
	protected void setCalories(int calories) {
		this.calories = calories;
	}
	

	@Override
	public String toString() {
		return "[Name: " + name + ", Price:" + price + ", Description:" + description + ", Calories:"
				+ calories + "]";
	}
	
}
