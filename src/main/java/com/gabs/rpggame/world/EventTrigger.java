package com.gabs.rpggame.world;

import java.awt.Rectangle;

import com.gabs.rpggame.Main;

public class EventTrigger {
	public com.gabs.rpggame.entities.FunctionalInterface action;
	private int x = 0;
	private int y = 0;
	private int size = Main.GameProperties.TileSize;
	
	public boolean isTriggered() {
		Rectangle trigger = new Rectangle(this.x, this.y, this.size, this.size);
		Rectangle player = new Rectangle(Main.player.getX(), Main.player.getY(), size, size);
		System.out.println(this.x + "x"+this.y);
		System.out.println(Main.player.getX() + "x" + Main.player.getY());
		return trigger.intersects(player);
	}

	public com.gabs.rpggame.entities.FunctionalInterface getAction() {
		return action;
	}

	public EventTrigger setAction(com.gabs.rpggame.entities.FunctionalInterface action) {
		this.action = action;
		return this;
	}

	public int getX() {
		return x;
	}

	public EventTrigger setX(int x) {
		this.x = x;
		return this;
	}

	public int getY() {
		return y;
	}

	public EventTrigger setY(int y) {
		this.y = y;
		return this;
	}

	public int getSize() {
		return size;
	}

	public EventTrigger setSize(int size) {
		this.size = size;
		return this;
	}	
	
	
}
