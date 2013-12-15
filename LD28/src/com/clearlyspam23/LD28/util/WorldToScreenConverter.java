package com.clearlyspam23.LD28.util;

import com.badlogic.gdx.math.Vector2;

public class WorldToScreenConverter {
	
	private Vector2 ratio;
	
	public WorldToScreenConverter(float worldWidth, float worldHeight, float gridWidth, float gridHeight)
	{
		ratio = new Vector2(worldWidth/gridWidth, worldHeight/gridHeight);
	}
	
	public WorldToScreenConverter(float pipeWidth, float pipeHeight)
	{
		ratio = new Vector2(pipeWidth, pipeHeight);
	}
	
	public void set(WorldToScreenConverter other)
	{
		ratio.set(other.ratio);
	}
	
	public Location convertFromCameraToGrid(Vector2 v)
	{
		return convertFromCameraToGrid(v.x, v.y);
	}
	
	public Location convertFromCameraToGrid(float x, float y)
	{
		return new Location((int)(x/ratio.x), (int)(y/ratio.y));
	}
	
	public Vector2 convertFromGridToCamera(Location l)
	{
		return convertFromGridToCamera(l.x, l.y);
	}
	
	public Vector2 convertFromGridToCamera(int x, int y)
	{
		return new Vector2(x*ratio.x, y*ratio.y);
	}
	
	public Vector2 clampToGrid(Vector2 v)
	{
		return clampToGrid(v.x, v.y);
	}
	
	public Vector2 clampToGrid(float x, float y)
	{
		return new Vector2(((int)(x/ratio.x))*ratio.x, ((int)(y/ratio.y))*ratio.y);
	}

}
