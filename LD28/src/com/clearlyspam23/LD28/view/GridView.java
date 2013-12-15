package com.clearlyspam23.LD28.view;

import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.clearlyspam23.LD28.model.GridWorld;
import com.clearlyspam23.LD28.model.Pipe;
import com.clearlyspam23.LD28.model.PipeDef;

public class GridView {
	
	private GridWorld myWorld;
	private GridWorld previousWorld;
	
	public GridView(GridWorld world, GridWorld previous)
	{
		myWorld = world;
		previousWorld = previous;
	}
	
	private HashMap<PipeDef, PipeRenderer> rendererMap = new HashMap<PipeDef, PipeRenderer>();
	
	public void addRenderer(PipeDef def, PipeRenderer renderer)
	{
		rendererMap.put(def, renderer);
	}
	
	
	public void render(SpriteBatch batch, float delta)
	{
		if(delta>1)
			delta = 1;
		Pipe[][] allPipes = myWorld.getAllPipes();
		Pipe[][] previousPipes = previousWorld.getAllPipes();
		for(int i = 0; i < allPipes.length; i++)
			for(int j = 0; j < allPipes[i].length; j++)
				if(allPipes[i][j]!=null)
					rendererMap.get(allPipes[i][j].getDefinition()).render(batch, allPipes[i][j], previousPipes[i][j], delta);
	}
	
	public GridWorld getWorld()
	{
		return myWorld;
	}

}
