package com.gabs.rpggame.entities;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import com.gabs.rpggame.GameState;
import com.gabs.rpggame.Main;
import com.gabs.rpggame.entities.collectables.Collectable;
import com.gabs.rpggame.graphics.Animation;
import com.gabs.rpggame.world.Camera;
import com.gabs.rpggame.world.Direction;
import com.gabs.rpggame.world.World;

public class Player extends AliveEntity {
	
	private boolean right, left, up, down;
	private boolean moving;
	private boolean attacking;
	
	private Direction direction;
	private int speed;
	
	private Animation downAnimation;
	private Animation leftAnimation;
	private Animation rightAnimation;
	private Animation upAnimation;
	/*
	private Animation damageDownAnimation = new Animation(1, 5, 
			Main.spritesheet.getSprite(0+256, 0+256, this.getWidth(), this.getHeight()),
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
	*/
	private int ammo = 0;
	private List<Collectable> inventory = new ArrayList<>();
	
	public Player() {
		super();
		for(int i = 0; i < Main.GameProperties.InventorySizeX * Main.GameProperties.InventorySizeY; i++)
			inventory.add(null);
		
		this.setWidth(32)
			.setHeight(32);
		
		this.setDirection(Direction.DOWN);
		this.setTargetable(true);
		this.setMaxLife(Main.GameProperties.PlayerMaxLife);
		this.setLife(this.getMaxLife());
		this.setArmor(Main.GameProperties.PlayerArmor);
		
		downAnimation = new Animation(1, 5, 
				Main.spritesheet.getSprite(0, 0, this.getWidth(), this.getHeight()*2),
				Main.spritesheet.getSprite(32, 0, this.getWidth(), this.getHeight()*2),
				Main.spritesheet.getSprite(64, 0, this.getWidth(), this.getHeight()*2));
		
		leftAnimation = new Animation(1, 5, 
				Main.spritesheet.getSprite(0, 64, this.getWidth(), this.getHeight()*2),
				Main.spritesheet.getSprite(32, 64, this.getWidth(), this.getHeight()*2),
				Main.spritesheet.getSprite(64, 64, this.getWidth(), this.getHeight()*2));
		
		rightAnimation = new Animation(1, 5, 
				Main.spritesheet.getSprite(0, 64*2, this.getWidth(), this.getHeight()*2),
				Main.spritesheet.getSprite(32, 64*2, this.getWidth(), this.getHeight()*2),
				Main.spritesheet.getSprite(64, 64*2, this.getWidth(), this.getHeight()*2));
		
		upAnimation = new Animation(1, 5, 
				Main.spritesheet.getSprite(0, 64*3, this.getWidth(), this.getHeight()*2),
				Main.spritesheet.getSprite(32, 64*3, this.getWidth(), this.getHeight()*2),
				Main.spritesheet.getSprite(64, 64*3, this.getWidth(), this.getHeight()*2));
	}

	public void collectItem(Collectable item) {
		/*
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
			for(int j = 0; j s< inventory.get(i).size(); j++) {
				
			}
		}*/
	}
	
	public void consumeItem(int positionX, int positionY) {
		/*inventory.get(positionX).set(positionY, null);
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
		}*/
	}

