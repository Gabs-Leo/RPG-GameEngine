package com.gabs.rpggame.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.gabs.rpggame.Main;

public class Dialog implements UI{
	
	private String speaker = "You";
	private String message = "pinas pipinas";

	@Override
	public void trigger() {
		// TODO Auto-generated method stub
	} 

	@Override
	public void render(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, Main.GameProperties.ScreenHeight-70, Main.GameProperties.ScreenWidth, 70);
		g.setColor(Color.black);
		g.fillRect(5, Main.GameProperties.ScreenHeight-70+5, Main.GameProperties.ScreenWidth-10, 70-10);
	
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial", Font.BOLD, 10));
		g.drawString(speaker, 10, 200);
		
		g.setFont(new Font("arial", Font.PLAIN, 10));
		g.drawString(message, 10, 210);
	}

	public String getSpeaker() {
		return speaker;
	}

	public Dialog setSpeaker(String speaker) {
		this.speaker = speaker;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public Dialog setMessage(String message) {
		this.message = message;
		return this;
	}
	
	
}
