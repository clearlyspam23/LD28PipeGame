package com.clearlyspam23.LD28.model;

import java.util.ArrayList;

public class Pipe {
	
	private static final int MAX_FILL = 100;
	
	private PipeDef definition;
	private int fill;
	private Location location = new Location();
	private Direction inputFrom;
	private ArrayList<Direction> outputs;
	
	public Pipe()
	{
		
	}
	
	public Pipe(PipeDef def)
	{
		this.definition = def;
	}
	
	public void setDefinition(PipeDef def)
	{
		definition = def;
	}
	
	public boolean isFull()
	{
		return fill>=MAX_FILL;
	}
	
	public int getFill()
	{
		return fill;
	}
	
	public int getOverflow()
	{
		return fill-MAX_FILL;
	}
	
	public void fillUp(int amount)
	{
		fill+=amount;
	}
	
	public void setLocation(int x, int y)
	{
		location.set(x, y);
	}
	
	public Location getLocation()
	{
		return location;
	}
	
	public boolean canAcceptFrom(Location l)
	{
		Location current;
		for(Direction d : definition.inputs)
		{
			current = location.getAdjacent(d);
			if(l.equals(current))
				return true;
		}
		return false;
	}
	
	public void inputFrom(Direction d)
	{
		inputFrom = d;
		outputs = new ArrayList<Direction>();
		for(Direction dir : definition.outputs)
			if(!dir.equals(d))
				outputs.add(dir);
	}
	
	public Direction getInput()
	{
		return inputFrom;
	}
	
	public ArrayList<Direction> getOutputs()
	{
		return outputs;
	}

}
