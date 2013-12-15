package com.clearlyspam23.LD28.controller;

import java.util.ArrayList;
import java.util.Stack;

import com.clearlyspam23.LD28.model.GridWorld;
import com.clearlyspam23.LD28.model.PipeDef;
import com.clearlyspam23.LD28.util.Location;

public class GridEditingController {
	
	private ArrayList<PipeDef> pipeDefinitions = new ArrayList<PipeDef>();
	private PipeDef currentPipe;
	private int numMoves = 1;
	
	private GridWorld current;
	
	//private WorldToScreenConverter converter;
	
	private Stack<GridWorld> undoStack = new Stack<GridWorld>();
	
	public GridEditingController(GridWorld world)
	{
		current = world;
	}
	
	public void setCurrentPipe(int index)
	{
		System.out.println(index);
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
		return numMoves;
	}
	
	public void setNumMoves(int num)
	{
		numMoves = num;
	}
	
	public void decrementNumMoves()
	{
		numMoves--;
	}
	
	public void addCurrentPipeToWorld(GridWorld world, int x, int y)
	{
		world.addNormalPipe(currentPipe, x, y);
	}
	
	public void addCurrentPipe(Location loc)
	{
		undoStack.push(new GridWorld(current));
		current.addNormalPipe(currentPipe, loc.x, loc.y);
		numMoves--;
	}
	
	public boolean canUndo()
	{
		return !undoStack.empty();
	}
	
	public void undo()
	{
		current.set(undoStack.pop());
	}
	
	public void attemptAddPipe(Location l)
	{
		System.out.println(l);
		System.out.println(numMoves);
		System.out.println(currentPipe);
		if(numMoves<=0||currentPipe==null)
			return;
		System.out.println("might add");
		if(current.isValidLocation(l)&&current.isLocationEmpty(l)&&currentPipe!=null)
			addCurrentPipe(l);
	}

}
