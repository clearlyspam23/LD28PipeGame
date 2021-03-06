package com.clearlyspam23.LD28.util;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class WorldToScreenConverter {
	
	private Vector3 unprojection = new Vector3();
	
	private Vector2 gridDim;
	private Camera worldCamera;
	private Camera mouseCamera;
	
	public WorldToScreenConverter(Camera worldCam, Camera mouseCam, float gridWidth, float gridHeight)
	{
		gridDim = new Vector2(gridWidth, gridHeight);
		worldCamera = worldCam;
		mouseCamera = mouseCam;
	}
	
	public Vector2 convertFromMouseToScreen(Vector2 v)
	{
		unprojection.set(v.x, v.y, 0);
		mouseCamera.unproject(unprojection);
		return v.set(unprojection.x, unprojection.y);
	}
	
	public Vector2 convertFromMouseToScreen(float x, float y)
	{
		unprojection.set(x, y, 0);
		mouseCamera.unproject(unprojection);
		return new Vector2(unprojection.x, unprojection.y);
	}
	
	public Vector2 convertFromMouseToWorld(Vector2 v)
	{
		return convertFromMouseToWorld(v.x, v.y);
	}
	
	public Vector2 convertFromMouseToWorld(float x, float y)
	{
		return convertFromMouseToWorld(x, y, new Vector2());
	}
	
	public Vector2 convertFromMouseToWorld(float x, float y, Vector2 output)
	{
		unprojection.set(x, y, 0);
		worldCamera.unproject(unprojection);
		return output.set(unprojection.x, unprojection.y);
	}
	
	public Vector2 convertFromScreenToWorld(Vector2 vector)
	{
		return vector.set(vector.x*worldCamera.viewportWidth/mouseCamera.viewportWidth, vector.y*worldCamera.viewportHeight/mouseCamera.viewportHeight);
	}
	
	public Vector2 convertFromScreenToWorld(float x, float y)
	{
		return new Vector2(x*worldCamera.viewportWidth/mouseCamera.viewportWidth, y*worldCamera.viewportHeight/mouseCamera.viewportHeight);
	}
	
	public Vector2 convertFromScreenToWorld(float x, float y, Vector2 out)
	{
		return out.set(x*worldCamera.viewportWidth/mouseCamera.viewportWidth, y*worldCamera.viewportHeight/mouseCamera.viewportHeight);
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
