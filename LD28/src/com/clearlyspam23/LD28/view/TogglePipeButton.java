package com.clearlyspam23.LD28.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TogglePipeButton extends Button {
	
	private boolean activated;
	private boolean isOver;
	private TextureRegion active;
	private TextureRegion inactive;
	private TextureRegion over;
	private PipeRenderer renderer;

	public TogglePipeButton(TextureRegion active, TextureRegion inactive, TextureRegion over, PipeRenderer renderer, float width, float height,
			ClickEvent e) {
		super(width, height, e);
		this.active = active;
		this.inactive = inactive;
		this.renderer = renderer;
		this.over = over;
	}
	
	public void activate(boolean flag)
	{
		activated = flag;
	}
	
	public void onMouseOver(float x, float y)
	{
		isOver = true;
	}

	@Override
	public void render(SpriteBatch batch, float delta) {
		if(activated)
		{
			batch.draw(active, bounds.x, bounds.y, bounds.width, bounds.height);
		}
		else if(isOver)
		{
			batch.draw(over, bounds.x, bounds.y, bounds.width, bounds.height);
		}
		else
		{
			batch.draw(inactive, bounds.x, bounds.y, bounds.width, bounds.height);
		}
		renderer.renderEmpty(batch, bounds.x+3, bounds.y+3, 64, 64);
		isOver = false;
	}

}
