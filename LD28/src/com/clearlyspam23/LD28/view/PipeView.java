package com.clearlyspam23.LD28.view;

import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.clearlyspam23.LD28.model.GridWorld;
import com.clearlyspam23.LD28.model.Pipe;
import com.clearlyspam23.LD28.model.PipeDef;

public class PipeView {
	
	private GridWorld myWorld;
	private PipeRenderData[][] renderData;
	
	public PipeView(GridWorld world)
	{
		myWorld = world;
		renderData = new PipeRenderData[world.getWidth()][world.getHeight()];
		for(int i = 0; i < renderData.length; i++)
			for(int j = 0; j < renderData[i].length; j++)
				renderData[i][j] = new PipeRenderData();
	}
	
	private HashMap<PipeDef, PipeRenderer> rendererMap = new HashMap<PipeDef, PipeRenderer>();
	
	public void addRenderer(PipeDef def, PipeRenderer renderer)
	{
		rendererMap.put(def, renderer);
	}
	
	public void beforeWorldSimulation()
	{
		Pipe[][] allPipes = myWorld.getAllPipes();
		for(int i = 0; i < allPipes.length; i++)
			for(int j = 0; j < allPipes[i].length; j++)
				if(allPipes[i][j]!=null)
					renderData[i][j].lastFill = allPipes[i][j].getFill();
	}
	
	public void render(SpriteBatch batch, float delta)
	{
		if(delta>1)
			delta = 1;
		Pipe[][] allPipes = myWorld.getAllPipes();
		for(int i = 0; i < allPipes.length; i++)
			for(int j = 0; j < allPipes[i].length; j++)
				if(allPipes[i][j]!=null)
					rendererMap.get(allPipes[i][j].getDefinition()).render(batch, allPipes[i][j], renderData[i][j], delta);
	}

}
