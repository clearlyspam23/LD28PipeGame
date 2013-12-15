package com.clearlyspam23.LD28.util;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class WorldToScreenConverter {
	
	private Vector2 gridDim;
	private Camera worldCamera;
	
	public WorldToScreenConverter(Camera worldCam, float gridWidth, float gridHeight)
	{
		gridDim = new Vector2(gridWidth, gridHeight);
		worldCamera = worldCam;
	}
	
	public Vector2 convertFromScreenToWorld(Vector2 v)
	{
		return convertFromScreenToWorld(v.x, v.y);
	}
	
	public Vector2 convertFromScreenToWorld(float x, float y)
	{
		Vector3 v3 = new Vector3(x, y, 0);
		worldCamera.unproject(v3);
		return new Vector2(v3.x, v3.y);
	}
	
	public Location convertFromWorldToGrid(Vector2 v)
	{
		return convertFromWorldToGrid(v.x, v.y);
	}
	
	public Location convertFromWorldToGrid(float x, float y)
	{
		return new Location((int)(x/gridDim.x), (int)(y/gridDim.y));
	}
	
	public Vector2 clampToGrid(Vector2 v)
	{
		return clampToGrid(v.x, v.y);
	}
	
	public Vector2 clampToGrid(float x, float y)
	{
		Location l = convertFromWorldToGrid(x, y);
		return new Vector2(l.x * gridDim.x, l.y * gridDim.y);
	}

}
