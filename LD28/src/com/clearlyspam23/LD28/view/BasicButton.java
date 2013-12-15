package com.clearlyspam23.LD28.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BasicButton extends Button{
	
	private TextureRegion up;
	private TextureRegion down;
	
	public BasicButton(TextureRegion up, TextureRegion down, float width, float height, ClickEvent e)
	{
		super(width, height, e);
		this.up = up;
		this.down = down;
	}
	
	public void render(SpriteBatch batch, float delta)
	{
		if(pressed||!enabled)
			batch.draw(down, bounds.x, bounds.y, bounds.width, bounds.height);
		else
			batch.draw(up, bounds.x, bounds.y, bounds.width, bounds.height);
	}
	
	public void onMouseOver(float x, float y)
	{
		
	}

}
