package org.ns.marsrover;

import java.awt.Point;
import java.util.HashSet;

public class Plateau {
	Point plateu_bounds;
	HashSet<Integer> rover_pos_hashes;
	
	Plateau (Point boundPoint_)
	{
		plateu_bounds = boundPoint_;
		rover_pos_hashes = new HashSet<Integer>();
	}
	
	boolean isValidPos (Point newPos)
	{
		if ((newPos.x > plateu_bounds.x) || (newPos.y > plateu_bounds.y))
			return false;
		else
			return true;
	}
	
	boolean setRoverPosition (Point new_pos_, Point old_pos_)
	{
		Integer rover_hash = RoverHash (new_pos_);
		if (rover_pos_hashes.contains(rover_hash))
		{
			return false;
		}
		else
		{
			rover_pos_hashes.remove(RoverHash(old_pos_));
			rover_pos_hashes.add(rover_hash);
			return true;
		}
	}
	
	Integer RoverHash (Point pos_)
	{
		return pos_.hashCode();
	}
}
