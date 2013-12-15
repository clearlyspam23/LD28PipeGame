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
	private BasicButton undoButton;
	private BasicButton startButton;
	private TextureRegion boundingRegion;
	private TextureRegion background;
	private TextureRegion UIBackground;
	private GridEditingController controller;
	private OrthographicCamera gridCamera;
	private OrthographicCamera fullCamera;
	private WorldToScreenConverter converter;
	
	private Map<PipeDef, PipeRenderer> renderMap;
	
	private boolean isDown;
	private Vector2 downLoc = new Vector2();
	private Vector2 upLoc = new Vector2();
	private Rectangle worldBounds = new Rectangle();
	private Rectangle mouseBounds = new Rectangle();
	
	
	private Vector2 mouseCoord = new Vector2();
	
	public void initialize()
	{
		worldBounds.set(0, 0, (gameView.getWorld().getWidth())*64, (gameView.getWorld().getHeight())*64);
		mouseBounds.set(worldBounds.x, worldBounds.y, worldBounds.width+64, worldBounds.height+64+32);
		gridCamera = new OrthographicCamera(worldBounds.width*17/12, worldBounds.height);
		gridCamera.position.x = gridCamera.viewportWidth/2;
		gridCamera.position.y = gridCamera.viewportHeight/2;
		gridCamera.update();
		fullCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		fullCamera.position.x = fullCamera.viewportWidth/2;
		fullCamera.position.y = fullCamera.viewportHeight/2;
		fullCamera.update();
		sidebar.setLocation(fullCamera.viewportWidth*3/4 - 20, 20);
		undoButton.setLocation(fullCamera.viewportWidth*3/4 - 20, sidebar.getY()+sidebar.getHeight()+20);
		startButton.setLocation(fullCamera.viewportWidth*3/4 - 20 + startButton.getBounds().getWidth() + 10, sidebar.getY()+sidebar.getHeight()+20);
		System.out.println(fullCamera.viewportHeight);
		converter = new WorldToScreenConverter(gridCamera, 64, 64);
		startButton.enable(controller.getNumMoves()<=0);
		undoButton.enable(controller.canUndo());
		sidebar.initialize();
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
		batch.setProjectionMatrix(fullCamera.combined);
		if(background!=null)
			batch.draw(background, worldBounds.x, worldBounds.y);
		batch.setProjectionMatrix(gridCamera.combined);
		if(isInBounds(mouseCoord.x, Gdx.graphics.getHeight() - mouseCoord.y))
		{
			Vector2 worldCoord = converter.convertFromScreenToWorld(mouseCoord);
			Location l = converter.convertFromWorldToGrid(worldCoord);
			Vector2 clampedWorldCoord = converter.clampToGrid(worldCoord);
			if(gameView.getWorld().isValidLocation(l))
			{
				if(gameView.getWorld().isLocationEmpty(l)&&controller.getCurrentPipe()!=null&&controller.getNumMoves()>0)
				{
					Pipe temp = new Pipe(controller.getCurrentPipe());
					temp.setLocation(l.x, l.y);
					renderMap.get(controller.getCurrentPipe()).render(batch, temp, temp, delta);
					batch.draw(boundingRegion, clampedWorldCoord.x, clampedWorldCoord.y);
				}
				else if(!gameView.getWorld().isLocationEmpty(l)&&gameView.getWorld().getPipe(l).hasRotationTable())
				{
					batch.draw(boundingRegion, clampedWorldCoord.x, clampedWorldCoord.y);
				}
			}
		}
		gameView.render(batch, delta);
		batch.setProjectionMatrix(fullCamera.combined);
		batch.draw(UIBackground, sidebar.getX()-25, 0);
		sidebar.render(batch, delta);
		startButton.render(batch, delta);
		undoButton.render(batch, delta);
		batch.setProjectionMatrix(m);
	}
	
	private boolean isInBounds(float x, float y)
	{
		return mouseBounds.contains(x, y);
	}
	
	public void checkInput()
	{
		mouseCoord.set(Gdx.input.getX(), Gdx.input.getY());
		startButton.checkMouseOver(mouseCoord.x, Gdx.graphics.getHeight() - mouseCoord.y);
		undoButton.checkMouseOver(mouseCoord.x, Gdx.graphics.getHeight() - mouseCoord.y);
		sidebar.checkMouseOver(mouseCoord.x, Gdx.graphics.getHeight() - mouseCoord.y);
		if(Gdx.input.isTouched()&&!isDown)
		{
			downLoc.set(Gdx.input.getX(), Gdx.input.getY());
			startButton.onDown(downLoc.x, fullCamera.viewportHeight - downLoc.y);
			undoButton.onDown(downLoc.x, fullCamera.viewportHeight - downLoc.y);
			sidebar.onMouseDown(downLoc.x, fullCamera.viewportHeight - downLoc.y);
			isDown = true;
		}
		else if(!Gdx.input.isTouched()&&isDown)
		{
			upLoc.set(Gdx.input.getX(), Gdx.input.getY());
			startButton.onUp(upLoc.x, fullCamera.viewportHeight - upLoc.y);
			undoButton.onUp(upLoc.x, fullCamera.viewportHeight - upLoc.y);
			sidebar.onMouseUp(downLoc.x, fullCamera.viewportHeight - downLoc.y);
			isDown = false;
			if(isCloseEnough())
			{
				Location loc = converter.convertFromWorldToGrid(converter.convertFromScreenToWorld(downLoc));
				if(gameView.getWorld().isValidLocation(loc))
				{
					if(gameView.getWorld().isLocationEmpty(loc))
						controller.attemptAddPipe(loc);
					else
						controller.attemptRotatePipe(loc);
				}
			}
		}
		startButton.enable(controller.getNumMoves()<=0);
		undoButton.enable(controller.canUndo());
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

	public void setUndoButton(TextureRegion up, TextureRegion down) {
		undoButton = new BasicButton(up, down, 128, 128, new UndoButtonEvent(controller));
	}

	public void setStartButton(TextureRegion up, TextureRegion down) {
		startButton = new BasicButton(up, down, 128, 128, new StartButtonEvent(controller));
	}

	public void setBackground(TextureRegion background) {
		this.background = background;
	}

	public void setUIBackground(TextureRegion uIBackground) {
		UIBackground = uIBackground;
	}

	private static class StartButtonEvent implements ClickEvent
	{
		
		private GridEditingController controller;
		
		public StartButtonEvent(GridEditingController cont)
		{
			controller = cont;
		}

		@Override
		public void onClick(float x, float y) {
			controller.toggleStart(true);
		}
		
	}
	
	private static class UndoButtonEvent implements ClickEvent
	{
		
		private GridEditingController controller;
		
		public UndoButtonEvent(GridEditingController cont)
		{
			controller = cont;
		}

		@Override
		public void onClick(float x, float y) {
			controller.undo();
		}
		
	}

}
