package com.clearlyspam23.LD28.controller;

import java.util.ArrayList;
import java.util.Stack;

import com.clearlyspam23.LD28.model.GridWorld;
import com.clearlyspam23.LD28.model.Pipe;
import com.clearlyspam23.LD28.model.PipeDef;
import com.clearlyspam23.LD28.util.Location;

public class GridEditingController {
	
	private ArrayList<PipeDef> pipeDefinitions = new ArrayList<PipeDef>();
	private PipeDef currentPipe;
	private int numMoves = 1;
	
	private GridWorld current;
	
	private boolean shouldStart;
	
	//private WorldToScreenConverter converter;
	
	private Stack<GridWorld> undoStack = new Stack<GridWorld>();
	
	public GridEditingController(GridWorld world)
	{
		current = world;
	}
	
	public boolean shouldStart()
	{
		return shouldStart;
	}
	
	public void toggleStart(boolean flag)
	{
		shouldStart = flag;
	}
	
	public void setCurrentPipe(int index)
	{
		if(index<0||index>=pipeDefinitions.size())
			currentPipe = null;
		else
			currentPipe = pipeDefinitions.get(index);
	}
	
	public PipeDef getCurrentPipe()
	{
		return currentPipe;
	}
	
	public void addPipeDef(PipeDef def)
	{
		pipeDefinitions.add(def);
	}
	
	public ArrayList<PipeDef> getPipeDefinitions()
	{
		return pipeDefinitions;
	}
	
	public int getNumMoves()
	{
		return numMoves - undoStack.size();
	}
	
	public void setNumMoves(int num)
	{
		numMoves = num;
	}
	
	public void addCurrentPipeToWorld(GridWorld world, int x, int y)
	{
		world.addNormalPipe(currentPipe, x, y);
	}
	
	public void addCurrentPipe(Location loc)
	{
		undoStack.push(new GridWorld(current));
		current.addNormalPipe(currentPipe, loc.x, loc.y);
	}
	
	public void rotatePipe(Pipe p)
	{
		undoStack.push(new GridWorld(current));
		current.addNormalPipe(p.getRotatedPipe(), p.getLocation().x, p.getLocation().y);
	}
	
	public boolean canUndo()
	{
		return !undoStack.empty();
	}
	
	public void undo()
	{
		current.set(undoStack.pop());
	}
	
	public void setWorldToOriginal()
	{
		GridWorld world = null;
		while(!undoStack.empty())
			world = undoStack.pop();
		if(world!=null)
			current.set(world);
	}
	
	public void attemptAddPipe(Location l)
	{
		if(getNumMoves()<=0||currentPipe==null)
			return;
		if(current.isValidLocation(l)&&current.isLocationEmpty(l)&&currentPipe!=null)
			addCurrentPipe(l);
	}
	
	public void attemptRotatePipe(Location l)
	{
		if(getNumMoves()<=0||!current.isValidLocation(l)||current.isLocationEmpty(l))
			return;
		Pipe p = current.getPipe(l);
		if(p.hasRotationTable())
			rotatePipe(p);
	}

}
