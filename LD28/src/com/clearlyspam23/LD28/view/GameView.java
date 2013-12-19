package com.clearlyspam23.LD28.view;

import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.clearlyspam23.LD28.LD28Game;
import com.clearlyspam23.LD28.controller.GridEditingController;
import com.clearlyspam23.LD28.controller.GridRunningController;
import com.clearlyspam23.LD28.controller.VictoryListener;
import com.clearlyspam23.LD28.model.Pipe;
import com.clearlyspam23.LD28.model.PipeDef;
import com.clearlyspam23.LD28.util.Location;
import com.clearlyspam23.LD28.util.WorldToScreenConverter;

public class GameView implements VictoryListener{
	
	private PipeBar sidebar;
	private TextureRegion startUp;
	private TextureRegion startDown;
	private TextureRegion undoUp;
	private TextureRegion undoDown;
	private TextureRegion cancelUp;
	private TextureRegion cancelDown;
	private TextureRegion fastUp;
	private TextureRegion fastDown;
	private TextureRegion uiBackground;
	
	private GridView gridView;
	
	private GameUI ui;
	
	private VictoryOverlay overlay;
	
	private TextureRegion boundingRegion;
	private TextureRegion rotateArrow;
	private TextureRegion background;
	private GridEditingController editingController;
	private GridRunningController runningController;
	private OrthographicCamera gridCamera;
	private OrthographicCamera fullCamera;
	private WorldToScreenConverter converter;
	
	private Map<PipeDef, PipeRenderer> renderMap;
	
	private boolean isDown;
	private Vector2 downLoc = new Vector2();
	private Vector2 upLoc = new Vector2();
	private Rectangle worldBounds = new Rectangle();
	private Rectangle mouseBounds = new Rectangle();
	
	private BitmapFont font;
	
	
	private Vector2 mouseCoord = new Vector2();
	
	public void initialize()
	{
		worldBounds.set(0, 0, (gridView.getWorld().getWidth())*64, (gridView.getWorld().getHeight())*64);
		mouseBounds.set(worldBounds.x, worldBounds.y, worldBounds.width+64, worldBounds.height+64+32);
		gridCamera = new OrthographicCamera(worldBounds.width*17/12, worldBounds.height);
		gridCamera.position.x = gridCamera.viewportWidth/2;
		gridCamera.position.y = gridCamera.viewportHeight/2;
		gridCamera.update();
		fullCamera = new OrthographicCamera(LD28Game.OPTIMAL_RENDER_WIDTH, LD28Game.OPTIMAL_RENDER_HEIGHT);
		fullCamera.position.x = fullCamera.viewportWidth/2;
		fullCamera.position.y = fullCamera.viewportHeight/2;
		fullCamera.update();
		setEditingUI();
		converter = new WorldToScreenConverter(gridCamera, fullCamera, 64, 64);
	}
	
	public GridView getGameView() {
		return gridView;
	}
	public void setGameView(GridView gameView) {
		this.gridView = gameView;
	}

