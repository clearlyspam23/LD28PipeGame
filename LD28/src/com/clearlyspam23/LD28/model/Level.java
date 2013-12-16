package com.clearlyspam23.LD28.model;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.clearlyspam23.LD28.util.Direction;
import com.clearlyspam23.LD28.util.Location;

public class Level {
	
	public int[][] levelData;
	public int[] allowedPipes;
	public int numMoves;
	public Location startLoc;
	public Direction startDirection;
	public Location endLoc;
	
	public TextureRegion background;
	
	public String tutorialText;

}
