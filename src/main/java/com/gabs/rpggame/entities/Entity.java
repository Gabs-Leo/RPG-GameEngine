package com.gabs.rpggame.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gabs.rpggame.Main;
import com.gabs.rpggame.entities.collectables.Collectable;
import com.gabs.rpggame.world.Camera;
import com.gabs.rpggame.world.CollisionMask;
import com.gabs.rpggame.world.DamageType;
import com.gabs.rpggame.world.World;

public abstract class Entity {
	private String name;
	private String mapColor;
	private int x, y, width = Main.GameProperties.TileSize, height = Main.GameProperties.TileSize;
	@JsonIgnore
	private BufferedImage sprite;
	private CollisionMask collisionMask = new CollisionMask(Main.GameProperties.TileSize, Main.GameProperties.TileSize);
	
	private boolean targetable = false;
	private boolean takingDamage = false;
	
	private int maxLife;
	private int life;
	private int armor;
	private int magicalResistance;
	private DamageType damageType = DamageType.PHYSICAL_DAMAGE;
	private int damage;
	
	public Entity() {
		collisionMask.setVisible(Main.GameProperties.ShowCollisionMask);
	}
	public Entity(int x, int y, int width, int height) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}


	public void eventTick() {
		this.collisionMask.setX(this.getX() + this.getWidth() / 2 - this.collisionMask.getWidth() / 2);
		this.collisionMask.setY(this.getY() + this.getHeight() / 2 - this.collisionMask.getHeight() / 2);

	}
	
	public void heal(int value) {
		if(this.getLife() + value > this.getMaxLife())
			this.setLife(this.getMaxLife());
		else
			this.setLife(this.getLife() + value);
	}
	
	public void inflictDamage(int amount, DamageType damageType, Entity target) {
		if(target.isTargetable()) {
			if(!target.isTakingDamage()) {
				switch(damageType) {
					case PHYSICAL_DAMAGE: {
						int damage = World.calculatePostMitigationDamage(amount, target.getArmor());
						target.setLife(target.getLife() - damage <= 0 ? 0 : target.getLife() - damage);
						System.out.println("Dano: " + amount + ", vida restante: " + target.getLife());
						break;
					}
					case MAGICAL_DAMAGE: {
						int damage = World.calculatePostMitigationDamage(amount, target.getMagicalResistance());
						target.setLife(target.getLife() - damage <= 0 ? 0 : target.getLife() - damage);
						break;
					}
					case TRUE_DAMAGE: {
						int damage = amount;
						target.setLife(target.getLife() - damage <= 0 ? 0 : target.getLife() - damage);
						break;
					}
				}
				if(target instanceof Player)
					Main.player.setTakingDamage(true);
			}
		}
	}
	
	public void render(Graphics g) {
		if(this instanceof Prop || this instanceof Collectable)
			g.drawImage(sprite, this.getX() - Camera.getX(), this.getY() - Camera.getY(), null);
		
		if(this.getCollisionMask().isVisible()) {
			g.setColor(new Color(0f, 0f, 0f, .5f));
			g.fillRect((this.getCollisionMask().getX() - Camera.getX()), 
						this.getCollisionMask().getY() - Camera.getY(), 
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
		this.collisionMask.setX(x);
		this.x = x;
		return this;
	}

	public int getY() {
		return y;
	}

	public Entity setY(int y) {
		this.collisionMask.setY(y);
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
	public boolean isTargetable() {
		return targetable;
	}
	public Entity setTargetable(boolean targetable) {
		this.targetable = targetable;
		return this;
	}
	public boolean isTakingDamage() {
		return takingDamage;
	}
	public Entity setTakingDamage(boolean takingDamage) {
		this.takingDamage = takingDamage;
		return this;
	}
	public int getMaxLife() {
		return maxLife;
	}
	public Entity setMaxLife(int maxLife) {
		this.maxLife = maxLife;
		return this;
	}
	public int getLife() {
		return life;
	}
	public Entity setLife(int life) {
		this.life = life;
		return this;
	}
	public DamageType getDamageType() {
		return damageType;
	}
	public Entity setDamageType(DamageType damageType) {
		this.damageType = damageType;
		return this;
	}
	public int getArmor() {
		return armor;
	}
	public Entity setArmor(int armor) {
		this.armor = armor;
		return this;
	}
	public int getMagicalResistance() {
		return magicalResistance;
	}
	public Entity setMagicalResistance(int magicalResistance) {
		this.magicalResistance = magicalResistance;
		return this;
	}
	public int getDamage() {
		return damage;
	}
	public Entity setDamage(int damage) {
		this.damage = damage;
		return this;
	}
	public String getName() {
		return name;
	}
	public Entity setName(String name) {
		this.name = name;
		return this;
	}
	public String getMapColor() {
		return mapColor;
	}
	public Entity setMapColor(String mapColor) {
		this.mapColor = mapColor;
		return this;
	}
}
