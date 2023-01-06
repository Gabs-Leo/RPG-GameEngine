package com.gabs.rpggame.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.gabs.rpggame.Main;
import com.gabs.rpggame.entities.Enemy;
import com.gabs.rpggame.entities.Prop;
import com.gabs.rpggame.entities.collectables.Collectable;
import com.gabs.rpggame.entities.collectables.Equipment;

public class World {
	
	private static Tile[] tiles;
	public static int WIDTH, HEIGHT;
	
	public World () {}
	public World(String path) {
		try {
			BufferedImage mapSprite = ImageIO.read(getClass().getResource(path));
			WIDTH = mapSprite.getWidth();
			HEIGHT = mapSprite.getHeight();
			int[] pixels = new int[WIDTH * HEIGHT];
			tiles = new Tile[mapSprite.getWidth() * mapSprite.getHeight()];
			
			mapSprite.getRGB(0, 0, 
							WIDTH, 
							HEIGHT,
							pixels,
							0,
							WIDTH);
			
			for (int xx = 0; xx < WIDTH; xx++) {
				for(int yy = 0; yy < HEIGHT; yy++) {
					int currentTile = pixels[xx + (yy * WIDTH)];
					
					Tile tile = new Tile();
					tile.setX(xx * Main.GameProperties.TileSize)
						.setY(yy * Main.GameProperties.TileSize)
						.setType(CollisionType.NO_COLLISION)
						.setSprite(Main.spritesheet.getSprite(96, 64, Main.GameProperties.TileSize, Main.GameProperties.TileSize));
					if(currentTile == 0xFF303030) {
						tile.setType(CollisionType.BLOCK)
							.setSprite(Main.spritesheet.getSprite(96, 32, Main.GameProperties.TileSize, Main.GameProperties.TileSize));	
					} else if(currentTile == 0xFF000000) {
						tile.setType(CollisionType.BLOCK)
							.setSprite(Main.spritesheet.getSprite(0, 0, 1, 1));
					} else if(currentTile == 0xFF353535) {
						tile.setType(CollisionType.BLOCK)
							.setSprite(Main.spritesheet.getSprite(96, 0, Main.GameProperties.TileSize, Main.GameProperties.TileSize));
					} 
					if (currentTile == 0xFFFF0000) {
						tile.setX(xx * Main.GameProperties.TileSize)
							.setY(yy * Main.GameProperties.TileSize)
							.setType(CollisionType.NO_COLLISION)
							.setSprite(Main.spritesheet.getSprite(0, 288, Main.GameProperties.TileSize, Main.GameProperties.TileSize));
					} else if(currentTile == 0xFFDD36E5) {
						Main.player.setX(xx*Main.GameProperties.TileSize);
						Main.player.setY(yy*Main.GameProperties.TileSize);
						Main.eventTriggers.add(new EventTrigger().setAction(() -> System.out.println("xd")).setX(xx*Main.GameProperties.TileSize).setY(yy*Main.GameProperties.TileSize));
						
					}
					else if (currentTile == 0xFFFF00FF) {
						Collectable prop = new Collectable();
						prop
							.setSprite(Main.spritesheet.getSprite(544, 64, Main.GameProperties.TileSize, Main.GameProperties.TileSize))
							.setX(xx * Main.GameProperties.TileSize)
							.setY(yy * Main.GameProperties.TileSize);
						prop.setMethod(() -> {Main.player.heal(15); Main.entities.remove(prop);});
						Main.entities.add(prop);
					} else if (currentTile == 0xFFfe00ff) {
						Prop prop = new Prop();
						prop
							.setSprite(Main.spritesheet.getSprite(576, 64, Main.GameProperties.TileSize, Main.GameProperties.TileSize))
							.setX(xx * Main.GameProperties.TileSize)
							.setY(yy * Main.GameProperties.TileSize);
						
						tile.setType(CollisionType.BLOCK);
						Main.entities.add(prop);
					} else if (currentTile == 0xFFfc00ff) {
						Prop prop = new Prop();
						prop
							.setSprite(Main.spritesheet.getSprite(576, 32, Main.GameProperties.TileSize, Main.GameProperties.TileSize))
							.setX(xx * Main.GameProperties.TileSize)
							.setY(yy * Main.GameProperties.TileSize);
						
						Main.frontEntities.add(prop);
					} else if (currentTile == 0xFFfb00ff) {
						Prop prop = new Prop();
						prop
							.setSprite(Main.spritesheet.getSprite(576, 0, Main.GameProperties.TileSize, Main.GameProperties.TileSize))
							.setX(xx * Main.GameProperties.TileSize)
							.setY(yy * Main.GameProperties.TileSize);
						Main.frontEntities.add(prop);
					} else if (currentTile == 0xFFfd00ff) {
						Prop prop = new Prop();
						prop
							.setSprite(Main.spritesheet.getSprite(608, 64, Main.GameProperties.TileSize, Main.GameProperties.TileSize))
							.setX(xx * Main.GameProperties.TileSize)
							.setY(yy * Main.GameProperties.TileSize);
						Main.entities.add(prop);
					} else if (currentTile == 0xFF0000FF) {
						Prop prop = new Prop();
						prop
							.setSprite(Main.spritesheet.getSprite(352, 160, Main.GameProperties.TileSize, Main.GameProperties.TileSize))
							.setX(xx * Main.GameProperties.TileSize)
							.setY(yy * Main.GameProperties.TileSize);
						
						tile.setType(CollisionType.BLOCK);
						Main.entities.add(prop);
						
					//Enemy
					} else if (currentTile == 0xFFFE0000) {
						Enemy enemy = new Enemy();
						enemy.getCollisionMask()
							.setWidth(16).setHeight(16);
						enemy
							.setDamage(20);
						enemy
							.setX(xx * Main.GameProperties.TileSize)
							.setY(yy * Main.GameProperties.TileSize);
						Main.enemies.add(enemy);
					}
					//Food
					else if(currentTile == 0xFF00ff85) {
						Collectable food = new Collectable();
						food.setSprite(Main.spritesheet.getSprite(64, 320, Main.GameProperties.TileSize, Main.GameProperties.TileSize))
							.setX(xx * Main.GameProperties.TileSize)
							.setY(yy * Main.GameProperties.TileSize);
						
						food.setMethod(() -> Main.player.heal(10));
						Main.entities.add(food);
					}
					//Ammo
					else if(currentTile == 0xFFfffb00) {
						Collectable ammo = new Collectable();
						ammo.setSprite(Main.spritesheet.getSprite(32, 320, Main.GameProperties.TileSize, Main.GameProperties.TileSize))
							.setX(xx * Main.GameProperties.TileSize)
							.setY(yy * Main.GameProperties.TileSize);
						
						ammo.setMethod(() -> Main.player.setAmmo(Main.player.getAmmo() + 10));
						Main.entities.add(ammo);
					}
					//Sword
					else if(currentTile == 0xFFfa9aff) {
						Equipment sword = Main.assets.findEquipmentByName("sword1").get();
						sword
							.setX(xx * Main.GameProperties.TileSize)
							.setY(yy * Main.GameProperties.TileSize);
						
						sword.setMethod(() -> sword.equipTo(Main.player));
						Main.entities.add(sword);
					}
					tiles[xx + (yy * WIDTH)] = tile;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void render(Graphics g) {
		int xStart = Camera.getX() / Main.GameProperties.TileSize;
		int yStart = Camera.getY() / Main.GameProperties.TileSize;
		
		int xFinal = xStart + Main.GameProperties.ScreenWidth*Main.GameProperties.ScreenScale / Main.GameProperties.TileSize;
		int yFinal = yStart + Main.GameProperties.ScreenHeight*Main.GameProperties.ScreenScale / Main.GameProperties.TileSize;
		//int xFinal = xStart + Main.GameProperties.ScreenWidth / GameProperties.TILE_SIZE;
		//int yFinal = yStart + Main.GameProperties.ScreenHeight / GameProperties.TILE_SIZE;
		
		
		for(int xx = xStart; xx <= xFinal; xx++) {
			for(int yy = yStart; yy <= yFinal; yy++) {
				
				if(xx < 0 || yy < 0 ||
				   xx >= WIDTH || yy >= HEIGHT) continue;
				tiles[xx + (yy * WIDTH)].render(g);
			}
		}
		/*
		for(int xx = 0; xx < WIDTH; xx++) {
			for(int yy = 0; yy < HEIGHT; yy++) {
				tiles[xx + (yy * WIDTH)].render(g);
			}
		}
		*/
	}
	
	public static boolean placeFree(int nextX, int nextY) {
		int x = Main.player.getWidth();
		int y = Main.player.getHeight();
		
		int x1 = nextX / x;
		int y1 = nextY / y;
		
		int x2 = (nextX + x - 1) / x;
		int y2 = nextY / y;
		
		int x3 = nextX / x;
		int y3 = (nextY + y - 1) / y;
		
		int x4 = (nextX + x - 1) / x;
		int y4 = (nextY + y - 1) / y;
		
		try {
			return 	tiles[x1 + y1*World.WIDTH].getType() == CollisionType.NO_COLLISION &&
					tiles[x2 + y2*World.WIDTH].getType() == CollisionType.NO_COLLISION &&
					tiles[x3 + y3*World.WIDTH].getType() == CollisionType.NO_COLLISION &&
					tiles[x4 + y4*World.WIDTH].getType() == CollisionType.NO_COLLISION;
		}catch(Exception e) {
			return false;
		}
	}
	
	public static int calculatePostMitigationDamage(int damage, int resistance) {
		return damage / (1 + resistance / 100);
	};
	
	public Tile[] getTiles() {
		return tiles;
	}
	public World setTiles(Tile[] tiles) {
		World.tiles = tiles;
		return this;
	}
	
	public static int getWIDTH() {
		return WIDTH;
	}
	public static int getHEIGHT() {
		return HEIGHT;
	}
}
