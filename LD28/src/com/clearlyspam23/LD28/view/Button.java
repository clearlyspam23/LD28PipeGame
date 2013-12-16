package com.clearlyspam23.LD28.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Button {
	
	protected boolean pressed;
	private Vector2 downLoc = new Vector2();
	protected boolean enabled = true;
	
	protected Rectangle bounds;
	private ClickEvent event;
	
	public Button(float width, float height, ClickEvent e)
	{
		bounds = new Rectangle(0, 0, width, height);
		event = e;
	}
	
	public void setLocation(float x, float y)
	{
		bounds.x = x;
		bounds.y = y;
	}
	
	public Rectangle getBounds()
	{
		return bounds;
	}
	
	public void enable(boolean flag)
	{
		enabled = flag;
	}
	
	public void onDown(float x, float y)
	{
		if(bounds.contains(x, y)&&enabled)
		{
			pressed = true;
			downLoc.set(x, y);
			if(event!=null)
				event.onDown(x, y);
		}
	}
	
	public void onUp(float x, float y)
	{
		if(pressed)
		{
			pressed = false;
			if(event!=null) {
				if(closeEnough(x, y))
					event.onClick(downLoc.x, downLoc.y);
				event.onUp(x, y);
			}
		}
	}
	
	public void checkMouseOver(float x, float y)
	{
		if(bounds.contains(x, y))
			onMouseOver(x, y);
	}
	
	protected void onMouseOver(float x, float y)
	{
		
	}
	
	private boolean closeEnough(float x, float y)
	{
		return Math.abs(x-downLoc.x)<5&&Math.abs(y-downLoc.y)<5;
	}
	
	public abstract void render(SpriteBatch batch, float delta);

}
