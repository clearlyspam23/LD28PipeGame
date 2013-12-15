package com.clearlyspam23.LD28;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
<<<<<<< HEAD
import com.badlogic.gdx.graphics.Texture;
=======
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
>>>>>>> 6d137583960acb2c40b57bee8f6ba28261714aaa
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.clearlyspam23.LD28.model.GridWorld;
import com.clearlyspam23.LD28.model.PipeDef;
import com.clearlyspam23.LD28.util.Direction;
import com.clearlyspam23.LD28.util.Location;
<<<<<<< HEAD
import com.clearlyspam23.LD28.util.WorldToScreenConverter;
import com.clearlyspam23.LD28.view.GameView;
import com.clearlyspam23.LD28.view.GridView;
import com.clearlyspam23.LD28.view.renderers.BasicPipeRenderer;

public class LD28Game implements ApplicationListener {
	//private OrthographicCamera camera;
=======
import com.clearlyspam23.LD28.view.PipeView;
import com.clearlyspam23.LD28.view.renderers.BasicPipeRenderer;

public class LD28Game implements ApplicationListener {
	private OrthographicCamera camera;
>>>>>>> 6d137583960acb2c40b57bee8f6ba28261714aaa
	private SpriteBatch batch;
	private Texture texture;
	
	private static final int PIPE_WIDTH = 64;
	private static final int PIPE_HEIGHT = 64;
	private static final float WORLD_STEP_DELAY = 0.25f;
	
	private float worldDelay;
<<<<<<< HEAD
	private GridWorld currentWorld;
	private GridWorld previousWorld;
	private GameView view;
	//private GridController controller;
=======
	private GridWorld world;
	private PipeView view;
>>>>>>> 6d137583960acb2c40b57bee8f6ba28261714aaa
	
