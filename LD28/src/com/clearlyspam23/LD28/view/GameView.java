package com.clearlyspam23.LD28.view;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;

public class GameView{
	
	private GridView gameView;
	private PipeBar sidebar;
	private OrthographicCamera gridCamera;
	
	public void initialize()
	{
		gridCamera = new OrthographicCamera((gameView.getWorld().getWidth()*64)*4/3, gameView.getWorld().getHeight()*64);
		gridCamera.position.x = gridCamera.viewportWidth/2;
		gridCamera.position.y = gridCamera.viewportHeight/2;
		gridCamera.update();
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
		batch.setProjectionMatrix(m);
	}

}
