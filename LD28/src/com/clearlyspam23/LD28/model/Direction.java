package com.clearlyspam23.LD28.model;

public enum Direction {
	
	RIGHT(1, 0), UP(0, -1), LEFT(-1, 0), DOWN(0, 1);
	
	public final Location offset;
	
	private Direction(int offsetx, int offsety)
	{
		this.offset = new Location(offsetx, offsety);
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
