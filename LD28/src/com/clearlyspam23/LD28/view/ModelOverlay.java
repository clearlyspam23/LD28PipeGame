package com.clearlyspam23.LD28.view;

import java.util.Map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.clearlyspam23.LD28.controller.GridEditingController;
import com.clearlyspam23.LD28.model.Pipe;
import com.clearlyspam23.LD28.model.PipeDef;
import com.clearlyspam23.LD28.util.Location;
import com.clearlyspam23.LD28.util.WorldToScreenConverter;

public class ModelOverlay {
	
	private TextureRegion boundingRegion;
	private TextureRegion rotateArrow;
	private WorldToScreenConverter converter;
	private GridView gridView;
	private GridEditingController editingController;
	private Map<PipeDef, PipeRenderer> renderMap;
	private Vector2 worldBounds = new Vector2();
	
	public void setWorldBounds(float x, float y)
	{
		worldBounds.set(x, y);
	}
	
	public void setGridView(GridView gridView) {
		this.gridView = gridView;
	}

	public void setEditingController(GridEditingController editingController) {
		this.editingController = editingController;
	}

	public void setRenderMap(Map<PipeDef, PipeRenderer> renderMap) {
		this.renderMap = renderMap;
	}

	private Vector2 mouseCoord = new Vector2();

	public void setBoundingRegion(TextureRegion boundingRegion) {
		this.boundingRegion = boundingRegion;
	}

	public void setRotateArrow(TextureRegion rotateArrow) {
		this.rotateArrow = rotateArrow;
	}

	public void setConverter(WorldToScreenConverter converter) {
		this.converter = converter;
	}

	public void render(SpriteBatch batch, float delta)
	{
//		Vector2 mouseLoc = new Vector2(mouseCoord.x, mouseCoord.y);
//		mouseLoc = converter.convertFromMouseToWorld(mouseLoc);
		if(isInBounds(mouseCoord.x, mouseCoord.y))
		{
			Vector2 worldCoord = converter.convertFromScreenToWorld(mouseCoord.x, mouseCoord.y);
			Location l = converter.convertFromWorldToGrid(worldCoord);
			if(gridView.getWorld().isValidLocation(l)&&editingController.getNumMoves()>0)
			{
				Vector2 clampedWorldCoord = converter.clampToGrid(worldCoord);
				if(gridView.getWorld().isLocationEmpty(l)&&editingController.getCurrentPipe()!=null)
				{
					Pipe temp = new Pipe(editingController.getCurrentPipe());
					temp.setLocation(l.x, l.y);
					renderMap.get(editingController.getCurrentPipe()).render(batch, temp, temp, delta);
					batch.draw(boundingRegion, clampedWorldCoord.x, clampedWorldCoord.y);
				}
				else if(!gridView.getWorld().isLocationEmpty(l)&&gridView.getWorld().getPipe(l).hasRotationTable())
				{
					batch.draw(boundingRegion, clampedWorldCoord.x, clampedWorldCoord.y);
					batch.draw(rotateArrow, clampedWorldCoord.x, clampedWorldCoord.y);
				}
			}
		}
	}
	
	private boolean isInBounds(float x, float y)
	{
		return x>=0&&x<worldBounds.x&&y>=0&&y<worldBounds.y;
	}
	
	public void onMouseUp(float x, float y)
	{
		if(isCloseEnough(x, y))
		{
			Location loc = converter.convertFromWorldToGrid(converter.convertFromScreenToWorld(mouseCoord.x, mouseCoord.y));
			if(gridView.getWorld().isValidLocation(loc))
			{
				if(gridView.getWorld().isLocationEmpty(loc))
					editingController.attemptAddPipe(loc);
				else
					editingController.attemptRotatePipe(loc);
			}
		}
	}
	
	private boolean isCloseEnough(float x, float y)
	{
		return Math.abs(x-mouseCoord.x)<5&&Math.abs(y-mouseCoord.y)<5;
	}
	
	public void onMouseDown(float x, float y)
	{
		mouseCoord = converter.convertFromMouseToScreen(x, y);
	}

}
