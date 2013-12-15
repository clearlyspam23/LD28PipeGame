package com.clearlyspam23.LD28.model;

import java.util.ArrayList;

import com.clearlyspam23.LD28.util.Direction;
import com.clearlyspam23.LD28.util.Location;

public class GridWorld {
	
	private Pipe[][] pipes;
<<<<<<< HEAD
	private ArrayList<Location> currentFillLocations;
	private ArrayList<Location> finishingLocations;
	private boolean hasWon;
	
	//private ArrayList<Location> lastFillLocations = new ArrayList<Location>();
=======
	private ArrayList<Location> currentFillLocations = new ArrayList<Location>();
	
	private ArrayList<Location> lastFillLocations = new ArrayList<Location>();
>>>>>>> 6d137583960acb2c40b57bee8f6ba28261714aaa
	
	public GridWorld(int width, int height)
	{
		pipes = new Pipe[width][height];
<<<<<<< HEAD
		currentFillLocations = new ArrayList<Location>();
		finishingLocations = new ArrayList<Location>();
	}
	
	public GridWorld(GridWorld other)
	{
		set(other);
=======
>>>>>>> 6d137583960acb2c40b57bee8f6ba28261714aaa
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
<<<<<<< HEAD
		//lastFillLocations.clear();
		ArrayList<Location> fillLocations = new ArrayList<Location>();
=======
		lastFillLocations.clear();
		ArrayList<Location> fillLocations = lastFillLocations;
>>>>>>> 6d137583960acb2c40b57bee8f6ba28261714aaa
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
<<<<<<< HEAD
							if(finishingLocations.contains(o.getLocation()))
								hasWon = true;
=======
>>>>>>> 6d137583960acb2c40b57bee8f6ba28261714aaa
						}
					}
				}
			}
			else
				fillLocations.add(p.getLocation());
		}
<<<<<<< HEAD
		//lastFillLocations = currentFillLocations;
		currentFillLocations = fillLocations;
	}
	
	public void addStart(Location l, Direction input)
=======
		lastFillLocations = currentFillLocations;
		currentFillLocations = fillLocations;
	}
	
	public void startFrom(Location l, Direction input)
>>>>>>> 6d137583960acb2c40b57bee8f6ba28261714aaa
	{
		currentFillLocations.add(l);
		Pipe p = getPipe(l);
		p.inputFrom(input);
	}
	
<<<<<<< HEAD
	public void addFinish(Location l)
	{
		finishingLocations.add(l);
	}
	
=======
>>>>>>> 6d137583960acb2c40b57bee8f6ba28261714aaa
	public Pipe[] getCurrentPipes()
	{
		Pipe[] ans = new Pipe[currentFillLocations.size()];
		for(int i = 0; i < currentFillLocations.size(); i++)
		{
			ans[i] = getPipe(currentFillLocations.get(i));
		}
		return ans;
	}
	
<<<<<<< HEAD
//	public Pipe[] getLastPipes()
//	{
//		Pipe[] ans = new Pipe[lastFillLocations.size()];
//		for(int i = 0; i < lastFillLocations.size(); i++)
//		{
//			ans[i] = getPipe(lastFillLocations.get(i));
//		}
//		return ans;
//	}
=======
	public Pipe[] getLastPipes()
	{
		Pipe[] ans = new Pipe[lastFillLocations.size()];
		for(int i = 0; i < lastFillLocations.size(); i++)
		{
			ans[i] = getPipe(lastFillLocations.get(i));
		}
		return ans;
	}
>>>>>>> 6d137583960acb2c40b57bee8f6ba28261714aaa
	
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
<<<<<<< HEAD
		return !hasWon&&currentFillLocations.size()==0;
	}
	
	public boolean hasWon()
	{
		return hasWon;
=======
		return currentFillLocations.size()==0;
>>>>>>> 6d137583960acb2c40b57bee8f6ba28261714aaa
	}
	
	public boolean isLocationEmpty(Location l)
	{
		return getPipe(l) == null;
	}
<<<<<<< HEAD
	
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
		for(int i = 0; i < pipes.length; i++)
			for(int j = 0; j < pipes[i].length; j++)
				if(world.pipes[i][j]!=null)
					pipes[i][j].set(world.pipes[i][j]);
		currentFillLocations.clear();
		for(Location l : world.currentFillLocations)
			currentFillLocations.add(l);
		finishingLocations.clear();
		for(Location l : world.finishingLocations)
			finishingLocations.add(l);
		hasWon = world.hasWon;
	}
=======
>>>>>>> 6d137583960acb2c40b57bee8f6ba28261714aaa

}
