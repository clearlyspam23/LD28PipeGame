package com.clearlyspam23.LD28.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class VictoryOverlay {
	
	private static final String[] VICTORY_STRINGS = new String[]{"Well Done!", "Victory!", "Congratulations!" };
	
	private static final String PRESS_ANY_KEY = "Press Any Key To Continue";
	
	private float alphaDelay;
	
	private BitmapFont font;
	
	private String victory;
	private Vector2 victoryOffset;
	
	private Vector2 pressAnyOffset;
	
	public VictoryOverlay(BitmapFont font)
	{
		this.font = font;
		victory = VICTORY_STRINGS[(int)(Math.random()*VICTORY_STRINGS.length)];
		int hwidth = Gdx.graphics.getWidth()/2;
		int hheight = Gdx.graphics.getHeight()/2;
		TextBounds b = font.getBounds(victory);
		victoryOffset = new Vector2(hwidth-b.width/2, hheight*5/4-b.height/2);
		b = font.getBounds(PRESS_ANY_KEY);
		pressAnyOffset = new Vector2(hwidth-b.width/2, hheight*3/4-b.height/2);
	}
	
	public void render(SpriteBatch batch, float delta)
	{
		alphaDelay+=Gdx.graphics.getDeltaTime();
		Color c = font.getColor();
		font.setColor(1, 1, 1, MathUtils.clamp(alphaDelay, 0, 1));
		font.draw(batch, victory, victoryOffset.x, victoryOffset.y);
		font.setColor(1, 1, 1, MathUtils.clamp(alphaDelay-1, 0, 1));
		font.draw(batch, PRESS_ANY_KEY, pressAnyOffset.x, pressAnyOffset.y);
		font.setColor(c);
	}

}
