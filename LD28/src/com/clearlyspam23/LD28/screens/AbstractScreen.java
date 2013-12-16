package com.clearlyspam23.LD28.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.clearlyspam23.LD28.LD28Game;

public abstract class AbstractScreen implements Screen {

	protected LD28Game game;
	protected SpriteBatch batch;
	
	public AbstractScreen(LD28Game g, SpriteBatch b)
	{
		game = g;
		batch = b;
	}

}
