package com.clearlyspam23.LD28.view;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.clearlyspam23.LD28.model.Pipe;

public abstract class PipeRenderer {
	
	public abstract void render(Batch batch, Pipe currentPipe, Pipe lastPipe, float delta);
	
	public abstract void renderEmpty(Batch batch, float x, float y, float width, float height);
}
