package com.clearlyspam23.LD28.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.clearlyspam23.LD28.controller.GridEditingController;
import com.clearlyspam23.LD28.util.WorldToScreenConverter;

public class GameView{
	
	private GridView gameView;
	private PipeBar sidebar;
	private GridEditingController controller;
	private OrthographicCamera gridCamera;
	private OrthographicCamera fullCamera;
	private WorldToScreenConverter converter;
	
	private boolean isDown;
	private Vector2 downLoc = new Vector2();
	private Vector2 upLoc = new Vector2();
	
	public void initialize()
	{
		gridCamera = new OrthographicCamera((gameView.getWorld().getWidth()*64)*17/12, gameView.getWorld().getHeight()*64);
		gridCamera.position.x = gridCamera.viewportWidth/2;
		gridCamera.position.y = gridCamera.viewportHeight/2;
		gridCamera.update();
		fullCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		fullCamera.position.x = fullCamera.viewportWidth/2;
		fullCamera.position.y = fullCamera.viewportHeight/2;
		fullCamera.update();
		sidebar.setLocation(fullCamera.viewportWidth*3/4 - 20, 20);
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
		gameView.render(batch, delta);
		batch.setProjectionMatrix(fullCamera.combined);
		sidebar.render(batch, delta);
		batch.setProjectionMatrix(m);
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

}
