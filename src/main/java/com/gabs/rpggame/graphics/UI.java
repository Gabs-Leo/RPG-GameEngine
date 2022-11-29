package com.gabs.rpggame.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.gabs.rpggame.Main;

public class UI {
	public void render(Graphics g) {
		g.setColor(new Color(50, 50, 50));
		g.fillRect(0, 0, Main.WIDTH, 40);
		
		g.setColor(Color.red);
		g.fillRect(10, 10, Main.player.getMaxLife(), 20);
		
		g.setColor(Color.green);
		g.fillRect(10, 10, Main.player.getLife(), 20);
		
		g.setFont(new Font("Javanese Text", Font.PLAIN, 17));
		g.drawString("x"+Main.player.getAmmo(), Main.WIDTH - 30, 25);
	}
}
