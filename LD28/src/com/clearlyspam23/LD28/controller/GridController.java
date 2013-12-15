package com.clearlyspam23.LD28.controller;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.clearlyspam23.LD28.model.GridWorld;
import com.clearlyspam23.LD28.model.PipeDef;
import com.clearlyspam23.LD28.util.Location;
import com.clearlyspam23.LD28.util.WorldToScreenConverter;

public class GridController {
	
	//private GridWorld model;
	
	private int timeStep;
	
	private WorldToScreenConverter converter;
	
	private ArrayList<PipeDef> pipeDefinitions = new ArrayList<PipeDef>();
	private PipeDef currentPipe;
	private int numMoves = 1;
	
	private boolean wasDown = false;
	
//	public GridController(GridWorld world)
//	{
//		this.model = world;
//	}
	
	public void setCurrentPipe(int index)
	{
		if(index<0)
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
	
	public void simulate(GridWorld world)
	{
		world.simulate(timeStep);
	}

	public WorldToScreenConverter getConverter() {
		return converter;
	}

	public void setConverter(WorldToScreenConverter converter) {
		this.converter = converter;
	}
	
	public void control(GridWorld world)
	{
//		if(Gdx.input.isTouched()&&wasDown&&numMoves>0&&currentPipe!=null)
//		{
//			wasDown = true;
//			Vector2 worldLoc = converter.convertFromScreenToCamera(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
//			Location gridLoc = converter.convertFromCameraToGrid(worldLoc);
//			if(world.isValidLocation(gridLoc))
//			{
//				world.addNormalPipe(currentPipe, gridLoc.x, gridLoc.y);
//				numMoves--;
//			}
//		}
//		else if(!Gdx.input.isTouched())
//			wasDown = false;
	}

}
