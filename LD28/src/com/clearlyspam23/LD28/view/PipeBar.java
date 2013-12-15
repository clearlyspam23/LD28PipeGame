package com.clearlyspam23.LD28.view;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.clearlyspam23.LD28.controller.GridEditingController;
import com.clearlyspam23.LD28.model.PipeDef;

public class PipeBar{
	
	private static final String title = "Pipes";
	
	private GridEditingController controller;
	private HashMap<PipeDef, PipeRenderer> map;
	
	//private Vector2 location = new Vector2();
	
	private Sprite background;
	
	private TextureRegion pipeBorder;
	
	private Vector2 loc = new Vector2();
	
	public PipeBar(TextureRegion background, TextureRegion pipeBorder)
	{
		this.background = new Sprite(background);
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

	public void setController(GridEditingController controller) {
		this.controller = controller;
	}

}
