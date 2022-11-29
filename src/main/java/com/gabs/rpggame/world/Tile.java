package com.gabs.rpggame.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Tile {
	
	private BufferedImage sprite;
	private int x,y;
	private CollisionType type;
	
	public Tile () {}
	
	public void render(Graphics g) {
		g.drawImage(this.getSprite(), this.getX() - Camera.getX(), this.getY() - Camera.getY(), null);
	}

	public BufferedImage getSprite() {
		return sprite;
	}

	public Tile setSprite(BufferedImage sprite) {
		this.sprite = sprite;
		return this;
	}

	public int getX() {
		return x;
	}

	public Tile setX(int x) {
		this.x = x;
		return this;
	}

	public int getY() {
		return y;
	}

	public Tile setY(int y) {
		this.y = y;
		return this;
	}

	public CollisionType getType() {
		return type;
	}

	public Tile setType(CollisionType type) {
		this.type = type;
		return this;
	}
	
}