	public void render(SpriteBatch batch, float delta)
	{
		Matrix4 m = batch.getProjectionMatrix();
		Vector2 mouseLoc = new Vector2(mouseCoord.x, mouseCoord.y);
		mouseLoc = converter.convertFromMouseToWorld(mouseLoc);
		batch.setProjectionMatrix(fullCamera.combined);
		if(background!=null)
			batch.draw(background, worldBounds.x, worldBounds.y);
		batch.setProjectionMatrix(gridCamera.combined);
		if(isInBounds(mouseCoord.x, Gdx.graphics.getHeight() - mouseCoord.y))
		{
			Vector2 worldCoord = converter.convertFromMouseToWorld(mouseCoord);
			Location l = converter.convertFromWorldToGrid(worldCoord);
			if(gridView.getWorld().isValidLocation(l))
			{
				Vector2 clampedWorldCoord = converter.clampToGrid(worldCoord);
				if(gridView.getWorld().isLocationEmpty(l)&&editingController.getCurrentPipe()!=null&&editingController.getNumMoves()>0)
				{
					Pipe temp = new Pipe(editingController.getCurrentPipe());
					temp.setLocation(l.x, l.y);
					renderMap.get(editingController.getCurrentPipe()).render(batch, temp, temp, delta);
					batch.draw(boundingRegion, clampedWorldCoord.x, clampedWorldCoord.y);
				}
				else if(!gridView.getWorld().isLocationEmpty(l)&&gridView.getWorld().getPipe(l).hasRotationTable()&&editingController.getNumMoves()>0)
				{
					batch.draw(boundingRegion, clampedWorldCoord.x, clampedWorldCoord.y);
					batch.draw(rotateArrow, clampedWorldCoord.x, clampedWorldCoord.y);
				}
			}
		}
		gridView.render(batch, delta);
		batch.setProjectionMatrix(fullCamera.combined);
		if(!editingController.shouldStart())
			font.draw(batch, "Moves Left : " + editingController.getNumMoves(), 20, fullCamera.viewportHeight-20);
		ui.render(batch, delta);
		if(overlay!=null)
			overlay.render(batch, delta);
		batch.setProjectionMatrix(m);
	}
	
	private boolean isInBounds(float x, float y)
	{
		return x>=0&&x<Gdx.graphics.getWidth()*3/4-20&&y>=0&&y<Gdx.graphics.getHeight();
	}
	
	public void checkInput()
	{
		if(overlay!=null)
		{
			if(Gdx.input.isKeyPressed(Keys.ANY_KEY))
				runningController.setAllFinished(true);
			if(Gdx.input.isTouched()&&!isDown)
				runningController.setAllFinished(true);
			else if(!Gdx.input.isTouched()&&isDown)
				isDown = false;
			return;
		}
		mouseCoord.set(Gdx.input.getX(), Gdx.input.getY());
		ui.checkMouseOver(mouseCoord.x, Gdx.graphics.getHeight() - mouseCoord.y);
		if(Gdx.input.isTouched()&&!isDown)
		{
			downLoc.set(Gdx.input.getX(), Gdx.input.getY());
			ui.checkMouseDown(mouseCoord.x, Gdx.graphics.getHeight() - mouseCoord.y);
			isDown = true;
		}
		else if(!Gdx.input.isTouched()&&isDown)
		{
			upLoc.set(Gdx.input.getX(), Gdx.input.getY());
			ui.checkMouseUp(mouseCoord.x, Gdx.graphics.getHeight() - mouseCoord.y);
			isDown = false;
			if(isCloseEnough())
			{
				Location loc = converter.convertFromWorldToGrid(converter.convertFromMouseToWorld(downLoc));
				if(gridView.getWorld().isValidLocation(loc))
				{
					if(gridView.getWorld().isLocationEmpty(loc))
						editingController.attemptAddPipe(loc);
					else
						editingController.attemptRotatePipe(loc);
				}
			}
		}
		ui.checkEnable();
	}
	
	private boolean isCloseEnough()
	{
		return Math.abs(downLoc.x-upLoc.x)<5&&Math.abs(downLoc.y-upLoc.y)<5;
	}

	public GridEditingController getEditingController() {
		return editingController;
	}

