package com.clearlyspam23.LD28.view;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Button {
	
	private TextureRegion up;
	private TextureRegion down;
	
	private Rectangle bounds;
	
	public Button(TextureRegion up, TextureRegion down, float x, float y, float width, float height)
	{
		this.up = up;
		this.down = down;
		this.bounds = new Rectangle(x, y, width, height);
	}
	
	private boolean press;
	private boolean enabled;
	
	public void enable(boolean flag)
	{
		enabled = flag;
	}
	
	public void onDown(float x, float y)
	{
		
	}

}
