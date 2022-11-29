package com.gabs.rpggame.world;

public class Camera {
	
	private static int x, y;
	
	public Camera () {}
	
	public static int clamp(int currentX, int xMin, int xMax) {
		if(currentX < xMin) {
			currentX = xMin;
		} 
		if (currentX > xMax) {
			currentX = xMax;
		}
		return currentX;
	}

	public static int getX() {
		return x;
	}

	public static void setX(int x) {
		Camera.x = x;

	}

	public static int getY() {
		return y;
	}

	public static void setY(int y) {
		Camera.y = y;

	}
}