	@Override
	public void eventTick() {
		this.setMoving(false);
		if(!this.isAttacking()) {
			if(this.isRight() && World.placeFree(this.getX() + this.getSpeed(), this.getY()) && this.getX() + this.getWidth() <= World.WIDTH*Main.GameProperties.TileSize) {
				this.setMoving(true);
				if(this.isDown() ||this.isUp())
					this.setX(this.getX() + this.getSpeed()/2);
				else
					this.setX(this.getX() + this.getSpeed());
				this.setDirection(Direction.RIGHT);
				
			}else if (this.isLeft() && World.placeFree(this.getX() - this.getSpeed(), this.getY()) && this.getX() >= 0) {
				this.setMoving(true);
				if(this.isDown() || this.isUp())
					this.setX(this.getX() - this.getSpeed()/2);
				else
					this.setX(this.getX() - this.getSpeed());
				this.setDirection(Direction.LEFT);
			}
			if(this.isUp() && World.placeFree(this.getX(), this.getY() - this.getSpeed()) && this.getY() >= 0) {
				this.setMoving(true);
				this.setY(this.getY() - this.getSpeed());
				this.setDirection(Direction.UP);
			}else if (this.isDown() && World.placeFree(this.getX(), this.getY() + this.getSpeed())) {
				this.setMoving(true);
				this.setY(this.getY() + this.getSpeed());
				this.setDirection(Direction.DOWN);
			}
		}
		if(this.isMoving()) {
			getDownAnimation().run();
			getUpAnimation().run();
			getLeftAnimation().run();
			getRightAnimation().run();
			/*
			damageDownAnimation.run();
			damageUpAnimation.run();
			damageLeftAnimation.run();
			damageRightAnimation.run();
			*/
		}
		
		if(Main.GameProperties.Clamp) {
			Camera.setX(Camera.clamp(this.getX() - Main.GameProperties.ScreenWidth/2, 0, World.WIDTH*Main.GameProperties.TileSize - Main.GameProperties.ScreenWidth));
			Camera.setY(Camera.clamp(this.getY() - Main.GameProperties.ScreenHeight/2, 0, World.HEIGHT*Main.GameProperties.TileSize - Main.GameProperties.ScreenHeight));
		} else {
			Camera.setX(this.getX() - Main.GameProperties.ScreenWidth/2);
			Camera.setY(this.getY() - Main.GameProperties.ScreenHeight/2);
		}
		
		if(this.isAttacking()) {
			this.setAttacking(false);
			DamageShot attack = new DamageShot();
			attack.setWidth(32)
			  .setHeight(32);
			attack.setDirection(direction)
				  .setSpeed(5)
				  .setRange(10)
				  .setDamage(20);
			if(this.direction == Direction.RIGHT) {
				attack
				  .setX(this.getX()+this.getWidth()/2)
				  .setY(this.getY());
			} else if(this.direction == Direction.LEFT) {
				attack
				  .setX(this.getX()-this.getWidth()/2)
				  .setY(this.getY());
			} else if(this.direction == Direction.UP) {
				attack
				  .setX(this.getX())
				  .setY(this.getY()-this.getWidth()/2);
			} else if(this.direction == Direction.DOWN) {
				attack
				  .setX(this.getX())
				  .setY(this.getY()+this.getWidth()/2);
			}

			Main.damageShots.add(attack);
		}
		
		if(this.getLife() <= 0) {
			Main.state = GameState.GAME_OVER;
		}
		
		super.eventTick();
	}
	
	@Override
	public void render(Graphics g) {
		if(!this.isTakingDamage()) {
			if(this.getDirection() == Direction.DOWN) {
				g.drawImage(getDownAnimation().getImages().get(getDownAnimation().getIndex()), this.getX() - Camera.getX(), this.getY() - Camera.getY() - 32, null);
				//if(this.getEquipments().get(4) != null)
				//	g.drawImage(this.getEquipments().get(4).getAnimations().get(0).getImages().get(getDownAnimation().getIndex()), this.getX() - Camera.getX(), this.getY() - Camera.getY() - 32, null);
			}else if(this.getDirection() == Direction.UP) {
				g.drawImage(getUpAnimation().getImages().get(getUpAnimation().getIndex()), this.getX() - Camera.getX(), this.getY() - Camera.getY() - 32, null);
			}
			
			if (this.getDirection() == Direction.RIGHT) {
				g.drawImage(getRightAnimation().getImages().get(getRightAnimation().getIndex()), this.getX() - Camera.getX(), this.getY() - Camera.getY() - 32, null);
			}else if(this.getDirection() == Direction.LEFT) {
				g.drawImage(getLeftAnimation().getImages().get(getLeftAnimation().getIndex()), this.getX() - Camera.getX(), this.getY() - Camera.getY() - 32, null);
			}
		} else {
			/*
			if(this.getDirection() == Direction.DOWN) {
				g.drawImage(damageDownAnimation.getImages().get(damageDownAnimation.getIndex()), this.getX() - Camera.getX(), this.getY() - Camera.getY(), null);
			}else if(this.getDirection() == Direction.UP) {
				g.drawImage(damageUpAnimation.getImages().get(damageUpAnimation.getIndex()), this.getX() - Camera.getX(), this.getY() - Camera.getY(), null);
			}
			
			if (this.getDirection() == Direction.RIGHT) {
				g.drawImage(damageRightAnimation.getImages().get(damageRightAnimation.getIndex()), this.getX() - Camera.getX(), this.getY() - Camera.getY(), null);
			}else if(this.getDirection() == Direction.LEFT) {
				g.drawImage(damageLeftAnimation.getImages().get(damageLeftAnimation.getIndex()), this.getX() - Camera.getX(), this.getY() - Camera.getY(), null);
			}*/
		}
		super.render(g);
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
	public Direction getDirection() {
		return direction;
	}
	public Player setDirection(Direction direction) {
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
	public boolean isAttacking() {
		return attacking;
	}
	public Player setAttacking(boolean attacking) {
		this.attacking = attacking;
		return this;
	}

	public Animation getDownAnimation() {
		return downAnimation;
	}

	public Animation getUpAnimation() {
		return upAnimation;
	}

	public Animation getLeftAnimation() {
		return leftAnimation;
	}

	public Animation getRightAnimation() {
		return rightAnimation;
	}
}
