package com.clearlyspam23.LD28;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.clearlyspam23.LD28.model.PipeDef;
import com.clearlyspam23.LD28.screens.TitleScreen;
import com.clearlyspam23.LD28.util.Direction;
import com.clearlyspam23.LD28.view.PipeRenderer;
import com.clearlyspam23.LD28.view.renderers.BasicPipeRenderer;

public class LD28Game implements ApplicationListener {
	//private OrthographicCamera camera;
	public SpriteBatch batch;
	public Texture texture;
	public Texture UI;
	public Texture toolBarBackground;
	public Texture worldBackground;
	public Texture UIBackground;
	public Texture gradientBackground;
	
	public BitmapFont font;
	
	public static final int PIPE_WIDTH = 64;
	public static final int PIPE_HEIGHT = 64;
	public static final float WORLD_STEP_DELAY = 0.0625f;
	public static final int OPTIMAL_RENDER_WIDTH = 1024;
	public static final int OPTIMAL_RENDER_HEIGHT = 768;
	
	public HashMap<PipeDef, PipeRenderer> renderMap = new HashMap<PipeDef, PipeRenderer>();
	public ArrayList<PipeDef> definitions = new ArrayList<PipeDef>();
	
	public LevelsHardCode levels;
	
	private Screen currentScreen;
	
	public void changeScreen(Screen screen)
	{
		currentScreen.hide();
		currentScreen = screen;
		currentScreen.show();
	}
	
//	private float worldDelay;
//	private GridWorld currentWorld;
//	private GridWorld previousWorld;
//	private GameView view;
//	private GridEditingController editingController;
//	private GridRunningController runningController;
	
