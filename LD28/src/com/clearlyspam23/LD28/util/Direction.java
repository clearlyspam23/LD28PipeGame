package com.clearlyspam23.LD28.util;

public enum Direction {
	
	RIGHT(new Location(1, 0)), 
	UP(new Location(0, 1)), 
	LEFT(new Location(-1, 0)),
	DOWN(new Location(0, -1)), 
	NO_DIRECTION(new Location());
	
	public final Location offset;
	
	private Direction(Location offset)
	{
		this.offset = offset;
	}
	
	public Direction getOpposite()
	{
		switch(this)
		{
		case RIGHT:
			return LEFT;
		case UP:
			return DOWN;
		case LEFT:
			return RIGHT;
		case DOWN:
			return UP;
		}
		return null;
	}

}
