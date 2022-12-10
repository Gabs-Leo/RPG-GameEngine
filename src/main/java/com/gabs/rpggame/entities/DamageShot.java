package com.gabs.rpggame.entities;

import java.awt.Color;
import java.awt.Graphics;

import com.gabs.rpggame.Main;
import com.gabs.rpggame.world.Camera;
import com.gabs.rpggame.world.DamageType;
import com.gabs.rpggame.world.Direction;

public class DamageShot extends Entity{
	
	private Direction direction;
	private int speed;
	private int currentPosition = 0;
	private int range;
	
	@Override
	public void eventTick() {
		/*
		if(directionX == Direction.RIGHT)
			this.setX(this.getX()+this.getSpeed());
		else if (directionX == Direction.LEFT)
			this.setX(this.getX()-this.getSpeed());*/
		
		currentPosition++;
		Main.enemies.forEach(enemy -> {
			if(isColliding(this, enemy)) {
				this.inflictDamage(this.getDamage(), DamageType.PHYSICAL_DAMAGE, enemy);
				switch(this.direction) {
				case DOWN:
					enemy.setY(enemy.getY()+32);
					break;
				case LEFT:
					enemy.setX(enemy.getX()-32);
					break;
				case RIGHT:
					enemy.setX(enemy.getX()+32);
					break;
				case UP:
					enemy.setY(enemy.getY()-32);
					break;
				default:
					break;
					
				}
					
			};
		});
		
		if(currentPosition >= range) {
			Main.damageShots.remove(this);
			return;
		}
	}
	
	@Override
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(this.getX() - Camera.getX(), this.getY() - Camera.getY(), this.getWidth(), this.getHeight());
	}

	public int getSpeed() {
		return speed;
	}

	public DamageShot setSpeed(int speed) {
		this.speed = speed;
		return this;
	}

	public int getCurrentPosition() {
		return currentPosition;
	}

	public DamageShot setCurrentPosition(int currentPosition) {
		this.currentPosition = currentPosition;
		return this;
	}

	public int getRange() {
		return range;
	}

	public DamageShot setRange(int range) {
		this.range = range;
		return this;
	}

	public Direction getDirection() {
		return direction;
	}

	public DamageShot setDirection(Direction direction) {
		this.direction = direction;
		return this;
	}
	
	
}
