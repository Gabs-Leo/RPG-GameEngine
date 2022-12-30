package com.gabs.rpggame.graphics;

import java.awt.Color;
import java.awt.Graphics;

import com.gabs.rpggame.Main;

public class Transition {
	private boolean done = true;
	private float opacity = 0;
	
	public void render(Graphics g) {
		if(opacity >= 1f)
			opacity = 1f;
		else if(opacity <= 0f)
			opacity = 0f;
		
		
		if(!isDone()) {
			g.setColor(new Color(0,0,0, opacity));
			g.fillRect(0, 0, Main.GameProperties.ScreenWidth, Main.GameProperties.ScreenHeight);
			opacity += .05f;
		} else {
			g.setColor(new Color(0,0,0, opacity));
			g.fillRect(0, 0, Main.GameProperties.ScreenWidth, Main.GameProperties.ScreenHeight);
			opacity -= .05f;
		}

	}
	
	public void startTransition() {
		opacity = 0f;
		setDone(false);
	}
	
	public void endTransition() {
		opacity = 1f;
		setDone(true);
	}

	public boolean isDone() {
		return done;
	}

	public Transition setDone(boolean done) {
		this.done = done;
		return this;
	}
}