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
			{
				System.out.println("\t Location: " + p.getLocation() + " - " + p.getFill() + " percent filled");
			}
		}
		System.out.println("finished");
	}
	
	public static PipeDef createBidirectional(Direction[] dir)
	{
		PipeDef p = new PipeDef();
		p.inputs = p.outputs = dir;
		return p;
	}

}
