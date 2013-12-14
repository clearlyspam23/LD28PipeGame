package com.clearlyspam23.LD28.model;

public class Location {
	
	public int x;
	public int y;
	
	public Location()
	{
		
	}
	
	public Location(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Location(Location l)
	{
		x = l.x;
		y = l.y;
	}
	
	public void set(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void set(Location l)
	{
		x = l.x;
		y = l.y;
	}
	
	public Location add(Location l)
	{
		this.x+=l.x;
		this.y+=l.y;
		return this;
	}
	
	public Location getAdjacent(Direction d)
	{
		return new Location(this).add(d.offset);
	}
	
	public boolean equals(Location other)
	{
		return other.x == x && other.y == y;
	}
	
	public String toString()
	{
		return "( " + x + ", " + y + " )";
	}

}
