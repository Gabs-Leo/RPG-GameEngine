package com.gabs.rpggame.graphics;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gabs.rpggame.Main;
import com.gabs.rpggame.world.Coordinate;
import com.gabs.rpggame.world.Direction;


public class Animation {
	@JsonIgnore
	private List<BufferedImage> images = new ArrayList<>();
	private Direction direction;
	private List<Coordinate> coordinates = new ArrayList<>();
	
	private int frames = 0,
				animDelay = 0,
				index = 0,
				startIndex = 0;
	
	@JsonCreator
	public Animation(@JsonProperty("startIndex") int startIndex, @JsonProperty("animdDelay") int animDelay, @JsonProperty("framePositions") List<List<Integer>> framePositions) {
		this.index = startIndex;
		this.startIndex = startIndex;
		this.animDelay = animDelay;
		framePositions.forEach(positions -> images.add(Main.spritesheet.getSprite(positions.get(0), positions.get(1))));
	}
	
	public Animation(int startIndex, int animDelay) {
		coordinates.forEach(coordinate -> images.add(Main.spritesheet.getSprite(coordinate.getX(), coordinate.getY())));
		
		this.startIndex = startIndex;
		this.index = startIndex;
		this.animDelay = animDelay;
	}
	
	public Animation(Direction direction, int startIndex, int index, int animDelay, List<Coordinate> coordinates) {
		this.direction = direction;
		this.startIndex = startIndex;
		this.index = startIndex;
		this.animDelay = animDelay;
		
		this.coordinates = coordinates;
	};
	
	public Animation(int startIndex, int animDelay, BufferedImage ... images) {
		for(BufferedImage image : images)
			this.images.add(image);
		
		this.startIndex = startIndex;
		this.index = startIndex;
		this.animDelay = animDelay;
	}
	
	public void run() {
		this.setFrames(this.getFrames()+1);
		if(this.getFrames() == this.getAnimDelay()) {
			this.setFrames(0);
			this.setIndex(this.getIndex()+1);
			if(this.getIndex() > this.getImages().size()-1)
				this.setIndex(0);
		};
	}

	public int getFrames() {
		return frames;
	}

	public Animation setFrames(int frames) {
		this.frames = frames;
		return this;
	}

	public int getAnimDelay() {
		return animDelay;
	}

	public Animation setAnimDelay(int animDelay) {
		this.animDelay = animDelay;
		return this;
	}

	public int getIndex() {
		return index;
	}

	public Animation setIndex(int index) {
		this.index = index;
		return this;
	}

	public List<BufferedImage> getImages() {
		return images;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public Animation setStartIndex(int startIndex) {
		this.startIndex = startIndex;
		return this;
	}

	public Direction getDirection() {
		return direction;
	}

	public Animation setDirection(Direction direction) {
		this.direction = direction;
		return this;
	}
}
