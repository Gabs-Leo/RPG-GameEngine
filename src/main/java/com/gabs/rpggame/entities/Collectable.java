package com.gabs.rpggame.entities;

import com.gabs.rpggame.Main;


public class Collectable extends Entity {
	private FunctionalInterface method;
	
	@Override
	public void eventTick() {
		super.eventTick();
		checkPlayerCollision();
	}

	public void checkPlayerCollision () {
		if(Entity.isColliding(this, Main.player)) {
			method.execute();
			Main.entities.remove(this);
		}
	}

	public FunctionalInterface getMethod() {
		return method;
	}

	public Collectable setMethod(FunctionalInterface method) {
		this.method = method;
		return this;
	}
	
}