	public void setEditingController(GridEditingController controller) {
		this.editingController = controller;
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

	public void setBackground(TextureRegion background) {
		this.background = background;
	}
	
	public void setEditingUI()
	{
		ui = new GameUI();
		ui.setSidebar(sidebar);
		ui.setUIBackground(uiBackground);
		UndoButtonHandler undoHandler = new UndoButtonHandler(editingController);
		ui.setButton1(undoUp, undoDown, undoHandler, undoHandler);
		StartButtonHandler startHandler = new StartButtonHandler(editingController, this);
		ui.setButton2(startUp, startDown, startHandler, startHandler);
		ui.setLocation(fullCamera.viewportWidth*3/4, 0);
		ui.initialize();
	}
	
	public void setRunningUI()
	{
		ui = new GameUI();
		ui.setSidebar(sidebar);
		ui.setUIBackground(uiBackground);
		CancelButtonHandler cancelHandler = new CancelButtonHandler(editingController, this);
		ui.setButton1(cancelUp, cancelDown, cancelHandler, cancelHandler);
		FastForwardButtonHandler fastHandler = new FastForwardButtonHandler(runningController);
		ui.setButton2(fastUp, fastDown, fastHandler, fastHandler);
		ui.setLocation(fullCamera.viewportWidth*3/4, 0);
		ui.initialize();
	}

	public void setSidebar(PipeBar sidebar) {
		this.sidebar = sidebar;
	}

	private static class StartButtonHandler implements ClickEvent, ButtonEnabler
	{
		
		private GridEditingController controller;
		private GameView gameView;
		
		public StartButtonHandler(GridEditingController cont, GameView view)
		{
			controller = cont;
			gameView = view;
		}

		@Override
		public void onClick(float x, float y) {
			controller.toggleStart(true);
			gameView.setRunningUI();
		}

		@Override
		public void onDown(float x, float y) {
		}

		@Override
		public void onUp(float x, float y) {
		}

		@Override
		public boolean shouldEnable() {
			return controller.getNumMoves()<=0;
		}
		
	}
	
	private static class UndoButtonHandler implements ClickEvent, ButtonEnabler
	{
		
		private GridEditingController controller;
		
		public UndoButtonHandler(GridEditingController cont)
		{
			controller = cont;
		}

		@Override
		public void onClick(float x, float y) {
			controller.undo();
		}

		@Override
		public void onDown(float x, float y) {	
		}

		@Override
		public void onUp(float x, float y) {
		}

		@Override
		public boolean shouldEnable() {
			return controller.canUndo();
		}
		
	}
	
	private static class FastForwardButtonHandler implements ClickEvent, ButtonEnabler
	{
		
		private GridRunningController controller;
		
		public FastForwardButtonHandler(GridRunningController cont)
		{
			controller = cont;
		}

		@Override
		public void onClick(float x, float y) {
			
		}

		@Override
		public void onDown(float x, float y) {	
			controller.setTimeStep(40);
		}

		@Override
		public void onUp(float x, float y) {
			controller.setTimeStep(10);
		}

		@Override
		public boolean shouldEnable() {
			return true;
		}
		
	}
	
	private static class CancelButtonHandler implements ClickEvent, ButtonEnabler
	{
		
		private GridEditingController controller;
		private GameView gameView;
		
		public CancelButtonHandler(GridEditingController cont, GameView view)
		{
			controller = cont;
			gameView = view;
		}

		@Override
		public void onClick(float x, float y) {
			controller.reset();
			gameView.setEditingUI();
		}

		@Override
		public void onDown(float x, float y) {	
		}

		@Override
		public void onUp(float x, float y) {
		}

		@Override
		public boolean shouldEnable() {
			return true;
		}
		
	}

	public void setStartButtonTextures(TextureRegion startUp,
			TextureRegion startDown) {
		this.startUp = startUp;
		this.startDown = startDown;
		
	}

	public void setUndoButtonTextures(TextureRegion undoUp,
			TextureRegion undoDown) {
		this.undoUp = undoUp;
		this.undoDown = undoDown;
		
	}

	public void setCancelButtonTextures(TextureRegion cancelUp,
			TextureRegion cancelDown) {
		this.cancelUp = cancelUp;
		this.cancelDown = cancelDown;
		
	}

	public void setFastForwardButtonTextures(TextureRegion fastUp2,
			TextureRegion fastDown2) {
		fastUp = fastUp2;
		fastDown = fastDown2;
		
	}

	public void setUIBackground(TextureRegion textureRegion) {
		uiBackground = textureRegion;
		
	}

	public GridRunningController getRunningController() {
		return runningController;
	}

	public void setRunningController(GridRunningController runningController) {
		this.runningController = runningController;
	}

	public void setFont(BitmapFont font) {
		this.font = font;
	}

	public void setRotateArrow(TextureRegion rotateArrow) {
		this.rotateArrow = rotateArrow;
	}

	@Override
	public void onVictory(GridRunningController controller) {
		overlay = new VictoryOverlay(font);
		
	}

}
