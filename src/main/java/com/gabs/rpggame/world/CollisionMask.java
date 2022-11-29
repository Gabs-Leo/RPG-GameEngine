package com.gabs.rpggame.world;

public class CollisionMask {
	private int x;
	private int y;
	private int width;
	private int height;
	private boolean visible;
	
	public CollisionMask() {}
	public CollisionMask(int width, int height) {
		super();
		this.width = width;
		this.height = height;
	}
	public CollisionMask(int x, int y, int width, int height) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	public CollisionMask(int x, int y, int width, int height, boolean visible) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.visible = visible;
	}
	
	public int getX() {
		return x;
	}
	public CollisionMask setX(int x) {
		this.x = x;
		return this;
	}
	public int getY() {
		return y;
	}
	public CollisionMask setY(int y) {
		this.y = y;
		return this;
	}
	public int getWidth() {
		return width;
	}
	public CollisionMask setWidth(int width) {
		this.width = width;
		return this;
	}
	public int getHeight() {
		return height;
	}
	public CollisionMask setHeight(int height) {
		this.height = height;
		return this;
	}
	public boolean isVisible() {
		return visible;
	}
	public CollisionMask setVisible(boolean visible) {
		this.visible = visible;
		return this;
	}
}
