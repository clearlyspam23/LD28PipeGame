package com.clearlyspam23.LD28.view;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.clearlyspam23.LD28.controller.GridEditingController;
import com.clearlyspam23.LD28.model.PipeDef;

public class PipeBar{
	
	private GridEditingController controller;
	private HashMap<PipeDef, PipeRenderer> map;
	
	private ArrayList<TogglePipeButton> buttons = new ArrayList<TogglePipeButton>();
	
	//private Vector2 location = new Vector2();
	
	private Sprite background;
	
	private TextureRegion pipeActive;
	private TextureRegion pipeInactive;
	private TextureRegion pipeOver;
	
	public PipeBar(TextureRegion background, TextureRegion pipeActive, TextureRegion pipeInactive, TextureRegion pipeOver)
	{
		this.background = new Sprite(background);
		this.pipeActive = pipeActive;
		this.pipeInactive = pipeInactive;
		this.pipeOver = pipeOver;
	}
	
	public void initialize()
	{
		boolean down = false;
		Vector2 loc = new Vector2(40, 150);
		Vector2 step = new Vector2(110, 100);
		for(int i = 0; i < controller.getPipeDefinitions().size(); i++)
		{
			PipeDef d = controller.getPipeDefinitions().get(i);
			PipeClickEvent e = new PipeClickEvent(controller, buttons, i);
			TogglePipeButton b = new TogglePipeButton(pipeActive, pipeInactive, pipeOver, map.get(d), 70, 70, e);
			//b.setLocation(getX()+loc.x, getY()+getHeight()-loc.y);
			b.setLocation(getX()+loc.x, getY()+getHeight()-loc.y);
			buttons.add(b);
			e.setButton(b);
			if(down)
				loc.set(loc.x-step.x, loc.y+step.y);
			else
				loc.set(loc.x+step.x, loc.y);
			down = !down;
		}
	}
	
	public void setLocation(float x, float y)
	{
		background.setPosition(x, y);
	}
	
	public float getX()
	{
		return background.getX();
	}
	
	public float getY()
	{
		return background.getY();
	}
	
	public float getWidth()
	{
		return background.getWidth();
	}
	
	public float getHeight()
	{
		return background.getHeight();
	}
	
	public void render(SpriteBatch batch, float delta)
	{
		background.draw(batch);
		for(TogglePipeButton b : buttons)
			b.render(batch, delta);
	}

	public void setMap(HashMap<PipeDef, PipeRenderer> map) {
		this.map = map;
	}

	public void setController(GridEditingController controller) {
		this.controller = controller;
	}
	
	public void checkMouseOver(float x, float y)
	{
		for(TogglePipeButton b : buttons)
			b.checkMouseOver(x, y);
	}
	
	public void onMouseDown(float x, float y)
	{
		for(TogglePipeButton b : buttons)
			b.onDown(x, y);
	}
	
	public void onMouseUp(float x, float y)
	{
		for(TogglePipeButton b : buttons)
			b.onUp(x, y);
	}
	
	private static class PipeClickEvent implements ClickEvent
	{
		
		private GridEditingController controller;
		private ArrayList<TogglePipeButton> buttons;
		
		private int index;
		
		private TogglePipeButton myButton;
		
		public PipeClickEvent(GridEditingController controller, ArrayList<TogglePipeButton> buttons, int index)
		{
			this.controller = controller;
			this.buttons = buttons;
			this.index = index;
		}
		
		public void setButton(TogglePipeButton button)
		{
			myButton = button;
		}

		@Override
		public void onClick(float x, float y) {
			for(TogglePipeButton b : buttons)
				b.activate(false);
			myButton.activate(true);
			controller.setCurrentPipe(index);
		}
		
	}

}
