package com.gabs.rpggame.entities;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.gabs.rpggame.Main;
import com.gabs.rpggame.graphics.Animation;
import com.gabs.rpggame.world.Camera;
import com.gabs.rpggame.world.World;

public class Player extends Entity implements KeyListener{
	
	private boolean right, left, up, down;
	private int rightDir = 0, 
				leftDir = 1, 
				upDir = 2, 
				downDir = 3; 
	private boolean moving;
	
	private int direction = downDir;
	private int speed;
	
	private Animation downAnimation = new Animation(1, 5, 
			Main.spritesheet.getSprite(0, 0, 32, 32),
			Main.spritesheet.getSprite(32, 0, 32, 32),
			Main.spritesheet.getSprite(64, 0, 32, 32));
	
	private Animation leftAnimation = new Animation(1, 5, 
			Main.spritesheet.getSprite(0, 32, 32, 32),
			Main.spritesheet.getSprite(32, 32, 32, 32),
			Main.spritesheet.getSprite(64, 32, 32, 32));
	
	private Animation rightAnimation = new Animation(1, 5, 
			Main.spritesheet.getSprite(0, 64, 32, 32),
			Main.spritesheet.getSprite(32, 64, 32, 32),
			Main.spritesheet.getSprite(64, 64, 32, 32));
	
	private Animation upAnimation = new Animation(1, 5, 
			Main.spritesheet.getSprite(0, 96, 32, 32),
			Main.spritesheet.getSprite(32, 96, 32, 32),
			Main.spritesheet.getSprite(64, 96, 32, 32));
	
	private Animation damageDownAnimation = new Animation(1, 5, 
			Main.spritesheet.getSprite(0+256, 0+256, 32, 32),
			Main.spritesheet.getSprite(32+256, 0+256, 32, 32),
			Main.spritesheet.getSprite(64+256, 0+256, 32, 32));
	
	private Animation damageLeftAnimation = new Animation(1, 5, 
			Main.spritesheet.getSprite(0+256, 32+256, 32, 32),
			Main.spritesheet.getSprite(32+256, 32+256, 32, 32),
			Main.spritesheet.getSprite(64+256, 32+256, 32, 32));
	
	private Animation damageRightAnimation = new Animation(1, 5, 
			Main.spritesheet.getSprite(0+256, 64+256, 32, 32),
			Main.spritesheet.getSprite(32+256, 64+256, 32, 32),
			Main.spritesheet.getSprite(64+256, 64+256, 32, 32));
	
	private Animation damageUpAnimation = new Animation(1, 5, 
			Main.spritesheet.getSprite(0+256, 96+256, 32, 32),
			Main.spritesheet.getSprite(32+256, 96+256, 32, 32),
			Main.spritesheet.getSprite(64+256, 96+256, 32, 32));
	

	private int ammo = 0;
	
	public Player() {
		this.setMaxLife(Main.GameProperties.PlayerMaxLife);
		this.setLife(this.getMaxLife());
		this.setArmor(Main.GameProperties.PlayerArmor);
	}

	@Override
	public void eventTick() {
		
		
		this.setMoving(false);
		if(this.isRight() && World.placeFree(this.getX() + this.getSpeed(), this.getY()) && this.getX() + Main.GameProperties.TileSize <= World.WIDTH*Main.GameProperties.TileSize) {
			this.setMoving(true);
			if(this.isDown() ||this.isUp())
				this.setX(this.getX() + this.getSpeed()/2);
			else
				this.setX(this.getX() + this.getSpeed());
			this.setDirection(rightDir);
			
		}else if (this.isLeft() && World.placeFree(this.getX() - this.getSpeed(), this.getY()) && this.getX() >= 0) {
			this.setMoving(true);
			if(this.isDown() || this.isUp())
				this.setX(this.getX() - this.getSpeed()/2);
			else
				this.setX(this.getX() - this.getSpeed());
			this.setDirection(leftDir);
		}
		if(this.isUp() && World.placeFree(this.getX(), this.getY() - this.getSpeed()) && this.getY() >= 0) {
			this.setMoving(true);
			this.setY(this.getY() - this.getSpeed());
			this.setDirection(upDir);
		}else if (this.isDown() && World.placeFree(this.getX(), this.getY() + this.getSpeed())) {
			this.setMoving(true);
			this.setY(this.getY() + this.getSpeed());
			this.setDirection(downDir);
		}
		
		if(this.isMoving()) {
			/*
			frames++;
			if(frames == animDelay) {
				frames = 0;
				index++;
				if(index > rightFrames.size()-1)
					index = 0;
			}*/
			downAnimation.run();
			upAnimation.run();
			leftAnimation.run();
			rightAnimation.run();
			
			damageDownAnimation.run();
			damageUpAnimation.run();
			damageLeftAnimation.run();
			damageRightAnimation.run();
		}
		/*
		if(GameProperties.CLAMP) {
			Camera.setX(Camera.clamp(this.getX() - Main.GameProperties.ScreenWidth*Main.SCALE/2, 0, World.WIDTH*GameProperties.TILE_SIZE - Main.GameProperties.ScreenWidth*Main.SCALE));
			Camera.setY(Camera.clamp(this.getY() - Main.GameProperties.ScreenHeight*Main.SCALE/2, 0, World.HEIGHT*GameProperties.TILE_SIZE - Main.GameProperties.ScreenHeight*Main.SCALE));
		} else {
			Camera.setX(this.getX() - Main.GameProperties.ScreenWidth*Main.SCALE/2 - 10);
			Camera.setY(this.getY() - Main.GameProperties.ScreenHeight*Main.SCALE/2 - 10);
		}*/
		
		
		if(Main.GameProperties.Clamp) {
			Camera.setX(Camera.clamp(this.getX() - Main.GameProperties.ScreenWidth/2, 0, World.WIDTH*Main.GameProperties.TileSize - Main.GameProperties.ScreenWidth));
			Camera.setY(Camera.clamp(this.getY() - Main.GameProperties.ScreenHeight/2, 0, World.HEIGHT*Main.GameProperties.TileSize - Main.GameProperties.ScreenHeight));
		} else {
			Camera.setX(this.getX() - Main.GameProperties.ScreenWidth/2);
			Camera.setY(this.getY() - Main.GameProperties.ScreenHeight/2);
		}
		
		super.eventTick();
	}
	
