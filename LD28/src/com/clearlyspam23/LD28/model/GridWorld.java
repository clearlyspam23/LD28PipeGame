package com.clearlyspam23.LD28.model;

import java.util.ArrayList;

public class GridWorld {
	
	private Pipe[][] pipes;
	private ArrayList<Location> currentFillLocations = new ArrayList<Location>();
	
	private ArrayList<Location> lastFillLocations = new ArrayList<Location>();
	
	public GridWorld(int width, int height)
	{
		pipes = new Pipe[width][height];
	}
	
	public void addNormalPipe(PipeDef def, int x, int y)
	{
		Pipe p = new Pipe(def);
		p.setLocation(x, y);
		pipes[x][y] = p;
	}
	
	public void simulate(int timeStep)
	{
		lastFillLocations.clear();
		ArrayList<Location> fillLocations = lastFillLocations;
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
						}
					}
				}
			}
			else
				fillLocations.add(p.getLocation());
		}
		lastFillLocations = currentFillLocations;
		currentFillLocations = fillLocations;
	}
	
	public void startFrom(Location l, Direction input)
	{
		currentFillLocations.add(l);
		Pipe p = getPipe(l);
		p.inputFrom(input);
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
	
	public Pipe[] getLastPipes()
	{
		Pipe[] ans = new Pipe[lastFillLocations.size()];
		for(int i = 0; i < lastFillLocations.size(); i++)
		{
			ans[i] = getPipe(lastFillLocations.get(i));
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
		return currentFillLocations.size()==0;
	}
	
	public boolean isLocationEmpty(Location l)
	{
		return getPipe(l) == null;
	}

}
