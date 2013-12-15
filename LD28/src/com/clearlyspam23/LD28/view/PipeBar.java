package com.clearlyspam23.LD28.view;

import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.clearlyspam23.LD28.controller.GridController;
import com.clearlyspam23.LD28.model.PipeDef;

public class PipeBar{
	
	private GridController controller;
	private HashMap<PipeDef, PipeRenderer> map;
	
	//private Vector2 location = new Vector2();
	
	private Sprite background;
	
	private TextureRegion pipeBorder;
	
	public PipeBar(TextureRegion background, TextureRegion pipeBorder, float x, float y)
	{
		this.background = new Sprite(background);
		this.background.setPosition(x, y);
		this.pipeBorder = pipeBorder;
	}
	
	public void setLocation(float x, float y)
	{
		background.setPosition(x, y);
	}
	
	public void render(SpriteBatch batch, float delta)
	{
		background.draw(batch);
	}

	public void setMap(HashMap<PipeDef, PipeRenderer> map) {
		this.map = map;
	}

	public void setController(GridController controller) {
		this.controller = controller;
	}

}
