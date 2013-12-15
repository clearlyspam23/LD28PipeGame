package com.clearlyspam23.LD28.model;

import java.util.ArrayList;

import com.clearlyspam23.LD28.util.Direction;
import com.clearlyspam23.LD28.util.Location;

public class Pipe {
	
	public static final int MAX_FILL = 100;
	
	private PipeDef definition;
	private int fill;
	private Location location = new Location();
	private Direction inputFrom = Direction.NO_DIRECTION;
	private ArrayList<Direction> outputs;
	
	public Pipe()
	{
		
	}
	
<<<<<<< HEAD
	public Pipe(Pipe other)
	{
		set(other);
	}
	
=======
>>>>>>> 6d137583960acb2c40b57bee8f6ba28261714aaa
	public Pipe(PipeDef def)
	{
		this.definition = def;
	}
	
	public void setDefinition(PipeDef def)
	{
		definition = def;
	}
	
	public PipeDef getDefinition()
	{
		return definition;
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
<<<<<<< HEAD
	
	public void set(Pipe pipe)
	{
		definition = pipe.definition;
		fill = pipe.fill;
		location.set(pipe.location);
		inputFrom = pipe.inputFrom;
		outputs = pipe.outputs;
	}
=======
>>>>>>> 6d137583960acb2c40b57bee8f6ba28261714aaa

}
