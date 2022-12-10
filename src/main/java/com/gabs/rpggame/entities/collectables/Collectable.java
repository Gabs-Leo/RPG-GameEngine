package com.gabs.rpggame.entities.collectables;

import com.gabs.rpggame.Main;
import com.gabs.rpggame.entities.Entity;
import com.gabs.rpggame.entities.FunctionalInterface;

public class Collectable extends Entity {
	private FunctionalInterface method;
	
	private int[] positions = new int[2];
	private int size = 3;
	private boolean placeholder = false;
	
	public Collectable() {}
	public Collectable(int size, boolean placeholder) {
		super();
		this.size = size;
		this.placeholder = placeholder;
	}

	@Override
	public void eventTick() {
		super.eventTick();
		checkPlayerCollision();
	}
	
	

	public void checkPlayerCollision () {
		if(Entity.isColliding(this, Main.player)) {
			method.execute();
			Main.entities.remove(this);
			Main.player.collectItem(this);
			//Main.player.printInventory();
		}
	}

	public FunctionalInterface getMethod() {
		return method;
	}

	public Collectable setMethod(FunctionalInterface method) {
		this.method = method;
		return this;
	}

	public int getSize() {
		return size;
	}

	public Collectable setSize(int size) {
		this.size = size;
		return this;
	}

	public boolean isPlaceholder() {
		return placeholder;
	}

	public Collectable setPlaceholder(boolean placeholder) {
		this.placeholder = placeholder;
		return this;
	}
	public int[] getPositions() {
		return positions;
	}
}