	@Override
	public void create() {		
		//Model code
		
<<<<<<< HEAD
		currentWorld = new GridWorld(10, 10);
		PipeDef horizontalPipe = createBidirectional(new Direction[] { Direction.LEFT, Direction.RIGHT } );
		PipeDef verticalPipe = createBidirectional(new Direction[] { Direction.UP, Direction.DOWN } );
		PipeDef finishHorizontalPipe = createBidirectional(new Direction[] { Direction.LEFT, Direction.RIGHT } );
		PipeDef finishVerticalPipe = createBidirectional(new Direction[] { Direction.UP, Direction.DOWN } );
=======
		world = new GridWorld(3, 3);
		PipeDef horizontalPipe = createBidirectional(new Direction[] { Direction.LEFT, Direction.RIGHT } );
		PipeDef verticalPipe = createBidirectional(new Direction[] { Direction.UP, Direction.DOWN } );
>>>>>>> 6d137583960acb2c40b57bee8f6ba28261714aaa
		PipeDef downRightLPipe = createBidirectional( new Direction[] { Direction.DOWN, Direction.RIGHT } );
		PipeDef downLeftLPipe = createBidirectional( new Direction[] { Direction.DOWN, Direction.LEFT } );
		PipeDef upRightLPipe = createBidirectional( new Direction[] { Direction.UP, Direction.RIGHT } );
		PipeDef upLeftLPipe = createBidirectional(new Direction[] { Direction.UP, Direction.LEFT } );
<<<<<<< HEAD
		currentWorld.addNormalPipe(finishHorizontalPipe, 9, 9);
		currentWorld.addNormalPipe(downRightLPipe, 8, 9);
		currentWorld.addNormalPipe(verticalPipe, 8, 8);
		currentWorld.addNormalPipe(verticalPipe, 8, 7);
		currentWorld.addNormalPipe(verticalPipe, 8, 6);
		currentWorld.addNormalPipe(verticalPipe, 8, 5);
		currentWorld.addNormalPipe(verticalPipe, 8, 4);
		currentWorld.addNormalPipe(verticalPipe, 8, 3);
		currentWorld.addNormalPipe(verticalPipe, 8, 2);
		currentWorld.addNormalPipe(upLeftLPipe, 8, 1);
		currentWorld.addNormalPipe(horizontalPipe, 7, 1);
		currentWorld.addNormalPipe(horizontalPipe, 6, 1);
		currentWorld.addNormalPipe(horizontalPipe, 5, 1);
		currentWorld.addNormalPipe(horizontalPipe, 4, 1);
		currentWorld.addNormalPipe(horizontalPipe, 3, 1);
		currentWorld.addNormalPipe(horizontalPipe, 2, 1);
		currentWorld.addNormalPipe(horizontalPipe, 1, 1);
		currentWorld.addNormalPipe(downRightLPipe, 0, 1);
		currentWorld.addNormalPipe(finishVerticalPipe, 0, 0);
		currentWorld.addStart(new Location(9, 9), Direction.RIGHT);
		currentWorld.addFinish(new Location(0, 0));
		previousWorld = new GridWorld(currentWorld);
		
		//Controller code
		
		//controller = new GridController();
=======
		world.addNormalPipe(horizontalPipe, 2, 2);
		world.addNormalPipe(downRightLPipe, 1, 2);
		world.addNormalPipe(verticalPipe, 1, 1);
		world.addNormalPipe(upLeftLPipe, 1, 0);
		world.addNormalPipe(downRightLPipe, 0, 0);
		world.startFrom(new Location(2, 2), Direction.RIGHT);
>>>>>>> 6d137583960acb2c40b57bee8f6ba28261714aaa
		
		//View code
		
		texture = new Texture(Gdx.files.internal("data/Pipes.png"));
		TextureRegion[][] regions = TextureRegion.split(texture, PIPE_WIDTH, PIPE_HEIGHT);
//		for(TextureRegion[] trs : regions)
//			for(TextureRegion r : trs)
//				r.flip(false, true);
<<<<<<< HEAD
		view = new GameView();
		WorldToScreenConverter converter = new WorldToScreenConverter(PIPE_WIDTH, PIPE_HEIGHT);
		GridView gridView = new GridView(currentWorld, previousWorld);
		gridView.addRenderer(horizontalPipe, new BasicPipeRenderer(regions[0][0], regions[2][0]));
		gridView.addRenderer(verticalPipe, new BasicPipeRenderer(regions[0][1], regions[2][1]));
		gridView.addRenderer(finishHorizontalPipe, new BasicPipeRenderer(regions[1][0], regions[3][0]));
		gridView.addRenderer(finishVerticalPipe, new BasicPipeRenderer(regions[1][1], regions[3][1]));
		gridView.addRenderer(downRightLPipe, new BasicPipeRenderer(regions[0][2], regions[2][2]));
		gridView.addRenderer(downLeftLPipe, new BasicPipeRenderer(regions[0][3], regions[2][3]));
		gridView.addRenderer(upRightLPipe, new BasicPipeRenderer(regions[1][2], regions[3][2]));
		gridView.addRenderer(upLeftLPipe, new BasicPipeRenderer(regions[1][3], regions[3][3]));
		view.setGameView(gridView);
		
		view.initialize();
		
		
//		camera = new OrthographicCamera(currentWorld.getWidth()*PIPE_WIDTH, currentWorld.getHeight()*PIPE_HEIGHT);
//		//camera.setToOrtho(true, camera.viewportWidth, camera.viewportHeight);
//		camera.position.x = camera.viewportWidth/2;
//		camera.position.y = camera.viewportHeight/2;
//		camera.update();
=======
		
		view = new PipeView(world);
		view.addRenderer(horizontalPipe, new BasicPipeRenderer(regions[0][0], regions[2][0]));
		view.addRenderer(verticalPipe, new BasicPipeRenderer(regions[0][1], regions[2][1]));
		view.addRenderer(downRightLPipe, new BasicPipeRenderer(regions[0][2], regions[2][2]));
		view.addRenderer(downLeftLPipe, new BasicPipeRenderer(regions[0][3], regions[2][3]));
		view.addRenderer(upRightLPipe, new BasicPipeRenderer(regions[1][2], regions[3][2]));
		view.addRenderer(upLeftLPipe, new BasicPipeRenderer(regions[1][3], regions[3][3]));
		
		camera = new OrthographicCamera(world.getWidth()*PIPE_WIDTH, world.getHeight()*PIPE_HEIGHT);
		//camera.setToOrtho(true, camera.viewportWidth, camera.viewportHeight);
		camera.position.x = camera.viewportWidth/2;
		camera.position.y = camera.viewportHeight/2;
		camera.update();
>>>>>>> 6d137583960acb2c40b57bee8f6ba28261714aaa
		batch = new SpriteBatch();
		
		//texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	}

	@Override
	public void dispose() {
<<<<<<< HEAD
		//batch.dispose();
=======
		batch.dispose();
>>>>>>> 6d137583960acb2c40b57bee8f6ba28261714aaa
		texture.dispose();
	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		worldDelay+=Gdx.graphics.getDeltaTime();
		if(worldDelay>=WORLD_STEP_DELAY)
		{
<<<<<<< HEAD
			previousWorld.shallowSet(currentWorld);
			currentWorld.simulate(20);
			worldDelay-=WORLD_STEP_DELAY;
		}
		//view.render(worldDelay/WORLD_STEP_DELAY);
		
//		batch.setProjectionMatrix(camera.combined);
=======
			view.beforeWorldSimulation();
			world.simulate(10);
			worldDelay-=WORLD_STEP_DELAY;
		}
		
		batch.setProjectionMatrix(camera.combined);
>>>>>>> 6d137583960acb2c40b57bee8f6ba28261714aaa
		batch.begin();
		view.render(batch, worldDelay/WORLD_STEP_DELAY);
		batch.end();
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
