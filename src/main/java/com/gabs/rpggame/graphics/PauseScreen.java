package com.gabs.rpggame.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.gabs.rpggame.Main;
import com.gabs.rpggame.world.Direction;

public class PauseScreen {
	
	private int option = 0;
	
	public void render(Graphics g) {
		g.setColor(new Color(0,0,0, .5f));
		g.fillRect(0, 0, Main.GameProperties.ScreenWidth, Main.GameProperties.ScreenHeight);
		
		g.setColor(new Color(100, 100, 100));
		g.setFont(new Font("Courier New", Font.BOLD, 40));
		g.drawString("Continue", 250, 100);
		
		g.setColor(new Color(100, 100, 100));
		g.setFont(new Font("Courier New", Font.BOLD, 40));
		g.drawString("Main Menu", 250, 160);
		
		g.setColor(new Color(100, 100, 100));
		g.setFont(new Font("Courier New", Font.BOLD, 40));
		g.drawString("Exit", 250, 220);
		
		switch(option) {
			case 0:
				g.setColor(new Color(255, 255, 255));
				g.setFont(new Font("Courier New", Font.BOLD, 40));
				g.drawString("Continue", 250, 100);
				break;
			case 1:
				g.setColor(new Color(255, 255, 255));
				g.setFont(new Font("Courier New", Font.BOLD, 40));
				g.drawString("Main Menu", 250, 160);
				break;
			case 2:
				g.setColor(new Color(255, 255, 255));
				g.setFont(new Font("Courier New", Font.BOLD, 40));
				g.drawString("Exit", 250, 220);
				break;		
		}
	}
	
	public void changeOption( Direction direction ) {
		switch(direction) {
		case DOWN:
			option = option == 2 ? 0 : option+1;
			break;
		case UP:
			option = option == 0 ? 2 : option-1;
			break;
		default:
			break;
		}
	}

	public int getOption() {
		return option;
	}

	public PauseScreen setOption(int option) {
		this.option = option;
		return this;
	}
}