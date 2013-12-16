package com.clearlyspam23.LD28;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.clearlyspam23.LD28.model.Level;
import com.clearlyspam23.LD28.util.Direction;
import com.clearlyspam23.LD28.util.Location;

public class LevelsHardCode {
	
	private Texture dirtBackground;
	private Texture grassBackground;
	
	public Level[] levels;
	
	/**
	 * because im running out of time damnit
	 */
	public LevelsHardCode()
	{
//		finishHorizontalPipe 0
//		finishVerticalPipe   1
//		horizontalPipe       2
//		verticalPipe         3
//		downRightLPipe       4
//		downLeftLPipe        5
//		upRightLPipe         6
//		upLeftLPipe          7
//      downRightLeftTPipe   8
//      upDownLeftTPipe      9
//      upLeftRightTPipe     10
//      upDownRightTPipe     11
		levels = new Level[4];
		dirtBackground = new Texture(Gdx.files.internal("data/DirtBackground1.png"));
		grassBackground = new Texture(Gdx.files.internal("data/GrassBackground1.png"));
		
		TextureRegion dirt = new TextureRegion(dirtBackground);
		TextureRegion grass = new TextureRegion(grassBackground);
		
		//LEVEL 1
		levels[0] = new Level();
		levels[0].levelData = new int[][]{
				{ -1,  6,  2,  0 },
				{ -1,  3, -1, -1 },
				{ -1,  3, -1, -1 },
				{ -1,  1, -1, -1 }
		};
		levels[0].allowedPipes = new int[] {};
		levels[0].startLoc = new Location(3, 3);
		levels[0].startDirection = Direction.RIGHT;
		levels[0].endLoc = new Location(1, 0);
		levels[0].tutorialText = "Click on a Pipe to rotate it";
		levels[0].background = grass;
		levels[0].numMoves = 1;
		
		//LEVEL 2
		
		levels[1] = new Level();
		levels[1].levelData = new int[][]{
				{ -1, -1, -1, -1, -1, -1, -1, -1,  4,  0},
				{ -1, -1, -1, -1, -1, -1, -1, -1,  3, -1},
				{ -1, -1, -1, -1, -1, -1, -1, -1,  3, -1},
				{ -1, -1,  4,  5, -1, -1, -1, -1,  3, -1},
				{ -1, -1,  6,  7, -1, -1, -1, -1, -1, -1},
				{ -1, -1, -1, -1, -1, -1, -1, -1,  3, -1},
				{ -1, -1, -1, -1, -1, -1, -1, -1,  3, -1},
				{ -1, -1, -1, -1, -1, -1, -1, -1,  3, -1},
				{ -1,  4,  2,  2,  2,  2,  2,  2,  7, -1},
				{ -1,  1, -1, -1, -1, -1, -1, -1, -1, -1},
		};
		levels[1].allowedPipes = new int[] { 2, 3 };
		levels[1].startLoc = new Location(9, 9);
		levels[1].startDirection = Direction.RIGHT;
		levels[1].endLoc = new Location(1, 0);
		levels[1].tutorialText = "select and place a pipe using the side bar";
		levels[1].background = grass;
		levels[1].numMoves = 1;
		
		//LEVEL 3
		
		levels[2] = new Level();
		levels[2].levelData = new int[][]{
				{ -1, -1, -1, -1, -1,  4,  2,  2,  2,  0},
				{ -1, -1, -1, -1, -1,  3, -1, -1, -1, -1},
				{ -1, -1, -1, -1, -1,  3, -1, -1, -1, -1},
				{ -1, -1,  2,  2,  2, 10,  5, -1, -1, -1},
				{ -1,  3, -1, -1, -1, -1,  3, -1, -1, -1},
				{ -1,  3, -1, -1, -1, -1,  3, -1, -1, -1},
				{ -1,  3, -1, -1, -1, -1,  3, -1, -1, -1},
				{ -1, 11,  2,  2,  2,  2, -1, -1, -1, -1},
				{ -1,  3, -1, -1, -1, -1, -1, -1, -1, -1},
				{ -1,  1, -1, -1, -1, -1, -1, -1, -1, -1},
		};
		levels[2].allowedPipes = new int[] { 4, 5, 6, 7 };
		levels[2].startLoc = new Location(9, 9);
		levels[2].startDirection = Direction.RIGHT;
		levels[2].endLoc = new Location(1, 0);
		levels[2].tutorialText = "some levels have more than one solution";
		levels[2].background = dirt;
		levels[2].numMoves = 1;
		
		//LEVEL 4
		
		levels[3] = new Level();
		levels[3].levelData = new int[][]{
				{ -1, -1, -1, -1, -1, -1, -1,  4,  2,  0},
				{ -1, -1, -1,  4,  2,  2,  5,  3, -1, -1},
				{ -1, -1, -1,  3, -1, -1,  3,  3, -1, -1},
				{ -1,  4,  2,  7, -1, -1,  6,  7,  5, -1},
				{ -1,  3, -1, -1, -1, -1,  3, -1,  3, -1},
				{ -1,  4,  2,  2,  2,  2,  7,  3,  3, -1},
				{ -1,  3, -1, -1, -1, -1, -1,  4,  7, -1},
				{ -1,  3, -1, -1, -1, -1, -1,  3, -1, -1},
				{ -1,  9,  2,  2,  2,  2,  2,  7, -1, -1},
				{ -1,  1, -1, -1, -1, -1, -1, -1, -1, -1},
		};
		levels[3].allowedPipes = new int[] { 2, 3, 4, 5, 6, 7, 8, 9 };
		levels[3].startLoc = new Location(9, 9);
		levels[3].startDirection = Direction.RIGHT;
		levels[3].endLoc = new Location(1, 0);
		levels[3].tutorialText = "select and place a pipe using the side bar";
		levels[3].background = dirt;
		levels[3].numMoves = 1;
	}
	
	public void dispose()
	{
		dirtBackground.dispose();
		grassBackground.dispose();
	}

}
