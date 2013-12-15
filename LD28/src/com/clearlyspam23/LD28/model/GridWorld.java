package com.clearlyspam23.LD28.model;

import java.util.ArrayList;

import com.clearlyspam23.LD28.util.Direction;
import com.clearlyspam23.LD28.util.Location;

public class GridWorld {
	
	private Pipe[][] pipes;
	private ArrayList<Location> currentFillLocations;
	private ArrayList<Location> finishingLocations;
	private boolean hasWon;
	
	//private ArrayList<Location> lastFillLocations = new ArrayList<Location>();
	
	public GridWorld(int width, int height)
	{
		pipes = new Pipe[width][height];
		currentFillLocations = new ArrayList<Location>();
		finishingLocations = new ArrayList<Location>();
	}
	
	public GridWorld(GridWorld other)
	{
		set(other);
	}
	
	public int getWidth()
	{
		return pipes.length;
	}
	
	public int getHeight()
	{
		return pipes[0].length;
	}
	
	public void addNormalPipe(PipeDef def, int x, int y)
	{
		Pipe p = new Pipe(def);
		p.setLocation(x, y);
		pipes[x][y] = p;
	}
	
	public void simulate(int timeStep)
	{
		//lastFillLocations.clear();
		ArrayList<Location> fillLocations = new ArrayList<Location>();
		Pipe[] pipes = getCurrentPipes();
		for(Pipe p : pipes)
		{
			//it's assumed the pipes wont be null
			p.fillUp(timeStep);
			if(p.isFull())
			{
				ArrayList<Direction> outputDirections = p.getOutputs();
				for(Direction outputdir : outputDirections)
				{
					Location outloc = p.getLocation().getAdjacent(outputdir);
					if(doesPipeExist(outloc))
					{
						Pipe o = getPipe(outloc);
						if(o.canAcceptFrom(p.getLocation()))
						{
							o.inputFrom(outputdir.getOpposite());
							o.fillUp(p.getOverflow());
							fillLocations.add(o.getLocation());
							if(finishingLocations.contains(o.getLocation()))
								hasWon = true;
						}
					}
				}
			}
			else
				fillLocations.add(p.getLocation());
		}
		//lastFillLocations = currentFillLocations;
		currentFillLocations = fillLocations;
	}
	
	public void addStart(Location l, Direction input)
	{
		currentFillLocations.add(l);
		Pipe p = getPipe(l);
		p.inputFrom(input);
	}
	
	public void addFinish(Location l)
	{
		finishingLocations.add(l);
	}
	
	public Pipe[] getCurrentPipes()
	{
		Pipe[] ans = new Pipe[currentFillLocations.size()];
		for(int i = 0; i < currentFillLocations.size(); i++)
		{
			ans[i] = getPipe(currentFillLocations.get(i));
		}
		return ans;
	}
	
	public Pipe[][] getAllPipes()
	{
		return pipes;
	}
	
	public Pipe getPipe(Location l)
	{
		return pipes[l.x][l.y];
	}
	
	public boolean isValidLocation(Location l)
	{
		return l.x>=0&&l.x<pipes.length&&l.y>=0&&l.y<pipes[0].length;
	}
	
	public boolean doesPipeExist(Location l)
	{
		return isValidLocation(l)&&!isLocationEmpty(l);
	}
	
	public boolean hasLost()
	{
		return !hasWon&&currentFillLocations.size()==0;
	}
	
	public boolean hasWon()
	{
		return hasWon;
	}
	
	public boolean isLocationEmpty(Location l)
	{
		return getPipe(l) == null;
	}
	
	public void set(GridWorld world)
	{
		pipes = new Pipe[world.pipes.length][world.pipes[0].length];
		for(int i = 0; i < pipes.length; i++)
			for(int j = 0; j < pipes[i].length; j++)
				if(world.pipes[i][j]!=null)
					pipes[i][j] = new Pipe(world.pipes[i][j]);
		currentFillLocations = new ArrayList<Location>();
		for(Location l : world.currentFillLocations)
			currentFillLocations.add(l);
		finishingLocations = new ArrayList<Location>();
		for(Location l : world.finishingLocations)
			finishingLocations.add(l);
		hasWon = world.hasWon;
	}
	
	public void shallowSet(GridWorld world)
	{
		for(int i = 0; i < pipes.length; i++) {
			for(int j = 0; j < pipes[i].length; j++) {
				if(world.pipes[i][j]!=null) {
					if(pipes[i][j]==null)
						pipes[i][j] = new Pipe(world.pipes[i][j]);
					else
						pipes[i][j].set(world.pipes[i][j]);
				}
			}
		}
		currentFillLocations.clear();
		for(Location l : world.currentFillLocations)
			currentFillLocations.add(l);
		finishingLocations.clear();
		for(Location l : world.finishingLocations)
			finishingLocations.add(l);
		hasWon = world.hasWon;
	}

}