	@Override
	public void render(Graphics g) {
		if(!this.isTakingDamage()) {
			if(this.getDirection() == downDir) {
				g.drawImage(downAnimation.getImages().get(downAnimation.getIndex()), this.getX() - Camera.getX(), this.getY() - Camera.getY(), null);
				//g.drawImage(downFrames.get(index), this.getX() - Camera.getX(), this.getY() - Camera.getY(), null);
			}else if(this.getDirection() == upDir) {
				g.drawImage(upAnimation.getImages().get(upAnimation.getIndex()), this.getX() - Camera.getX(), this.getY() - Camera.getY(), null);
				//g.drawImage(upFrames.get(index), this.getX() - Camera.getX(), this.getY() - Camera.getY(), null);
			}
			
			if (this.getDirection() == rightDir) {
				g.drawImage(rightAnimation.getImages().get(rightAnimation.getIndex()), this.getX() - Camera.getX(), this.getY() - Camera.getY(), null);
				//g.drawImage(rightFrames.get(index), this.getX() - Camera.getX(), this.getY() - Camera.getY(), null);
			}else if(this.getDirection() == leftDir) {
				g.drawImage(leftAnimation.getImages().get(leftAnimation.getIndex()), this.getX() - Camera.getX(), this.getY() - Camera.getY(), null);
				//g.drawImage(leftFrames.get(index), this.getX() - Camera.getX(), this.getY() - Camera.getY(), null);
			}
		} else {
			if(this.getDirection() == downDir) {
				g.drawImage(damageDownAnimation.getImages().get(damageDownAnimation.getIndex()), this.getX() - Camera.getX(), this.getY() - Camera.getY(), null);
				//g.drawImage(downFrames.get(index), this.getX() - Camera.getX(), this.getY() - Camera.getY(), null);
			}else if(this.getDirection() == upDir) {
				g.drawImage(damageUpAnimation.getImages().get(damageUpAnimation.getIndex()), this.getX() - Camera.getX(), this.getY() - Camera.getY(), null);
				//g.drawImage(upFrames.get(index), this.getX() - Camera.getX(), this.getY() - Camera.getY(), null);
			}
			
			if (this.getDirection() == rightDir) {
				g.drawImage(damageRightAnimation.getImages().get(damageRightAnimation.getIndex()), this.getX() - Camera.getX(), this.getY() - Camera.getY(), null);
				//g.drawImage(rightFrames.get(index), this.getX() - Camera.getX(), this.getY() - Camera.getY(), null);
			}else if(this.getDirection() == leftDir) {
				g.drawImage(damageLeftAnimation.getImages().get(damageLeftAnimation.getIndex()), this.getX() - Camera.getX(), this.getY() - Camera.getY(), null);
				//g.drawImage(leftFrames.get(index), this.getX() - Camera.getX(), this.getY() - Camera.getY(), null);
			}
		}
		super.render(g);
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			this.setRight(true);
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			this.setLeft(true);
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			this.setUp(true);
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			this.setDown(true);
		}
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			this.setRight(false);
			this.rightAnimation.setIndex(this.rightAnimation.getStartIndex());

		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			this.setLeft(false);
			this.leftAnimation.setIndex(this.leftAnimation.getStartIndex());
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			this.setUp(false);
			this.upAnimation.setIndex(this.upAnimation.getStartIndex());

		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			this.setDown(false);
			this.downAnimation.setIndex(this.downAnimation.getStartIndex());
		}
		
	}
	
	public boolean isRight() {
		return right;
	}
	public Player setRight(boolean right) {
		this.right = right;
		return this;
	}
	public boolean isLeft() {
		return left;
	}
	public Player setLeft(boolean left) {
		this.left = left;
		return this;
	}
	public boolean isUp() {
		return up;
	}
	public Player setUp(boolean up) {
		this.up = up;
		return this;
	}
	public boolean isDown() {
		return down;
	}
	public Player setDown(boolean down) {
		this.down = down;
		return this;
	}
	public int getSpeed() {
		return speed;
	}
	public Player setSpeed(int speed) {
		this.speed = speed;
		return this;
	}
	public int getDirection() {
		return direction;
	}
	public Player setDirection(int direction) {
		this.direction = direction;
		return this;
	}
	public boolean isMoving() {
		return moving;
	}
	public Player setMoving(boolean moving) {
		this.moving = moving;
		return this;
	}

	public int getAmmo() {
		return ammo;
	}

	public Player setAmmo(int ammo) {
		this.ammo = ammo;
		return this;
	}
	
}