	@Override
	public void create() {		
		
		Texture.setEnforcePotImages(false);
		
		PipeDef horizontalPipe = createBidirectional(new Direction[] { Direction.LEFT, Direction.RIGHT } );
		PipeDef verticalPipe = createBidirectional(new Direction[] { Direction.UP, Direction.DOWN } );
		PipeDef finishHorizontalPipe = createBidirectional(new Direction[] { Direction.LEFT, Direction.RIGHT } );
		PipeDef finishVerticalPipe = createBidirectional(new Direction[] { Direction.UP, Direction.DOWN } );
		PipeDef downRightLPipe = createBidirectional( new Direction[] { Direction.DOWN, Direction.RIGHT } );
		PipeDef downLeftLPipe = createBidirectional( new Direction[] { Direction.DOWN, Direction.LEFT } );
		PipeDef upRightLPipe = createBidirectional( new Direction[] { Direction.UP, Direction.RIGHT } );
		PipeDef upLeftLPipe = createBidirectional(new Direction[] { Direction.UP, Direction.LEFT } );
		PipeDef downRightLeftTPipe = createBidirectional( new Direction[] { Direction.DOWN, Direction.RIGHT, Direction.LEFT } );
		PipeDef upDownLeftTPipe = createBidirectional( new Direction[] { Direction.DOWN, Direction.LEFT, Direction.UP } );
		PipeDef upDownRightTPipe = createBidirectional( new Direction[] { Direction.UP, Direction.RIGHT, Direction.DOWN } );
		PipeDef upLeftRightTPipe = createBidirectional(new Direction[] { Direction.UP, Direction.LEFT, Direction.RIGHT } );
		
		PipeDef[] flatRotationTable = new PipeDef[]{ horizontalPipe, verticalPipe };
		PipeDef[] lRotationTable = new PipeDef[]{ downRightLPipe, downLeftLPipe, upLeftLPipe, upRightLPipe };
		PipeDef[] tRotationTable = new PipeDef[]{ downRightLeftTPipe, upDownLeftTPipe, upLeftRightTPipe, upDownRightTPipe };
		
		horizontalPipe.rotationTable = verticalPipe.rotationTable = flatRotationTable;
		horizontalPipe.placeInTable = 0;
		verticalPipe.placeInTable = 1;
		
		downRightLPipe.rotationTable = downLeftLPipe.rotationTable = upRightLPipe.rotationTable = upLeftLPipe.rotationTable = lRotationTable;
		downRightLPipe.placeInTable = 0;
		downLeftLPipe.placeInTable = 1;
		upLeftLPipe.placeInTable = 2;
		upRightLPipe.placeInTable = 3;
		
		downRightLeftTPipe.rotationTable = upDownLeftTPipe.rotationTable = upLeftRightTPipe.rotationTable = upDownRightTPipe.rotationTable = tRotationTable;
		downRightLeftTPipe.placeInTable = 0;
		upDownLeftTPipe.placeInTable = 1;
		upLeftRightTPipe.placeInTable = 2;
		upDownRightTPipe.placeInTable = 3;
		
		definitions.add(finishHorizontalPipe);
		definitions.add(finishVerticalPipe);
		definitions.add(horizontalPipe);
		definitions.add(verticalPipe);
		definitions.add(downRightLPipe);
		definitions.add(downLeftLPipe);
		definitions.add(upRightLPipe);
		definitions.add(upLeftLPipe);
		definitions.add(downRightLeftTPipe);
		definitions.add(upDownLeftTPipe);
		definitions.add(upLeftRightTPipe);
		definitions.add(upDownRightTPipe);
		
		texture = new Texture(Gdx.files.internal("data/Pipes.png"));
		UI = new Texture(Gdx.files.internal("data/UI.png"));
		toolBarBackground = new Texture(Gdx.files.internal("data/ToolBarBackground.png"));
		worldBackground = new Texture(Gdx.files.internal("data/GrassBackground1.png"));
		UIBackground = new Texture(Gdx.files.internal("data/UIBackground.png"));
		gradientBackground = new Texture(Gdx.files.internal("data/BlueGradientBackground.png"));
		font = new BitmapFont(Gdx.files.internal("data/GadugiBlack.fnt"));
		TextureRegion[][] regions = TextureRegion.split(texture, PIPE_WIDTH, PIPE_HEIGHT);
//		for(TextureRegion[] trs : regions)
//			for(TextureRegion r : trs)
//				r.flip(false, true);
		
		//WorldToScreenConverter converter = new WorldToScreenConverter(PIPE_WIDTH, PIPE_HEIGHT);
		renderMap.put(horizontalPipe, new BasicPipeRenderer(regions[0][0], regions[2][0]));
		renderMap.put(verticalPipe, new BasicPipeRenderer(regions[0][1], regions[2][1]));
		renderMap.put(finishHorizontalPipe, new BasicPipeRenderer(regions[1][0], regions[3][0]));
		renderMap.put(finishVerticalPipe, new BasicPipeRenderer(regions[1][1], regions[3][1]));
		renderMap.put(downRightLPipe, new BasicPipeRenderer(regions[0][2], regions[2][2]));
		renderMap.put(downLeftLPipe, new BasicPipeRenderer(regions[0][3], regions[2][3]));
		renderMap.put(upRightLPipe, new BasicPipeRenderer(regions[1][2], regions[3][2]));
		renderMap.put(upLeftLPipe, new BasicPipeRenderer(regions[1][3], regions[3][3]));
		renderMap.put(downRightLeftTPipe, new BasicPipeRenderer(regions[0][4], regions[2][4]));
		renderMap.put(upDownLeftTPipe, new BasicPipeRenderer(regions[0][5], regions[2][5]));
		renderMap.put(upDownRightTPipe, new BasicPipeRenderer(regions[1][4], regions[3][4]));
		renderMap.put(upLeftRightTPipe, new BasicPipeRenderer(regions[1][5], regions[3][5]));
		
		
//		camera = new OrthographicCamera(currentWorld.getWidth()*PIPE_WIDTH, currentWorld.getHeight()*PIPE_HEIGHT);
//		//camera.setToOrtho(true, camera.viewportWidth, camera.viewportHeight);
//		camera.position.x = camera.viewportWidth/2;
//		camera.position.y = camera.viewportHeight/2;
//		camera.update();
		batch = new SpriteBatch();
		
		levels = new LevelsHardCode();
		
		currentScreen = new TitleScreen(this, batch, new TextureRegion(gradientBackground), font);
		currentScreen.show();
		
		//texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	}

	@Override
	public void dispose() {
		batch.dispose();
		texture.dispose();
		UI.dispose();
		toolBarBackground.dispose();
		worldBackground.dispose();
		UIBackground.dispose();
		gradientBackground.dispose();
		levels.dispose();
	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		currentScreen.render(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
	
	public static PipeDef createBidirectional(Direction[] dir)
	{
		PipeDef p = new PipeDef();
		p.inputs = p.outputs = dir;
		return p;
	}
}
