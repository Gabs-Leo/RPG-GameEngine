package com.gabs.rpggame.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.gabs.rpggame.GameState;
import com.gabs.rpggame.Main;
import com.gabs.rpggame.world.Direction;

public class PauseScreen implements UI {
	
	private int option = 0;
	
	public void render(Graphics g) {
		g.setColor(new Color(0,0,0, .5f));
		g.fillRect(0, 0, Main.GameProperties.ScreenWidth, Main.GameProperties.ScreenHeight);
		
		g.setColor(new Color(100, 100, 100));
		g.setFont(new Font("Courier New", Font.BOLD, 30));
		
		g.drawString("Continue", 10, 100);
		g.drawString("Main Menu", 10, 130);
		g.drawString("Exit", 10, 160);
		
		g.setColor(new Color(255, 255, 255));
		
		switch(option) {
			case 0:
				g.drawString("Continue", 10, 100);
				break;
			case 1:
				g.drawString("Main Menu", 10, 130);
				break;
			case 2:
				g.drawString("Exit", 10, 160);
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

	@Override
	public void trigger() {
		switch(option) {
			case 0:
				Main.state = GameState.RUNNING;
				break;
			case 1:
				Main.state = GameState.MAIN_MENU;
				break;
			case 2:
				Main.closeGame();
				break;
		}
	}
}