package com.clearlyspam23.LD28.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.clearlyspam23.LD28.LD28Game;
import com.clearlyspam23.LD28.controller.GridEditingController;
import com.clearlyspam23.LD28.controller.GridRunningController;
import com.clearlyspam23.LD28.model.GridWorld;
import com.clearlyspam23.LD28.model.Level;
import com.clearlyspam23.LD28.view.GameView;
import com.clearlyspam23.LD28.view.GridView;
import com.clearlyspam23.LD28.view.PipeBar;

public class GameScreen extends AbstractScreen {
	
	private float worldDelay;
	private GridWorld currentWorld;
	private GridWorld previousWorld;
	private GameView view;
	private GridEditingController editingController;
	private GridRunningController runningController;
	private int levelIndex;

	public GameScreen(LD28Game g, SpriteBatch b, int level) {
		super(g, b);
		levelIndex = level;
	}

	@Override
	public void render(float delta) {
		if(!editingController.shouldStart())
		{
			
		}
		else
		{
			worldDelay+=Gdx.graphics.getDeltaTime();
			if(worldDelay>=LD28Game.WORLD_STEP_DELAY)
			{
				previousWorld.shallowSet(currentWorld);
				runningController.simulate();
				worldDelay-=LD28Game.WORLD_STEP_DELAY;
			}
		}
		
		view.checkInput();
		//view.render(worldDelay/WORLD_STEP_DELAY);
		
//		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		view.render(batch, worldDelay/LD28Game.WORLD_STEP_DELAY);
		batch.end();
		
		if(runningController.isAllFinished())
		{
			if(levelIndex+1<game.levels.levels.length)
				game.changeScreen(new GameScreen(game, batch, levelIndex+1));
			else
				game.changeScreen(new EndingScreen(game, batch, new TextureRegion(game.gradientBackground), game.font));
		}

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		//Model code
		
		Level l = game.levels.levels[levelIndex];
		
		currentWorld = new GridWorld(l.levelData[0].length, l.levelData.length);
		
		for(int i = 0; i < l.levelData.length; i++)
		{
			for(int j = 0; j < l.levelData[i].length; j++)
			{
				if(l.levelData[i][j]>=0)
					currentWorld.addNormalPipe(game.definitions.get(l.levelData[i][j]), j, l.levelData.length-i-1);
			}
		}
		
//		currentWorld.addNormalPipe(finishHorizontalPipe, 9, 9);
//		currentWorld.addNormalPipe(downRightLPipe, 8, 9);
//		currentWorld.addNormalPipe(verticalPipe, 8, 8);
//		currentWorld.addNormalPipe(verticalPipe, 8, 7);
//		currentWorld.addNormalPipe(verticalPipe, 8, 6);
//		//currentWorld.addNormalPipe(verticalPipe, 8, 5);
//		currentWorld.addNormalPipe(verticalPipe, 8, 4);
//		currentWorld.addNormalPipe(verticalPipe, 8, 3);
//		currentWorld.addNormalPipe(verticalPipe, 8, 2);
//		currentWorld.addNormalPipe(upLeftLPipe, 8, 1);
//		currentWorld.addNormalPipe(horizontalPipe, 7, 1);
//		currentWorld.addNormalPipe(horizontalPipe, 6, 1);
//		currentWorld.addNormalPipe(horizontalPipe, 5, 1);
//		currentWorld.addNormalPipe(horizontalPipe, 4, 1);
//		currentWorld.addNormalPipe(horizontalPipe, 3, 1);
//		currentWorld.addNormalPipe(horizontalPipe, 2, 1);
//		currentWorld.addNormalPipe(horizontalPipe, 1, 1);
//		currentWorld.addNormalPipe(downRightLPipe, 0, 1);
//		currentWorld.addNormalPipe(finishVerticalPipe, 0, 0);
//		currentWorld.addStart(new Location(9, 9), Direction.RIGHT);
//		currentWorld.addFinish(new Location(0, 0));
//		previousWorld = new GridWorld(currentWorld);
		currentWorld.addStart(l.startLoc, l.startDirection);
		currentWorld.addFinish(l.endLoc);
		previousWorld = new GridWorld(currentWorld);
		
		//Controller code
		
		editingController = new GridEditingController(currentWorld);
		editingController.setNumMoves(l.numMoves);
		for(int i = 0; i < l.allowedPipes.length; i++)
			editingController.addPipeDef(game.definitions.get(l.allowedPipes[i]));
//		editingController.addPipeDef(verticalPipe);
//		editingController.addPipeDef(horizontalPipe);
//		editingController.addPipeDef(upLeftLPipe);
		
		runningController = new GridRunningController(currentWorld);
		runningController.setTimeStep(10);
		
		//View code
		
		view = new GameView();
		runningController.addVictoryListener(view);
		
		GridView gridView = new GridView(currentWorld, previousWorld, game.renderMap);
		view.setRenderMap(game.renderMap);
		view.setGameView(gridView);
		TextureRegion background = new TextureRegion(game.toolBarBackground);
		TextureRegion pipeActive = new TextureRegion(game.UI, 320, 0, 70, 70);
		TextureRegion pipeInactive = new TextureRegion(game.UI, 64, 0, 70, 70);
		TextureRegion pipeOver = new TextureRegion(game.UI, 192, 0, 70, 70);
		TextureRegion startUp = new TextureRegion(game.UI, 0, 128, 128, 128);
		TextureRegion startDown = new TextureRegion(game.UI, 128, 128, 128, 128);
		TextureRegion undoUp = new TextureRegion(game.UI, 0, 256, 128, 128);
		TextureRegion undoDown = new TextureRegion(game.UI, 128, 256, 128, 128);
		TextureRegion cancelUp = new TextureRegion(game.UI, 256, 128, 128, 128);
		TextureRegion cancelDown = new TextureRegion(game.UI, 384, 128, 128, 128);
		TextureRegion fastUp = new TextureRegion(game.UI, 0, 384, 128, 128);
		TextureRegion fastDown = new TextureRegion(game.UI, 128, 384, 128, 128);
		PipeBar sidebar = new PipeBar(background, pipeActive, pipeInactive, pipeOver);
		sidebar.setController(editingController);
		sidebar.setMap(game.renderMap);
		view.setFont(game.font);
		view.setRotateArrow(new TextureRegion(game.UI, 446, 0, 64, 64));
		view.setSidebar(sidebar);
		view.setEditingController(editingController);
		view.setRunningController(runningController);
		view.setBoundingRegion(new TextureRegion(game.UI, 0, 0, 64, 64));
		view.setStartButtonTextures(startUp, startDown);
		view.setUndoButtonTextures(undoUp, undoDown);
		view.setCancelButtonTextures(cancelUp, cancelDown);
		view.setFastForwardButtonTextures(fastUp, fastDown);
		view.setBackground(l.background);
		view.setUIBackground(new TextureRegion(game.UIBackground));
		
		view.initialize();
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
