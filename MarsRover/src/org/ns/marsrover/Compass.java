package org.ns.marsrover;

public class Compass {
	Facing direction_lookup[][];
	Compass()
	{
		direction_lookup = new Facing[Facing.values().length][Rotation.values().length];
		
		direction_lookup[Facing.NORTH.ordinal()][Rotation.LEFT.ordinal()] = Facing.WEST;
		direction_lookup[Facing.EAST.ordinal()][Rotation.LEFT.ordinal()] = Facing.NORTH;
		direction_lookup[Facing.SOUTH.ordinal()][Rotation.LEFT.ordinal()] = Facing.EAST;
		direction_lookup[Facing.WEST.ordinal()][Rotation.LEFT.ordinal()] = Facing.SOUTH;
		
		direction_lookup[Facing.NORTH.ordinal()][Rotation.RIGHT.ordinal()] = Facing.EAST;
		direction_lookup[Facing.EAST.ordinal()][Rotation.RIGHT.ordinal()] = Facing.SOUTH;
		direction_lookup[Facing.SOUTH.ordinal()][Rotation.RIGHT.ordinal()] = Facing.WEST;
		direction_lookup[Facing.WEST.ordinal()][Rotation.RIGHT.ordinal()] = Facing.NORTH;
	}
	Facing getNewFacing (Facing current_facing, Rotation rotate)
	{
		Facing new_facing = direction_lookup[current_facing.ordinal()][rotate.ordinal()];
		return new_facing;
	}
}
