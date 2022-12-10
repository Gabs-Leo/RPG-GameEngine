package com.gabs.rpggame;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.gabs.rpggame.entities.DamageShot;
import com.gabs.rpggame.entities.Enemy;
import com.gabs.rpggame.entities.Entity;
import com.gabs.rpggame.entities.Player;
import com.gabs.rpggame.graphics.Spritesheet;
import com.gabs.rpggame.graphics.UI;
import com.gabs.rpggame.world.World;

public class Main extends Canvas implements Runnable {
	/*
	 * Made with <3 By Gabs
	 */
	private static final long serialVersionUID = 5837494021292687605L;
	public static JFrame frame;

	public static GameProperties GameProperties;
	private Thread thread;
	private boolean running = true;
	
	public static Player player;
	public static List<Entity> entities;
	public static List<Entity> frontEntities;
	public static List<DamageShot> damageShots;
	
	public static List<Enemy> enemies;
	public static Spritesheet spritesheet;
	public static World world;
	
	public static Random random;
	public UI ui;
	
	private BufferedImage image;
	
	public static void main(String args[]) {
		try {
			//File file = new File("game-properties.yml");
			File file = new File(Thread.currentThread().getContextClassLoader().getResource("game-properties.yml").getFile());
			ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
			GameProperties = mapper.readValue(file, GameProperties.class);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e);
		}
		Main main = new Main();
		main.start();
	};
	
	public Main() {
		random = new Random();
		startFrame();
		image = new BufferedImage(GameProperties.ScreenWidth, GameProperties.ScreenHeight, BufferedImage.TYPE_INT_RGB);
		entities = new ArrayList<Entity>();
		frontEntities = new ArrayList<Entity>();
		enemies = new ArrayList<Enemy>();
		spritesheet = new Spritesheet("/HaloweenSpritesheet.png");
		damageShots = new ArrayList<>();
		
		ui = new UI();
		player = new Player();
		player
			.setWidth(GameProperties.TileSize)
			.setHeight(GameProperties.TileSize)
			.setSprite(spritesheet.getSprite(0, 0, GameProperties.TileSize, GameProperties.TileSize));
		player
			.setSpeed(4);
		addKeyListener(player);
		
		world = new World("/map.png");
		entities.add(player);
	};
	
	public void eventTick() {
		for(int i = 0; i < entities.size(); i++) {
			entities.get(i).eventTick();
		}
		for(int i = 0; i < enemies.size(); i++)
			enemies.get(i).eventTick();
		frontEntities.forEach(i -> i.eventTick());
		for(int i = 0; i < damageShots.size(); i++)
			damageShots.get(i).eventTick();
		
		if(Main.player.getLife() <= 0) {
			entities = new ArrayList<Entity>();
			frontEntities = new ArrayList<Entity>();
			enemies = new ArrayList<Enemy>();
			
			spritesheet = new Spritesheet("/HaloweenSpritesheet.png");
			world = new World("/map.png");
			ui = new UI();
			player = new Player();
			player
				.setWidth(GameProperties.TileSize)
				.setHeight(GameProperties.TileSize)
				.setSprite(spritesheet.getSprite(0, 0, GameProperties.TileSize, GameProperties.TileSize));
			player
				.setSpeed(4);
			addKeyListener(player);

			entities.add(player);
		}
	}
	
	public void render() {
		var bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, GameProperties.ScreenWidth, GameProperties.ScreenHeight);
		
		world.render(g);
		for(int i = 0; i < entities.size(); i++) 
			entities.get(i).render(g);
		for(int i = 0; i < enemies.size(); i++) 
			enemies.get(i).render(g);
		for(int i = 0; i < frontEntities.size(); i++) 
			frontEntities.get(i).render(g);
		for(int i = 0; i < damageShots.size(); i++) 
			damageShots.get(i).render(g);
		ui.render(g);
		
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, GameProperties.ScreenWidth*GameProperties.ScreenScale, GameProperties.ScreenHeight*GameProperties.ScreenScale, null);
		bs.show();
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		running = true;
		thread.start();
	}
	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double nanoSeconds = 1000000000 / amountOfTicks;
		double delta = 0;
		
		@SuppressWarnings("unused")
		int frames = 0;
		
		double timer = System.currentTimeMillis();
		requestFocus();
		
		int targetableCounter = 0;
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / nanoSeconds;
			
			lastTime = now;
			if(delta >= 1) {
				eventTick();
				render();
				frames++;
				delta--;
			}
			if(System.currentTimeMillis() - timer >= 1000) {
				//System.out.println("FPS: " + frames);
				if(Main.player.isTakingDamage()) {
					targetableCounter += 1;
					System.out.println(targetableCounter);
					if(targetableCounter == 2) {
						targetableCounter = 0;
						Main.player.setTakingDamage(false);
					}
				}
				frames = 0;
				timer += 1000;
			}
		}
		stop();
	};
	
	public void startFrame() {		
		this.setPreferredSize(new Dimension(GameProperties.ScreenWidth*GameProperties.ScreenScale, GameProperties.ScreenHeight*GameProperties.ScreenScale));
		frame = new JFrame("Game #1");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	};
}
