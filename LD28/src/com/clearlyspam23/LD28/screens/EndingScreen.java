package com.clearlyspam23.LD28.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.clearlyspam23.LD28.LD28Game;

public class EndingScreen extends AbstractScreen{
	
	public static final String WIN_TEXT = "Congratulations! You Won!";
	public static final String THANKS_TEXT = "Thanks for playing!";
	
	public TextureRegion background;
	private OrthographicCamera camera;
	private BitmapFont font;
	
	private float alphaDelay;
	
	private Vector2 victoryOffset;
	
	private Vector2 pressAnyOffset;

	public EndingScreen(LD28Game g, SpriteBatch b, TextureRegion background, BitmapFont font) {
		super(g, b);
		this.background = background;
		this.font = font;
	}

	@Override
	public void render(float delta) {
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(background, 0, 0, camera.viewportWidth, camera.viewportHeight);
		alphaDelay+=Gdx.graphics.getDeltaTime();
		Color c = font.getColor();
		font.setColor(1, 1, 1, MathUtils.clamp(alphaDelay, 0, 1));
		font.draw(batch, WIN_TEXT, victoryOffset.x, victoryOffset.y);
		font.setColor(1, 1, 1, MathUtils.clamp(alphaDelay-1, 0, 1));
		font.draw(batch, THANKS_TEXT, pressAnyOffset.x, pressAnyOffset.y);
		font.setColor(c);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.x = camera.viewportWidth/2;
		camera.position.y = camera.viewportHeight/2;
		camera.update();
		int hwidth = Gdx.graphics.getWidth()/2;
		int hheight = Gdx.graphics.getHeight()/2;
		TextBounds b = font.getBounds(WIN_TEXT);
		victoryOffset = new Vector2(hwidth-b.width/2, hheight*5/4-b.height/2);
		b = font.getBounds(THANKS_TEXT);
		pressAnyOffset = new Vector2(hwidth-b.width/2, hheight*3/4-b.height/2);
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}

}
