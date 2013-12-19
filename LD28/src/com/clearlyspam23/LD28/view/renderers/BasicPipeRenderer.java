package com.clearlyspam23.LD28.view.renderers;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.clearlyspam23.LD28.model.Pipe;
import com.clearlyspam23.LD28.util.Direction;
import com.clearlyspam23.LD28.view.PipeRenderer;

public class BasicPipeRenderer extends PipeRenderer {
	
	private TextureRegion empty;
	private TextureRegion full;
	//private Rectangle bounds = new Rectangle();
	private Vector2 offset = new Vector2();
	private Vector2 dimensions = new Vector2();
	private Vector2 uv = new Vector2();
	private Vector2 uv2 = new Vector2();
	
	public BasicPipeRenderer(TextureRegion empty, TextureRegion full)
	{
		this.empty = empty;
		this.full = full;
	}
	
	public void renderEmpty(Batch batch, float x, float y, float width, float height)
	{
		batch.draw(empty, x, y, width, height);
	}

	@Override
	public void render(Batch batch, Pipe currentPipe, Pipe lastPipe, float delta) {
		batch.draw(empty, currentPipe.getLocation().x*empty.getRegionWidth(), currentPipe.getLocation().y*empty.getRegionHeight(), empty.getRegionWidth(), empty.getRegionHeight());
		if(lastPipe==null)
			return;
		Direction start = currentPipe.getInput();
		int fillDif = currentPipe.getFill() - lastPipe.getFill();
		float percentage = (((float)lastPipe.getFill())+fillDif*delta)/Pipe.MAX_FILL;
		if(percentage>1)
			percentage = 1;
		offset.set(0, 0);
		float dif;
		switch(start)
		{
		case DOWN:
			dif = full.getV() - full.getV2();
			dimensions.x = full.getRegionWidth();
			dimensions.y = full.getRegionHeight()*percentage;
			uv.x = full.getU();
			uv.y = full.getV2();
			uv2.x = full.getU2();
			uv2.y = full.getV2() + dif*percentage;
//			bounds.x = full.getRegionX();
//			bounds.y = full.getRegionY();
//			bounds.width = full.getRegionWidth();
//			bounds.height = full.getRegionHeight()*percentage;
			break;
		case LEFT:
			dimensions.x = full.getRegionWidth()*percentage;
			dimensions.y = full.getRegionHeight();
			dif = full.getU2() - full.getU();
			uv.x = full.getU();
			uv.y = full.getV2();
			uv2.x = full.getU() + dif*percentage;
			uv2.y = full.getV();
//			bounds.x = full.getRegionX();
//			bounds.y = full.getRegionY();
//			bounds.width = full.getRegionWidth()*percentage;
//			bounds.height = full.getRegionHeight();
			break;
		case UP:
			dimensions.x = full.getRegionWidth();
			dimensions.y = full.getRegionHeight()*percentage;
			offset.y = full.getRegionHeight()*(1-percentage);
			dif = full.getV() - full.getV2();
			uv.x = full.getU();
			uv.y = full.getV() - dif*percentage;
			uv2.x = full.getU2();
			uv2.y = full.getV();
//			bounds.x = full.getRegionX();
//			bounds.y = full.getRegionY()+offset.y;
//			bounds.width = full.getRegionWidth();
//			bounds.height = full.getRegionHeight()*percentage;
			break;
		case RIGHT:
			dimensions.x = full.getRegionWidth()*percentage;
			dimensions.y = full.getRegionHeight();
			offset.x = full.getRegionWidth()*(1-percentage);
			dif = full.getU2() - full.getU();
			uv.x = full.getU2() - dif*percentage;
			uv.y = full.getV2();
			uv2.x = full.getU2();
			uv2.y = full.getV();
//			bounds.x = full.getRegionX()+offset.x;
//			bounds.y = full.getRegionY();
//			bounds.width = full.getRegionWidth()*percentage;
//			bounds.height = full.getRegionHeight();
			break;
		default:
			dimensions.x = 0;
			dimensions.y = 0;
			uv.x = full.getU();
			uv.y = full.getV2();
			uv2.x = full.getU2();
			uv2.y = full.getV();
		}
		batch.draw(full.getTexture(), currentPipe.getLocation().x*full.getRegionWidth() + offset.x, currentPipe.getLocation().y*full.getRegionHeight() + offset.y, dimensions.x, dimensions.y, uv.x, uv.y, uv2.x, uv2.y);
		
	}

}
