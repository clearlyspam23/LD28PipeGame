package com.clearlyspam23.LD28.controller;

import com.clearlyspam23.LD28.model.GridWorld;

public class GridRunningController {
	
	private int timeStep;
	
	public void simulate(GridWorld world)
	{
		world.simulate(timeStep);
	}

}
