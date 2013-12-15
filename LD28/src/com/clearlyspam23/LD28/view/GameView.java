package com.clearlyspam23.LD28.view;

import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.clearlyspam23.LD28.controller.GridEditingController;
import com.clearlyspam23.LD28.model.Pipe;
import com.clearlyspam23.LD28.model.PipeDef;
import com.clearlyspam23.LD28.util.Location;
import com.clearlyspam23.LD28.util.WorldToScreenConverter;

public class GameView{
	
	private GridView gameView;
	private PipeBar sidebar;
	private TextureRegion boundingRegion;
	private GridEditingController controller;
	private OrthographicCamera gridCamera;
	private OrthographicCamera fullCamera;
	private WorldToScreenConverter converter;
	
	private Map<PipeDef, PipeRenderer> renderMap;
	
	private boolean isDown;
	private Vector2 downLoc = new Vector2();
	private Vector2 upLoc = new Vector2();
	private Rectangle worldBounds = new Rectangle();
	
	public void initialize()
	{
		worldBounds.set(0, 0, gameView.getWorld().getWidth()*64, gameView.getWorld().getHeight()*64);
		gridCamera = new OrthographicCamera(worldBounds.width*17/12, worldBounds.height);
		gridCamera.position.x = gridCamera.viewportWidth/2;
		gridCamera.position.y = gridCamera.viewportHeight/2;
		gridCamera.update();
		fullCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		fullCamera.position.x = fullCamera.viewportWidth/2;
		fullCamera.position.y = fullCamera.viewportHeight/2;
		fullCamera.update();
		sidebar.setLocation(fullCamera.viewportWidth*3/4 - 10, 10);
		System.out.println(fullCamera.viewportHeight);
		converter = new WorldToScreenConverter(gridCamera, 64, 64);
	}
	
	public GridView getGameView() {
		return gameView;
	}
	public void setGameView(GridView gameView) {
		this.gameView = gameView;
	}
	public PipeBar getSidebar() {
		return sidebar;
	}
	public void setSidebar(PipeBar sidebar) {
		this.sidebar = sidebar;
	}

	public void render(SpriteBatch batch, float delta)
	{
		Matrix4 m = batch.getProjectionMatrix();
		batch.setProjectionMatrix(gridCamera.combined);
		Vector2 mouseCoord = new Vector2(Gdx.input.getX(), Gdx.input.getY());
		if(isInBounds(mouseCoord)&&controller.getCurrentPipe()!=null&&controller.getNumMoves()>0)
		{
			Vector2 worldCoord = converter.convertFromScreenToWorld(mouseCoord);
			Location l = converter.convertFromWorldToGrid(worldCoord);
			if(gameView.getWorld().isLocationEmpty(l))
			{
				Pipe temp = new Pipe(controller.getCurrentPipe());
				temp.setLocation(l.x, l.y);
				renderMap.get(controller.getCurrentPipe()).render(batch, temp, temp, delta);
				Vector2 clampedWorldCoord = converter.clampToGrid(worldCoord);
				batch.draw(boundingRegion, clampedWorldCoord.x, clampedWorldCoord.y);
			}
		}
		gameView.render(batch, delta);
		batch.setProjectionMatrix(fullCamera.combined);
		sidebar.render(batch, delta);
		batch.setProjectionMatrix(m);
	}
	
	private boolean isInBounds(Vector2 point)
	{
		return worldBounds.contains(point);
	}
	
	public void checkInput()
	{
		if(Gdx.input.isTouched()&&!isDown)
		{
			downLoc.set(Gdx.input.getX(), Gdx.input.getY());
			isDown = true;
		}
		else if(!Gdx.input.isTouched()&&isDown)
		{
			upLoc.set(Gdx.input.getX(), Gdx.input.getY());
			isDown = false;
			if(isCloseEnough())
			{
				controller.attemptAddPipe(converter.convertFromWorldToGrid(converter.convertFromScreenToWorld(downLoc)));
			}
		}
		if(Gdx.input.isKeyPressed(Keys.Q))
			controller.setCurrentPipe(0);
		if(Gdx.input.isKeyPressed(Keys.W))
			controller.setCurrentPipe(1);
		if(Gdx.input.isKeyPressed(Keys.E))
			controller.setCurrentPipe(2);
	}
	
	private boolean isCloseEnough()
	{
		return Math.abs(downLoc.x-upLoc.x)<5&&Math.abs(downLoc.y-upLoc.y)<5;
	}

	public GridEditingController getController() {
		return controller;
	}

	public void setController(GridEditingController controller) {
		this.controller = controller;
	}

	public Map<PipeDef, PipeRenderer> getRenderMap() {
		return renderMap;
	}

	public void setRenderMap(Map<PipeDef, PipeRenderer> renderMap) {
		this.renderMap = renderMap;
	}

	public TextureRegion getBoundingRegion() {
		return boundingRegion;
	}

	public void setBoundingRegion(TextureRegion boundingRegion) {
		this.boundingRegion = boundingRegion;
	}

}
