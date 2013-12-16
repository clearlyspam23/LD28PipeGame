package com.clearlyspam23.LD28.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class GameUI {
	
	private PipeBar sidebar;
	private BasicButton button1;
	private BasicButton button2;
	private ButtonEnabler enabler1;
	private ButtonEnabler enabler2;
	private TextureRegion UIBackground;
	
	private Vector2 location = new Vector2();
	
	public void initialize()
	{
		sidebar.setLocation(location.x - 20, location.y+20);
		button1.setLocation(location.x - 20, sidebar.getY()+sidebar.getHeight()+20);
		button2.setLocation(location.x - 20 + button2.getBounds().getWidth() + 10, sidebar.getY()+sidebar.getHeight()+20);
		button2.enable(enabler1.shouldEnable());
		button1.enable(enabler2.shouldEnable());
		sidebar.initialize();
	}
	
	public void setUIBackground(TextureRegion uIBackground) {
		UIBackground = uIBackground;
	}
	
	public void setButton1(TextureRegion up, TextureRegion down, ClickEvent event, ButtonEnabler enabler) {
		button1 = new BasicButton(up, down, 128, 128, event);
		enabler1 = enabler;
	}

	public void setButton2(TextureRegion up, TextureRegion down, ClickEvent event, ButtonEnabler enabler) {
		button2 = new BasicButton(up, down, 128, 128, event);
		enabler2 = enabler;
	}
	
	public void render(SpriteBatch batch, float delta)
	{
		batch.draw(UIBackground, sidebar.getX()-25, 0);
		sidebar.render(batch, delta);
		button2.render(batch, delta);
		button1.render(batch, delta);
	}
	
	public void checkMouseOver(float x, float y)
	{
		button2.checkMouseOver(x, y);
		button1.checkMouseOver(x, y);
		sidebar.checkMouseOver(x, y);
	}
	
	public void checkMouseDown(float x, float y)
	{
		button2.onDown(x, y);
		button1.onDown(x, y);
		sidebar.onMouseDown(x, y);
	}
	
	public void checkMouseUp(float x, float y)
	{
		button2.onUp(x, y);
		button1.onUp(x, y);
		sidebar.onMouseUp(x, y);
	}
	
	public void checkEnable()
	{
		button1.enable(enabler1.shouldEnable());
		button2.enable(enabler2.shouldEnable());
//		startButton.enable(controller.getNumMoves()<=0);
//		undoButton.enable(controller.canUndo());
	}
	
	public PipeBar getSidebar() {
		return sidebar;
	}
	public void setSidebar(PipeBar sidebar) {
		this.sidebar = sidebar;
	}

	public Vector2 getLocation() {
		return location;
	}

	public void setLocation(float x, float y) {
		location.set(x, y);
	}
}
