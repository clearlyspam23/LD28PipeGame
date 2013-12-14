package com.clearlyspam23.LD28;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.clearlyspam23.LD28.model.GridWorld;
import com.clearlyspam23.LD28.model.PipeDef;
import com.clearlyspam23.LD28.util.Direction;
import com.clearlyspam23.LD28.util.Location;
import com.clearlyspam23.LD28.view.PipeView;
import com.clearlyspam23.LD28.view.renderers.BasicPipeRenderer;

public class LD28Game implements ApplicationListener {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture texture;
	
	private static final int PIPE_WIDTH = 64;
	private static final int PIPE_HEIGHT = 64;
	private static final float WORLD_STEP_DELAY = 0.25f;
	
	private float worldDelay;
	private GridWorld world;
	private PipeView view;
	
	@Override
	public void create() {		
		//Model code
		
		world = new GridWorld(3, 3);
		PipeDef horizontalPipe = createBidirectional(new Direction[] { Direction.LEFT, Direction.RIGHT } );
		PipeDef verticalPipe = createBidirectional(new Direction[] { Direction.UP, Direction.DOWN } );
		PipeDef downRightLPipe = createBidirectional( new Direction[] { Direction.DOWN, Direction.RIGHT } );
		PipeDef downLeftLPipe = createBidirectional( new Direction[] { Direction.DOWN, Direction.LEFT } );
		PipeDef upRightLPipe = createBidirectional( new Direction[] { Direction.UP, Direction.RIGHT } );
		PipeDef upLeftLPipe = createBidirectional(new Direction[] { Direction.UP, Direction.LEFT } );
		world.addNormalPipe(horizontalPipe, 2, 2);
		world.addNormalPipe(downRightLPipe, 1, 2);
		world.addNormalPipe(verticalPipe, 1, 1);
		world.addNormalPipe(upLeftLPipe, 1, 0);
		world.addNormalPipe(downRightLPipe, 0, 0);
		world.startFrom(new Location(2, 2), Direction.RIGHT);
		
		//View code
		
		texture = new Texture(Gdx.files.internal("data/Pipes.png"));
		TextureRegion[][] regions = TextureRegion.split(texture, PIPE_WIDTH, PIPE_HEIGHT);
//		for(TextureRegion[] trs : regions)
//			for(TextureRegion r : trs)
//				r.flip(false, true);
		
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
		batch = new SpriteBatch();
		
		//texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	}

	@Override
	public void dispose() {
		batch.dispose();
		texture.dispose();
	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		worldDelay+=Gdx.graphics.getDeltaTime();
		if(worldDelay>=WORLD_STEP_DELAY)
		{
			view.beforeWorldSimulation();
			world.simulate(10);
			worldDelay-=WORLD_STEP_DELAY;
		}
		
		batch.setProjectionMatrix(camera.combined);
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
