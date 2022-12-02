package com.gabs.rpggame.entities;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

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
	
	private List<List<Collectable>> inventory = new ArrayList<>();
	private List<Collectable> wearables = new ArrayList<>();
	
	public Player() {
		for(int i = 0; i < Main.GameProperties.InvenrotySizeY; i++) {
			inventory.add(new ArrayList<>());
			for(int j = 0; j < Main.GameProperties.InventorySizeX; j++)
				inventory.get(i).add(null);
		}
		for(int i = 0; i < 7; i++)
			wearables.add(null);
		
		printInventory();
		this.setMaxLife(Main.GameProperties.PlayerMaxLife);
		this.setLife(this.getMaxLife());
		this.setArmor(Main.GameProperties.PlayerArmor);
	}
	
	public void printInventory() {
		inventory.forEach(i -> System.out.println(i));
		inventory.get(0).forEach(i -> {if(i != null) System.out.println(i.isPlaceholder()); else System.out.println("empty");});
	}
	
	public void collectItem(Collectable item) {
		for(int i = 0; i < inventory.size(); i++) {
			for(int j = 0; j < inventory.get(i).size(); j++) {
				if(inventory.get(i).get(j) == null) {
					item.getPositions()[0] = i;
					item.getPositions()[1] = j;
					item.setPlaceholder(false);
					inventory.get(i).set(j, item);
					if(inventory.get(i).get(j).getSize() > 1) {
						for(int k = 0; k < inventory.get(i).get(j).getSize()-1; k++) {
							Collectable col = new Collectable();
							col.setPlaceholder(true);
							inventory.get(i).set(j+1+k, col);
						}
					}
					return;
				}
			}
		}
		for(int i = 0; i < inventory.size(); i++) {
			for(int j = 0; j < inventory.get(i).size(); j++) {
				
			}
		}
	}
	
	public void consumeItem(int positionX, int positionY) {
		inventory.get(positionX).set(positionY, null);
		for(int i = positionX; i < inventory.size(); i++) {
			for(int j = positionY; j < inventory.get(i).size(); j++) {
				if(inventory.get(i).get(j) != null ) {
					if(inventory.get(i).get(j).isPlaceholder()) {
						inventory.get(i).set(j, null);
					} else {
						return;
					}
				}
			}
		}
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
		
		if (e.getKeyCode() == KeyEvent.VK_E) {
			this.consumeItem(0, 0);
			this.printInventory();
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
