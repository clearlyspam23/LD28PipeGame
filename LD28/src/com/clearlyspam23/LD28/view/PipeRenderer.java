package com.clearlyspam23.LD28.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.clearlyspam23.LD28.model.Pipe;

public abstract class PipeRenderer {
	
	public abstract void render(SpriteBatch batch, Pipe pipe, PipeRenderData data, float delta);
}
