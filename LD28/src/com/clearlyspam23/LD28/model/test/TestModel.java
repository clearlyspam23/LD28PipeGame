package com.clearlyspam23.LD28.model.test;

import com.clearlyspam23.LD28.model.GridWorld;
import com.clearlyspam23.LD28.model.Pipe;
import com.clearlyspam23.LD28.model.PipeDef;
import com.clearlyspam23.LD28.util.Direction;
import com.clearlyspam23.LD28.util.Location;

public class TestModel {
	
	public static void main(String[] args)
	{
		GridWorld world = new GridWorld(3, 3);
		PipeDef horizontalPipe = createBidirectional(new Direction[] { Direction.LEFT, Direction.RIGHT } );
		PipeDef verticalPipe = createBidirectional(new Direction[] { Direction.UP, Direction.DOWN } );
		PipeDef downRightLPipe = createBidirectional( new Direction[] { Direction.DOWN, Direction.RIGHT } );
<<<<<<< HEAD
		//PipeDef downLeftLPipe = createBidirectional( new Direction[] { Direction.DOWN, Direction.LEFT } );
		//PipeDef upRightLPipe = createBidirectional( new Direction[] { Direction.UP, Direction.RIGHT } );
		PipeDef upLeftLPipe = createBidirectional(new Direction[] { Direction.UP, Direction.LEFT } );
		world.addNormalPipe(horizontalPipe, 2, 2);
		world.addNormalPipe(downRightLPipe, 1, 2);
		world.addNormalPipe(verticalPipe, 1, 1);
		world.addNormalPipe(upLeftLPipe, 1, 0);
		world.addNormalPipe(downRightLPipe, 0, 0);
		world.addStart(new Location(2, 2), Direction.RIGHT);
		world.addFinish(new Location(0, 0));
		GridWorld lastWorld = new GridWorld(world);
		while(!world.hasLost()&&!world.hasWon())
		{
			lastWorld.shallowSet(world);
			world.simulate(10);
			System.out.println("currently filling pipes:");
			for(Pipe p : lastWorld.getCurrentPipes())
=======
		PipeDef downLeftLPipe = createBidirectional( new Direction[] { Direction.DOWN, Direction.LEFT } );
		PipeDef upRightLPipe = createBidirectional( new Direction[] { Direction.UP, Direction.RIGHT } );
		PipeDef upLeftLPipe = createBidirectional(new Direction[] { Direction.UP, Direction.LEFT } );
		world.addNormalPipe(horizontalPipe, 2, 0);
		world.addNormalPipe(downRightLPipe, 1, 0);
		world.addNormalPipe(verticalPipe, 1, 1);
		world.addNormalPipe(upLeftLPipe, 1, 2);
		world.addNormalPipe(downRightLPipe, 0, 2);
		world.startFrom(new Location(2, 0), Direction.RIGHT);
		while(!world.hasLost())
		{
			world.simulate(10);
			System.out.println("currently filling pipes:");
			for(Pipe p : world.getLastPipes())
>>>>>>> 6d137583960acb2c40b57bee8f6ba28261714aaa
			{
				System.out.println("\t Location: " + p.getLocation() + " - " + p.getFill() + " percent filled");
			}
		}
		System.out.println("finished");
<<<<<<< HEAD
		if(world.hasWon())
			System.out.println("you won!");
		else
			System.out.println("you lost!");
=======
>>>>>>> 6d137583960acb2c40b57bee8f6ba28261714aaa
	}
	
	public static PipeDef createBidirectional(Direction[] dir)
	{
		PipeDef p = new PipeDef();
		p.inputs = p.outputs = dir;
		return p;
	}

}
