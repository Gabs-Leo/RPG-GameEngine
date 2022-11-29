package com.gabs.rpggame.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.gabs.rpggame.Main;
import com.gabs.rpggame.world.Camera;
import com.gabs.rpggame.world.CollisionMask;

public abstract class Entity {
	private int x, y, width = Main.GameProperties.TileSize, height = Main.GameProperties.TileSize;
	private BufferedImage sprite;
	private CollisionMask collisionMask = new CollisionMask(Main.GameProperties.TileSize, Main.GameProperties.TileSize);
	
	public Entity() {}
	public Entity(int x, int y, int width, int height, BufferedImage sprite) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
	}


	public void eventTick() {
		
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, this.getX() - Camera.getX(), this.getY() - Camera.getY(), null);
		
		if(this.getCollisionMask().isVisible()) {
			g.setColor(new Color(0f, 0f, 0f, .5f));
			g.fillRect((this.getX() + Main.GameProperties.TileSize / 2 - this.getCollisionMask().getWidth() / 2 - Camera.getX()), 
						this.getY() + Main.GameProperties.TileSize / 2 - this.getCollisionMask().getHeight() / 2 - Camera.getY(), 
						this.getCollisionMask().getWidth(), this.getCollisionMask().getHeight());
		}
	}
	
	public static boolean isColliding(Entity entity1, Entity entity2) {
		Rectangle mask1 = new Rectangle(entity1.getX() + entity1.getCollisionMask().getX(),
										entity1.getY() + entity1.getCollisionMask().getY(),
										entity1.getCollisionMask().getWidth(), entity1.getCollisionMask().getHeight());
		
		Rectangle mask2 = new Rectangle(entity2.getX() + entity2.getCollisionMask().getX(),
										entity2.getY() + entity2.getCollisionMask().getY(),
										entity2.getCollisionMask().getWidth(), entity2.getCollisionMask().getHeight());
		
		return mask1.intersects(mask2);
	}
	
	public int getX() {
		return x;
	}

	public Entity setX(int x) {
		this.x = x;
		return this;
	}

	public int getY() {
		return y;
	}

	public Entity setY(int y) {
		this.y = y;
		return this;
	}

	public int getWidth() {
		return width;
	}

	public Entity setWidth(int width) {
		this.width = width;
		return this;
	}
 
	public int getHeight() {
		return height;
	}

	public Entity setHeight(int height) {
		this.height = height;
		return this;
	}

	public BufferedImage getSprite() {
		return sprite;
	}

	public Entity setSprite(BufferedImage sprite) {
		this.sprite = sprite;
		return this;
	}
	public CollisionMask getCollisionMask() {
		return collisionMask;
	}
	public Entity setCollisionMask(CollisionMask collisionMask) {
		this.collisionMask = collisionMask;
		return this;
	}
}
