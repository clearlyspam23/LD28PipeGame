package com.clearlyspam23.LD28.controller;

import java.util.ArrayList;

import com.clearlyspam23.LD28.model.GridWorld;

public class GridRunningController {
	
	private int timeStep;
	private GridWorld world;
	private boolean isAllFinished;
	
	private boolean didWin;
	private ArrayList<VictoryListener> listeners = new ArrayList<VictoryListener>();
	
	public GridRunningController(GridWorld w)
	{
		world = w;
	}
	
	public void addVictoryListener(VictoryListener listener)
	{
		listeners.add(listener);
	}
	
	public void simulate()
	{
		world.simulate(timeStep);
		if(!didWin&&world.hasWon())
		{
			didWin = true;
			for(VictoryListener l : listeners)
				l.onVictory(this);
		}
	}
	
	public void setTimeStep(int value)
	{
		timeStep = value;
	}

	public boolean isAllFinished() {
		return isAllFinished;
	}

	public void setAllFinished(boolean isAllFinished) {
		this.isAllFinished = isAllFinished;
	}

}
